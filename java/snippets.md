# Java Snippets

TODO

## cast

to String

- Object
- byte[]
- boolean
- char[]
- char
- int
- long
- float
- double

```java
String.valueOf(new Object());
// e.g. "java.lang.Object@3bd40a57"
```

format

```java
String.format("%s, %s!", "Hello", "world");
```

String to other types

```java
Integer intVar = Integer.parseInt("11");
Float floatVar = Float.parseFloat("1.2");
```

## conditional

Collection

```java
CollectionUtils.isEmpty(collection)
CollectionUtils.isNotEmpty(collection)
```

## enum

```java
package xyz.icehe.type;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang.StringUtils;

import java.util.Set;

public enum Young {

    BOY("BOY"),
    GIRL("GIRL"),
    ;

    public static final Set<Young> VALUES
            = ImmutableSet.copyOf(values());

    private String value;

    Young(String value) {
        this.value = value;
    }

    public static Young parse(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }

        for (Young young : values()) {
            if (young.equals(value)) {
                return young;
            }
        }

        return null;
    }

    public static boolean isValidYoung(String value) {
        return null != parse(value);
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return value;
    }

}
```

## new

### List

```java
import java.util.Arrays;
List<Integer> intList = Arrays.asList(1, 2, 3);
```

### Set

Set

```java
import com.google.common.collect.Sets;
Set<Integer> intSet = Sets.newHashSet(1, 2, 3);
```

Immutable Set

```java
import com.google.common.collect.ImmutableSet;
public static final Set<String> CONSTANTS = ImmutableSet.of(AAA, SSS);
```

### empty collections

```java
Collections.emptyList();
Collections.emptySet();
Collections.emptyMap();

// generic type
Collections.<String>emptySet();
……
```

## optinal

Optional.ofNullable(…).ifPresent(…);

```java
Optional.ofNullable(map.get("content"))
        .ifPresent(it -> doSomething.withContent((Map<String, Object>) content));
```

## primitive types

long & double

```java
……
    public static void main(String[] args) {
        long midL = 4305912891445794L; // 16 位数
        double midD = (double) midL;
        // long 范围 -9,223,372,036,854,775,808 ~ 9,223,372,036,854,775,807

        double flags = 12;
        while (flags > 1) {
            flags /= 10;
        }
        double midWithFlagsD = midD + flags;
        // 4305912891445794.12

        System.out.println(midWithFlagsD);
        // output 4.305912891445794E15
        // double 有效位数为 15 位
        // 新添加小数部分，被截断了
        // 还是 4305912891445794
    }
……
```

## sort

```java
Map<String, List<Long>> keyValuesMap = getKeyValuesMap();

// 返回结果是乱序的；
// 需要根据输入参数 valueList 中 value 的原始顺序，重新排列好
keyValuesMap.forEach((key, vals) -> Collections.
        sort(vals, Comparator.comparingInt(val -> valueList.indexOf(val))));
```

## split

List Partion

- https://stackoverflow.com/questions/2895342/java-how-can-i-split-an-arraylist-in-multiple-small-arraylists

```java
List<List<String>> listPartions = Lists.partition(list, 50);
```

## sql

### query count

```java
String sql = "SELECT count(*) FROM ? …";
int count = jdbcInfo.getJdbcTemplate().queryForInt(sql, params);
```

## stream

### collect

```java
List<Long> longList = ……;
Map<Double, String> scoreValues = longList.stream()
        .collect(Collectors.toMap(val -> (double) val, val -> String.valueOf(val)));
```

### concat

concat integers with ","

```java
import java.util.stream.Collectors;
Set<Integer> intSet = Sets.newHashSet(1, 2, 3);
String str = intSet.stream()
        .map(elem -> String.valueOf(elem)) // cast Integer to String
        .collect(Collectors.joining(",")); // concat with charactor ","
```

### filter

filter blank string

```java
List<String> list0 = Arrays.asList("a", "", "b");
List<String> list1 = list0.stream()
        .filter(Objects::isnull)
        .filter(Objects::nonNull) // or
        .filter(StringUtils::isNotBlank) // or
        .collect(Collectors.toList());
```

### toArray

```java
return wordCountMap.entrySet().stream()
        .filter(entry -> entry.getValue() == 1)
        .map(Map.Entry::getKey)
        .collect(Collectors.toList())
        .toArray(new String[0]);
        // 关键点 new String[0]
        // 实际长度比较大，也会适应到指定的长度（震惊）！

        /**
         * 解释摘要：<T> T[] toArray(T[] a);
         * ……
         * Otherwise, a new
         * array is allocated with the runtime type of the specified array and
         * the size of this list.
         * ……
         */

```

## executor ( TODO )

References

- https://blog.csdn.net/chzphoenix/article/details/78968075
- Java并发编程：线程池的使用 - Matrix海子 - 博客园 : https://www.cnblogs.com/dolphin0520/p/3932921.html
- https://blog.csdn.net/wqh8522/article/details/79224290

# StringParsableUtils

```java

package xyz.icehe.response;

import com.alibaba.common.lang.StringUtil;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Function;

@UtilityClass
public class StringParsableUtils {

    public boolean isInteger(String string) {
        return isStringParsable(string, Integer::parseInt);
    }

    public boolean isLong(String string) {
        return isStringParsable(string, Long::parseLong);
    }

    public boolean isDouble(String string) {
        return isStringParsable(string, Double::parseDouble);
    }

    public boolean isDateTime(String string) {
        return isStringParsable(string, LocalDateTime::parse);
    }

    private <T> Boolean isStringParsable(
            String string, Function<? super String, T> parsingFunction) {
        return parseByFunction(string, parsingFunction) != null;
    }

    private <T> T parseByFunction(
            String string, Function<? super String, T> parsingFunction) {

        Objects.requireNonNull(parsingFunction, "parsingFunction must not be null";

        if (StringUtil.isBlank(string)) {
            return null;
        }

        try {
            return parsingFunction.apply(string);
        } catch (Exception e) {
            return null;
        }
    }
}

```