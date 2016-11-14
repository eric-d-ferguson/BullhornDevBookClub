package java_generics.chapter2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SubtypingAndWildcards {

    public static void main(String[] args) {

    	wildcardsWithSuper();

	    // GET-PUT PRINCIPLE

	    // Any subtype of number works
	    sum(Arrays.asList(1,2,3));       // Integer
	    sum(Arrays.asList(2.78, 3.14));  // Double

	    // Any SuperType of Integer works
	    count(new ArrayList<Integer>(), 5); // Integer
	    count(new ArrayList<Number>(), 5);  // Number
	    count(new ArrayList<Object>(), 5);  // Object

    }

    private static void wildcardsWithSuper() {

    	List<? extends Serializable> objects = Arrays.asList(2, 3.14, "four");
	    List<Integer> integers = Arrays.asList(5, 6);

	    // this won't work
	    // Collections.copy(integers, objects);

	    // but this will... see method arguments (objects is supertype of Integer)
	    Collections.copy((List<? super Integer>) objects, integers);

	    assert objects.toString().equals("[5, 6, four]");

    }

    private static double sum(Collection<? extends Number> numbers) {
    	double sum = 0.0;
	    for (Number number : numbers) {
	    	sum += number.doubleValue();
	    }
	    return sum;
    }

    private static void count(Collection<? super Integer> integers,
                              int n) {
    	for (int i = 0; i < n; i++) {
    		integers.add(i);
	    }
    }

}
