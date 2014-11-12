package rubik2;

import java.util.BitSet;

public class Test {
	public static void main(String [ ] args) {

		BitSet test = new BitSet(5);
		test.set(4);
		
		setThree(test);
		
		if (test.get(3)) {
			System.out.println("Woo");
		} else {
			System.out.println("Awww");
		}
		
		
		
	}
	
	public static void setThree(BitSet state) {
		state.set(3);
	}

}
