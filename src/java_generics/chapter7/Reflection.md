#Chapter 7 - Reflection

###*Discussion Topics*

#####Generics for Reflection
- Summed up... Java added a type parameter to the class Class 
- We use this all over core-services
    - All of our meta is built using this. All hibernate entities are tagged with @BullhornEntity. We then use the following to get key components and specifics about the entity.
    - New rest Exposed services also uses reflection for determining RestExposed methods on an entity
- Why is this awesome?
    - No casting needed.... pass the it the class... and you are guaranteed it back
    - heavy lifting for this is all done in Java's reflection framework
    
```JAVA
    // Meta2Service Example
    private String determineFieldMapColumnFromAnnotation(String pname, EntityMeta oemeta)  {
        String ret = null;
        while (ret == null && oemeta != null) {
            BullhornEntity bhe = oemeta.getEntityClass().getAnnotation(BullhornEntity.class);
            // get Parents field maps
            if (bhe != null && bhe.fieldMapColumns().length > 0) {
                for (FieldMapColumn fmc: bhe.fieldMapColumns()) {
                    if (pname.equals(fmc.property())) {
                        ret = fmc.name();
                        break;
                    }
                }
            }
            if (ret == null) {
                oemeta = oemeta.getParentEntityMeta();
            }
        }
        return ret;
    }
```
    
#####Reflection for Generics
- Summed up... Java adds methods and classes that support access to generic types.
- Allows you to print or process detailed information about a class. 
    - i.e. superclass, interfaces, fields, methods, etc
    
 ```JAVA
   // REST Exposed Example
   public static List<RestExposedMethodPojo> getAllMethods(Class clazz) {
   
        final List<RestExposedMethodPojo> results = new ArrayList<RestExposedMethodPojo>();
        if (clazz == null) {
            return results;
        }

        for (Method method : clazz.getMethods()) {
            for (Annotation annotation : method.getDeclaredAnnotations()) {
                if (annotation.annotationType().equals(RestExposedMethod.class)) {
                    RestExposedMethod restExposedMethod = (RestExposedMethod) annotation;
                    results.add(new RestExposedMethodPojo(restExposedMethod.name(),
                                                          restExposedMethod.type()));
                }
            }
        }
        return results;
    }
   
 ```


    
        
    