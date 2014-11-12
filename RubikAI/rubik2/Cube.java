package rubik2;

import java.util.BitSet;

public class Cube {
	public BitSet[] state;

	public void print() {
		System.out.println(state[0].toString());
		System.out.println(state[1].toString());
		System.out.println(state[2].toString());
		System.out.println(state[3].toString());
		System.out.println(state[4].toString());
		System.out.println(state[5].toString());
		System.out.println(state[6].toString());
		System.out.println(state[7].toString());
		
		System.out.println("-------------");
		
		System.out.println(state[8].toString());
		System.out.println(state[9].toString());
		System.out.println(state[10].toString());
		System.out.println(state[11].toString());
		System.out.println(state[12].toString());
		System.out.println(state[13].toString());
		System.out.println(state[14].toString());
		System.out.println(state[15].toString());
		System.out.println(state[16].toString());
		System.out.println(state[17].toString());
		System.out.println(state[18].toString());
		System.out.println(state[19].toString());
		System.out.println("\n\n");

	}
}

