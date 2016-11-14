# Chapter 6: [Benchmarking](https://github.com/getify/You-Dont-Know-JS/blob/master/async%20%26%20performance/ch6.md)
### TLDR of chapter 6: Web workers are cool, and there are other ways to improve program performance.


###Effective Benchmarking
* Unless you're really into statistics, you might as well use the Benchmark.js library
* It's easy to write flawed tests so be very careful when writing them
* Pay attention to the big picture, rather than microperformance

###[Benchmark.js](https://developers.google.com/v8)
Statistically sound and easy to use

```javascript
function foo() {
    // operation(s) to test
}

var bench = new Benchmark(
    "foo test",             // test name
    foo,                    // function to test (just contents)
    {
        // ..               // optional extra options (see docs)
    }
);

bench.hz;                   // number of operations per second
bench.stats.moe;            // margin of error
bench.stats.variance;       // variance across samples
// ..
```

###[jsPerf.com](http://jsperf.com)
To get valuable, reliable conclusions, you need to run tests in different environments.

###[V8 JavaScript Engine](https://developers.google.com/v8)
What is the V8 Javascript engine? V8 is Google's open source high-performance JavaScript engine, written in C++ and used in Google Chrome, the open source browser from Google. It has seen use in many other projects, such as Couchbase, MongoDB and Node.js that are used server side.

Some commonly cited examples (https://github.com/petkaantonov/bluebird/wiki/Optimization-killers) for v8:
 * Don't pass the arguments variable from one function to any other function, as such "leakage" slows down the function implementation.
 * Isolate a try..catch in its own function. Browsers struggle with optimizing any function with a try..catch in it, so moving that construct to its own function means you contain the de-optimization harm while letting the surrounding code be optimizable.

 ###Focusing on the Big Picture
 > Programmers waste enormous amounts of time thinking about, or worrying about, the speed of noncritical parts of their programs, and these attempts at efficiency actually have a strong negative impact when debugging and maintenance are considered. We should forget about small efficiencies, say about 97% of the time: premature optimization is the root of all evil. Yet we should not pass up our opportunities in that critical 3%.
 -Donald Knuth
* Basically, if your code is critical path, you should optimize it. Otherwise, don't waste your time.


###Tail Call Optimization
> a "tail call" is a function call that appears at the "tail" of another function, such that after the call finishes, there's nothing left to do (except perhaps return its result value).
```javascript
function foo(x) {
    return x;
}

function bar(y) {
    return foo( y + 1 );    // tail call b/c after foo(..) finishes, bar(..) is also finished except in this case returning the result of the foo(..) call
}

function baz() {
    return 1 + bar( 40 );   // not tail call b/c bar(40) is not a tail call because after it completes, its result value must be added to 1 before baz() can return it.
}

baz();                      // 42
```

> Recursion is a hairy topic in JS because without TCO, engines have had to implement arbitrary (and different!) limits to how deep they will let the recursion stack get before they stop it, to prevent running out of memory. With TCO, recursive functions with tail position calls can essentially run unbounded, because there's never any extra usage of memory!