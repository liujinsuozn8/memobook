* 查找两个字符串中的最大相同字串(只考虑存在一个)
	```java
	public static String getMaxString(String str1, String str2){
	    if (str1 != null && str2 != null){
    		String maxStr = null;
    		String minStr = null;
    		if (str1.length() >= str1.length()){
    		    maxStr = str1;
    		    minStr = str2;
    		} else {
    		    maxStr = str2;
    		    minStr = str1;
    		}
    		// 外层循环次数：删除0个字符~删除长度-100个字符
    		int minLength = minStr.length();
    		String temp = null;
    		for(int i=0; i<minLength; i++){
				// 当前字串检查完后，整体向右移动1个字符
    			for (int x=0, y = minLength - i; y <= minLength; x++, y++){
    				temp = minStr.substring(x, y);
    				if (maxStr.contains(temp)){
    					return temp;
    				}
    			}
    		}
	    }	
		return null;
	}
	
	@Test
	public test(){
		String a = getMaxString("aaaaacbdsdfer", "qqqqacbdeeee");
        System.out.println(a); // acbd
	}
	```
* 统计一个字符串在另一个字符串中出现的次数
	```java
	public static int getCount(String str, String sub){
		int subLength = sub.length();
		int strLength = str.length();
		int startIndex = 0;
		int count = 0;
		// 从startIndex开始子字符串是否存在(如果不存在，则结束检索)
		while ((startIndex = str.indexOf(sub, startIndex)) != -1){
			count++;
			//找到一次后,在当前索引基础上向后移动sub的长度,得到新的起始位置
			startIndex += subLength;
		}
		return count;
	}
	
	@Test
	public test(){
		String str = "abcfffabsdfabhfgaba"; // ab = 4
		String sub = "ab";
		int result = findCount(str, sub)
	}
	```
