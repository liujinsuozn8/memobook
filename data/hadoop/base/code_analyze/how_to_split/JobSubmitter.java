@InterfaceAudience.Private
@InterfaceStability.Unstable
class JobSubmitter {
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

  // 1. 创建切片，并将切片信息写入提交路径
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

}