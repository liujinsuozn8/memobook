package com.ljs.learn.collection.collapi;

import org.junit.Test;

import java.util.*;

/*
* Collection接口API测试
*
|no|方法|描述|备注|
|-|-|-|-|
|1|add(Object obj)|添加|基本数据类型会自动进行装箱|
|2|addAll(Collection coll)|添加多个元素||
|3|int size()|获取有效元素个数||
|4|void clear()|清空集合||
|5|boolean isEmpty()|判断是否是空集合||
|6|boolean contains(Object obj)|检查集合是否包含某个元素，要求实现equals方法|在实现类中，内部通过元素的equals方法来判断是否是用一个对象<br>内部是通过`obj.equals(集合元素)`来判断<br>对于某个类的实例，如果没有重写equals()，只能比较地址，即使内容相同结果也是false|
|7|boolean containsAll(Collection c)|比较两个集合的元素，要求实现equals方法|A.containsAll(B) 表示A是否包含B的所有元素<br>调用各元素的equals方法来比较|
|8|boolean retainAll(Collection c)|取两个集合的交集|A.retainAll(B) 交集的结果存A中，不影响B<br>如果集合B是没有元素的空集合，则A也会变为空集合|
|9|boolean remove(Object obj)|删除某个元素，返回是否删除成功|通过equals来比较元素，只删除找到的**第一个元素**|
|10|boolean removeAll(Collection coll)|从集合中删除集合，只是删除两个集合中共有的元素，相当于取当前集合的差集||
|11|boolean equals(Object obj)|判断集合是否相等|判断时是否需要元素的顺序相等，需要根据不同的实现类来判断<br>ArrayList需要顺序相同<br>Map不需要顺序相同|
|12|hashCode()|获取集合对象的hash值||
|13|Object[] toArray()|转换成数组对象||
|14|iterator()|返回迭代器对象，用于集合遍历|
* */
public class CollectionAPITest {

    @Test
    public void testAdd(){
        // |1|add(Object obj)|添加|基本数据类型会自动进行装箱|

        Collection coll = new ArrayList();

        // 添加数据
        coll.add("AA");
        coll.add("BB");
        coll.add(123);
        coll.add(new Date());

        // 打印当前集合中的元素个数
        assert (coll.size() == 4);
    }

    @Test
    public void testAddAll(){
        // |2|addAll(Collection coll)|添加多个元素||

        Collection coll = new ArrayList();

        // 添加数据
        coll.add("AA");
        coll.add("BB");
        coll.add(123);
        coll.add(new Date());

        Collection subColl = new ArrayList();

        // 添加数据
        coll.add("CCC");
        coll.add("GGG");
        coll.add(1456);

        coll.addAll(subColl);

        // 打印当前集合中的元素个数
        assert(coll.size() == 7);
    }

    @Test
    public void testIsEmpty(){
        // |5|boolean isEmpty()|判断是否是空集合||

        Collection coll = new ArrayList();

        // 判读集合是否为空集合
        assert (coll.isEmpty() == true);
        // 添加数据
        coll.add("AA");
        coll.add("BB");
        coll.add(123);
        coll.add(new Date());

        // 判读集合是否为空集合
        assert (coll.isEmpty() == false);
    }

    @Test
    public void testClear(){
        // |4|void clear()|清空集合||
        Collection coll = new ArrayList();
        // 添加数据
        coll.add("AA");
        coll.add("BB");
        coll.add(123);
        coll.add(new Date());

        // 判读集合是否为空集合
        assert (coll.isEmpty() == false);

        // 清空集合
        coll.clear();

        // 判读集合是否为空集合
        assert(coll.isEmpty() == true);
    }

    @Test
    public void testContains() {
        // |6|boolean contains(Object obj)|是否包含某个元素|
        Collection coll = new ArrayList();
        coll.add("AA");
        coll.add(new String("BB"));
        coll.add(123);
        coll.add(new Person("test", 18));
        coll.add(new Date());
        coll.add(new NoEqual("val"));

        // 检查集合中是否包含某个元素

        assert (coll.contains(new String("BB")));
        assert (!coll.contains("XX"));

        Person testPerson = new Person("test", 18);
        System.out.println(testPerson);
        assert (coll.contains(testPerson));

        // 如果对象没有equals方法，只能进行地址的比较
        assert (!coll.contains(new NoEqual("val")));
    }

