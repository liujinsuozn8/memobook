@InterfaceAudience.Public
@InterfaceStability.Evolving
public class Cluster {
  // 5.2.1 从 `Job.java` 的 `connect` 跳转到此处
  public Cluster(Configuration conf) throws IOException {
    this(null, conf);  // 5.2.2 调用重载的构造器
  }

  public Cluster(InetSocketAddress jobTrackAddr, Configuration conf) 
      throws IOException {
    // 5.2.3 获取到的配置对象中是 hadoop 配置文件的信息
    // Configuration: core-default.xml, core-site.xml, mapred-default.xml, mapred-site.xml, yarn-default.xml, yarn-site.xml, hdfs-default.xml, hdfs-site.xml
    this.conf = conf;
    this.ugi = UserGroupInformation.getCurrentUser();
    // 5.2.4 对集群进行初始化
    initialize(jobTrackAddr, conf);
  }

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


}