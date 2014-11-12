package rubik2;

import java.util.Arrays;
import java.util.BitSet;

public class ParityChecker {
	
	public void checkParities(BitSet[] state) {
		permutationParityCheck(state);
		cornerParityCheck(state);
	}
	
	// Checks for the permutation parity
	private void permutationParityCheck(BitSet[] state) {
		int[] cornerOrder = new int[8];
		int[] edgeOrder = new int[12];
		int parity = 0;
		
		// Gets the order of the corners
		for (int i = 0; i < 8; i++) {
			cornerOrder[i] = getCornerNumber(state[i]);
		}
		
		// Gets the order of the edges
		for (int i = 8; i < 20; i++) {
			edgeOrder[i - 8] = getEdgeNumber(state[i]);
		}
		
		// Finds parity for the corners
		for (int i = 0; i < cornerOrder.length; i++) {
			for(int x = i + 1; x < cornerOrder.length; x++) {
				if (cornerOrder[i] > cornerOrder[x]) {
					parity++;
				}
			}
		}
		
		// Finds parity for the edges
		for (int i = 0; i < edgeOrder.length; i++) {
			for (int x = i + 1; x < edgeOrder.length; x++) {
				if (edgeOrder[i] > edgeOrder[x]) {
					parity++;
				}
			}
		}
		
		if (parity % 2 != 0) {
			System.out.println("false");
			System.exit(0);
		}
		
	}
	
	// Calculates the corner parity
	private void cornerParityCheck(BitSet[] state) {
		int parity = 0;
		
		for (int i = 0; i < 8; i++) {
			parity += getCornerOrientation(state[i]);
		}
		
		if (parity % 3 != 0) {
			System.out.println("false");
			System.exit(0);
		}
		
	}
	
	// Calculates value of corner
	private int getCornerNumber(BitSet state) {
		int value = 0;
		int index = 4;
		
		for (int i = 0; i < 3; i++) {
			value += state.get(i) ? index : 0;
			index = index / 2;
		}
		
		return value;
	}
	
	// Calculates corner orientation
	private int getCornerOrientation(BitSet state) {
		if (state.get(3)) {
			return 2;
		} else if (state.get(4)) {
			return 1;
		} else {
			return 0;
		}
	}
	
	// Calculates value of edge
	private int getEdgeNumber(BitSet state) {
		int value = 0;
		int index = 8;
		
		for (int i = 0; i < 4; i++) {
			value += state.get(i) ? index : 0;
			index = index / 2;
		}
		
		return value;
	}

}
