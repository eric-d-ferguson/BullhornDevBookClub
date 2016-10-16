#Chapter 2 - Subtyping and Wildcards

###*Interesting Topics*

#####The Get And Put Principle
- use an _extends_ wildcard when you only get values out of a structure
- use a _super_ wildcard when you put
- use neither when you need to get and put

#####Subtype Pitfalls with Collections
- Even though Integer is a subtype of Number... "List< Integer>" IS NOT a subtype of "List< Number>" 

#####Greatest argument for using Collections over Arrays
- Issues from poorly written code are caught at compile time (compiler enforces type rules)
- Array issues aren't often caught until runtime (i.e. data store exceptions)

#####But... when to use Arrays
- when storing/iterating over primitive types... much more efficient because no boxing/unboxing 

#####Wildcards are awesome... but important to know when you can't use them
- Top Level Instance Creation --> List< ?> list = new ArrayList<?>();
- Generic Method Calls that expect a type --> List< ?> = SomeClass.<?>someMethod();
- Class Supertypes --> class MyClass extends ArrayList<?> 