#Chapter 15 - Lists

#####Introduction
- allows duplicates (unlike set) and gives full visibility/control over ordering (unlike queues)
- Positional Access
    - add, addAll, get, remove, set (all allow this)
- Search
    - indexOf, lastIndexOf (returns -1 if not present)
- Range-View
    - subList - results in a view of the original list 
        - careful... if you add/remove to the original list and then access the view you will get a Concurrent Modification Exception
- Iteration
    - listIterator allows you add/remove items from a list while navigating it

#####ArrayList
- list backed by an array... most common implementation
- once created.. cannot be resized
    - if reaches max... a new one is created and copied over
- initial size is 10... if copied over it's 110% that size
    
#####LinkedList
- not good for random access.... since it has to iterate over to access
- good for adding/removing anywhere other than the end

#####CopyOnWriteArrayList
- Thread safety with fast read access 
- any edits creates a new list

#####*Discussion Topics*
- What would be a good scenario to use each an ArrayList and a LinkedList