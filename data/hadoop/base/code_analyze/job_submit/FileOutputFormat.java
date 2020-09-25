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