    @Test
    public void testRemove(){
        // |9|boolean remove(Object obj)|删除某个元素
        Collection coll = new ArrayList();
        coll.add("AA");
        coll.add(new String("BB"));
        coll.add(123);
        coll.add(new Person("test", 18));
        coll.add(new Date());
        coll.add(new NoEqual("val"));

        assert ( coll.remove(123) );

        // 删除重写equals方法的类对象，虽然对象不同但是可以直接删除
        Person testPerson = new Person("test", 18);
        assert (coll.remove(testPerson));

        // 删除没有重写equals方法的类对象，无法正常删除
        NoEqual noEqual = new NoEqual("val");
        assert ( ! coll.remove(noEqual));
    }

    @Test
    public void testRemoveAll(){
        // |10|boolean removeAll(Collection coll)|
        // 从集合中删除集合，只是删除两个集合中共有的元素，相当于取当前集合的差集||
        Collection coll = new ArrayList();
        coll.add("AA");
        coll.add(new String("BB"));
        coll.add(123);
        coll.add(new Person("test", 18));
        coll.add(new Date());
        coll.add(new NoEqual("val"));

        Collection delColl = new ArrayList();
        delColl.add("BB");
        delColl.add(123);
        delColl.add(456);
        delColl.add(new Person("test", 18));

        System.out.println(coll.toString());

        // 可以正常删除，返回true
        assert ( coll.removeAll(delColl) );
        System.out.println(coll.toString());

        // 没有可删除的元素，返回false
        Collection delColl2 = Arrays.asList("qqq", "eee", "www");
        assert ( ! coll.removeAll(delColl2) );
    }

    @Test
    public void testRetainAll(){
        // |8|boolean retainAll(Collection c)|取两个集合的交集
        Collection coll1 = new ArrayList();
        coll1.add("AAA");
        coll1.add("BBB");
        coll1.add("CCC");

        Collection coll2 = new ArrayList();
        coll2.add("DDD");
        coll2.add("BBB");
        coll2.add("CCC");

        // 求交集
        coll1.retainAll(coll2);

        System.out.println(coll1);
    }

    @Test
    public void testEquals(){
        // |11|boolean equals(Object obj)|判断集合是否相等||

        Collection coll1 = new ArrayList();
        coll1.add("AAA");
        coll1.add("BBB");
        coll1.add("CCC");

        Collection coll2 = new ArrayList();
        coll2.add("DDD");
        coll2.add("BBB");
        coll2.add("CCC");

        // 虽然元素相同和coll1相同，单数元素顺序不同，则coll1和coll3不同
        Collection coll3 = new ArrayList();
        coll3.add("BBB");
        coll3.add("CCC");
        coll3.add("AAA");

        // 元素相同和coll1相同，元素顺序也相同，则coll1和coll3相同
        Collection coll4 = new ArrayList();
        coll4.add("AAA");
        coll4.add("BBB");
        coll4.add("CCC");

        assert ( ! coll1.equals(coll2) );

        // 比较时是否需要元素顺序相同，需要根据使用的实现类来判断
        assert ( ! coll1.equals(coll3) );
        assert ( coll1.equals(coll4) );
    }

    @Test
    public void testHashCode(){
        // |12|hashCode()|获取集合对象的hash值||
        Collection coll1 = new ArrayList();
        coll1.add("AAA");
        coll1.add("BBB");
        coll1.add("CCC");

        System.out.println(coll1.hashCode());
    }

    @Test
    public void testToArray(){
        // |13|Object[] toArray()|转换成数组对象||
        Collection coll1 = new ArrayList();
        coll1.add("AAA");
        coll1.add("BBB");
        coll1.add(new Date());
        coll1.add(123);
        coll1.add("CCC");

        Object[] objects = coll1.toArray();
        for (Object object : objects) {
            System.out.println(object);
        }
    }
}
