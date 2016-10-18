# Chapter 2: [Promises](https://github.com/getify/You-Dont-Know-JS/blob/master/async%20%26%20performance/ch3.md)
### TLDR of chapter 3: Promises are awesome. Use them. They solve the inversion of control issues that plague us with callbacks-only code.

### What's a promise?
*A Promise is a proxy for a value not necessarily known when the promise is created. It allows you to associate handlers to an asynchronous action's eventual success value or failure reason. This lets asynchronous methods return values like synchronous methods: instead of the final value, the asynchronous method returns a promise for the value at some point in the future.*
 [MDN on Promises](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Promise)

Promises handle waiting on values for you - no need to check for undefined like in the callbacks example.
```js
function add(xPromise,yPromise) {
    // `Promise.all([ .. ])` takes an array of promises,
    // and returns a new promise that waits on them
    // all to finish
    return Promise.all( [xPromise, yPromise] )

    // when that promise is resolved, let's take the
    // received `X` and `Y` values and add them together.
    .then( function(values){
        // `values` is an array of the messages from the
        // previously resolved promises
        return values[0] + values[1];
    } );
}

// `fetchX()` and `fetchY()` return promises for
// their respective values, which may be ready
// *now* or *later*.
add( fetchX(), fetchY() )

// we get a promise back for the sum of those
// two numbers.
// now we chain-call `then(..)` to wait for the
// resolution of that returned promise.
.then( function(sum){
    console.log( sum ); // that was easier!
} );
```

###Handling Success and Failure
A Promise's then(..) call can take two functions - one for fulfillment/success, and one for failure/rejection. Promises can be reasoned about as a temporal flow-control mechanism for an async task. Do this, then do this if success or do this if failure. The 'then' (or the resolution) is triggered by a "completion event." Once resolved, a Promise stays resolved - it becomes an immutable value, and as such, it can't be modified accidentally or maliciously. You can observe a promise as many times as you'd like - the state won't ever change, once resolved.


```js
add( fetchX(), fetchY() )
.then(
    // fullfillment handler
    function(sum) {
        console.log( sum );
    },
    // rejection handler
    function(err) {
        console.error( err ); // bummer!
    }
);
```
###Thenable Duck Typing
Basically, there are a bunch of promise libraries and a bunch of pre-ES6 native Promises legacy stuff that make things a bit more complicated. And so, it was decided that you could recognize a Promise (or something that behaves like a Promise) if it is an object or function which has a then(..) method. Kyle says - "It is assumed that any such value is  Promise-conforming thenable." It it looks like a duck and quacks like a duck, it must be a duck. Likewise, if it's an object with a then method, it must be a thenable.

There are some dangers here - as as any object with a then method is treaded as a thenable, and this has made some pre-ES6 non-Promise libraries rename their then(..) methods to avoid confusion, which is confusing.

###Promises & Trust
One of the problems with callbacks was that you couldn't always trust them. Promises address those concerns.

* ~~Call the callback too early~~ When you call then(..) on a Promise, even if that Promise was already resolved, the callback you provide to then(..) will always be called asynchronously. Even an immediately resolved Promise cannot be observed synchronously. And in doing so, we prevent Zalgo.
* ~~Call the callback too late~~ When a Promise is resolved, all then(..) registered callbacks on it will be called, in order, immediately at the next asynchronous opportunity (again, see "Jobs" in Chapter 1), and nothing that happens inside of one of those callbacks can affect/delay the calling of the other callbacks.
* ~~Never calling the callback~~ First, nothing (not even a JS error) can prevent a Promise from notifying you of its resolution (if it's resolved). If you register both fulfillment and rejection callbacks for a Promise, and the Promise gets resolved, one of the two callbacks will always be called. You can handle the Promise never being resolved with a "race".
* ~~Call the callback too few or too many times~~ A Promise should only be called once, and so Promises are defined so that they can only be resolved once. You can observe that resolution multiple times, so there can still be user error.
* ~~Fail to pass along any necessary environment/parameters~~
* ~~Swallow any errors/exceptions that may happen~~


**Promises use callbacks**
One common pattern is allowing split callbacks, an onSuccess and an onFailure method. Usually, onFailure() is optional, and if not passed in, any errors will be swallowed.


**You still have to think about error handling, and there are still problems there.**
Promise error handling is unquestionably "pit of despair" design. By default, it assumes that you want any error to be swallowed by the Promise state, and if you forget to observe that state, the error silently languishes/dies in obscurity -- usually despair.

You can use a catch, which helps but doesn't fix the problems - what if the handleErrors function errors?

```js
var p = Promise.resolve( 42 );

p.then(
    function fulfilled(msg){
        // numbers don't have string functions,
        // so will throw an error
        console.log( msg.toLowerCase() );
    }
)
.catch( handleErrors );
```

Some promise libraries use a global unhandled rejection handler.

Chrome and Firefox both attempt to log those in the console. Browsers have a unique capability that our code does not have: they can track and know for sure when any object gets thrown away and garbage collected. So, browsers can track Promise objects, and whenever they get garbage collected, if they have a rejection in them, the browser knows for sure this was a legitimate "uncaught error," and can thus confidently know it should report it to the developer console.


**Default handlers if you don't provide a success or fail**
```js
var p = Promise.resolve( 42 );

p.then(
    // assumed fulfillment handler, if omitted or
    // any other non-function value passed
    // function(v) {
    //     return v;
    // }
    null,
    function rejected(err){
        // never gets here
    }
);
```


###Promise Patterns
**Promise.all([..])**
You can pass in an array of patterns, and Promise.all will wait for all the Promises in the array to resolve. This is a gate - a "gate" is a mechanism that waits on two or more parallel/concurrent tasks to complete before continuing. It doesn't matter what order they finish in, just that all of them have to complete for the gate to open and let the flow control through

The main promise returned from Promise.all([ .. ]) will only be fulfilled if and when all its constituent promises are fulfilled. If any one of those promises instead is rejected, the main Promise.all([ .. ]) promise is immediately rejected, discarding all results from any other promises.

**Promise.race([..])**
You can pass in an array of patterns, and Promise.race will resolve when the first Promise crosses the finish line. Similar to Promise.all([ .. ]), Promise.race([ .. ]) will fulfill if and when any Promise resolution is a fulfillment, and it will reject if and when any Promise resolution is a rejection.

###Promise API Recap
```js
//Promise constructor
var p = new Promise( function(resolve,reject){
    // `resolve(..)` to resolve/fulfill the promise
    // `reject(..)` to reject the promise
} );

//These two are equivalent - the second is a shortcut for creating an already rejected promise
var p1 = new Promise( function(resolve,reject){
    reject( "Oops" );
} );

var p2 = Promise.reject( "Oops" );

//These two are equivalent - the second is a shortcut for creating an already resolved promise
// note that Promise.resolve(..) unwraps thenable values and in that case, the Promise retuned adopts the final resolution of the thenable it is passed.
var fulfilledTh = {
    then: function(cb) { cb( 42 ); }
};
var rejectedTh = {
    then: function(cb,errCb) {
        errCb( "Oops" );
    }
};

var p1 = Promise.resolve( fulfilledTh );
var p2 = Promise.resolve( rejectedTh );

// `p1` will be a fulfilled promise
// `p2` will be a rejected promise
```

*Promises are a pattern that augments callbacks with trustable semantics, so that the behavior is more reason-able and more reliable. By uninverting the inversion of control of callbacks, we place the control with a trustable system (Promises) that was designed specifically to bring sanity to our async.*
