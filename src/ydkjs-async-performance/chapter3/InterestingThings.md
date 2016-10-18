* Why Promise.resolve instead of Promise.fulfill? Isn't a rejection a resolution as well?
*reject(..) simply rejects the promise, but resolve(..) can either fulfill the promise or reject it, depending on what it's passed. If resolve(..) is passed an immediate, non-Promise, non-thenable value, then the promise is fulfilled with that value.

But if resolve(..) is passed a genuine Promise or thenable value, that value is unwrapped recursively, and whatever its final resolution/state is will be adopted by the promise.*

* It's promises all the way down.
*then(..) and catch(..) also create and return a new promise, which can be used to express Promise chain flow control. If the fulfillment or rejection callbacks have an exception thrown, the returned promise is rejected. If either callback returns an immediate, non-Promise, non-thenable value, that value is set as the fulfillment for the returned promise. If the fulfillment handler specifically returns a promise or thenable value, that value is unwrapped and becomes the resolution of the returned promise.*
