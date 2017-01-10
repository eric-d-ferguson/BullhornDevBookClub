#Chapter 13 - Sets

###*Discussion Topics*
- What is your goto set implementation... and why?
- What is one set that you think we should be using more of... and why?

#####HashSet
- your basic run-of-the-mill set
- as long as there are no collisions, the cost of inserting/retrieving is constant
- since elements are stored in order via their hash.. order can't be guaranteed
- major downside is iterating over it... since the hash table will contain buckets without elements (empty buckets).. based on it's initial load
    - load can be set at construction which will improve performance
        - but Josh Bloch says don't do this

#####LinkedHashSet
- inherits from above... with the sole difference that elements will be iterated over in order of their insertion
- pro-tip: if need to iterate over a set frequently... use this
    - you won't run into the empty bucket scenario since a linked list is stored with this set that points to the buckets with elements
    
#####CopyOnWriteArraySet
- inherits from set... but backed by an immutable array
- provides thread safety during reads 
    - since it can't be modified after creation it can be used by any thread w/o danger
    
#####EnumSet
- set that takes advantage of knowing the number of possible elements (since they all must be of a declared enum)
- order is determined by the order in which they are declared

#####SortedSet
- Subinterface of Set 
- guarantees traversal in element order (either natural order... or defined using Comparable interface)
- you can get first and last items too with e.first() or e.last()
- you can also get Range Views

#####NavigableSet
- Created to supplement SortedSet above... should use this one instead
- allows you to traverse the set in reverse order 

#####TreeSet
- you know them... you love them
- they allow you to retrieve items in order... and by content
    - giving you the best of both worlds