#Chapter 8 - Effective Generics

###*Discussion Topics*

#####Take Care when Calling Legacy Code
- checkedLists/checkedCollections --> catch Class Cast exceptions easier
```java
    List<Integer> aList = new ArrayList<Integer>();
    List<Integer> view = Collections.checkedList(aList, Integer.class);
```

#####Specialize to Create Reifiable Types
- not to much here.. just know that you can create "Specialized" objects either by wrapping or extending.
- not sure what you gain from this... other than not having Parameter Typed Objects
    - i.e. List<'String'> and become ListString
 
#####Maintain Binary Compatibility
- nothing to good here... anyone?
 