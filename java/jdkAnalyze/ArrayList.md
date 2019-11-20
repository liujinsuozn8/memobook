* Collection
## ArrayList
* ArrayList概述
    * 基于数组实现
    * 支持自动扩容的动态数组
* 继承与实现关系
    * 实现的四个接口
        * `java.util.List`:提供数组的添加、删除、修改、迭代遍历 等操作
        * `java.util.RandomAccess`:ArrayList支持快速的随机访问
        * `java.io.Serializable`:提供序列化功能
        * `java.lang.Cloneable`:表示支持克隆
    * 继承关系
        * `java.util.AbstractList`抽象类
            * `AbstractList`提供了List接口的主要实现，大幅度减少了实现**迭代遍历**相关操作的代码

## 辅助接口和抽象类
### RandomAccess接口
* 接口来源：`java.util.RandomAccess`
* `RandomAccess`是`List`实现所使用的标记接口(Marker)，**接口本身为空**
    * <label style="color:red">空接口表明该接口只起到标记作用</label>
* `RandomAccess`表明其实现类：**支持(通常是固定时间)随机访问××
    * 可以用来标记能够随机访问元素的集合，即底层是数组实现的集合
        * 对于实现类`ArrayList`表示通过数组的下标访问
* `RandomAccess`的主要目的：允许一般的算法更改其行为，从而在将其应用到随机或连续访问列表时能提供良好的性能
* `List`实现了`RandomAccess`接口，表示该类能够快速随机访问存储的元素
* 为了提升性能，在遍历集合前，可以通过`obj instanceof RandomAccess`做判断，选择合适的集合遍历方式
    * 在数据量很大时，可以提升性能
    * 随机访问列表使用循环遍历，顺序访问列表使用迭代器变量
        * 属于`RandomAccess`时使用for循环
        * 不属于`RandomAccess`时使用迭代器循环
* 使用实例：`ArrayList`(实现了RandomAccess)和`LinkedList`(没有实现RandomAccess)的比较
    * 使用`obj instanceof RandomAccess`做使用区分
        ```java
        ```
    * 大数据量的性能测试
        ```java
        ```


### Cloneable接口
* 部分内容可以参考：
    * `https://blog.csdn.net/qq_37113604/article/details/81168224`
* 接口来源：`java.lang.Cloneable`
* 标记接口(Marker)，**接口本身为空**
* 通过实现`Cloneable`接口，来告知`Object.clone()`方法可以合法的对该实现类进行**按字段复制**
    * 需要在类中重写`Object`的`clone`方法
    * 如果在没有实现`Cloneable`接口的实例上调用`Object`的`clone`方法，会引发`CloneNotSupportedException`异常
* `clone`是`native`方法
* `clone`的拷贝过程
    * 分配内存：分配方式与调用`clone`方法的对象的内存构造相同
    * 填充对象：将源对象中的各个变量值填充到新的位置
        * 对象内部包含其他子对象时，如果在`clone`方法中没有特殊处理，**只会填充该子对象的地址**
            * 如果拷贝的是地址，拷贝后，在源对象或拷贝对象中修改子对象时，会影响另一方的使用
        * String比较特殊，同样也是拷贝地址，但是修改后会创建一个新的字符串对象，不会影响源对象/拷贝对象的使用
    * 返回新对象的地址：填充后，将新分配内存的地址返回，该地址中的对象与源对象完全相同，只是地址不同
* 深拷贝与浅拷贝
    * 浅拷贝
        ```java
        public class Student{
            private String name;   //姓名
            private int age;       //年龄
            private StringBuffer sex;  //性别
            public String getName() {
                return name;
            }
            public void setName(String name) {
                this.name = name;
            }
            public int getAge() {
                return age;
            }
            public void setAge(int age) {
                this.age = age;
            }
            public StringBuffer getSex() {
                return sex;
            }
            public void setSex(StringBuffer sex) {
                this.sex = sex;
            }
            @Override
            public String toString() {
                return "Student [name=" + name + ", age=" + age + ", sex=" + sex + "]";
            }
        }

        public  class School implements Cloneable{
            private String schoolName;   //学校名称
            private int stuNums;         //学校人数
            private Student stu;         //一个学生
            public String getSchoolName() {
                return schoolName;
            }
            public void setSchoolName(String schoolName) {
                this.schoolName = schoolName;
            }
            public int getStuNums() {
                return stuNums;
            }
            public void setStuNums(int stuNums) {
                this.stuNums = stuNums;
            }
            public Student getStu() {
                return stu;
            }
            public void setStu(Student stu) {
                this.stu = stu;
            }
            @Override
            protected School clone() throws CloneNotSupportedException {
                // TODO Auto-generated method stub
                // 只克隆自身，不克隆内部所包含的子对象
                return (School)super.clone();;
            }
            @Override
            public String toString() {
                return "School [schoolName=" + schoolName + ", stuNums=" + stuNums + ", stu=" + stu + "]";
            }
        }

        class Test {
            psvm
            public static void main(String[] args){
                public static void main(String[] args) throws CloneNotSupportedException {
                School s1 = new School();       
                s1.setSchoolName("实验小学");
                s1.setStuNums(100);
                Student stu1 = new Student();
                stu1.setAge(20);
                stu1.setName("zhangsan");
                stu1.setSex(new StringBuffer("男"));
                s1.setStu(stu1);
                System.out.println("s1: "+s1+" s1的hashcode:"+s1.hashCode()+"  s1中stu1的hashcode:"+s1.getStu().hashCode());
                School s2 = s1.clone();  //调用重写的clone方法，clone出一个新的school---s2
                System.out.println("s2: "+s2+" s2的hashcode:"+s2.hashCode()+" s2中stu1的hashcode:"+s2.getStu().hashCode());
            }
        }
        ```
    * 深拷贝
        * 可以通过序列化反序列化完成
        * 在`clone`方法中手动添加子对象的`clone`处理
        ```java
        public class Student implements Cloneable{
            
            private String name;
            private int age;
            private StringBuffer sex;
            public String getName() {
                return name;
            }
            public void setName(String name) {
                this.name = name;
            }
            public int getAge() {
                return age;
            }
            public void setAge(int age) {
                this.age = age;
            }
            public StringBuffer getSex() {
                return sex;
            }
            public void setSex(StringBuffer sex) {
                this.sex = sex;
            }
            @Override
            public String toString() {
                return "Student [name=" + name + ", age=" + age + ", sex=" + sex + "]";
            }
            @Override
            protected Student clone() throws CloneNotSupportedException {
                // TODO Auto-generated method stub
                return (Student)super.clone();
            }
        }

        public  class School implements Cloneable{
            private String schoolName;   //学校名称
            private int stuNums;         //学校人数
            private Student stu;         //一个学生
            public String getSchoolName() {
                return schoolName;
            }
            public void setSchoolName(String schoolName) {
                this.schoolName = schoolName;
            }
            public int getStuNums() {
                return stuNums;
            }
            public void setStuNums(int stuNums) {
                this.stuNums = stuNums;
            }
            public Student getStu() {
                return stu;
            }
            public void setStu(Student stu) {
                this.stu = stu;
            }
            
            @Override
            protected School clone() throws CloneNotSupportedException {
                // TODO Auto-generated method stub
                School s = null;
                s = (School)super.clone();
                s.stu = stu.clone(); //等同于重新做设定参数
                return s;
            }

            @Override
            public String toString() {
                return "School [schoolName=" + schoolName + ", stuNums=" + stuNums + ", stu=" + stu + "]";
            }
        }
        ```
************************
