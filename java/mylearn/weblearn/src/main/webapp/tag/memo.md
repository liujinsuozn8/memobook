- 自定义标签测试
- simpleTag.jsp，使用jstl核型标签库，循环打印List
    - http://localhost:8080/weblearn_war_exploded/tag/simpletag.jsp
- 自定义标签的tld文件保存在：webapp/WEB-INF/mytag.tld
- hellotag.jsp
    - 测试自定义标签：`hello`
    - 入口：http://localhost:8080/weblearn_war_exploded/tag/hellotag.jsp
    - 附加请求参数的入口
        - 入口：http://localhost:8080/weblearn_war_exploded/tag/hellotag.jsp?name=testUser
- showtag.jsp
    - 测试带属性的标签：show
    - 入口：http://localhost:8080/weblearn_war_exploded/tag/showtag.jsp
- maxtag.jsp
    - 测试最大值标签：max
    - 入口：http://localhost:8080/weblearn_war_exploded/tag/maxtag.jsp
    
- readFiletag.jsp
    - 测试文件读取标签
    - 入口：http://localhost:8080/weblearn_war_exploded/tag/readFiletag.jsp
    
- printUpper.jsp
    - 标签体测试
    - 入口：http://localhost:8080/weblearn_war_exploded/tag/printUpper.jsp
    
- foreach.jsp
    - 循环打印集合自定义标签测试
    - 入口：http://localhost:8080/weblearn_war_exploded/tag/foreach.jsp
    
- choosetag.jsp
    - 父子标签：choose, when otherwise
    - 需要附加请求参数：score
    - 入口：
        - http://localhost:8080/weblearn_war_exploded/tag/choosetag.jsp?score=55
        - http://localhost:8080/weblearn_war_exploded/tag/choosetag.jsp?score=66
        - http://localhost:8080/weblearn_war_exploded/tag/choosetag.jsp?score=22
        - http://localhost:8080/weblearn_war_exploded/tag/choosetag.jsp?score=9