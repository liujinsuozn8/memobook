package com.ljs.learn.collection.map;

import org.junit.Test;

import java.util.*;

public class HashMapTest {

    // 插入key=null，value=null的键值对
    @Test
    public void testNullKey(){
        Map map = new HashMap();
        map.put(null, 100);
        map.put("aa", null);
        System.out.println(map);
        //output:
        //{null=100, aa=null}
    }

    @Test
    public void testPut(){
        // |添加，删除，修改|Object put(Object key,Object value)
        // 将指定key-value添加到(或修改)当前map对象中||
        Map map = new HashMap();
        map.put("aa", 11);
        map.put("bb", 22);
        map.put("cc", 33);

        map.put("aa", 44);

        System.out.println(map);
        // output:
        // {aa=44, bb=22, cc=33}
    }

    @Test
    public void testPutAll(){
        // |添加，删除，修改|void putAll(Map m)|
        // 将m中的所有key-value对存放到当前map中||

        Map map = new HashMap();
        map.put("aa", 11);
        map.put("bb", 22);
        map.put("cc", 33);

        Map map2 = new HashMap();
        map.put("xx", 5);
        map.put("yy", 6);
        map.put("zz", 7);

        map.putAll(map2);

        System.out.println(map);
        // output:
        // {aa=11, bb=22, cc=33, xx=5, yy=6, zz=7}
    }

    @Test
    public void testRemove(){
        // |添加，删除，修改|Object remove(Object key)|
        // 移除指定key的key-value对，并返回value||

        Map map = new HashMap();
        map.put("aa", 11);
        map.put("bb", 22);
        map.put("cc", 33);

        System.out.println(map);
        // output:
        // {aa=11, bb=22, cc=33}

        //删除一个存在的key，返回value
        Object aval = map.remove("aa");
        System.out.println(aval);
        // output:
        // 11

        //删除一个不存在的key，返回null
        Object xval = map.remove("xx");
        System.out.println(xval);
        // output:
        // null

        System.out.println(map);
        // output:
        // {bb=22, cc=33}
    }

    @Test
    public void tetClear(){
        // |添加，删除，修改|void clear()|
        // 清空当前map中的所有数据||
        Map map = new HashMap();
        map.put("aa", 11);
        map.put("bb", 22);
        map.put("cc", 33);

        System.out.println(map);
        // output:
        // {aa=11, bb=22, cc=33}

        map.clear();
        System.out.println(map);
        //out:
        // {}
    }

    @Test
    public void testGet(){
        // |元素查询|Object get(Object key)|获取指定key对应的value||
        Map map = new HashMap();
        map.put("aa", 11);
        map.put("bb", 22);
        map.put("cc", 33);

        System.out.println(map.get("bb"));
        //output:
        // 22

        System.out.println(map.get("xx"));
        //output:
        // null
    }

    @Test
    public void testContainsKey(){
        // |元素查询|boolean containsKey(Object key)|是否包含指定的key||
        Map map = new HashMap();
        map.put("aa", 11);
        map.put("bb", 22);
        map.put("cc", 33);

        boolean hasAA = map.containsKey("aa");
        assert (hasAA);

        boolean hasXX = map.containsKey("xx");
        assert ( ! hasXX );
    }

    @Test
    public void testContainsValue(){
        // |元素查询|boolean containsValue(Object value)|是否包含指定的value||
        Map map = new HashMap();
        map.put("aa", 33);
        map.put("bb", 22);
        map.put("cc", 33);

        assert (map.containsValue(33));
        assert ( ! map.containsValue(55) );
    }

    @Test
    public void testIsEmpty(){
        // |元素查询|boolean isEmpty()|判断当前map是否为空||

        Map map = new HashMap();
        map.put("aa", 33);
        map.put("bb", 22);
        map.put("cc", 33);

        assert ( ! map.isEmpty() );

        map.clear();
        assert ( map.isEmpty() );
    }

    @Test
    public void testEquals(){
        // |元素查询|boolean equals(Object obj)|判断当前map和参数对象obj是否相等||
        Map map = new HashMap();
        map.put("aa", 11);
        map.put("bb", 22);
        map.put("cc", 33);

        Map map2 = new HashMap();
        map2.put("bb", 22);
        map2.put("cc", 33);
        map2.put("aa", 11);

        assert( map.equals(map2) );

        map.put("xx", 55);
        assert( ! map.equals(map2) );
    }

    @Test
    public void testKeySet(){
        // |元视图操作|Set keySet()|返回所有key构成的Set集合||
        Map map = new HashMap();
        map.put("aa", 11);
        map.put("bb", 22);
        map.put("cc", 33);

        Set keySet = map.keySet();
        for (Object key : keySet) {
            System.out.println(key);
        }

        // output:
        // aa
        // bb
        // cc

        Iterator iterator = keySet.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

    @Test
    public void testValues(){
        // |元视图操作|Collection values()|返回所有value构成的Collection集合||
        Map map = new HashMap();
        map.put("aa", 11);
        map.put("bb", 22);
        map.put("cc", 33);

        Collection values = map.values();
        for (Object v : values) {
            System.out.println(v);
        }

        // output:
        // 11
        // 22
        // 33

        Iterator iterator = values.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

    @Test
    public void testEntrySet(){
        // |元视图操作|Set entrySet()|
        // 返回所有key-value对构成的Set集合，遍历时需要将类型强制转换为Map.Entry||

        Map map = new HashMap();
        map.put("aa", 11);
        map.put("bb", 22);
        map.put("cc", 33);

        Set entrySet = map.entrySet();
        for (Object entry : entrySet) {
            System.out.println(entry);
        }

        // output:
        // aa=11
        // bb=22
        // cc=33

        Iterator iterator = entrySet.iterator();
        while (iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
        }

        //output:
        // key = aa, value = 11
        // key = bb, value = 22
        // key = cc, value = 33
    }
}
