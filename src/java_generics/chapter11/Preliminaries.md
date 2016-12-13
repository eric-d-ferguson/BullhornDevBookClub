#Chapter 11 - Preliminaries

###*Discussion Topics*

#####Iterable and Iterators
- Gives you a uniform method of iterating over a collection
    - rarely used since Java 5 included foreach
    - watch out for ConcurrentModificationException
 ```Java
    for (int x : list) {
        list.remove(x); // throws exception
    }
```

#####Implementations
- 3 main operations that collection interfaces require
    - insertion/removal of elements by position
    - retrieval of elements by content 
    - iteration over elements
- 4 Types of Collection Implementations
    - Arrays
        - fast for accessing elements by position and iterating over them
        - bad for inserting and removing elements at arbitrary position
        - Ex. ArrayList
    - Linked Lists
        - fast for inserting and removal operations
        - bad for accessing elements by position
        - Ex. LinkedList
    - Hash Tables
        - fast for access by content and inserting and removal
        - no access by position
        - Ex. HashSet
    - Trees
        - Relatively fast for inserting and removing and accessing. Can store and retrieve items in sorted order
        - Ex. TreeSet, TreeMap

#####Efficiency and O-Notation
- not going over this... hated it in college... still hate it now

#####Contracts
- Code to interfaces.. not implementations
    - provides maximum flexibility in your implementation
 
#####Synchronization and the Legacy
```Java
class BadStack implements Stack { // Not Thread Safe
    
    private int[] stack;
    private int index;
    
    public BadStack() {
        stack = new int[10];
        index = -1;
    }
    public void push() {
        if (index != stack.length - 1) {
            index++;
            stack[index] = elt;
        }
    }
    public void pop() {
        if (index != -1) {
            index--;
            return stack[index];
        }
    }
}
```
- the above can be easily fixed with synchronized keyword in the methods
```Java
class GoodStack implements Stack { // Thread Safe
    
    private int[] stack;
    private int index;
    
    public GoodStack() {
        stack = new int[10];
        index = -1;
    }
    public synchronized void push() {
        if (index != stack.length - 1) {
            index++;
            stack[index] = elt;
        }
    }
    public synchronized int pop() {
        if (index != -1) {
            index--;
            return stack[index];
        }
    }
}
```
- but wait... you still have to be careful... because this shit ain't cool
```Java
    GoodStack stack = new GoodStack();
    
    // and then later in the code
    if (stack.length > 0) {
        stack.pop(); // not thread safe
    }
```
- you can fix this though... by make your check/act logic atomic  
```Java
    GoodStack stack = new GoodStack();
    
    // and then later in the code
    synchronized(stack) {
        if (stack.length > 0) {
            stack.pop(); // now we good
        }
    }
```
- most of this is legacy... and can easily cause dead-lock situations
- very good to understand the concepts though

#####Concurrent Collections
- Now Java gives us these... which handles concurrent actions