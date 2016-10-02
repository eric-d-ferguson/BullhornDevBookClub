package java_generics.chapter1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Introduction {

    public static void main(String[] args) {

    	// before/after generics
    	summingNumbers();

	    // Boxing/Unboxing
	    sumIntegerShittily(Arrays.asList(1,2,3));
	    sumBetter(Arrays.asList(1,2,3));

	    // Generic Methods are the dopest
	    List<Integer> integerList = toList(1, 2, 3);
	    List<String> stringList = toList("1", "2", "3");
    }

    private static void summingNumbers() {

	    //BEFORE GENERICS - no way to indicate type of list... coder responsible for type
    	List ints1 = Arrays.asList(new Integer[] {
    		new Integer(1), new Integer(2), new Integer(3)
	    });
	    int sum1 = 0;
	    for (Iterator it = ints1.iterator(); it.hasNext();) {
	    	int n = ((Integer)it.next()).intValue();
		    sum1 += n;
	    }
	    assert sum1 == 6;

	    // WITH GENERICS
	    List<Integer> ints2 = Arrays.asList(1, 2, 3);
	    int sum2 = 0;
	    for (int n : ints2) {
		    sum2 += n;
	    }
	    assert sum2 == 6;
    }

    public static Integer sumIntegerShittily(List<Integer> ints) {

    	// 60% Slower than sumBetter()... due to unboxing every loop iteration
    	Integer s = 0;
	    for (Integer n : ints) {
	    	s += n;
	    }
	    return  s;
    }

    public static int sumBetter(List<Integer> ints) {
	    int s = 0;
	    for (int n : ints) {
		    s += n;
	    }
	    return  s;
    }

	public static <T> List<T> toList(T... things) {
		List<T> list = new ArrayList<>();
		for (T aThing : things) {
			list.add(aThing);
		}
		return list;
	}
}
