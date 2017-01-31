# Chapter 6: Routing

* Client side routing: enables bookmarking, refreshing, etc within a single page app
* Configuring routing in angular2 - routes (describes applications routes), routerOutlet ("placeholder" component that shows angular where to put the content of each route), RouterLink (directive used to link to routes)
* use route params for something like a record page -- ```record/:id```
* use query params for optional things
```javascript
//Record.app.routes.ts
export const routes: Routes = [
    {
        path: '',
        component: RecordApp,
        children: [
            { path: 'overview', component: OverviewPage, data: { preload: true } },
            { path: 'edit', component: EditTab, resolve: { entity: EditEntityResolve }, canDeactivate: [CanDeactivateEdit] },
            { path: 'track-edit', component: TrackEnabledEditTab, resolve: { entity: EditEntityResolve }, canDeactivate: [CanDeactivateEdit] },
            { path: 'activity', component: ActivityPage, data: { preload: true } },
            { path: 'email', component: EmailTab },
            { path: 'work-history', component: WorkHistory },
            { path: 'education', component: Education },
            { path: 'references', component: References },
            { path: 'notes', component: NotesPage },
            { path: 'contacts', component: ContactsPage },
            { path: 'files', component: FilesTab },
            { path: 'submission-workflow', component: SubmissionWorkflowPage },
            { path: 'blueprint', component: BlueprintPage },
            { path: 'linkedin', component: LinkedInPage },
            { path: 'custom', component: CustomTab },
            {
                path: '',
                redirectTo: 'overview',
                pathMatch: 'full'
            }
        ]
    }
];

export const routing = RouterModule.forChild(routes);

//part of Record.app.html
<main class="{{ config.color }}" [class.content-hidden]="this.animationStates.header.content === 'inactive'">
    <router-outlet></router-outlet>
    <layout-menu [routes]="routes" (change)="checkTabWidth()"></layout-menu>
</main>
```

#Additional Reading:
* [Routing URLs in Static Apps](https://staticapps.org/articles/routing-urls-in-static-apps/)
* [Guide to Routing](https://angular.io/docs/ts/latest/guide/router.html)
