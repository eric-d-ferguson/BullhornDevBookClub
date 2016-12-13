# Chapter 3: How Angular Works

* **Angular 2 Application**
  * A tree of components. The top level component is what the browser bootstraps
* **Composability**
  * Ability to build larger components from smaller ones
* **Decorators**
  * Decorators add meta data to the classes
   ```typescript
   @Component({
      selector: 'main-app',
      template: `<main-app>{{stuff}}</main-app>`
      });
   class MainApp {
   		let stuff: string = "Some app";
   }
   ```
   * Here the `@Component` adds component metadata to the class
* **`selector` keyword**
  * This keyword determines the tag name of the component in the HTML rendering
* **`template` or `templateUrl` keyword**
  * This keyword declares the HTML template the component will have
  ```typescript
  template: `<main-app></main-app>`
  ```
  ```typescript
  templateUrl: 'app/main-app.component.html'
  ```
  * `template` takes a string as an argument whereas `templateUrl` takes an HTML file.
* **Template binding**
  * Angular can evaluate an expression or value in the view using `{{...}}`
  * The content within the braces will be evaluated
* **Inputs**
  * `[brackets]` pass inputs to a component
  ```html
  <sub-component [inputVariable]="beingSent"></sub-component>
  ```
  * Here, the `sub-component` component is taking an input with the value `beingSent`
  * The value `beingSent` is passed from the parent component into this sub-component
  * Defined in the application with:
  ```typescript
  @Component({
      ...
    inputs: ['inputVariable']  
  })
  ```
  * `[brackets]` can also be used to set other data in the rendering
  ```html
  <div [class.green]="returnsTrue()"></div>
  ```
  * If evaluated `true` this will bind the class `green` to the `div` tag
  ```html
  <img [src]="someExpression()">
  ```
  * This will evaluate `someExpression()` and assign it to the `src` attribute once resolved
* **Outputs**
  * `(parenthesis)` are used to send data out of components
  ```html
  <main-app (actionWeListenTo)="functionWeWillCall()"></main-app>
  ```
  * Here the `(actionWeListenTo)` is the name of the action that we are waiting to be fulfilled
  * `functionWeWillCall()` is the name of the function we will call in the event of this action
  * Defined in the application with
  ```typescript
  @Component({
      ...
      outputs: ['actionWeListenTo']
  })
  ```