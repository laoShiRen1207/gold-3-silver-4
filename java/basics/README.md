# basics

[toc]

---

## 基本类型包装类

Java并不是纯面向对象的语言，虽然Java语言是一个面向对象的语言，但是Java中的基本数据类型却不是面向对象的。

我们的基本类型，如果想通过对象的形式去使用他们，Java提供的基本类型包装类，使得Java能够更好的体现面向对象的思想，同时也使得基本类型能够支持对象操作！

包装类实际上就行将我们的基本数据类型，封装成一个类（运用了封装的思想）

```java
public final class Integer extends Number implements Comparable<Integer> {
    //Integer内部其实本质还是存了一个基本类型的数据，但是我们不能直接操作
    private final int value;

    public Integer(int value) {
        this.value = value;
    }
}
```

现在我们操作的就是Integer对象而不是一个int基本类型了！

```java
public class Boxed {
    public static void main(String[] args) {
        //包装类型可以直接接收对应类型的数据，并变为一个对象！
        Integer i = 1;
        //包装类型可以直接被当做一个基本类型进行操作！
        System.out.println(i + i);
    }
}
```

### 自动装箱和拆箱

那么为什么包装类型能直接使用一个具体值来赋值呢？其实依靠的是自动装箱和拆箱机制

```java
public class Boxed {
    //其实这里只是简写了而已
    Integer i = 1;
    //编译后真正的样子
    Integer i = Integer.valueOf(1);
}
```

调用valueOf来生成一个Integer对象！

```java
public class Boxed {
    public static Integer valueOf(int i) {
        if (i >= IntegerCache.low && i <= IntegerCache.high)
            return IntegerCache.cache[i + (-IntegerCache.low)];
        //返回一个新创建好的对象
        return new Integer(i);
    }
}
```

而如果使用包装类来进行运算，或是赋值给一个基本类型变量，会进行自动拆箱：

```java
public class Boxed {
    public static void main(String[] args) {
        Integer i = Integer.valueOf(1);
        //简写
        int a = i;
        //编译后实际的代码
        int a = i.intValue();
        //其他类型也有！
        long c = i.longValue();
    }
}
```

既然现在是包装类型了，那么我们还能使用`==`来判断两个数是否相等吗？

```java
public class Boxed {
    public static void main(String[] args) {
        Integer i1 = 28914;
        Integer i2 = 28914;
        //实际上判断是两个对象是否为同一个对象（内存地址是否相同）
        System.out.println(i1 == i2);
        //这个才是真正的值判断！
        System.out.println(i1.equals(i2));
    }
}
```

## 泛型

使用Object类型作为引用，取值只能进行强制类型转换，显然无法在编译期确定类型是否安全，项目中代码量非常之大，进行类型比较又会导致额外的开销和增加代码量，如果不经比较就很容易出现类型转换异常，代码的健壮性有所欠缺！（此方法虽然可行，但并不是最好的方法）

为了解决以上问题，JDK1.5新增了泛型，它能够在编译阶段就检查类型安全，大大提升开发效率。

```java
//将Score转变为泛型类<T>
public class Score<T> {
    String name;
    String id;
    //T为泛型，根据用户提供的类型自动变成对应类型
    T score;

    //提供的score类型即为T代表的类型
    public Score(String name, String id, T score) {
        this.name = name;
        this.id = id;
        this.score = score;
    }


    public static void main(String[] args) {
        //直接确定Score的类型是字符串类型的成绩
        Score<String> score = new Score<String>("数据结构与算法基础", "EP074512", "优秀");
        //编译不通过，因为成员变量score类型被定为String！
        Integer i = score.score;
    }

}
```

泛型将数据类型的确定控制在了编译阶段，在编写代码的时候就能明确泛型的类型！如果类型不符合，将无法通过编译！

泛型本质上也是一个语法糖（并不是JVM所支持的语法，编译后会转成编译器支持的语法，比如之前的foreach就是），在编译后会被擦除，变回上面的Object类型调用，但是类型转换由编译器帮我们完成，而不是我们自己进行转换（安全）

```java
public class Main {
    //反编译后的代码
    public static void main(String[] args) {
        //直接确定Score的类型是字符串类型的成绩
        Score<String> score = new Score<String>("数据结构与算法基础", "EP074512", "优秀");
        //编译不通过，因为成员变量score类型被定为String！
        String i = score.score;
    }

}
```

像这样在编译后泛型的内容消失转变为Object的情况称为`类型擦除`（重要，需要完全理解），所以泛型只是为了方便我们在编译阶段确定类型的一种语法而已，并不是JVM所支持的。

