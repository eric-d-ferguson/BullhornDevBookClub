#Chapter 14 - Queues

###*Discussion Topics*
- What's the first real-world scenario you can think of that would likely benefit a queue?

#####Using The Methods of Queue
- adding elements
    - myQueue.add(someObject) // true if added, false if already present, exception if maxed out
    - myQueue.offer(someObject) // true if added, false if queue is maxed out, exception if element is illegal in some way
- retrieving elements 
    - myQueue.element() // retrieves but does not remove, exception if empty
    - myQueue.remove() // retrieves and removes, exception if empty
    - myQueue.peek() // retrieves but does not remove, no exception if empty
    - myQueue.poll() // retrieves and removes, no exception if empty
    
#####PriorityQueue
- either uses natural ordering... or takes a comparator
- careful: 
    - not thread safe
    - no guarantees on how it treats multiple elements with the same values 
        - i.e. if several items are tied for priority... not even the gods know which one will be returned
    
#####ConcurrentLinkedQueue
- thread safe queue... read/rights are done in constant time
    
#####BlockingQueue
- queue that allows you to specify timeouts on all operations

#####Deque
- queue that allows you to insert/remove from head or tail
- all the same methods described above... just add first or last to the ends of them