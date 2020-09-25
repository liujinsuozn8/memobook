<span id="catalog"></span>

<span style='font-size:18px'>目录---job提交</span>

- [参考代码](#参考代码)
- [job提交的整体控制---Job](#job提交的整体控制---Job)
- [连接job的运行环境---Cluster](#连接job的运行环境---Cluster)
- [执行job---JobSubmitter](#执行job---JobSubmitter)
- [校验输出目录---FileOutputFormat](#校验输出目录---FileOutputFormat)
- [](#)

# 参考代码
[top](#catalog)
- [Job.java](Job.java)
- [Cluster.java](Cluster.java)
- [JobSubmitter.java](JobSubmitter.java)
- [FileOutputFormat.java](FileOutputFormat.java)

# job提交的整体控制---Job
[top](#catalog)
- 整体控制流程
    1. 调用提交方法
    2. 检查是否输出执行日志
    3. 判断job的状态
        - 如果正在运行并且运行环境为空，说明出现了幽灵情况
        - 如果用于检查的job状态对象与对象内部的状态对象不同，说明job可能被替换了
    4. 设置 hadoop 的api
    5. 连接 job 的运行环境
    6. 提交job，并执行job
    7. 将job的状态设置为 `RUNNING`

- 调试断点位置
    - 从 driver 类中的 `job.waitForCompletion(true)`  job 跳转到 `Job` 下的方法中

- 参考
    - [Job.java](Job.java)
- 代码分析
    1. 整体控制
        ```java
        public boolean waitForCompletion(boolean verbose
                                         ) throws IOException, InterruptedException,
                                                  ClassNotFoundException {
          if (state == JobState.DEFINE) {
            submit(); // 1. 提交
          }
          if (verbose) {  // 2. 是否输出信息
            monitorAndPrintJob();
          } else {
            // get the completion poll interval from the client.
            int completionPollIntervalMillis =
              Job.getCompletionPollInterval(cluster.getConf());
            while (!isComplete()) {
              try {
                Thread.sleep(completionPollIntervalMillis);
              } catch (InterruptedException ie) {
              }
            }
          }
          return isSuccessful();
        }
        ```
        ```java
        // 调用提交方法
        public void submit()
               throws IOException, InterruptedException, ClassNotFoundException {
          ensureState(JobState.DEFINE);   // 3. 判断job的状态
          setUseNewAPI();                 // 4. 使用hadoop的新api
          connect();                      // 5. 网络连接，连接运行环境
          final JobSubmitter submitter =
              getJobSubmitter(cluster.getFileSystem(), cluster.getClient());
          status = ugi.doAs(new PrivilegedExceptionAction<JobStatus>() {
            public JobStatus run() throws IOException, InterruptedException,
            ClassNotFoundException {
              return submitter.submitJobInternal(Job.this, cluster);  // 6. 提交 job 的详细信息，参考: `JobSubmitter.java`
            }
          });

          // 7. 将job的状态设置为 RUNNING
          state = JobState.RUNNING;
          LOG.info("The url to track the job: " + getTrackingURL());
        }
        ```
    2. 判断job的状态
        ```java
        private void ensureState(JobState state) throws IllegalStateException {
          if (state != this.state) {
            throw new IllegalStateException("Job in state "+ this.state +
                                            " instead of " + state);
          }

          // 如果 job 处于正在运行的状态，但是集群对象 cluster 为空，说明出现了幽灵情况
          if (state == JobState.RUNNING && cluster == null) {
            throw new IllegalStateException
              ("Job in state " + this.state
               + ", but it isn't attached to any job tracker!");
          }
        }
        ```
    3. 连接 job 的运行环境
        ```java
        private synchronized void connect()
                throws IOException, InterruptedException, ClassNotFoundException {
          // 5.1 如果没有集群对象则创建，
          if (cluster == null) {
            cluster =
              ugi.doAs(new PrivilegedExceptionAction<Cluster>() {
                         public Cluster run()
                                throws IOException, InterruptedException,
                                       ClassNotFoundException {
                           return new Cluster(getConfiguration());    //  5.2 参考: `Cluster.java`
                         }
                       });
          }
        }
        ```

# 连接job的运行环境---Cluster
[top](#catalog)
- 参考
    - [Cluster.java](Cluster.java)
- 代码分析
    1. 调用重载方法，尝试连接运行环境
        ```java
        @InterfaceAudience.Public
        @InterfaceStability.Evolving
        public class Cluster {
          // 5.2.1 从 `Job.java` 的 `connect` 跳转到此处
          public Cluster(Configuration conf) throws IOException {
            this(null, conf);  // 5.2.2 调用重载的构造器
          }
        }
        ```

    2. 获取 hadoop 的所有配置文件信息，并开始初始化环境
        ```java
        public Cluster(InetSocketAddress jobTrackAddr, Configuration conf)
            throws IOException {
          // 5.2.3 获取到的配置对象中是 hadoop 配置文件的信息
          // Configuration: core-default.xml, core-site.xml, mapred-default.xml, mapred-site.xml, yarn-default.xml, yarn-site.xml, hdfs-default.xml, hdfs-site.xml
          this.conf = conf;
          this.ugi = UserGroupInformation.getCurrentUser();
          // 5.2.4 对集群进行初始化
          initialize(jobTrackAddr, conf);
        }
        ```
    3. 初始化环境: 集群 或 本地环境
        ```java
        // 5.2.5 对集群进行初始化， 主要用于判断是在 本地运行 还是 yarn 上运行，并根据运行的方式来初始化
        private void initialize(InetSocketAddress jobTrackAddr, Configuration conf)
            throws IOException {

          synchronized (frameworkLoader) {
            for (ClientProtocolProvider provider : frameworkLoader) {
              LOG.debug("Trying ClientProtocolProvider : "
                  + provider.getClass().getName());
              ClientProtocol clientProtocol = null;
              try {
                // 5.2.6 判断是在 本地运行 还是 yarn 上运行

                if (jobTrackAddr == null) {
                  // 5.2.7 本地运行，将会创建一个：`LocalJobRunner` 类
                  clientProtocol = provider.create(conf);
                } else {
                  // 5.2.8 在 yarn 上运行
                  clientProtocol = provider.create(jobTrackAddr, conf);
                }

                if (clientProtocol != null) {
                  clientProtocolProvider = provider;
                  client = clientProtocol;
                  LOG.debug("Picked " + provider.getClass().getName()
                      + " as the ClientProtocolProvider");
                  break;
                }
                else {
                  LOG.debug("Cannot pick " + provider.getClass().getName()
                      + " as the ClientProtocolProvider - returned null protocol");
                }
              }
              catch (Exception e) {
                LOG.info("Failed to use " + provider.getClass().getName()
                    + " due to error: ", e);
              }
            }
          }

          if (null == clientProtocolProvider || null == client) {
            throw new IOException(
                "Cannot initialize Cluster. Please check your configuration for "
                    + MRConfig.FRAMEWORK_NAME
                    + " and the correspond server addresses.");
          }
        }
        ```

# 执行job---JobSubmitter
[top](#catalog)
- 执行job的整体流程
    1. 使用 FileOutputFormat 校验输出目录是否存在
    2. 在本地创建**临时**提交目录
        - `file:/tmp/hadoop-Administrator/mapred/staging/AdministratorXXXXXXX`
    3. 创建 `Job_ID`
    4. 在提交路径下用 `Job_ID`，为**当前 job 创建目录**
        - `file:/tmp/hadoop-Administrator/mapred/staging/AdministratorXXXXXXX/.staging/<job_id>`
    5. 提交文件信息
        - 拷贝job信息及jar包
    6. 将切片信息写入提交路径
    7. 将配置: `job.xml` 写入提交目录
        - 所有的相关配置都会导入到 `job.xml` 中
    8. 提交 job 运行
    9. 删除提交目录

- 两次在本地创建目录
    1. 创建一个临时提交目录
    2. 在临时提交目录下，专门为当前 job 用 job_id 创建一个目录

- 参考
    - [JobSubmitter.java](JobSubmitter.java)

- 代码分析
    1. 校验输出路径
        ```java
        // 6.1 校验输出路径
        private void checkSpecs(Job job) throws ClassNotFoundException,
            InterruptedException, IOException {
          JobConf jConf = (JobConf)job.getConfiguration();
          // Check the output specification
          if (jConf.getNumReduceTasks() == 0 ?
              jConf.getUseNewMapper() : jConf.getUseNewReducer()) {
            org.apache.hadoop.mapreduce.OutputFormat<?, ?> output =
              ReflectionUtils.newInstance(job.getOutputFormatClass(),
                job.getConfiguration());
            output.checkOutputSpecs(job);  // 6.1.1 校验输出路径，参考：`FileOutputFormat.java`
          } else {
            jConf.getOutputFormat().checkOutputSpecs(jtFs, jConf);
          }
        }
        ```
    2. <span style='color:red'>提交任务的整体控制流程</span>
        ```java
        JobStatus submitJobInternal(Job job, Cluster cluster)
        throws ClassNotFoundException, InterruptedException, IOException {

          //validate the jobs output specs
          // 6.1 校验输出路径
          checkSpecs(job);

          Configuration conf = job.getConfiguration();
          addMRFrameworkToDistributedCache(conf);

          // 6.2 创建提交目录: file:/tmp/hadoop-Administrator/mapred/staging/AdministratorXXXXXXX
          // `/tmp/hadoop-Administrator` 目录位于 HADOOP_HOME 的同级目录下
          Path jobStagingArea = JobSubmissionFiles.getStagingDir(cluster, conf);

          // 6.3 创建 Job ID
          //configure the command line options correctly on the submitting dfs
          InetAddress ip = InetAddress.getLocalHost();
          if (ip != null) {
            submitHostAddress = ip.getHostAddress();
            submitHostName = ip.getHostName();
            conf.set(MRJobConfig.JOB_SUBMITHOST,submitHostName);
            conf.set(MRJobConfig.JOB_SUBMITHOSTADDR,submitHostAddress);
          }
          JobID jobId = submitClient.getNewJobID();
          job.setJobID(jobId);

          // 6.4 在提交路径下用 job_id，为当前 job 创建目录:
          // file:/tmp/hadoop-Administrator/mapred/staging/AdministratorXXXXXXX/.staging/<job_id>
          Path submitJobDir = new Path(jobStagingArea, jobId.toString());
          JobStatus status = null;
          try {
            conf.set(MRJobConfig.USER_NAME,
                UserGroupInformation.getCurrentUser().getShortUserName());
            conf.set("hadoop.http.filter.initializers",
                "org.apache.hadoop.yarn.server.webproxy.amfilter.AmFilterInitializer");
            conf.set(MRJobConfig.MAPREDUCE_JOB_DIR, submitJobDir.toString());
            LOG.debug("Configuring job " + jobId + " with " + submitJobDir
                + " as the submit dir");
            // get delegation token for the dir
            TokenCache.obtainTokensForNamenodes(job.getCredentials(),
                new Path[] { submitJobDir }, conf);

            populateTokenCache(conf, job.getCredentials());

            // generate a secret to authenticate shuffle transfers
            if (TokenCache.getShuffleSecretKey(job.getCredentials()) == null) {
              KeyGenerator keyGen;
              try {
                keyGen = KeyGenerator.getInstance(SHUFFLE_KEYGEN_ALGORITHM);
                keyGen.init(SHUFFLE_KEY_LENGTH);
              } catch (NoSuchAlgorithmException e) {
                throw new IOException("Error generating shuffle secret key", e);
              }
              SecretKey shuffleKey = keyGen.generateKey();
              TokenCache.setShuffleSecretKey(shuffleKey.getEncoded(),
                  job.getCredentials());
            }
            if (CryptoUtils.isEncryptedSpillEnabled(conf)) {
              conf.setInt(MRJobConfig.MR_AM_MAX_ATTEMPTS, 1);
              LOG.warn("Max job attempts set to 1 since encrypted intermediate" +
                      "data spill is enabled");
            }

            // 6.5 提交文件信息（拷贝job信息及jar包）
            copyAndConfigureFiles(job, submitJobDir);

            Path submitJobFile = JobSubmissionFiles.getJobConfPath(submitJobDir);

            // Create the splits for the job
            LOG.debug("Creating splits at " + jtFs.makeQualified(submitJobDir));

            // 6.6 将切片信息写入提交路径
            // 将会在提交目录下生成 4 个文件
            // .job.split.crc
            // .job.splitmetainfo.crc
            // job.split  <<<<<<<<< 记录切片信息，解析内容参考: `job.split`
            // job.splitmetainfo
            int maps = writeSplits(job, submitJobDir);
            conf.setInt(MRJobConfig.NUM_MAPS, maps);
            LOG.info("number of splits:" + maps);

            // write "queue admins of the queue to which job is being submitted"
            // to job file.
            String queue = conf.get(MRJobConfig.QUEUE_NAME,
                JobConf.DEFAULT_QUEUE_NAME);
            AccessControlList acl = submitClient.getQueueAdmins(queue);
            conf.set(toFullPropertyName(queue,
                QueueACL.ADMINISTER_JOBS.getAclName()), acl.getAclString());

            // removing jobtoken referrals before copying the jobconf to HDFS
            // as the tasks don't need this setting, actually they may break
            // because of it if present as the referral will point to a
            // different job.
            TokenCache.cleanUpTokenReferral(conf);

            if (conf.getBoolean(
                MRJobConfig.JOB_TOKEN_TRACKING_IDS_ENABLED,
                MRJobConfig.DEFAULT_JOB_TOKEN_TRACKING_IDS_ENABLED)) {
              // Add HDFS tracking ids
              ArrayList<String> trackingIds = new ArrayList<String>();
              for (Token<? extends TokenIdentifier> t :
                  job.getCredentials().getAllTokens()) {
                trackingIds.add(t.decodeIdentifier().getTrackingId());
              }
              conf.setStrings(MRJobConfig.JOB_TOKEN_TRACKING_IDS,
                  trackingIds.toArray(new String[trackingIds.size()]));
            }

            // Set reservation info if it exists
            ReservationId reservationId = job.getReservationId();
            if (reservationId != null) {
              conf.set(MRJobConfig.RESERVATION_ID, reservationId.toString());
            }

            // 6.7 将job文件写入提交目录
            /*
              执行后，在提交目录下会生成两个文件
               .job.xml.crc
               job.xml        <<<<< 保存了所有相关的配置，参考: `job.xml`
            */
            // Write job file to submit dir
            writeConf(conf, submitJobFile);

            // 6.8.1  实际的提交 job
            printTokens(jobId, job.getCredentials());

            // 6.8.2  提交 job
            status = submitClient.submitJob(
                jobId, submitJobDir.toString(), job.getCredentials());
            if (status != null) {
              return status;
            } else {
              throw new IOException("Could not launch job");
            }
          } finally {
            if (status == null) {
              LOG.info("Cleaning up the staging area " + submitJobDir);
              // 6.9 删除提交目录
              if (jtFs != null && submitJobDir != null)
                jtFs.delete(submitJobDir, true);
            }
          }
        }
        ```
    3. 提交文件信息
        ```java
        // 6.5 提交文件信息
        private void copyAndConfigureFiles(Job job, Path jobSubmitDir)
        throws IOException {
          // 6.5.1 创建 uploader 提交 job 提交信息 到 提交目录的 job_id 目录下:
          // file:/tmp/hadoop-Administrator/mapred/staging/AdministratorXXXXXXX/.staging/<job_id>
          // 会提交到本地目录
          JobResourceUploader rUploader = new JobResourceUploader(jtFs);
          rUploader.uploadFiles(job, jobSubmitDir);

          // Get the working directory. If not set, sets it to filesystem working dir
          // This code has been added so that working directory reset before running
          // the job. This is necessary for backward compatibility as other systems
          // might use the public API JobConf#setWorkingDirectory to reset the working
          // directory.
          job.getWorkingDirectory();
        }
        ```
    4. 将job文件写入提交目录
        ```java
        // 6.7 将job文件写入提交目录
        private void writeConf(Configuration conf, Path jobFile)
            throws IOException {
          // Write job file to JobTracker's fs
          // 6.7.1 创建输出流
          FSDataOutputStream out =
            FileSystem.create(jtFs, jobFile,
                              new FsPermission(JobSubmissionFiles.JOB_FILE_PERMISSION));
          // 6.7.2 将配置文件输出到提交目录，参考: `job.xml`
          try {
            conf.writeXml(out);
          } finally {
            out.close();
          }
        }
        ```

# 校验输出目录---FileOutputFormat
[top](#catalog)
- 参考
    - [FileOutputFormat.java](FileOutputFormat.java)
- 代码分析
    ```java
    @InterfaceAudience.Public
    @InterfaceStability.Stable
    public abstract class FileOutputFormat<K, V> extends OutputFormat<K, V> {
      public void checkOutputSpecs(JobContext job
                                   ) throws FileAlreadyExistsException, IOException{
        Path outDir = getOutputPath(job);
        // 6.1.2 检查输出路径是否已设置
        if (outDir == null) {
          throw new InvalidJobConfException("Output directory not set.");
        }

        TokenCache.obtainTokensForNamenodes(job.getCredentials(),
            new Path[] { outDir }, job.getConfiguration());

        // 6.1.3 输出路径必须不存在，否则抛出异常
        if (outDir.getFileSystem(job.getConfiguration()).exists(outDir)) {
          throw new FileAlreadyExistsException("Output directory " + outDir +
                                               " already exists");
        }
      }
    }
    ```

# 其他
[top](#catalog)
- 提交过程中的生成结果
  - [job.xml](job.xml)
  - 生成的切片信息: [job.split](job.split)


[top](#catalog)