综上，泛型其实就是一种类型参数，用于指定类型。

> 泛型无法使用基本类型，如果需要基本类型，只能使用基本类型的包装类进行替换！
>
那么为什么泛型无法使用基本类型呢？回想上一节提到的类型擦除，其实就很好理解了。由于JVM没有泛型概念，因此泛型最后还是会被编译器编译为Object，并采用强制类型转换的形式进行类型匹配，而我们的基本数据类型和引用类型之间无法进行类型转换，所以只能使用基本类型的包装类来处理。

## 集合

### List

#### ArrayList

首先介绍ArrayList，它的底层是用数组实现的，内部维护的是一个可改变大小的数组，也就是所说的线性表！

```java
public class ArrayListMain {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("1");
        String s = list.get(0);
        // rangeCheck(index);
        // 检查index 是否超过 list size的 超过抛出越界的异常
        list.set(1, "2");
        // 也是先范围检查
        // 获取老的元素
        // 元素覆盖
        // 将老元素返回
        list.add("3");
        // 首先确保容量够用
        // 然后赋值到数组对应的 size++的位置
    }

    // ArraryList#get
    public E get(int index) {
        rangeCheck(index);

        return elementData(index);
    }


    E elementData(int index) {
        return (E) elementData[index];
    }

    // ArrayList#set
    public E set(int index, E element) {
        rangeCheck(index);

        E oldValue = elementData(index);
        elementData[index] = element;
        return oldValue;
    }

    // ArrayList#add
    public boolean add(E e) {
        ensureCapacityInternal(size + 1);  // Increments modCount!!
        elementData[size++] = e;
        return true;
    }

    // 判断是否需要扩容 
    private void ensureExplicitCapacity(int minCapacity) {
        modCount++;

        // overflow-conscious code
        if (minCapacity - elementData.length > 0)
            grow(minCapacity);
    }

    // 右移一位 newCapacity = oldCapacity + 一半的oldCapacity
// 然后在拷贝到新数组里
    private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        // minCapacity is usually close to size, so this is a win:
        elementData = Arrays.copyOf(elementData, newCapacity);
    }
}
```

#### LinkedList

再来看LinkedList，其实本质就是一个链表，内部使用的是一个双向链表

```java
private static class Node<E> {
    E item;
    Node<E> next;
    Node<E> prev;

    Node(Node<E> prev, E element, Node<E> next) {
        this.item = element;
        this.next = next;
        this.prev = prev;
    }
}
```

当然，我们发现它还实现了Queue接口，所以LinkedList也能被当做一个队列或是栈来使用。

```java
public class LinkedList<E>
        extends AbstractSequentialList<E>
        implements List<E>, Deque<E>, Cloneable, java.io.Serializable {

    // LinkedList#add
    public boolean add(E e) {
        linkLast(e);
        return true;
    }

    void linkLast(E e) {
        // 获取尾节点
        final Node<E> l = last;
        // 新元素维护在尾节点之后
        final Node<E> newNode = new Node<>(l, e, null);
        // 将尾节点指向新元素
        last = newNode;
        // l 为空表示 是 新new 出来的连表
        if (l == null)
            // 新链表维护头结点
            first = newNode;
        else
            l.next = newNode;
        size++;
        modCount++;
    }

    public boolean remove(Object o) {
        if (o == null) {
            for (Node<E> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<E> x = first; x != null; x = x.next) {
                if (o.equals(x.item)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    // 获取node
    public E get(int index) {
        // 检查索引index
        checkElementIndex(index);
        return node(index).item;
    }

    Node<E> node(int index) {
        // assert isElementIndex(index);
        // 取linkedList的中位数 小于就从前往后找 大于就从后往前找
        if (index < (size >> 1)) {
            Node<E> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {
            Node<E> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }
}
```

### Iterator

我们之前学习数据结构时，已经得知，不同的线性表实现，在获取元素时的效率也不同，因此我们需要一种更好地方式来统一不同数据结构的遍历。

由于ArrayList对于随机访问的速度更快，而LinkedList对于顺序访问的速度更快，因此在上述的传统for循环遍历操作中，ArrayList的效率更胜一筹，因此我们要使得LinkedList遍历效率提升，就需要采用顺序访问的方式进行遍历，如果没有迭代器帮助我们统一标准，那么我们在应对多种集合类型的时候，就需要对应编写不同的遍历算法，很显然这样会降低我们的开发效率，而迭代器的出现就帮助我们解决了这个问题。

