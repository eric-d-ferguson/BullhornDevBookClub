# Chapter 2: [Generators](https://github.com/getify/You-Dont-Know-JS/blob/master/async%20%26%20performance/ch4.md)
### TLDR of chapter 3:

### What's a generator?
*A generator is a special kind of function that can start and stop one or more times, and doesn't necessarily ever have to finish.*

* New in ES6
* Generator Declaration - a * should be between function and the name
```js
function *iAmAGenerator () {
  ///stuff
  yield;
}
```
* Controlled by an iterator object. When you call next(..) on the iterator object, it instructs the generator to advance from it's current location - it will stop at the next yeild or at the end of the generator.


Generators break the expectation that a function will run to completion. They introduce a cooperative concurrency by allowing pause points in a function (notated by `yield`). ""

Here's the ES6 code to accomplish such cooperative concurrency:
```js
var x = 1;

function *foo() {
    x++;
    yield; // pause!
    console.log( "x:", x );
}

function bar() {
    x++;
}
```
How do you run that code so that `bar()` executes at the point of the `yield`?

```js
// construct an iterator `it` to control the generator - does not execute the generator
var it = foo();

// start `foo()` here! this starts the generator and runs up until the yeild statement
it.next();
x;                      // 2
bar();
x;                      // 3
//calling next() again resumes the generator from where it was paused
it.next();              // x: 3
```
###Input and Output
Generators are still function - still accept arguments and return a value. You can also interact with inputs/outputs via `yeild` and `next()`.

Here's an example of passing an argument into `next(..)`:
```js
function *foo(x) {
    var y = x * (yield);
    return y;
}

var it = foo( 6 );

// start `foo(..)`
it.next();

var res = it.next( 7 );

res.value;      // 42
```

Here's an example of outputting a return value from a `yeild`:
```js
function *foo(x) {
    var y = x * (yield "Hello");    // <-- yield a value!
    return y;
}

var it = foo( 6 );

var res = it.next();    // first `next()`, don't pass anything
res.value;              // "Hello"

res = it.next( 7 );     // pass `7` to waiting `yield`
res.value;              // 42
```

*`yield` and `next(..)` pair together as a two-way message passing system during the execution of the generator.*

* The first `next()` call that kicks off the generator cannot accept a value. Any argument passed in would be discarded.


###Multiple Iterators
Basically, there are a bunch of promise libraries and a bunch of pre-ES6 native Promises legacy stuff that make things a bit more complicated. And so, it was decided that you could recognize a Promise (or something that behaves like a Promise) if it is an object or function which has a then(..) method. Kyle says - "It is assumed that any such value is  Promise-conforming thenable." It it looks like a duck and quacks like a duck, it must be a duck. Likewise, if it's an object with a then method, it must be a thenable.

There are some dangers here - as as any object with a then method is treaded as a thenable, and this has made some pre-ES6 non-Promise libraries rename their then(..) methods to avoid confusion, which is confusing.

###Iterables
As of ES6, the way to retrieve an iterator from an iterable is that the iterable must have a function on it, with the name being the special ES6 symbol value Symbol.iterator. When this function is called, it returns an iterator. Though not required, generally each call should return a fresh new iterator.

###Generator Iterator
A generator can be treated as a producer of values that we extract one at a time through an iterator interface's next() calls. A generator is not technically an iterable, though it's similar - when you execute the generator, you get an iterator back.

An example of a generator to continualy generate values:
```js
function *something() {
    var nextVal;

    while (true) {
        if (nextVal === undefined) {
            nextVal = 1;
        }
        else {
            nextVal = (3 * nextVal) + 6;
        }

        yield nextVal;
    }
}
```

Using the generator in a `for..of` loop:
```js
for (var v of something()) {
//  ^^^ call the *something() generator to get its iterator for the for..of loop to use.
// the generator's iterator is also an iterable
    console.log( v );

    // don't let the loop run forever!
    if (v > 500) {
        break;
    }
}
// 1 9 33 105 321 969
```

###Iterating Generators Asynchronously
Generators allow you to use synchronous-looking code to complete tasks asynchronously. This makes code easier to reason about. The asynchrony is abstracted away so we can reason about the code sequentially. See below - "Make an Ajax request, and when it finishes print out the response."
```js
function foo(x,y) {
    ajax(
        "http://some.url.1/?x=" + x + "&y=" + y,
        function(err,data){
            if (err) {
                // throw an error into `*main()`
                it.throw( err );
            }
            else {
                // resume `*main()` with received `data`
                it.next( data );
            }
        }
    );
}

function *main() {
    try {
        var text = yield foo( 11, 31 );
        console.log( text );
    }
    catch (err) {
        console.error( err );
    }
}

var it = main();

// start it all up!
it.next();
```

**Synchronous Error Handling**
The yield-pause nature of generators means that not only do we get synchronous-looking return values from async function calls, but we can also synchronously catch errors from those async function calls! Big win for readability and ease of reasoning.

###Generators and Promises
**The natural way to get the most out of Promises and generators is to yield a Promise, and wire that Promise to control the generator's iterator.** The iterator should listen for the promise to resolve (fulfillment or rejection), and then either resume the generator with the fulfillment message or throw an error into the generator with the rejection reason.

```js
//setting it up
function foo(x,y) {
    return request(
        "http://some.url.1/?x=" + x + "&y=" + y
    );
}

function *main() {
    try {
        var text = yield foo( 11, 31 );
        console.log( text );
    }
    catch (err) {
        console.error( err );
    }
}

//kicking it off
var it = main();

var p = it.next().value;

// wait for the `p` promise to resolve
p.then(
    function(text){
        it.next( text );
    },
    function(err){
        it.throw( err );
    }
);
```

 There are some important details here and it makes sense to use a utility to handle concerns about combining promises and generators - Kyle Simpson has a library called aynquence. And, there are proposals for ES7 `async` and `await`.

###Thunks
Without getting bogged down in the historical nature, a narrow expression of a thunk in JS is a function that -- without any parameters -- is wired to call another function.

Simple thunk:
```js
function foo(x,y) {
    return x + y;
}

function fooThunk() {
    return foo( 3, 4 );
}

// later

console.log( fooThunk() );  // 7
```
Async Thunk:
```js
function foo(x,y,cb) {
    setTimeout( function(){
        cb( x + y );
    }, 1000 );
}

function fooThunk(cb) {
    foo( 3, 4, cb );
}

// later

fooThunk( function(sum){
    console.log( sum );     // 7
} );
```

**Why is he talking about thunks?**
Comparing thunks to promises generally: they're not directly interchangable as they're not equivalent in behavior. Promises are vastly more capable and trustable than bare thunks. But -- they both can be seen as a request for a value, which may be async in its answering.

###Transpilation, Pre-ES6, Polyfills
Go read this part. It's too much to summarize.

*Generators preserve a sequential, synchronous, blocking code pattern for async code, which lets our brains reason about the code much more naturally, addressing one of the two key drawbacks of callback-based async.*
