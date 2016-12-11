# Chapter 2: Built In Directives

* ngIf - if false, will remove an element from the DOM
* ngSwitch - basically syntactic sugar for using a ```switch``` statement in your markup (render different element based on case, can provide a default)
* ngStyle - use to set style properties on a component
* ngClass - use to apply classes
* ngFor - repeats a dom element, each time passing in a different value from an array Usage:```*ngFor="let item of items".``` You can also access the index: ```*ngFor="let c of cities; let num = index"```
* ngNonBindable - tells ng2 not to compile or bind a particular section