我们先来看看迭代器里面方法：

```java
public interface Iterator<E> {
    //...
}
```

每个集合类都有自己的迭代器，通过`iterator()`方法来获取：

```java
public class ItMain {
    public static void main(String[] args) {
        //生成一个新的迭代器
        Iterator<String> iterator = linkedList.iterator();
        //判断是否还有下一个元素
        while (iterator.hasNext()) {
            //获取下一个元素（获取一个少一个）
            String i = iterator.next();
            System.out.println(i);
        }
    }
}
```

迭代器生成后，默认指向第一个元素，每次调用`next()`方法，都会将指针后移，当指针移动到最后一个元素之后，调用`hasNext()`将会返回`false`，迭代器是一次性的，用完即止，如果需要再次使用，需要调用`iterator()`
方法。

ListIterator是List中独有的迭代器，在原有迭代器基础上新增了一些额外的操作。(是否有上一个)

### Set

我们之前已经看过`Set`接口的定义了，我们发现接口中定义的方法都是Collection中直接继承的，因此，Set支持的功能其实也就和Collection中定义的差不多，只不过使用方法上稍有不同。

Set集合特点：

* 不允许出现重复元素
* 不支持随机访问（不允许通过下标访问）

首先认识一下HashSet，它的底层就是采用哈希表实现的

```java
public class SetMain {

    public static void main(String[] args) {
        HashSet<Integer> hashSet = new HashSet<>();
        hashSet.add(1);
        hashSet.add(1);
        hashSet.add(2);
        hashSet.forEach(System.out::println);
    }
}
```

LinkedHashSet底层维护的不再是一个HashMap，而是LinkedHashMap，它能够在插入数据时利用链表自动维护顺序，因此这样就能够保证我们插入顺序和最后的迭代顺序一致了。

还有一种Set叫做TreeSet，它会在元素插入时进行排序：

```java
public class SetMain {
    public static void main(String[] args) {
        TreeSet<Integer> set = new TreeSet<>();
        set.add(1);
        set.add(3);
        set.add(2);
        System.out.println(set);
    }
}
```

可以看到最后得到的结果并不是我们插入顺序，而是按照数字的大小进行排列。当然，我们也可以自定义排序规则：

```java
public class SetMain {
    public static void main(String[] args) {
        TreeSet<Integer> set = new TreeSet<>((a, b) -> b - a);   //在创建对象时指定规则即可
        set.add(1);
        set.add(3);
        set.add(2);
        System.out.println(set);
    }
}
```

现在的结果就是我们自定义的排序规则了。

通过观察HashSet的源码发现，HashSet几乎都在操作内部维护的一个HashMap，也就是说，HashSet只是一个表壳，而内部维护的HashMap才是灵魂！

```java
public class HashSet{
    // Dummy value to associate with an Object in the backing Map
    private static final Object PRESENT = new Object();
}
```

我们发现，在添加元素时，其实添加的是一个键为我们插入的元素，而值就是`PRESENT`常量：

```java
public class HashSet {
    /**
     * Adds the specified element to this set if it is not already present.
     * More formally, adds the specified element <tt>e</tt> to this set if
     * this set contains no element <tt>e2</tt> such that
     * <tt>(e==null&nbsp;?&nbsp;e2==null&nbsp;:&nbsp;e.equals(e2))</tt>.
     * If this set already contains the element, the call leaves the set
     * unchanged and returns <tt>false</tt>.
     *
     * @param e element to be added to this set
     * @return <tt>true</tt> if this set did not already contain the specified
     * element
     */
    public boolean add(E e) {
        return map.put(e, PRESENT)==null;
    }
}
```

观察其他的方法，也几乎都是在用HashMap做事，所以说，HashSet利用了HashMap内部的数据结构，轻松地就实现了Set定义的全部功能！

再来看TreeSet，实际上用的就是我们的TreeMap：

```java
public class HashSet {
    /**
     * The backing map.
     */
    private transient NavigableMap<E,Object> m;
}
```

同理，这里就不多做阐述了。



### Map

#### 什么是映射

我们在高中阶段其实已经学习过映射了，映射指两个元素的之间相互“对应”的关系，也就是说，我们的元素之间是两两对应的，是以键值对的形式存在。

#### Map接口

Map就是为了实现这种数据结构而存在的，我们通过保存键值对的形式来存储映射关系。

```java
public class MapMain {

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
    }

}
```

