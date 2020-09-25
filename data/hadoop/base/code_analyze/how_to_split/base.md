<span id="catalog"></span>

<span style='font-size:18px'>目录---FileInputFormat切片流程分析</span>

- [参考代码](#参考代码)
- [提交代码时执行切片---JobSubmitter](#提交代码时执行切片---JobSubmitter)
- [获取切片信息---FileInputFormat](#获取切片信息---FileInputFormat)
- [获取最大切片数的逻辑处理---Configuration](#获取最大切片数的逻辑处理---Configuration)
- [](#)

# 参考代码
[top](#catalog)
- [JobSubmitter.java](JobSubmitter.java)
- [FileInputFormat.java](FileInputFormat.java)
- [Configuration.java](Configuration.java)

# 提交代码时执行切片---JobSubmitter
[top](#catalog)
- 参考
    - [JobSubmitter.java](JobSubmitter.java)
- 代码分析
    1. 提交 job，并创建切片
        ```java
        JobStatus submitJobInternal(Job job, Cluster cluster) 
          throws ClassNotFoundException, InterruptedException, IOException {
          // ...
          try {
            // 提交文件信息（拷贝job信息及jar包）
            copyAndConfigureFiles(job, submitJobDir);
            // ...

            // 1. 创建切片，并将切片信息写入提交路径
            int maps = writeSplits(job, submitJobDir);
            //...

            // 将job文件写入提交目录
            writeConf(conf, submitJobFile);
            // ...

            // 提交 job
            status = submitClient.submitJob(
                jobId, submitJobDir.toString(), job.getCredentials());
            // ...
          }
        }
        ```
    2. 创建切片，并将切片信息写入提交路径
        ```java
        private int writeSplits(org.apache.hadoop.mapreduce.JobContext job,
            Path jobSubmitDir) throws IOException,
            InterruptedException, ClassNotFoundException {
          JobConf jConf = (JobConf)job.getConfiguration();
          int maps;
          if (jConf.getUseNewMapper()) {
            // 2. 创建并写入切片
            maps = writeNewSplits(job, jobSubmitDir);
          } else {
            maps = writeOldSplits(jConf, jobSubmitDir);
          }
          return maps;
        }
        ```
    3. 调用 `InputFormat` 获取切片信息
        ```java
        private <T extends InputSplit> int writeNewSplits(JobContext job, Path jobSubmitDir) throws IOException,
            InterruptedException, ClassNotFoundException {
          Configuration conf = job.getConfiguration();
          InputFormat<?, ?> input =
            ReflectionUtils.newInstance(job.getInputFormatClass(), conf);

          // 3. 获取切片信息，参考: `FileInputFormat.java`
          List<InputSplit> splits = input.getSplits(job);
          T[] array = (T[]) splits.toArray(new InputSplit[splits.size()]);

          // sort the splits into order based on size, so that the biggest
          // go first
          Arrays.sort(array, new SplitComparator());
          JobSplitWriter.createSplitFiles(jobSubmitDir, conf,
              jobSubmitDir.getFileSystem(conf), array);
          return array.length;
        }
        ```

# 获取切片信息---FileInputFormat
[top](#catalog)
- 参考
    - [FileInputFormat.java](FileInputFormat.java)
- 代码分析
    1. 获取切片信息
        ```java
        public List<InputSplit> getSplits(JobContext job) throws IOException {
          StopWatch sw = new StopWatch().start();

          // 4. 获取切片数
          // 4.1 最小切片数: 1
          long minSize = Math.max(getFormatMinSplitSize(), getMinSplitSize(job));
          // 4.2 最大切片数
          long maxSize = getMaxSplitSize(job);

          // generate splits
          List<InputSplit> splits = new ArrayList<InputSplit>();
          List<FileStatus> files = listStatus(job);

          // 4.3 遍历提交的所有文件，对每一个文件尝试切片
          for (FileStatus file: files) {
            Path path = file.getPath();

            // 5. 获取文件大小
            long length = file.getLen();
            if (length != 0) {
              BlockLocation[] blkLocations;
              if (file instanceof LocatedFileStatus) {
                blkLocations = ((LocatedFileStatus) file).getBlockLocations();
              } else {
                FileSystem fs = path.getFileSystem(job.getConfiguration());
                blkLocations = fs.getFileBlockLocations(file, 0, length);
              }
              // 6. 判断文件是否可分割
              if (isSplitable(job, path)) {
                // 7. 获取块的大小: 本地运行为 32M， yarn 运行为 128M
                long blockSize = file.getBlockSize();
                // 8. 根据块大小，计算文件每个切片的大小
                long splitSize = computeSplitSize(blockSize, minSize, maxSize);

                long bytesRemaining = length;

                // SPLIT_SLOP = 1.1
                // 如果: `文件的剩余容量 / 数据块大小 > 1.1` 时， 添加新的切片
                // bytesRemaining 初始为文件的大小
                while (((double) bytesRemaining)/splitSize > SPLIT_SLOP) {
                  int blkIndex = getBlockIndex(blkLocations, length-bytesRemaining);
                  splits.add(makeSplit(path, length-bytesRemaining, splitSize,
                              blkLocations[blkIndex].getHosts(),
                              blkLocations[blkIndex].getCachedHosts()));
                  // 计算文件的剩余大小
                  bytesRemaining -= splitSize;
                }

                if (bytesRemaining != 0) {
                  int blkIndex = getBlockIndex(blkLocations, length-bytesRemaining);
                  splits.add(makeSplit(path, length-bytesRemaining, bytesRemaining,
                             blkLocations[blkIndex].getHosts(),
                             blkLocations[blkIndex].getCachedHosts()));
                }
              } else {
                // 不切片
                splits.add(makeSplit(path, 0, length, blkLocations[0].getHosts(),
                            blkLocations[0].getCachedHosts()));
              }
            } else {
              //Create empty hosts array for zero length files
              splits.add(makeSplit(path, 0, length, new String[0]));
            }
          }
          // Save the number of input files for metrics/loadgen
          job.getConfiguration().setLong(NUM_INPUT_FILES, files.size());
          sw.stop();
          if (LOG.isDebugEnabled()) {
            LOG.debug("Total # of splits generated by getSplits: " + splits.size()
                + ", TimeTaken: " + sw.now(TimeUnit.MILLISECONDS));
          }
          return splits;
        }
        ```
    2. 获取最大切片数
        ```java
        public static long getMaxSplitSize(JobContext context) {
          // 如果设置了: mapreduce.input.fileinputformat.split.maxsize，则使用该属性作为最大切片数
          // 如果没有设置，则使用: Long.MAX_VALUE = 923372036854775807
          return context.getConfiguration().getLong(SPLIT_MAXSIZE, Long.MAX_VALUE);
        }
        ```
    3. 根据块大小，计算文件分片数量
        ```java
        protected long computeSplitSize(long blockSize, long minSize, long maxSize) {
          // 返回 `minSize < [blockSize] < maxSize` 比较过程的中间值
          return Math.max(minSize, Math.min(maxSize, blockSize));
        }
        ```

# 获取最大切片数的逻辑处理---Configuration
[top](#catalog)
- 参考
    - [Configuration.java](Configuration.java)
- 代码分析
    ```java
    public long getLong(String name, long defaultValue) {
      // 获取数值字符串
      String valueString = getTrimmed(name);
      // 如果没有获取到，则返回默认值
      if (valueString == null)
        return defaultValue;
      // 检查数值字符串是不是 16 进制
      String hexString = getHexDigits(valueString);

      if (hexString != null) {
        // 如果是 16 进制，将16进制字符串解析为 10 进制 Long 型数字
        return Long.parseLong(hexString, 16);
      }
      // 如果不是 16 进制，则按照10进制解析，并返回
      return Long.parseLong(valueString);
    }
    ```

[top](#catalog)
