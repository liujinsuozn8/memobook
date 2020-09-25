@InterfaceAudience.Public
@InterfaceStability.Stable
public class Configuration implements Iterable<Map.Entry<String,String>>,
                                      Writable {
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
}