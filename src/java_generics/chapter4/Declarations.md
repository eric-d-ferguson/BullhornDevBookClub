#Chapter 4 - Declarations

###*Discussion Topics*

#####Constructors
- Type parameters appear in header of generic class... not constructor
- Not required to supply them when creating a new instance... but pretend you are
```java
class MyGenericClass<T, U> {
    private final T first;
    private final U second;
    
    public MyGenericClass(T first, U second) {
        this.first = first;
        this.second = second;
    }
}

// to invoke
MyGenericClass<String, Integer> genericClass = new MyGenericClass<String, Integer>("one", 2);
```

#####Static Members
- can not be generic
- they are independent of any type... and shared across all instances
```java
class ShitDoNotCompile<T> {
    private final T something;
    
    private static final List<T> listOfSomething; // <- illegal
    
    public ShitDoNotCompile(T something) {
        this.something = something;
    }
}
```

#####Nested Classes
- Inherit Type parameters when nested class is not static... otherwise it requires separate types
```java
class NestedClasses<T> {
    private final T something;
    
    public NestedClasses(T something) {
        this.something = something;
    }
    
    class InnerClass<T> {
        private final T nothing;
        
        public InnerClass(T nothing) {
            this.nothing = nothing;
        }
    }
}
```

#####How Erasure Works
- at compile time Java (using erasure) drops all generic types
    - 'List<'String>' becomes just List
- because of this and Java's requirement for distinct methods signatures... you can't do this:
```java
public class NonDistinctMethods {
    public static boolean allZero(List<Integer> ints) {
        for (int anInt : ints) {
            if (anInt != 0) {
                return false;
            }
        }       
        return true;
    }
    
    public static boolean allZero(List<String> strings) {
        for (String s : strings) {
            if (s.length() != 0) {
                return false;
            }
        }
        return true;
    }
}
```
