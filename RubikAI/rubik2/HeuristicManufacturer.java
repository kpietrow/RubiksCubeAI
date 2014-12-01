package rubik2;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.BitSet;

public class HeuristicManufacturer {
	
	public byte[] cornerArray = new byte[44089920];
	public byte[] edge1Array = new byte[21288960];
	public byte[] edge2Array = new byte[21288960];
	
	public void writeToFiles() {
		FileOutputStream cornerFOS = null;
		FileOutputStream edge1FOS = null;
		FileOutputStream edge2FOS = null;
		
		try {
			cornerFOS = new FileOutputStream("/Users/User/Documents/RubiksCubeAI/RubikAI/tables/corner.bin");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			edge1FOS = new FileOutputStream("/Users/User/Documents/RubiksCubeAI/RubikAI/tables/edge1.bin");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			edge2FOS = new FileOutputStream("/Users/User/Documents/RubiksCubeAI/RubikAI/tables/edge2.bin");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			cornerFOS.write(cornerArray, 0, 44089920);
			cornerFOS.close();
			
			edge1FOS.write(edge1Array, 0, 21288960);
			edge1FOS.close();
			
			edge2FOS.write(edge2Array, 0, 21288960);
			edge2FOS.close();
			
		} catch (IOException e) {
			System.out.println("Error in writing to files");
			e.printStackTrace();
		}
	}
	
	
	/*
	 *  Computes the heuristics for a given state.
	 *  Returns a true if one of the three heuristics has not been 
	 *  found before. False if this state has been completely discovered before.
	 */
	public boolean computeHeuristics(BitSet[] state, int depth) {
		
		boolean cornerResult = computeCorner(state, depth);
		boolean edge1Result = computeEdge1(state, depth);
		boolean edge2Result = computeEdge2(state, depth);
		
		return (cornerResult|edge1Result|edge2Result);
	}
	
	
	/*
	 * Computes the heuristic for the corners.
	 * Will return true if this is the first entry of its kind.
	 * False if this state has already been reached.
	 */
	public boolean computeCorner(BitSet[] state, int depth) {
		int[] values = { 0, 1, 2, 3, 4, 5, 6, 7 };
		int factorialValue = 7;
		int orientationMultiplier = 6;
		long orientationTotal = 0;
		long heuristic = 0;
		
		for (int i = 0; i < 7; i++) {
			heuristic += values[getValueOfCorner(state[i])] * factorial(factorialValue);
			factorialValue--;
			
			orientationTotal += Math.pow(getCornerOrientation(state[i]), orientationMultiplier);
			orientationMultiplier--;
			
			for (int x = getValueOfCorner(state[i]) + 1; x < 8; x++) {
				values[x] = values[x] - 1;
			}
		}
		
		orientationTotal = orientationTotal * factorial(8);
		heuristic += orientationTotal;
		
		return constructCornerFile(heuristic, depth);
	}
	
	/*
	 * Will return true if this is the first entry of its kind.
	 * False if this state has already been reached.
	 */
	public boolean computeEdge1(BitSet[] state, int depth) {
		int[] values = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
		int factorialValue = 11;
		int orientationMultiplier = 5;
		long orientationTotal = 0;
		long heuristic = 0;
		
		for (int i = 8; i < 14; i++) {
			heuristic += values[getValueOfEdge(state[i])] * factorial(factorialValue);
			factorialValue--;
			
			orientationTotal += Math.pow(getEdgeOrientation(state[i]), orientationMultiplier);
			orientationMultiplier--;
			
			for (int x = getValueOfCorner(state[i]) + 1; x < 12; x++) {
				values[x] = values[x] - 1;
			}
		}
		
		heuristic = heuristic / factorial(6);
		orientationTotal = orientationTotal * (factorial(12)/factorial(6));
		heuristic += orientationTotal;
		
		return constructEdge1File(heuristic, depth);
	}
	
	/*
	 * Will return true if this is the first entry of its kind.
	 * False if this state has already been reached.
	 */
	public boolean computeEdge2(BitSet[] state, int depth) {
		int[] values = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
		int factorialValue = 11;
		int orientationMultiplier = 5;
		long orientationTotal = 0;
		long heuristic = 0;
		
		for (int i = 14; i < 20; i++) {
			heuristic += values[getValueOfEdge(state[i])] * factorial(factorialValue);
			factorialValue--;
			
			orientationTotal += Math.pow(getEdgeOrientation(state[i]), orientationMultiplier);
			orientationMultiplier--;
			
			for (int x = getValueOfCorner(state[i]) + 1; x < 12; x++) {
				values[x] = values[x] - 1;
			}
		}
		
		heuristic = heuristic / factorial(6);
		orientationTotal = orientationTotal * (factorial(12)/factorial(6));
		heuristic += orientationTotal;
		
		return constructEdge2File(heuristic, depth);
	}
	
