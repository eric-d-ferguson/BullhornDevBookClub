#Chapter 3 - Comparison and Bounds

###*Discussion Topics*

#####Comparable Interface
- Used to compare two objects using "Natural Order" with .compareTo()
- Most java classes already implement this (Integer, String, Float, etc)
- This can be overriden

#####Be Careful when overriding CompareTo
- Good practice to continue Java's initial intentions with return values of -1, 0, 1
- Potential for wrong answers when comparing large negative numbers to large positive

```java
class BadIntegerCompareExample 
    implements Comparable<Integer> {
    
    @Override
    public int compareTo(Integer o) {
        // don't do something like this
        return this.value - o;
    }
}
```

#####Signatures for methods should be as general as possible to better maximize utility
- Huh? Not sure I agree with this... I tend to expose as needed
- Thoughts?

#####Comparator... or the other way to order objects "unnaturally"
- A way to order things that do not implement Comparable
```java
new Comparator<String>() {
    @Override
    public int compare(String s1, String s2) {
        return s1.length() < s2.length() ? -1 :
                s1.length() > s2.length() ? 1 :
                        s1.compareTo(s2);
    }
};
```

#####Enums using Comparable
- not much to take in here... just know that Enums implement "Comparable<'T'>" where T is your Enum 
- in other words... you an only compare enums of the same type.

#####Multiple Bounds
- oh... btw.. you can do this.. 
```java
public static <S extends Readable & Cloneable, T extends Appendable & Cloneable> void copy(S src, T trg) {
.......
//Some copy logic
.......
} 



