# Chapter 5: [Program Performance](https://github.com/getify/You-Dont-Know-JS/blob/master/async%20%26%20performance/ch5.md)
### TLDR of chapter 5: Web workers are cool, and there are other ways to improve program performance.


###Web Workers
* Awesome for handing off long-running or resource-intensive tasks to a different thread, leaving the main UI thread more responsive
* Browser Feature (added to the web platform circa HTML5)
* A web worker is a separate instance of the JS engine on its own thread that allows you to run tasks in parallel (type of parallelism is called "task parallelism," as the emphasis is on splitting up chunks of your program to run in parallel.)
* After instantiating a web worker, he browser will then spin up a separate thread and let that file run as an independent program in that thread.
* Workers do not share any scope or resources with each other or the main program

Instantiating a web worker:
```js
var w1 = new Worker( "http://some.url.1/mycoolworker.js" );
//note: this url should point to the location of a JS file - not an HTML page!

```
Communication between the main program and the web worker is done via messaging:
```js
//inside main.js
w1.addEventListener( "message", function(evt){
    // evt.data
} );

w1.postMessage( "something cool to say" );


// inside mycoolworker.js

addEventListener( "message", function(evt){
    // evt.data
} );

postMessage( "a really cool reply" );

```

You can load outside scripts, share workers (in some browsers), and transfer data different ways (<- which is important b/c scope is not shared).

###SIMD
SIMD proposes to map CPU-level parallel math operations to JavaScript APIs for high-performance data-parallel operations, like number processing on large data sets.

###asm.js
asm.js describes a small subset of JavaScript that avoids the hard-to-optimize parts of JS (like garbage collection and coercion) and lets the JS engine recognize and run such code through aggressive optimizations.

