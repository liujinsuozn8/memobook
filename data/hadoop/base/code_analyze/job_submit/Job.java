@InterfaceAudience.Public
@InterfaceStability.Evolving
public class Job extends JobContextImpl implements JobContext {
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

  public void submit() 
         throws IOException, InterruptedException, ClassNotFoundException {
    ensureState(JobState.DEFINE);   // 3. 判断job的状态
    setUseNewAPI();                 // 4. 使用hadoop的新api
    connect();                      // 5. 网络连接
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

  // 3. 判断job的状态
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
}