哈希表的本质其实就是一个用于存放后续节点的头结点的数组，数组里面的每一个元素都是一个头结点（也可以说就是一个链表），当要新插入一个数据时，会先计算该数据的哈希值，找到数组下标，然后创建一个新的节点，添加到对应的链表后面。

而HashMap就是采用的这种方式，我们可以看到源码中同样定义了这样的一个结构：

```java
public class HashMap {
    /**
     * The table, initialized on first use, and resized as
     * necessary. When allocated, length is always a power of two.
     * (We also tolerate length zero in some operations to allow
     * bootstrapping mechanics that are currently not needed.)
     */
    transient Node<K, V>[] table;
}
```

这个表会在第一次使用时初始化，同时在必要时进行扩容，并且它的大小永远是2的倍数！

```java
public class HashMap {
    /**
     * The default initial capacity - MUST be a power of two.
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16

}
```

我们可以看到默认的大小为2的4次方，每次都需要是2的倍数，也就是说，下一次增长之后，大小会变成2的5次方。

我们现在需要思考一个问题，当我们表中的数据不断增加之后，链表会变得越来越长，这样会严重导致查询速度变慢，首先想到办法就是，我们可以对数组的长度进行扩容，来存放更多的链表，那么什么情况下会进行扩容呢？

```java
public class HashMap {
    /**
     * The load factor for the hash table.
     *
     * @serial
     */
    final float loadFactor;
}
```

我们还发现HashMap源码中有这样一个变量，也就是`负载因子`，那么它是干嘛的呢？

负载因子其实就是用来衡量当前情况是否需要进行扩容的标准。我们可以看到默认的负载因子是`0.75`

```java
public class HashMap {
    /**
     * The load factor used when none specified in constructor.
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
}
```

那么负载因子是怎么控制扩容的呢？`0.75`的意思是，在插入新的结点后，如果当前数组的占用率达到75%则进行扩容。在扩容时，会将所有的数据，重新计算哈希值，得到一个新的下标，组成新的哈希表。

但是这样依然有一个问题，链表过长的情况还是有可能发生，所以，为了从根源上解决这个问题，在JDK1.8时，引入了红黑树这个数据结构。

当链表的长度达到8 (hash表长度大于64) 时，会自动将链表转换为红黑树，这样能使得原有的查询效率大幅度降低！当使用红黑树之后，我们就可以利用二分搜索的思想，快速地去寻找我们想要的结果，而不是像链表一样挨个去看。

```java
/**
 * Entry for Tree bins. Extends LinkedHashMap.Entry (which in turn
 * extends Node) so can be used as extension of either regular or
 * linked node.
 */
static final class TreeNode<K, V> extends LinkedHashMap.Entry<K, V> {
}
```

除了Node以外，HashMap还有TreeNode，很明显这就是为了实现红黑树而设计的内部类。不过我们发现，TreeNode并不是直接继承Node，而是使用了LinkedHashMap中的Entry实现，它保存了前后节点的顺序（也就是我们的插入顺序）。

```java
/**
 * HashMap.Node subclass for normal LinkedHashMap entries.
 */
static class Entry<K, V> extends HashMap.Node<K, V> {
    Entry<K, V> before, after;

    Entry(int hash, K key, V value, Node<K, V> next) {
        super(hash, key, value, next);
    }
}
```

除了Node以外，HashMap还有TreeNode，很明显这就是为了实现红黑树而设计的内部类。不过我们发现，TreeNode并不是直接继承Node，而是使用了LinkedHashMap中的Entry实现，它保存了前后节点的顺序（也就是我们的插入顺序）。

```java
/**
 * HashMap.Node subclass for normal LinkedHashMap entries.
 */
static class Entry<K, V> extends HashMap.Node<K, V> {
    Entry<K, V> before, after;

    Entry(int hash, K key, V value, Node<K, V> next) {
        super(hash, key, value, next);
    }
}
```

LinkedHashMap是直接继承自HashMap，具有HashMap的全部性质，同时得益于每一个节点都是一个双向链表，保存了插入顺序，这样我们在遍历LinkedHashMap时，顺序就同我们的插入顺序一致。当然，也可以使用访问顺序，也就是说对于刚访问过的元素，会被排到最后一位。

```java
public class MapMain {
    public static void main(String[] args) {
        //以访问顺序
        LinkedHashMap<Integer, String> map = new LinkedHashMap<>(16, 0.75f, true);  
        map.put(1, "A");
        map.put(2, "B");
        map.put(3, "C");
        map.get(2);
        System.out.println(map);
    }
}
```

观察结果，我们发现，刚访问的结果被排到了最后一位。


















