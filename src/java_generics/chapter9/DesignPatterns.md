#Chapter 9 - Design Patterns

###*Quick Rundown*
- Goes over different common design patterns implemented with Generics
    - Visitor, Interpreter, Function, Strategy, Subject-Observer

###*Discussion Topics*

#####Visitor
- possible to provide new operations w/o modifying the classes that define the data structure
- example in the book sucked... real life analogy would be Shopping in the Supermarket
    - Shopping Cart is your set of elements
    - when you get to the checkout, the cashier acts as a Visitor, taking the objects in your cart, handles them appropriately (i.e. some have tags, some are priced by weight) and gives you a total
- _Code Example -->  EntityInfoBuilder.java_ 
    - Doesn't use generics.. but is a good example of a Visitor in Bullhorn
