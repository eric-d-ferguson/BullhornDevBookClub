# Chapter 1: [Asynchrony: Now & Later](https://github.com/getify/You-Dont-Know-JS/blob/master/async%20%26%20performance/ch1.md)
### TLDR of chapter 1: In JS, things happen now and later, not all at once - there is one single threaded event loop, and all your functions have to get in line, like middle school kids in a lunch line.

JS programs are almost always broken into chunks where the first chunk runs now and the next chunk runs later, in response to an event.
```js
function now() {
    return 21;
}

function later() {
    answer = answer * 2;
    console.log( "Meaning of life:", answer );
}

var answer = now();

setTimeout( later, 1000 ); // Meaning of life: 42
```


###Event Loop: A simplistic visualization
```js
// `eventLoop` is an array that acts as a queue (first-in, first-out)
var eventLoop = [ ];
var event;

// keep going "forever"
while (true) {
    // perform a "tick"
    if (eventLoop.length > 0) {
        // get the next event in the queue
        event = eventLoop.shift();

        // now, execute the next event
        try {
            event();
        }
        catch (err) {
            reportError(err);
        }
    }
}
```

###Run-to-completion behavior - foo() can't interupt bar(), and bar() can't interupt foo()

So, for the following code, there are two possible outcomes depending on the order the functions are executed - one result if foo() gets called first, and the other result if bar() gets called first. Either way, the statements within the functions won't be conflated with each other (because single threaded).

```js
var a = 1;
var b = 2;

function foo() {
    a++;
    b = b * a;
    a = b + 3;
}

function bar() {
    b--;
    a = 8 + b;
    b = a * 2;
}

// ajax(..) is some arbitrary Ajax function given by a library
ajax( "http://some.url.1", foo );
ajax( "http://some.url.2", bar );
```

```js
/* Outcome 1, foo happens first then bar  */
foo();
bar();
a; // 11
b; // 22
```

```js
/* Outcome 2, bar happens first then foo*/
bar();
foo();
a; // 183
b; // 180
```
Hey, that's a race Condition -  AKA, function-ordering nondeterminism.
Nondeterminism isn't inherently bad - it can be perfectly acceptable, as long as the indeterminate functions don't interact.

When indeterminate functions DO interact, you need to explicitly control those interactions. Here are two simple examples:

```js
//Gate Example
var a, b;

function foo(x) {
    a = x * 2;
    if (a && b) {
    //^^ That's a gate - need both variables to be defined to get through.
        baz();
    }
}

function bar(y) {
    b = y * 2;
    if (a && b) {
        baz();
    }
}

function baz() {
    console.log( a + b );
}

// ajax(..) is some arbitrary Ajax function given by a library
ajax( "http://some.url.1", foo );
ajax( "http://some.url.2", bar );
```
```js
//Latch example
var a;

function foo(x) {
    if (a == undefined) {
    //^^ that's a latch - this would only resolve to true the first time (and only that time)
        a = x * 2;
        baz();
    }
}

function bar(x) {
    if (a == undefined) {
        a = x / 2;
        baz();
    }
}

function baz() {
    console.log( a );
}

// ajax(..) is some arbitrary Ajax function given by a library
ajax( "http://some.url.1", foo );
ajax( "http://some.url.2", bar );
```


###ES6 Jobs & the Job Queue -  later, but as soon as possible.
the asynchronous behavior of Promises is based on Jobs -- more on that in chapter 3


### Review of Chapter from Kyle Simpson - he keeps it short and sweet:

A JavaScript program is (practically) always broken up into two or more chunks, where the first chunk runs now and the next chunk runs later, in response to an event. Even though the program is executed chunk-by-chunk, all of them share the same access to the program scope and state, so each modification to state is made on top of the previous state.

Whenever there are events to run, the event loop runs until the queue is empty. Each iteration of the event loop is a "tick." User interaction, IO, and timers enqueue events on the event queue.

At any given moment, only one event can be processed from the queue at a time. While an event is executing, it can directly or indirectly cause one or more subsequent events.

Concurrency is when two or more chains of events interleave over time, such that from a high-level perspective, they appear to be running simultaneously (even though at any given moment only one event is being processed).

It's often necessary to do some form of interaction coordination between these concurrent "processes" (as distinct from operating system processes), for instance to ensure ordering or to prevent "race conditions." These "processes" can also cooperate by breaking themselves into smaller chunks and to allow other "process" interleaving.

