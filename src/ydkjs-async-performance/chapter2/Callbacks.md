# Chapter 2: [Callbacks](https://github.com/getify/You-Dont-Know-JS/blob/master/async%20%26%20performance/ch2.md)
### TLDR of chapter 2: callbacks are the most fundamental async pattern in JS, but they can get crazy.

Callbacks are a workhorse, but they have some shortcomings. There's the promise (nudge nudge) of something better.

### What's a callback?
*A callback function, also known as a higher-order function, is a function that is passed to another function (let's call this other function “otherFunction”) as a parameter, and the callback function is called (or executed) inside the otherFunction.*
 [Read about call backs from Javascript Is Sexy](http://javascriptissexy.com/understand-javascript-callback-functions-and-use-them/)

When we talked about JS having a now and a later, callbacks are the later - the continuation of the program after some event.

Here's an example:
```js
// A
console.log('we start here...');
setTimeout( function(){
    // C
    console.log("hi, i'm a callback.");
}, 1000 );
// B
console.log('continue on to here..');
```

###Why callbacks (can) suck
Kyle posits that because our brains operate, reason, and plan synchronously, asynchronous code (ergo, callbacks) is hard to reason about. Hard to reason about code is bad code is buggy code.
Nested callbacks can lead to 'callback hell' - callback hell can sometimes be seen in code and called the pyramid of doom (due to the triangular shape of all that nesting). Even formatted prettily, nested callbacks can mean a lot of convoluted logic and a lot of potential for things to go wrong.

###Trying to trust callbacks
Callbacks suffer from *inversion of control* - that is, they pass control of your code to another party. When you pass in a callback, you're trusting a third party (whether it's an external API, some utility you wrote, or even JS"s own setTimeout function) to continue your program via the callback. You're trusting them to execute your code appropriately - at the right time, only once, and without error. That exposes a lot of potential breakdowns along those trust lines.

**Split-callbacks**
One common pattern is allowing split callbacks, an onSuccess and an onFailure method. Usually, onFailure() is optional, and if not passed in, any errors will be swallowed.
```js
function onSuccess(data) {
    console.log( data );
}

function onFailure(err) {
    console.error( err );
}

ajax( "http://some.url.1", onSuccess, onFailure );
```

**Node Style or error-first style**
The first argument is reserved for an error. If success, this argument will be empty/falsy (and any subsequent arguments will be the success data), but if an error result is being signaled, the first argument is set/truthy (and usually nothing else is passed):
```js
function response(err,data) {
    // error?
    if (err) {
        console.error( err );
    }
    // otherwise, assume success
    else {
        console.log( data );
    }
}

ajax( "http://some.url.1", response );

//for example, if successful the call might be
response(null, "yay");

//and on failure it might be
response("eric ferguson is the worst");
```

But sadly, you should still have trust issues with callbacks. These approaches solve the problem of catching an error, but only sort of. If your callback gets called multiple times, you might get both success calls and error calls. Or neither. Or maybe it will never get called at all.

**Always be asyncing. Don't release Zalgo.**

###Related Reading
[Callback Hell](http://callbackhell.com/)
[Understand Callback Functions and Use Them](http://javascriptissexy.com/understand-javascript-callback-functions-and-use-them/)