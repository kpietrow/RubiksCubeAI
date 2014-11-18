package rubik2;

import java.util.Arrays;
import java.util.BitSet;

public class ParityChecker {
	
	// { R Y G }, { B Y R }, { O Y B }, { G Y O }, { O W G }, { B W O }, { R W B }, { G W R }
	private int[][] validCorners = {{0, 2, 1}, {3, 2, 0}, {4, 2, 3}, {1, 2, 4}, {4, 5, 1}, {3, 5, 4}, {0, 5, 3}, {1, 5, 0}};
			
	// { R W }, { R B }, { R Y }, { R G }, { G Y }, { G O }, { G W }, { B Y }, { O Y }, { B W }, { B O }, { O W }
	private int[][] validEdges = {{0, 5}, {0, 3}, {0, 2}, {0, 1}, {1, 2}, {1, 4}, {1, 5}, {3, 2}, {4, 2}, {3, 5}, {3, 4}, {4, 5}};
		
	
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
	
	// Retrieves the value of the edge from the BitSet
	private int getValueOfEdge(BitSet edge) {
		int value = 0;
		
		if (edge.get(0)) {
			value = value + 8;
		}
		
		if (edge.get(1)) {
			value = value + 4;
		}
		
		if (edge.get(2)) {
			value = value + 2;
		}
		
		if (edge.get(3)) {
			value = value + 1;
		}
		
		return value + 8;
	}
	
	// Checks the parity of edges. Blue is the front face, with yellow on top and white on the bottom.
	private void checkEdgeParity(BitSet[] state) {
		
		// Rule #1: (Top & Bot) Any edge containing white or yellow stickers facing up or down is good
		// Rule #2: (Top & Bot) Any edge containing white or yellow on the side faces is bad
		int[] topOrBotEdges = {8, 10, 12, 14, 15, 16, 17, 19};    // Positions of the top or bottom edges in the state
		int[] nonWhiteOrYellowEdges = {9, 11, 13, 18};
		int parityCount = 0;    // The parity count
		boolean valid = true;    // If turned false, a bad edge has been found
		boolean whiteOrYellow = false;    // Keeps track of if white or yellow was found
		
		// Loops through the top edge positions
		for (int i = 0; i < topOrBotEdges.length; i++) {
			
			// Checks to see if it could be a valid edge
			for (int x = 0; x < topOrBotEdges.length; x++) {
				// Invalid edge. White or yellow on the side
				if (getValueOfEdge(state[topOrBotEdges[i]]) == topOrBotEdges[x] && state[topOrBotEdges[i]].get(4)) {
					whiteOrYellow = true;
					valid = false;
					break;
				// Valid edge. White or yellow on top
				} else if (getValueOfEdge(state[topOrBotEdges[i]]) == topOrBotEdges[x] && !state[topOrBotEdges[i]].get(4)) {
					whiteOrYellow = true;
					break;
				}
			}
			
			// If it wasn't valid, increment the parity counter
			if (!valid) {
				parityCount++;
				valid = true;
				
			// If a white or yellow edge was found
			} else if (whiteOrYellow){
				whiteOrYellow = false;
				
			// Rule #3 (Top & Bot): For edges without white or yellow. Any edge is bad if it has blue or green
			// facing the sides. Any edge is good if it has red or orange facing the sides.
			} else {
				for (int x = 0; x < nonWhiteOrYellowEdges.length; x++) {
					// If the side edge has blue or green facing the sides
					if (getValueOfEdge(state[topOrBotEdges[i]]) == nonWhiteOrYellowEdges[x] && !state[nonWhiteOrYellowEdges[x]].get(4)) {
						valid = false;
					}
					
					// Else it's good
				}
				
				if (!valid) {
					parityCount++;
					valid = true;
				} 
				
			}
		}
		
		// Rule #4 (Middle Layer):
		
		
		
	}

}
