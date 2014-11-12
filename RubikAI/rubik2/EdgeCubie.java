package rubik2;

import java.util.BitSet;

public class EdgeCubie {
	public BitSet state = new BitSet(5);
	
	public void print() {
		System.out.println(state.toString());
	}

}
