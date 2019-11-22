<span id="catalog"></span>

- [Collection](#collection)
    - [ArrayList](#arrayList)
        - [ArrayList概述](#arraylist概述)
        - [继承与实现关系](#继承与实现关系)
        - [属性](#属性)
        - [构造方法](#构造方法)
        - [构造方法](#构造方法)
        - [添加元素](#添加元素)
        - [数组扩容](#数组扩容)
        - [数组缩容](#数组缩容)
        - [删除元素](#删除元素)
        - [求与指定集合的交集](#求与指定集合的交集)
        - [查找元素的index](#查找元素的index)
        - [通过index获取元素get](#通过index获取元素get)
        - [通过index设置元素set](#通过index设置元素set)
        - [转换为数组](#转换为数组)
        - [求hash值](#求hash值)
        - [清空数组](#清空数组)
        - [序列化数组](#序列化数组)
        - [](#)
        - [](#)

- [辅助接口和抽象类](#辅助接口和抽象类)
    - [RandomAccess接口](#randomAccess接口)
    - [Cloneable接口](#cloneable接口)
    - [序列化接口](#序列化接口)
        - [Serializable](#serializable)
        - [Externalizable](#externalizable)
        - [Serializable和Externalizable的异同](#serializable和externalizable的异同)

# Collection
## ArrayList
[top](#catalog)
* 部分内容参考：
    * `http://iuuxx.com/article/65.html`
### ArrayList概述
[top](#catalog)
* 基于数组实现
* 支持自动扩容的动态数组
* 继承关系图:![ArrayListDiagram.png](./imgs/collection/ArrayListDiagram.png)

### 继承与实现关系
[top](#catalog)
* 实现的四个接口
    * `java.util.List`:提供数组的添加、删除、修改、迭代遍历 等操作
    * `java.util.RandomAccess`:ArrayList支持快速的随机访问
    * `java.io.Serializable`:提供序列化功能
    * `java.lang.Cloneable`:表示支持克隆
* 继承关系
    * `java.util.AbstractList`抽象类
        * `AbstractList`提供了List接口的主要实现，大幅度减少了实现**迭代遍历**相关操作的代码
        * `ArrayList`大量重写了`AbstractList`的方法实现
        * `AbstractList`本身对`ArrayList`意义不大，更多的是`AbstractList`对其他子类的影响
### 属性
[top](#catalog)
* 对象属性
    * `transient Object[] elementData;`，元素数组
        * 内部构成
            ```
            数组内容 |1|4|5|6|size属性标记的位置|null|null|null|null|
            index    0 1 2 3 size             
            ```
    * `private int size;`，elementData数组**已使用**的元素数量
        * 通过`public int size()`获取该属性
        * 每次添加新元素时，都会添加到`size`所对应的位置
* 类属性
    * `private static final int DEFAULT_CAPACITY = 10;`，默认的`elementData`初始化容量
    * `private static final Object[] EMPTY_ELEMENTDATA = {};`
        * 首次扩容为1，后续按照1.5倍扩容
    * `private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};`
        * 首次扩容为10，后续1.5被扩容
### 构造方法
[top](#catalog)
* `public ArrayList(int initialCapacity)` 通过集合大小来初始化
    * 如果初始化时就直到数组的大小，应该使用这种方法来进行初始化
    * 通过该方法可以**避免数组扩容，合理使用内存，来提升性能**
    * 如果`initialCapacity==0`，将会使用`EMPTY_ELEMENTDATA`创建一个空数组:`{}`
        * 在添加元素时会进行数组扩容
    ```java
    //根据容量来初始化：elementData
    public ArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+
                                            initialCapacity);
        }
    }
    ```

* `public ArrayList(Collection<? extends E> c)` 通过其他集合来构造
    * 使用其他集合来创建数组，集合类型必须是E及其子类
        ```java
        public ArrayList(Collection<? extends E> c) {
            elementData = c.toArray();
            if ((size = elementData.length) != 0) {
                // defend against c.toArray (incorrectly) not returning Object[]
                // (see e.g. https://bugs.openjdk.java.net/browse/JDK-6260652)
                if (elementData.getClass() != Object[].class)
                    elementData = Arrays.copyOf(elementData, size, Object[].class);
            } else {
                // replace with empty array.
                this.elementData = EMPTY_ELEMENTDATA;
            }
        }
        ```
    * bug:`JDK-6260652`的修复
        * 问题的原因：
            * `collection.toArray()`和`collection.toArray(new Object[0])`在功能上相同，但是`Arrays.ArrayList`没有遵守规则进行实现
                * 应该返回`Object[]`,但是`Arrays.ArrayList.toArray()`返回的真实类型是：`Type[] (某种类型的数组))`，**在往该数组添加其他类型的数据时会引发异常**
        * 问题的产生
            * 有两个`ArrayList`
                * `Arrays.ArrayList`，`Arrays`的内部类
                * `java.util.ArrayList`
            * 两个`ArrayList`的`toArray`在JDK8中实现不同
                * `Arrays.ArrayList`的实现
                    ```java
                    private final E[] a;
                    @Override
                    public Object[] toArray() {
                        return a.clone();
                    }
                    ```
                * `java.util.ArrayList`
                    ```java
                    public Object[] toArray() {
                        return Arrays.copyOf(elementData, size);
                    }
                    ```
            * `public ArrayList(Collection<? extends E> c) `构造中的参数有可能来自于：`Arrays.ArrayList`
                * 由于`Arrays.ArrayList.toArray()`的返回值类型不是`Object[].class`
                    * 在没有使用泛型的情况下，无法再添加其他类型的元素
                    * 会导致`ArrayList.toArray()`的返回值实际上不是`Object[]`????
                ```java
                @Test
                public void arraysToArrayTest(){
                    //构造Arrays.ArrayList时，已经确定类型为String
                    List<String> a = Arrays.asList("aa", "bb", "cc");
                    List b = new ArrayList(a);

                    //toArray虽然返回了Object[]，但是内部实际是String[]
                    System.out.println(b.toArray().getClass());
                    b.add("ddd");
                    b.add(100);
                }
                ```
        * JDK9及以后的`Arrays.ArrayList.toArray()`
            ```java
            @Override
            public Object[] toArray() {
                return Arrays.copyOf(a, a.length, Object[].class);
            }
            ```
        * bug解决之后产生的其他问题
            * bug解决之前的代码
                * 由于`Arrays.asList("a", "b", "c")`中的参数是`String`型，所以内部生成的也是`Arrays.ArrayList<String>`类型
                * 执行`toArray()`后，虽然返回的是`Object[]`，但是实际上是`String[]`
                ```java
                List<String> list = Arrays.asList("a", "b", "c");
                String[] array = (String[]) list.toArray();
                ```
            * bug解决之后的代码
                ```java
                List<String> list = Arrays.asList("a", "b", "c");
                String[] array = list.toArray(new String[0]);
                ```

* `public ArrayList()` 默认构造
    * 使用`DEFAULTCAPACITY_EMPTY_ELEMENTDATA`来创建一个空数组:`{}`
        * 在添加元素时会进行数组扩容
    * `ArrayList`考虑到节省内存，一些场景下仅创建了一个空数组，**在首次添加元素时**，会使用`DEFAULT_CAPACITY`来初始化一个容量为10的数组
    ```java
    public ArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }
    ```

### 添加元素
[top](#catalog)
* 添加单个元素
    * 直接添加元素
        * `modCount++`,增加数组的修改次数
            * `modCount`来源与父类`AbstractList`，用于记录数组修改的次数
        * `if (s == elementData.length)`，如果当前数组`elementData`已经用完，则进行扩容
        * 添加元素，并增加数组中已使用的元素数量
        ```java
        @Override
        public boolean add(E e) {
            modCount++;
            add(e, elementData, size);
            
            return true;
        }

        private void add(E e, Object[] elementData, int s) {
            if (s == elementData.length) //保证数组容量
                elementData = grow();
            elementData[s] = e; //添加元素
            size = s + 1;
        }
        ```
    * 在指定index位置上添加元素
        ```java
        public void add(int index, E element) {
            // 校验位置index是否有效
            rangeCheckForAdd(index);
            // 增加数组的修改次数
            modCount++;
            
            final int s;
            Object[] elementData;
            
            // 拷贝数组已经使用的大小，拷贝数组地址
            //如果数组已满，则进行扩容
            if ((s = size) == (elementData = this.elementData).length)
                elementData = grow();

            // (使用拷贝的方式)移动元素，从elementData的index开始，移动到index+1，共移动s-index个元素
            // 数据源:elementData
            // 数据源中的数据起始位置:index
            // (拷贝)移动的目标数组:elementData
            // (拷贝)移动的起始位置:index + 1
            // (拷贝)移动的元素数量:s - index
            System.arraycopy(elementData, index,
                            elementData, index + 1,
                            s - index);
            // 添加元素到指定位置
            elementData[index] = element;
            // 数组大小加一
            size = s + 1;
        }

        private void rangeCheckForAdd(int index) {
            if (index > size || index < 0)
                throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }

        private String outOfBoundsMsg(int index) {
            return "Index: "+index+", Size: "+size;
        }
        ```
* 添加多个元素
    * 直接添加多个元素
        * 如果剩余空间做足，则进行扩容
        * 扩容方案：`Max(numNew + oldLength - oldLength, oldLength >> 1) + oldLength`
            * 小数据量时，是1.5倍扩容
            * 大数据量时，按照数据的增长扩容，但是扩容后数组仍然是**满的**
        
        ```java
        public boolean addAll(Collection<? extends E> c) {
            // 转成数组
            Object[] a = c.toArray();
            // 增加修改次数
            modCount++;
            // 如果 a 数组大小为 0 ，返回 ArrayList 数组无变化
            int numNew = a.length;
            if (numNew == 0)
                return false;
            // 如果 elementData 剩余的空间不够，则进行扩容。要求扩容的大小，至少能够装下 a 数组。
            Object[] elementData;
            final int s;
            if (numNew > (elementData = this.elementData).length - (s = size))
                elementData = grow(s + numNew);
            // 将 a 复制到 elementData 从 s 开始位置
            System.arraycopy(a, 0, elementData, s, numNew);
            // 数组大小加 numNew
            size = s + numNew;
            return true;
        }
        ```
    * 在指定index位置插入多个元素
        ```java
        public boolean addAll(int index, Collection<? extends E> c) {
            // 校验位置是否在数组范围内
            rangeCheckForAdd(index);

            // 转成 a 数组
            Object[] a = c.toArray();
            // 增加数组修改次数
            modCount++;
            // 如果 a 数组大小为 0 ，返回 ArrayList 数组无变化
            int numNew = a.length;
            if (numNew == 0)
                return false;
            // 如果 elementData 剩余的空间不够，则进行扩容。要求扩容的大小，至于能够装下 a 数组。
            Object[] elementData;
            final int s;
            if (numNew > (elementData = this.elementData).length - (s = size))
                elementData = grow(s + numNew);

            // 【差异点】如果 index 开始的位置已经被占用，将它们后移
            int numMoved = s - index;
            if (numMoved > 0)
                System.arraycopy(elementData, index,
                                elementData, index + numNew,
                                numMoved);

            // 将 a 复制到 elementData 从 s 开始位置
            System.arraycopy(a, 0, elementData, index, numNew);
            // 数组大小加 numNew
            size = s + numNew;
            return true;
        }
        ```

### 数组扩容
[top](#catalog)
* 扩容的策略
    * 数组为空
        * 如果是通过空参构造器初始化的，则先创建一个长度为：10的数组
        * 如果是`EMPTY_ELEMENTDATA`初始化的, 即通过数量构造，且`数量=0`
            * oldCapacity = 0
            * oldCapacity >> 1 = 0
            * 扩容大小 = minCapacity - oldCapacity + oldCapacity = 1 + 0 + 0 = 1
    * 数组不为空
        * 如果是数组内有元素，则按照1.5倍扩容 = (oldCapacity >> 1) + oldCapacity
* 根据扩容策略，最少会保证**扩容后比扩容前多1个**，一般情况为**1.5被扩容**
```java
private Object[] grow(int minCapacity) {
    // 保存旧容量
    int oldCapacity = elementData.length;

    // 扩容策略1
    // 如果原容量大于 0 ，或者数组不是:DEFAULTCAPACITY_EMPTY_ELEMENTDATA
    if (oldCapacity > 0 || elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
        // 计算新的数组大小: Max(minCapacity - oldCapacity, oldCapacity >> 1) +　oldCapacity
        int newCapacity = ArraysSupport.newLength(oldCapacity,
                minCapacity - oldCapacity, /* minimum growth */
                oldCapacity >> 1           /* preferred growth */);

        // 进行扩容，并拷贝数据
        return elementData = Arrays.copyOf(elementData, newCapacity);
    } else {
        // 扩容策略2
        // 当前数组是：DEFAULTCAPACITY_EMPTY_ELEMENTDATA ，即通过空参构造的数组，构造一个长度为10的数组
        return elementData = new Object[Math.max(DEFAULT_CAPACITY, minCapacity)];
    }
}

private Object[] grow() {
    return grow(size + 1);
}
```

### 数组缩容
[top](#catalog)
* 只会在`elementData`有多余空间时执行缩容
* 如果数组完全没有被使用，则直接使用`EMPTY_ELEMENTDATA`
* 如果数组已有元素被被使用，则创建一个大小为`size`的数组，并拷贝数据
```java
public void trimToSize() {
    // 增加修改次数
    modCount++;
    // 检查是否有未使用的空间
    if (size < elementData.length) {
        elementData = (size == 0)
            ? EMPTY_ELEMENTDATA // 大小为 0 时，直接使用 EMPTY_ELEMENTDATA
            : Arrays.copyOf(elementData, size); // 大小大于 0 ，则创建大小为 size 的新数组，将原数组复制到其中。
    }
}
```

### 删除元素
[top](#catalog)
* 通过index来删除单个元素
    * 在`remove`中先获取要删除的元素，作为返回值返回
    * 在`fastRemove`中
        * 如果需要删除的元素在末尾，则直接删除
        * 如果需要删除的元素不在末尾，则进行元素的移动,然后将末尾的元素删除
    ```java
    public E remove(int index) {
        // 校验 index 不要超过 size
        Objects.checkIndex(index, size);
        final Object[] es = elementData;

        // 记录该位置的原值
        @SuppressWarnings("unchecked") E oldValue = (E) es[index]; //将Object类型强转为E类型
        // 快速移除
        fastRemove(es, index);

        // 返回该位置的原值
        return oldValue;
    }

    private void fastRemove(Object[] es, int i) {
        // 增加数组修改次数
        modCount++;
        // 如果 i 不是移除最末尾的元素，则将 i + 1 位置的数组往前挪
        final int newSize;
        if ((newSize = size - 1) > i) // -1 的原因是，size 是从 1 开始，而数组下标是从 0 开始。
            System.arraycopy(es, i + 1, es, i, newSize - i);
        // 将新的末尾置为 null ，帮助 GC
        es[size = newSize] = null;  //？？？？？ List<int> ？？？？？
    }
    ```
    
* 通过元素来删除单个元素
    * 找到第一个对应的元素后，会立刻删除该元素，也是使用`fastRemove`来删除
    * 通过`equals`来比较元素
    ```java
    public boolean remove(Object o) {
        final Object[] es = elementData;
        final int size = this.size;
        // 寻找首个为 o 的位置
        int i = 0;
        found: {
            if (o == null) { // o 为 null 的情况
                for (; i < size; i++)
                    if (es[i] == null)
                        break found;
            } else { // o 非 null 的情况
                for (; i < size; i++)
                    if (o.equals(es[i]))
                        break found;
            }
            // 如果没找到，返回 false
            return false;
        }
        // 快速移除
        fastRemove(es, i);
        // 找到了，返回 true
        return true;
    }
    ```
* 删除指定范围内的多个元素
    * 先移动(拷贝)元素，在将多余的元素设为`null`
    ```java
    protected void removeRange(int fromIndex, int toIndex) {
        // 检验范围
        if (fromIndex > toIndex) {
            throw new IndexOutOfBoundsException(
                    outOfBoundsMsg(fromIndex, toIndex));
        }
        // 增加数组修改次数
        modCount++;
        // 移除 [fromIndex, toIndex) 的多个元素
        shiftTailOverGap(elementData, fromIndex, toIndex);
    }

    private void shiftTailOverGap(Object[] es, int lo, int hi) {
        // 从hi(toIndex)开始将后面不需要删除的元素 拷贝到 lo(fromIndex)
        System.arraycopy(es, hi, es, lo, size - hi);

        // 计算新的size = size - (hi - lo)
        // 将[new_size, old_size)范围的元素设为null
        for (int to = size, i = (size -= hi - lo); i < to; i++)
            es[i] = null;
    }
    ```

* 删除通过列表删除多个元素
    * 只遍历一次数据
    * 通过`r`来标记当前遍历的位置
    * 通过`w`来标记当前的写入(替换)位置，在执行写入后`w++`
    * 整体遍历之后，数组结尾会产生`end-w`个多余的元素，将其设为`null`
    ```java
    public boolean removeAll(Collection<?> c) {
        return batchRemove(c, false, 0, size);
    }

    //complement表示是否需要保留
    //complement=false, 保留不存在于c中的数据
    boolean batchRemove(Collection<?> c, boolean complement,
                    final int from, final int end) {
        Objects.requireNonNull(c);
        final Object[] es = elementData;
        int r;
        // Optimize for initial run of survivors
        for (r = from;; r++) {
            // 如果elementData遍历结束，则返回false
            if (r == end)
                return false;

            // 搜索第一个不满足保留条件的元素
            if (c.contains(es[r]) != complement)
                break;
        }

        //w=r 开始记录元素的写入开始位置，下一个满足条件的从w开始写，然后w++
        //r++
        int w = r++;
        try {
            //继续搜索到结尾
            for (Object e; r < end; r++)
                //如果满足保留条件，则移动到 w 位置，w++
                if (c.contains(e = es[r]) == complement)
                    es[w++] = e;
        } catch (Throwable ex) {
            // Preserve behavioral compatibility with AbstractCollection,
            // even if c.contains() throws.
            System.arraycopy(es, r, es, w, end - r);
            w += end - r;
            throw ex;
        } finally {
            modCount += end - w;
            shiftTailOverGap(es, w, end); //将多余的元素设为null
        }
        return true;
    }
    ```

### 求与指定集合的交集
[top](#catalog)
* 执行后，会直接修改`ArraryList`对象自身
```java
public boolean retainAll(Collection<?> c) {
    // complement=true，保留存在于c中的数据
    return batchRemove(c, true, 0, size);
}
```

### 查找元素的index
[top](#catalog)
* 找到返回`index`，如果没有找到返回`-1`
* 通过循环遍历来查找
* 正序查找
    ```java
    public int indexOf(Object o) {
        return indexOfRange(o, 0, size);
    }

    int indexOfRange(Object o, int start, int end) {
        Object[] es = elementData;
        // o 为 null 的情况
        if (o == null) {
            for (int i = start; i < end; i++) { // 正序 start--->end
                if (es[i] == null) {
                    return i;
                }
            }
        // o 非 null 的情况
        } else {
            for (int i = start; i < end; i++) { // 正序 start--->end
                if (o.equals(es[i])) {
                    return i;
                }
            }
        }
        // 找不到，返回 -1
        return -1;
    }
    ```
* 正序查找的扩展：判断元素是否在当前的集合中
    ```java
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }
    ```
* 逆序查找
    * 基本实现与`indexOfRange`相同，只是数组遍历的方向是**逆序的**
    ```java
    public int lastIndexOf(Object o) {
        return lastIndexOfRange(o, 0, size);
    }

    int lastIndexOfRange(Object o, int start, int end) {
        Object[] es = elementData;
        // o 为 null 的情况
        if (o == null) {
            for (int i = end - 1; i >= start; i--) { // 倒序 end--->start
                if (es[i] == null) {
                    return i;
                }
            }
        // o 非 null 的情况
        } else {
            for (int i = end - 1; i >= start; i--) { // 倒序 end--->start
                if (o.equals(es[i])) {
                    return i;
                }
            }
        }

        // 找不到，返回 -1
        return -1;
    }
    ```

### 通过index获取元素get
[top](#catalog)
```java
public E get(int index) {
    Objects.checkIndex(index, size);
    // 获取元素
    return elementData(index);
}

@SuppressWarnings("unchecked")
E elementData(int index) {
    return (E) elementData[index];
}
```

### 通过index设置元素set
[top](#catalog)
* 设置新元素之后，将旧元素返回
```java
public E set(int index, E element) {
    // check范围
    Objects.checkIndex(index, size);
    // get旧元素
    E oldValue = elementData(index);
    // set新元素
    elementData[index] = element;
    // 返回旧元素
    return oldValue;
}
```

### 转换为数组
[top](#catalog)
* 转换为`Object[]`
    * 不直接返回`elementData`，通过`Arrays.copyOf`返回一个`Object[]`类型的数组
        * 必须拷贝，直接返回`elementData`，外部的修改会影响集合本身
    ```java
    //ArrayList.java

    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }
    ```
    ```java
    //Arrays.java

    @SuppressWarnings("unchecked")
    public static <T> T[] copyOf(T[] original, int newLength) {
        return (T[]) copyOf(original, newLength, original.getClass());
    }

    @HotSpotIntrinsicCandidate
    public static <T,U> T[] copyOf(U[] original, int newLength, Class<? extends T[]> newType) {
        @SuppressWarnings("unchecked")
        T[] copy = ((Object)newType == (Object)Object[].class)
            ? (T[]) new Object[newLength]
            : (T[]) Array.newInstance(newType.getComponentType(), newLength);
        System.arraycopy(original, 0, copy, 0,
                         Math.min(original.length, newLength));
        return copy;
    }
    ```
* 根据指定的数组类型来转换
    * 如果参数数组比当前集合大或者容量相等，则将集合内的数据拷贝到参数数组中并返回
        * 如果参数数组比当前集合大，则数据拷贝后，将size处的元素设为null
    * 如果参数数组比当前集合小，则将集合元素拷贝到一个新的数组中返回，数组的类型与参数数组类型相同
    * 依据参数数组的大小，有可能**直接在参数数组上修改**，有可能**创建新数组**,
    ```java
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        // 如果传入的数组小于 size 大小，则直接复制一个新数组返回
        if (a.length < size)
            // Make a new array of a's runtime type, but my contents:
            return (T[]) Arrays.copyOf(elementData, size, a.getClass());
        // 将 elementData 复制到 a 中
        System.arraycopy(elementData, 0, a, 0, size);
        // 如果传入的数组大于 size 大小，则将 size 赋值为 null
        if (a.length > size)
            a[size] = null;
        // 返回 a
        return a;
    }
    ```

### 求hash值
[top](#catalog)
* 如果数组为`{}`，则返回`1`
* 使用`31`作为hash值的计算因子
* 计算结果：$31^{size} + \sum_{i=0}^{size-1}(31^{size-1} \cdot hash(e[i]))$
    * 如果某个`e[i]==null`，则使用0作为该元素的hash值
```java
public int hashCode() {
    // 获得当前的数组修改次数
    int expectedModCount = modCount;
    // 计算哈希值
    int hash = hashCodeRange(0, size);
    // 如果修改次数发生改变，则抛出 ConcurrentModificationException 异常
    checkForComodification(expectedModCount);
    return hash;
}

int hashCodeRange(int from, int to) {
    final Object[] es = elementData;
    // 如果 to 超过大小，则抛出 ConcurrentModificationException 异常
    if (to > es.length) {
        throw new ConcurrentModificationException();
    }
    // 遍历每个元素，* 31 求哈希。
    int hashCode = 1;
    for (int i = from; i < to; i++) {
        Object e = es[i];  //性能?????????????
        hashCode = 31 * hashCode + (e == null ? 0 : e.hashCode());
    }
    return hashCode;
}
```

### 判断两个集合是否相等
* 比较的策略
    * 如果是自身，返回`true`
    * 如果参数不是`List`,返回`false`
    * 如果参数是`ArrayList`，则同时循环遍历两个集合，并进行比较
        * 比较前，检查两个集合的**元素数量**，如果数量不同，则返回`false`
        * 结束前需要检查参数的**修改次数**，防止并发操作对参数的影响
    * 如果参数不是`ArrayList`，对自身进行循环遍历，对参数进行迭代器遍历，并进行比较
        * 比较前，不会检查两个集合的**元素数量**,防止参数内部实现造成的影响
        * 在比较结束之后，再判断参数迭代器中是否还有元素
            * 如果还有元素，说明两个集合的元素数量不同，返回`false`
* 在返回结果之前需要检查自身的**修改次数**，防止并发操作的影响
```java
public boolean equals(Object o) {
    // 根据地址判断，如果是集合自身，则返回true
    if (o == this) {
        return true;
    }

    // 如果参数不是List，则返回false
    if (!(o instanceof List)) {
        return false;
    }

    // 获得当前的数组修改次数
    final int expectedModCount = modCount;
    // ArrayList can be subclassed and given arbitrary behavior, but we can
    // still deal with the common case where o is ArrayList precisely
    // 根据不同类型，调用不同比对的方法。主要考虑 ArrayList 可以直接使用其 elementData 属性，性能更优。
    boolean equal = (o.getClass() == ArrayList.class)
        ? equalsArrayList((ArrayList<?>) o)
        : equalsRange((List<?>) o, 0, size);

    // 如果修改次数发生改变，则抛出 ConcurrentModificationException 异常
    checkForComodification(expectedModCount);
    return equal;
}

// ArrayList间的比较
private boolean equalsArrayList(ArrayList<?> other) {
    // 获得 other 数组修改次数
    final int otherModCount = other.modCount;
    final int s = size;
    boolean equal;
    // 判断数组大小是否相等
    if (equal = (s == other.size)) {
        final Object[] otherEs = other.elementData;
        final Object[] es = elementData;
        // 如果 s 大于 es 或者 otherEs 的长度，说明发生改变，抛出 ConcurrentModificationException 异常
        if (s > es.length || s > otherEs.length) {
            throw new ConcurrentModificationException();
        }
        // 遍历，逐个比较每个元素是否相等
        for (int i = 0; i < s; i++) {
            if (!Objects.equals(es[i], otherEs[i])) {
                equal = false;
                break; // 如果不相等，则 break
            }
        }
    }
    // 如果 other 修改次数发生改变，则抛出 ConcurrentModificationException 异常
    other.checkForComodification(otherModCount);
    return equal;
}

// ArrayList 和其他 List 实现类的比较
boolean equalsRange(List<?> other, int from, int to) {
    // 如果 to 大于 es 大小，说明说明发生改变，抛出 ConcurrentModificationException 异常
    final Object[] es = elementData;
    if (to > es.length) {
        throw new ConcurrentModificationException();
    }
    // 通过迭代器遍历 other ，然后逐个元素对比
    var oit = other.iterator();
    for (; from < to; from++) {
        // 如果 oit 没有下一个，或者元素不相等，返回 false 不匹配
        if (!oit.hasNext() || !Objects.equals(es[from], oit.next())) {
            return false;
        }
    }
    // 通过 oit 是否遍历完。实现大小是否相等的效果。
    return !oit.hasNext();
}
```

### 清空数组
[top](#catalog)
```java
public void clear() {
    // 增加修改该次数
    modCount++;
    
    final Object[] es = elementData;

    // 遍历数组，并将所有的元素设为 null
    for (int to = size, i = size = 0; i < to; i++)
        es[i] = null;
}
```

### 序列化数组
[top](#catalog)



# 辅助接口和抽象类
## RandomAccess接口
[top](#catalog)
* 内容参考：`https://juejin.im/post/5a26134af265da43085de060`
* 接口来源：`java.util.RandomAccess`
* `RandomAccess`是`List`实现所使用的标记接口(Marker)，**接口本身为空**
    * <label style="color:red">空接口表明该接口只起到标记作用</label>
* `RandomAccess`表明其实现类：**支持(通常是固定时间)随机访问**
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
        public class RandomAccessTest {
            public static void interfaceTest(List list){
                if (list instanceof RandomAccess){
                    for (int i = 0; i < list.size(); i++) {
                        System.out.println("this is random= " + list.get(i));
                    }
                } else {
                    Iterator iterator = list.iterator();
                    while(iterator.hasNext()){
                        System.out.println("this is iter = " + iterator.next());
                    }
                }
            }

            public static void main(String[] args) {
                List<Integer> a = new ArrayList<>();
                a.add(22);
                a.add(44);
                a.add(66);
                interfaceTest(a);
                //this is random= 22
                //this is random= 44
                //this is random= 66

                List<Integer> b = new LinkedList<>();
                b.add(77);
                b.add(88);
                interfaceTest(b);
                //this is iter = 77
                //  this is iter = 88
            }
        }
        ```
    * 大数据量的性能测试
        * 从结果看
            * ArrayList的循环更快
            * LinkedList的迭代更快
        ```java
        //测试结果：
        //ArrayList loop time: 2
        //ArrayList iterator time: 3
        //LinkedList loop time: 440
        //LinkedList iterator time: 1

        public class RandomAccessTimeTest {
            public long loopTime(List list){
                long start = System.currentTimeMillis();
                for (int i = 0; i < list.size(); i++) {
                    list.get(i);
                }
                long end = System.currentTimeMillis();

                return end-start;
            }

            public long foreachTime(List list){
                long start = System.currentTimeMillis();
                for (Object o : list) {

                }
                long end = System.currentTimeMillis();

                return end-start;
            }

            public long iteratorTime(List list){
                Iterator iterator = list.iterator();
                long start = System.currentTimeMillis();
                while(iterator.hasNext()){
                    iterator.next();
                }
                long end = System.currentTimeMillis();

                return end-start;
            }

            @Test
            public void timeTest(){
                RandomAccessTimeTest t = new RandomAccessTimeTest();

                List<Integer> al = new ArrayList<>();
                for (int i = 0; i < 300000; i++) {
                    al.add(i);
                }

                System.out.println("ArrayList loop time: " + t.loopTime(al));
                System.out.println("ArrayList foreach time: " + t.foreachTime(al));
                System.out.println("ArrayList iterator time: " + t.iteratorTime(al));

                List<Integer> ll = new LinkedList<>();
                for (int i = 0; i < 300000; i++) {
                    ll.add(i);
                }

                System.out.println("LinkedList loop time: " + t.loopTime(ll));
                System.out.println("LinkedList iterator time: " + t.iteratorTime(ll));
            }
        }

        ```


## Cloneable接口
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
## 序列化接口
[top](#catalog)
* 部分内容参考：
    * `https://www.jianshu.com/p/e554c787c286`
### Serializable
[top](#catalog)
* `Serializable`本身是一个空接口
* `Serializable`的反序列化**不会调用空参构造器**
* 源码
    ```java
    package java.io;
    public interface Serializable {
    }
    ```
* 序列化**版本标识**符常量：`private static final long serialVersionUID = ...;`
    * 表示序列化版本标识符的静态变量
        * 用来表明类的不同版本间的兼容性(对序列化对象进行版本控制)
        * 序列化时，用serialVersionUID**验证版本的一致性**
        * 反序列化时，jvm将字节流中的serialVersionUID与本地对应实体类的serialVersionUID进行比较，**如果相同则一致，进行反序列化；不一致，则引发InvalidCastException**
    * 如果没有显示定义该常量，它的值是运行时根据类内部细节自动生成的，**如果类的实例变量发生变化，则serialVersionUID会变化**
* 如果不希望某些字段被序列化，可以使用`transient`来修饰字段
    * 反序列化后，这些字段的值是默认的0值
* 实例
    ```java
    class Person implements Serializable {
        private String name;
        private Integer age;
        private transient String skipParam;

        public Person(String name, Integer age, String skipParam) {
            this.name = name;
            this.age = age;
            this.skipParam = skipParam;
        }
        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age + '\'' +
                    ", skipParam=" + skipParam +
                    '}';
        }
    }

    public class SerializeTest {
        // 序列化
        @Test
        public void method(){
            Person p1 = new Person("aaa", 11, "skip1");
            System.out.println(p1); //Person{name='bbb', age=12', skipParam=skip2}

            Person p2 = new Person("bbb", 12, "skip2");
            System.out.println(p2); //Person{name='aaa', age=11', skipParam=skip1}

            ObjectOutputStream oos = null;
            try {
                FileOutputStream fos = new FileOutputStream("...");
                oos =new ObjectOutputStream(fos);

                oos.writeObject(p1);
                oos.flush();
                oos.writeObject(p2);
                oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (oos != null){
                    try {
                        oos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // 反序列化
        @Test
        public void method2(){
            ObjectInputStream ois = null;
            try {
                FileInputStream fis = new FileInputStream("...");
                ois = new ObjectInputStream(fis);

                Person p1 = (Person) ois.readObject();
                System.out.println(p1); //Person{name='aaa', age=11', skipParam=null}

                Person p2 = (Person) ois.readObject();
                System.out.println(p2); //Person{name='bbb', age=12', skipParam=null}
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (ois != null){
                    try {
                        ois.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    ```

### Externalizable
[top](#catalog)
* 源码
    ```java
    package java.io;

    import java.io.ObjectOutput;
    import java.io.ObjectInput;

    public interface Externalizable extends java.io.Serializable {
        //通过额外添加的两个方法
        void writeExternal(ObjectOutput out) throws IOException;
        void readExternal(ObjectInput in) throws IOException, ClassNotFoundException;
    }
    ```
* 反序列化要求类必须有一个**空参构造器**，否则会引发`InvalidClassException`异常
* 在`Externalizable`中，`transient`修饰符会失效
* `writeExternal`,`readExternal`中序列化参数的顺序必须是**一致的**，否则会引发`EOFException`异常
    * 没有参与序列化的参数，在反序列化时，是对应类型的0值
* 实例
    ```java
    class Person2 implements Externalizable {
        private String name;
        private Integer age;
        private transient String skipParam;

        public Person2(){
            System.out.println("this Person2()");
        }

        public Person2(String name, Integer age, String skipParam) {
            this.name = name;
            this.age = age;
            this.skipParam = skipParam;
        }
        @Override
        public String toString() {
            return "Person{" +
                    "name=" + name + 
                    ", age=" + age + 
                    ", skipParam=" + skipParam +
                    '}';
        }

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeUTF(name);
            out.writeInt(age);
            out.writeUTF(skipParam);
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            name = in.readUTF();
            age = in.readInt();
            skipParam = in.readUTF();
        }
    }

    public class ExternalTest {
        // 序列化
        @Test
        public void method(){
            Person2 p1 = new Person2("aaa", 11, "skip1");
            System.out.println(p1); //Person{name=bbb, age=12, skipParam=skip2}

            Person2 p2 = new Person2("bbb", 12, "skip2");
            System.out.println(p2); //Person{name=aaa, age=11, skipParam=skip1}

            ObjectOutputStream oos = null;
            try {
                FileOutputStream fos = new FileOutputStream(
                        "/Users/liujinsuo/myGit/memobook/java/jdkAnalyze/out.txt"
                );
                oos =new ObjectOutputStream(fos);

                oos.writeObject(p1);
                oos.flush();
                oos.writeObject(p2);
                oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (oos != null){
                    try {
                        oos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // 反序列化
        @Test
        public void method2(){
            ObjectInputStream ois = null;
            try {
                FileInputStream fis = new FileInputStream(
                        "/Users/liujinsuo/myGit/memobook/java/jdkAnalyze/out.txt"
                );
                ois = new ObjectInputStream(fis);

                Person2 p1 = (Person2) ois.readObject();
                System.out.println(p1); //Person{name=aaa, age=11, skipParam=skip1}

                Person2 p2 = (Person2) ois.readObject();
                System.out.println(p2); //Person{name=bbb, age=12, skipParam=skip2}
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (ois != null){
                    try {
                        ois.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    ```
### Serializable和Externalizable的异同
* 相同点
    * 最好都生成版本标识符常量：`serialVersionUID`
* 不同点
    * `transient`在`Externalizable`中失效
    * `Externalizable`的反序列化，必须要提供空参构造器
* 特殊点
    * `Externalizable`字段的读写顺序必须相同
************************


* String中hash值计算使用质数31作为计算因子
    * 一般的计算结果：`e[0]*31^(n-1) + e[1]*31^(n-2) + ... + e[n-1]`
    * 需要计算因子的原因：
        * 在计算hash值时，需要避免结果冲突，所以需要一个**特殊的质数**，来降低冲突率
        * 除了保证降低冲突率，还要保证计算结果在`int`的范围之内，**不能溢出**
    * 使用31的原因
        1. 31是一个不大不小的**质数**
            * 31是一个**奇质数**，如果是偶数会在乘法运算中产生溢出
            * 如果使用其他质数作为因子
                * 使用更小的因子：使用`2`作为计算因子
                    * 假设公式中的`n=6`
                    * `e[0]*31^(n-1) = e[0] * 2^5 = 32 * e[0]`
                    * 每一项中的系数会比较小，最终的计算结果会分布在一个**比较小的区间内部**，容易产生冲突
                    * **在字符串比较短的情况下**,结果区间会更小，**冲突的几率会更大**
                * 使用更大的因子：使用`101`作为计算因子
                    * 使用更大的因子的好处：大大降低了结果的冲突率
                    * 假设公式中的`n=6`
                    * `e[0]*31^(n-1) = e[0] * 2^101 = 10510100501 * e[0]`
                        * 该项的系数为`10510100501`，已经超过了`int`的最大值`2147483647`
                    * 每一项的系数会比较大，最终的计算结果容易产生**溢出**
        2. JVM中可以进行优化：`31 * i = (i << 5) - i`
            
