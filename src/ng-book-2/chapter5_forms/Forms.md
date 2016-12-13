# Chapter 5: Forms

* FormControl - represents a single input field and encapsulates the fields value
* FormControl - a group of formControls
```javascript
let personInfo = new FormGroup({
    firstName: new FormControl('Nate'),
    lastName: new FormControl('Murray'),
    zip: new FormControl('63105'),
})

personInfo.value; 
/*** yeilds an object with key/value pairs based on formControls{
firstName: 'Name',
lastName: 'Murray',
zip: '63105'
***/
```
* FormModule - gives us template driven directives like ngMOdel and NgForm
* ReactiveFormsModule - gives us formControl and ngFormGroup
* ````#form="ngForm"```` -> creates a local variable for this view; for instance, can have a click hanlder that takes the form ```(click)=doStuffWithTheForm(form)```


Example of a regular form in angular, which will automatically get NgForm: 
<form #f="ngForm"
 (ngSubmit)="onSubmit(f.value)"   ...

NgForm is automatically attached to
<form> tags (because of the default NgForm selector), which means we don't have to add an
ngForm attribute to use NgForm. **Handy, because we get some useful stuff like the _onSubmit_ for free

But here we're putting ngForm in an attribute (value) tag.
Is this a typo?
No, it's not a typo. If ngForm were the key of the attribute then we would be telling Angular
that we want to use NgForm on this attribute. In this case, we're using ngForm as the
attribute when we're assigning a _reference_. That is, we're saying the value
of the evaluated expression ngForm should be assigned to a local template
variable f'.
ngForm is already on this element and you can think of it as if we are "exporting" this
FormGroup so that we can reference it elsewhere in our view.

!!!There is an exception: NgForm won't be applied
to a <form> that has formGroup. The selector for NgForm is: 

form:not([ngNoForm]):not([formGroup]),ngForm,[ngForm]

This means you could have a form that doesn't get NgForm applied by using the ngNoForm
attribute.