	/*
	 * Constructs the array of the corner depths, with the position in the array being the 
	 * heuristic divided by 2. If there is a remainder, then 
	 */
	private boolean constructCornerFile(long heuristic, int depth) {
		
		// Do this division now, so we don't have to repeat later
		int value = (int) (heuristic / 2);
		
		// Protect against out of scope errors
		if (value > 44089920) {
			System.out.println("ERROR: CORNER OUT OF SCOPE");
			return false;
		}
		
		// If heuristic is odd, send it to the second half of the array element
		if (heuristic % 2 == 1) {
			// If this is true, then a value is already present. Return false.
			if (this.cornerArray[value] > 31) {
				return false;
			}
			
			depth = depth << 4;
			this.cornerArray[value] = (byte) (this.cornerArray[value] + depth);
		
		// If the heuristic is even, send it to first half of the array element
		} else {
			// Check to see if this corner state has already been reached
			if (this.cornerArray[value] < 32 && this.cornerArray[value] > 0) {
				return false;
			}
			
			this.cornerArray[value] = (byte) (depth);
		}
		
		return true;
	}
	
	/*
	 * Constructs the array of the edge1 depths, with the position in the array being the 
	 * heuristic divided by 2. If there is a remainder, then 
	 */
	private boolean constructEdge1File(long heuristic, int depth) {
		
		// Do this division now, so we don't have to repeat later
		int value = (int) (heuristic / 2);
		
		// Protect against out of scope errors
		if (value > 21288960) {
			System.out.println("ERROR: EDGE OUT OF SCOPE");
			return false;
		}
	
		// If heuristic is odd, send it to the second half of the array element
		if (heuristic % 2 == 1) {
			// If this is true, then a value is already present. Return false.
			if (this.edge1Array[value] > 31) {

				return false;
			}
			
			depth = depth << 4;
			this.edge1Array[value] = (byte) (this.edge1Array[value] + depth);
		
		// If the heuristic is even, send it to first half of the array element
		} else {
			// Check to see if this corner state has already been reached
			if (this.edge1Array[value] < 32 && this.edge1Array[value] > 0) {

				return false;
			}
			
			this.edge1Array[value] = (byte) (depth);
		}
		
		return true;
	}
	
	/*
	 * Constructs the array of the edge2 depths, with the position in the array being the 
	 * heuristic divided by 2. If there is a remainder, then 
	 */
	private boolean constructEdge2File(long heuristic, int depth) {
		
		// Do this division now, so we don't have to repeat later
		int value = (int) (heuristic / 2);
		
		// Protect against out of scope errors
		if (value > 21288960) {
			System.out.println("ERROR: EDGE OUT OF SCOPE");
			return true;
		}
	
		// If heuristic is odd, send it to the second half of the array element
		if (heuristic % 2 == 1) {
			// If this is true, then a value is already present. Return false.
			if (this.edge2Array[value] > 31) {

				return false;
			}
			
			depth = depth << 4;
			this.edge2Array[value] = (byte) (this.edge2Array[value] + depth);
		
		// If the heuristic is even, send it to first half of the array element
		} else {
			// Check to see if this corner state has already been reached
			if (this.edge2Array[value] < 32 && this.edge2Array[value] > 0) {

				return false;
			}
			
			this.edge2Array[value] = (byte) (depth);
		}
		
		return true;
	}
	
	private int factorial(int num) {
		int value = num;
		
		for (int i = num - 1; i > 1; i--) {
			value = value * i;
		}
		
		return value;
	}
	
	/*
	 * Converts the value of a corner cubie from bits to an int
	 */
	private int getValueOfCorner(BitSet corner) {
		int value = 0;
		
		if (corner.get(0)) {
			value = value + 4;
		}
		
		if (corner.get(1)) {
			value = value + 2;
		}
		
		if (corner.get(2)) {
			value = value + 1;
		}
		
		return value;
	}
	
	/*
	 * Converts the value of an edge cubie from bits to an int
	 */
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
		
		return value;
	}
	
	private int getCornerOrientation(BitSet corner) {
		if (corner.get(3)) {
			return 2;
		} else if (corner.get(4)) {
			return 1;
		} else {
			return 0;
		}
	}
	
	private int getEdgeOrientation(BitSet edge) {
		if (edge.get(4)) {
			return 1;
		} else {
			return 0;
		}
	}

}
