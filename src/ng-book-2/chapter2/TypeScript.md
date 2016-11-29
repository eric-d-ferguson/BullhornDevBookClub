# Chapter 2: Typescript

* superset of ES6
* Collaboration from microsoft and google
* Angular2 is written in Typescript

###Benefits of TypeScript
* Types (note: they are optional)
```typescript
function greetText(name: string): string {
  return "Hello" + name;
}
```
* If you omit typing a variable, its default type is 'any' which allows it to receive any kind of value
* Has all the stuff ES6 has -- classes, inheritance, utilities like fat arrow syntax and template strings
* constructors - in TypeScript, you can have only only one constructor per class, whereas with ES6, you can have more than one constructor as long as they have a different number of parameters
