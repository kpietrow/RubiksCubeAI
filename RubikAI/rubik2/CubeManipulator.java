package rubik2;

import java.util.Arrays;
import java.util.BitSet;

public class CubeManipulator {
	
	// { R Y G }, { B Y R }, { O Y B }, { G Y O }, { O W G }, { B W O }, { R W B }, { G W R }
	private int[][] validCorners = {{0, 2, 1}, {3, 2, 0}, {4, 2, 3}, {1, 2, 4}, {4, 5, 1}, {3, 5, 4}, {0, 5, 3}, {1, 5, 0}};
		
	// { R W }, { R B }, { R Y }, { R G }, { G Y }, { G O }, { G W }, { B Y }, { O Y }, { B W }, { B O }, { O W }
	private int[][] validEdges = {{0, 5}, {0, 3}, {0, 2}, {0, 1}, {1, 2}, {1, 4}, {1, 5}, {3, 2}, {4, 2}, {3, 5}, {3, 4}, {4, 5}};
	
	public BitSet[] goalState = new BitSet[20];
			
	/* 
	 * Constructs the cube. This method takes substrings from the cube that was read in, and constructs corner and edge
	 * cubies from them. This new state is the new cube, which is returned. This method should only be run once.
	 */
	public BitSet[] constructCube(String cubeinfo) {
		
		// Check first to make sure that the centers are valid
		checkCenters(cubeinfo);
		
		BitSet[] state = new BitSet[20];
		
		// Construct corners
		state[0] = constructCorner(cubeinfo.substring(6, 7), cubeinfo.substring(12, 13), cubeinfo.substring(11, 12));
		state[1] = constructCorner(cubeinfo.substring(15, 16), cubeinfo.substring(14, 15), cubeinfo.substring(8, 9));
		state[2] = constructCorner(cubeinfo.substring(38, 39), cubeinfo.substring(32, 33), cubeinfo.substring(33, 34));
		state[3] = constructCorner(cubeinfo.substring(29, 30), cubeinfo.substring(30, 31), cubeinfo.substring(36, 37));
		state[4] = constructCorner(cubeinfo.substring(42, 43), cubeinfo.substring(45, 46), cubeinfo.substring(27, 28));
		state[5] = constructCorner(cubeinfo.substring(35, 36), cubeinfo.substring(47, 48), cubeinfo.substring(44, 45));
		state[6] = constructCorner(cubeinfo.substring(2, 3), cubeinfo.substring(53, 54), cubeinfo.substring(17, 18));
		state[7] = constructCorner(cubeinfo.substring(9, 10), cubeinfo.substring(51, 52), cubeinfo.substring(0, 1));

		// Construct edges
		state[8] = constructEdge(cubeinfo.substring(1, 2), cubeinfo.substring(52, 53));
		state[9] = constructEdge(cubeinfo.substring(5, 6), cubeinfo.substring(16, 17));
		state[10] = constructEdge(cubeinfo.substring(7, 8), cubeinfo.substring(13, 14));
		state[11] = constructEdge(cubeinfo.substring(3, 4), cubeinfo.substring(10, 11));
		state[12] = constructEdge(cubeinfo.substring(20, 21), cubeinfo.substring(21, 22));
		state[13] = constructEdge(cubeinfo.substring(28, 29), cubeinfo.substring(39, 40));
		state[14] = constructEdge(cubeinfo.substring(18, 19), cubeinfo.substring(48, 49));
		state[15] = constructEdge(cubeinfo.substring(24, 25), cubeinfo.substring(23, 24));
		state[16] = constructEdge(cubeinfo.substring(37, 38), cubeinfo.substring(31, 32));
		state[17] = constructEdge(cubeinfo.substring(26, 27), cubeinfo.substring(50, 51));
		state[18] = constructEdge(cubeinfo.substring(34, 35), cubeinfo.substring(41, 42));
		state[19] = constructEdge(cubeinfo.substring(43, 44), cubeinfo.substring(46, 47));
		
		constructGoalState();
		
		return state;
	}
	
	// Constructs the goal state (all cubies in their proper place. This is used as a reference later.
	private void constructGoalState() {
		// Construct corners
		goalState[0] = constructCorner("0", "2", "1");
		goalState[1] = constructCorner("3", "2", "0");
		goalState[2] = constructCorner("4", "2", "3");
		goalState[3] = constructCorner("1", "2", "4");
		goalState[4] = constructCorner("4", "5", "1");
		goalState[5] = constructCorner("3", "5", "4");
		goalState[6] = constructCorner("0", "5", "3");
		goalState[7] = constructCorner("1", "5", "0");

		// Construct edges
		goalState[8] = constructEdge("0", "5");
		goalState[9] = constructEdge("0", "3");
		goalState[10] = constructEdge("0", "2");
		goalState[11] = constructEdge("0", "1");
		goalState[12] = constructEdge("1", "2");
		goalState[13] = constructEdge("1", "4");
		goalState[14] = constructEdge("1", "5");
		goalState[15] = constructEdge("3", "2");
		goalState[16] = constructEdge("4", "2");
		goalState[17] = constructEdge("3", "5");
		goalState[18] = constructEdge("3", "4");
		goalState[19] = constructEdge("4", "5");
		
	}
	
	/*
	 * Takes in three sides and constructs a corner cubie.
	 */
	private BitSet constructCorner(String side1, String side2, String side3) {
		BitSet corner = new BitSet(5);
		int[] sorted = new int[3];
		boolean valid = false;
		
		// Finds, and sets the orientation in the bitset
		// Creates the sorted corner for the validCorners search
		if (side1.equals("2") || side1.equals("5")) {
			corner.set(4);
			sorted[0] = Integer.parseInt(side3);
			sorted[1] = Integer.parseInt(side1);
			sorted[2] = Integer.parseInt(side2);
		} else if (side2.equals("2") || side2.equals("5")) {
			sorted[0] = Integer.parseInt(side1);
			sorted[1] = Integer.parseInt(side2);
			sorted[2] = Integer.parseInt(side3);
		} else if (side3.equals("2") || side3.equals("5")) {
			corner.set(3);
			sorted[0] = Integer.parseInt(side2);
			sorted[1] = Integer.parseInt(side3);
			sorted[2] = Integer.parseInt(side1);
		}
		
		// finds corner in validCorners list
		for (int i = 0; i < validCorners.length; i++) {
			if (Arrays.equals(validCorners[i], sorted)) {
				// when found, calculate state using that corner
				corner = cornerIntToBits(corner, i);
				valid = true;
				break;
			}
		}
		if (!valid) {
			System.out.println("false");
			System.exit(0);
		}
		
		return corner;
	}
	
	/*
	 * Converts the value of a corner cubie from int to bits
	 */
	private BitSet cornerIntToBits(BitSet corner, int integer) {
		// Get binary representation of corner position in validCorners list
		String bits = Integer.toBinaryString(integer);
		
		// Ensure that binary won't be too short
		for (int i = bits.length(); i < 3; i++) {
			bits = "0" + bits;
		}
				
		// Copy the position in binary into the bitset
		for (int i = 0; i < bits.length(); i++) {
			if (bits.substring(i, i + 1).equals("1")) {
				corner.set(i);
			}
		}
		return corner;
	}
	
	/*
	 * Converts the value of an edge from an int to bits
	 */
	private BitSet edgeIntToBits(BitSet edge, int integer) {
		// Get binary representation of edge position in validEdges list
		String bits = Integer.toBinaryString(integer);
		
		// Ensure that binary won't be too short
		for (int i = bits.length(); i < 4; i++) {
			bits = "0" + bits;
		}
		
		// Copy the position in binary into the bitset
		for (int i = 0; i < bits.length(); i++) {
			if (bits.substring(i, i + 1).equals("1")) {
				edge.set(i);
			}
		}
		
		return edge;
	}

	/*
	 * This method takes in two sides, and constructs a new edge cubie in the bitset.
	 * The first four bits are which cubie it is, while the fifth is the orientation.
	 */
	private BitSet constructEdge(String side1, String side2) {
		
		BitSet edge = new BitSet(5);
		int[] sorted = new int[2];
		
		// finds orientation, and sets if necessary
		// Makes sure White and Yellow are always in the 2nd position
		if (Integer.parseInt(side1) == 2 || Integer.parseInt(side1) == 5) {
			edge.set(4);
			sorted[0] = Integer.parseInt(side2);
			sorted[1] = Integer.parseInt(side1);
		} else if (Integer.parseInt(side2) == 2 || Integer.parseInt(side2) == 5) {
			sorted[0] = Integer.parseInt(side1);
			sorted[1] = Integer.parseInt(side2);		
		}	
			
		// Otherwise, they should all have the larger side on the right
		else if (Integer.parseInt(side1) > Integer.parseInt(side2)) {
			edge.set(4);
			sorted[0] = Integer.parseInt(side2);
			sorted[1] = Integer.parseInt(side1);
		} else {
			sorted[0] = Integer.parseInt(side1);
			sorted[1] = Integer.parseInt(side2);
		}
		
		for (int i = 0; i < validEdges.length; i++) {
			if (Arrays.equals(sorted, validEdges[i])) {
				edge = edgeIntToBits(edge, i);
			}
		}
		
		return edge;
	}
		
	// Makes sure that the centers of the cube faces are valid
	private void checkCenters(String cubeinfo) {
		if (!cubeinfo.substring(4, 5).equals("0") || 
				!cubeinfo.substring(19, 20).equals("1") ||
				!cubeinfo.substring(22, 23).equals("2") ||
				!cubeinfo.substring(25, 26).equals("3") ||
				!cubeinfo.substring(40, 41).equals("4") ||
				!cubeinfo.substring(49, 50).equals("5")) {
			System.out.println("false");
			System.exit(0);
		}
	}
	
	// Decrements the orientation of the state
	private void decrementCornerOrientation(BitSet state) {
		// State moves from orientation 1 to 0
		if (state.get(4)) {
			state.flip(4);
		// From orientation 2 to 1
		} else if (state.get(3)) {
			state.flip(4);
			state.flip(3);
		// From orientation 0 to 2
		} else {
			state.flip(3);
		}
	}
	
	// Increments the orientation of the state
	private void incrementCornerOrientation(BitSet state) {
		// State moves from orientation 1 to 2
		if (state.get(4)) {
			state.flip(4);
			state.flip(3);
		// From orientation 2 to 0
		} else if (state.get(3)) {
			state.flip(3);
		// From orientation 0 to 1
		} else {
			state.flip(4);
		}
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
		
		return value + 8;
	}
	
	/*
	 * Prints the state of a cube to the console.
	 */
	public void print(BitSet[] state) {
		System.out.println(state[0].toString() + " - " + getValueOfCorner(state[0]));
		System.out.println(state[1].toString() + " - " + getValueOfCorner(state[1]));
		System.out.println(state[2].toString() + " - " + getValueOfCorner(state[2]));
		System.out.println(state[3].toString() + " - " + getValueOfCorner(state[3]));
		System.out.println(state[4].toString() + " - " + getValueOfCorner(state[4]));
		System.out.println(state[5].toString() + " - " + getValueOfCorner(state[5]));
		System.out.println(state[6].toString() + " - " + getValueOfCorner(state[6]));
		System.out.println(state[7].toString() + " - " + getValueOfCorner(state[7]));
		
		System.out.println("-------------");
		
		System.out.println(state[8].toString() + " - " + getValueOfEdge(state[8]));
		System.out.println(state[9].toString() + " - " + getValueOfEdge(state[9]));
		System.out.println(state[10].toString() + " - " + getValueOfEdge(state[10]));
		System.out.println(state[11].toString() + " - " + getValueOfEdge(state[11]));
		System.out.println(state[12].toString() + " - " + getValueOfEdge(state[12]));
		System.out.println(state[13].toString() + " - " + getValueOfEdge(state[13]));
		System.out.println(state[14].toString() + " - " + getValueOfEdge(state[14]));
		System.out.println(state[15].toString() + " - " + getValueOfEdge(state[15]));
		System.out.println(state[16].toString() + " - " + getValueOfEdge(state[16]));
		System.out.println(state[17].toString() + " - " + getValueOfEdge(state[17]));
		System.out.println(state[18].toString() + " - " + getValueOfEdge(state[18]));
		System.out.println(state[19].toString() + " - " + getValueOfEdge(state[19]));
		System.out.println("\n\n");

	}
	
	/*
	 * These are the rotation functions from here on down. There are three rotations per side: 90 degrees
	 * 180 degrees, and 270 degrees. Each of these rotation functions has to account for the shifting positions of
	 * both corner cubies and edge cubies, and also to account for how their orientations will change during a move.
	 */
	public BitSet[] rotateRed90DegreesRight(BitSet[] state) {
		
		// Stores { B Y R }
		// Do the corners, make sure to adjust orientations
		BitSet temp = state[0];
		
		state[0] = state[1];
		decrementCornerOrientation(state[0]);
		state[1] = state[6];
		incrementCornerOrientation(state[1]);
		state[6] = state[7];
		decrementCornerOrientation(state[6]);
		state[7] = temp;
		incrementCornerOrientation(state[7]);
		
		// Do the edges
		temp = state[10];
		state[10] = state[9];
		state[9] = state[8];
		state[8] = state[11];
		state[11] = temp;
		return state;
	}
	
	public BitSet[] rotateRed180DegreesRight(BitSet[] state) {
		
		// Stores { B Y R }
		// Do the corners, make sure to adjust orientations
		BitSet temp = state[0];
		
		state[0] = state[6];
		state[6] = temp;
		temp = state[1];
		state[1] = state[7];
		state[7] = temp;
		
		// Do the edges
		temp = state[10];
		state[10] = state[8];
		state[8] = temp;
		temp = state[9];
		state[9] = state[11];
		state[11] = temp;
		return state;
	}
	
	public BitSet[] rotateRed270DegreesRight(BitSet[] state) {
		
		// Stores { B Y R }
		// Do the corners, make sure to adjust orientations
		BitSet temp = state[0];
		
		state[0] = state[7];
		decrementCornerOrientation(state[0]);
		state[7] = state[6];
		incrementCornerOrientation(state[7]);
		state[6] = state[1];
		decrementCornerOrientation(state[6]);
		state[1] = temp;
		incrementCornerOrientation(state[1]);
		
		// Do the edges
		temp = state[10];
		state[10] = state[11];
		state[11] = state[8];
		state[8] = state[9];
		state[9] = temp;
		return state;
	}
	
	public BitSet[] rotateGreen90DegreesRight(BitSet[] state) {
		
		// Stores { G Y O }
		// Do the corners, make sure to adjust orientations
		BitSet temp = state[3];
		
		state[3] = state[0];
		decrementCornerOrientation(state[3]);
		state[0] = state[7];
		incrementCornerOrientation(state[0]);
		state[7] = state[4];
		decrementCornerOrientation(state[7]);
		state[4] = temp;
		incrementCornerOrientation(state[4]);
		
		// Do the edges
		temp = state[12];
		state[12] = state[11];
		state[12].flip(4);
		state[11] = state[14];
		state[11].flip(4);
		state[14] = state[13];
		state[13] = temp;
		return state;
	}
	
	public BitSet[] rotateGreen180DegreesRight(BitSet[] state) {
		
		// Stores { G Y O }
		// Do the corners, make sure to adjust orientations
		BitSet temp = state[3];
		
		state[3] = state[7];
		state[7] = temp;
		temp = state[6];
		state[6] = state[4];
		state[4] = temp;
		
		// Do the edges
		temp = state[12];
		state[12] = state[14];
		state[14] = temp;
		temp = state[11];
		state[11] = state[13];
		state[11].flip(4);
		state[13] = temp;
		state[13].flip(4);
		return state;
	}
	
	public BitSet[] rotateGreen270DegreesRight(BitSet[] state) {
		
		// Stores { G Y O }
		// Do the corners, make sure to adjust orientations
		BitSet temp = state[3];
		
		state[3] = state[4];
		decrementCornerOrientation(state[3]);
		state[4] = state[7];
		incrementCornerOrientation(state[4]);
		state[7] = state[0];
		decrementCornerOrientation(state[7]);
		state[0] = temp;
		incrementCornerOrientation(state[0]);
		
		// Do the edges
		temp = state[12];
		state[12] = state[13];
		state[13] = state[14];
		state[14] = state[11];
		state[14].flip(4);
		state[11] = temp;
		state[11].flip(4);
		return state;
	}
	
	public BitSet[] rotateBlue90DegreesRight(BitSet[] state) {
		
		// Stores { B Y R }
		// Do the corners, make sure to adjust orientations
		BitSet temp = state[1];
		
		state[1] = state[2];
		decrementCornerOrientation(state[1]);
		state[2] = state[5];
		incrementCornerOrientation(state[2]);
		state[5] = state[6];
		decrementCornerOrientation(state[5]);
		state[6] = temp;
		incrementCornerOrientation(state[6]);
		
		// Do the edges
		temp = state[15];
		state[15] = state[18];
		state[18] = state[17];
		state[17] = state[9];
		state[17].flip(4);
		state[9] = temp;
		state[9].flip(4);
		return state;
	}
	
	public BitSet[] rotateBlue180DegreesRight(BitSet[] state) {
		
		// Stores { B Y R }
		// Do the corners, make sure to adjust orientations
		BitSet temp = state[1];
		
		state[1] = state[5];
		state[5] = temp;
		
		temp = state[2];
		state[2] = state[6];
		state[6] = temp;
		
		// Do the edges
		temp = state[15];
		state[15] = state[17];
		state[17] = temp;
		
		temp = state[9];
		state[9] = state[18];
		state[9].flip(4);
		state[18] = temp;
		state[18].flip(4);
		return state;
	}
	
	public BitSet[] rotateBlue270DegreesRight(BitSet[] state) {
		
		// Stores { B Y R }
		// Do the corners, make sure to adjust orientations
		BitSet temp = state[1];
		
		state[1] = state[6];
		decrementCornerOrientation(state[1]);
		state[6] = state[5];
		incrementCornerOrientation(state[6]);
		state[5] = state[2];
		decrementCornerOrientation(state[5]);
		state[2] = temp;
		incrementCornerOrientation(state[2]);
		
		// Do the edges
		temp = state[15];
		state[15] = state[9];
		state[15].flip(4);
		state[9] = state[17];
		state[9].flip(4);
		state[17] = state[18];
		state[18] = temp;
		return state;
	}
	
	public BitSet[] rotateOrange90DegreesRight(BitSet[] state) {
		
		// Stores { O Y B }
		// Do the corners, make sure to adjust orientations
		BitSet temp = state[2];
		
		state[2] = state[3];
		decrementCornerOrientation(state[2]);
		state[3] = state[4];
		incrementCornerOrientation(state[3]);
		state[4] = state[5];
		decrementCornerOrientation(state[4]);
		state[5] = temp;
		incrementCornerOrientation(state[5]);
		
		// Do the edges
		temp = state[16];
		state[16] = state[13];
		state[16].flip(4);
		state[13] = state[19];
		state[13].flip(4);
		state[19] = state[18];
		state[19].flip(4);
		state[18] = temp;
		state[18].flip(4);
		return state;
	}
	
	public BitSet[] rotateOrange180DegreesRight(BitSet[] state) {
		
		// Stores { O Y B }
		// Do the corners, make sure to adjust orientations
		BitSet temp = state[2];
		
		state[2] = state[4];
		state[4] = temp;
		temp = state[3];
		state[3] = state[5];
		state[5] = temp;
		
		// Do the edges
		temp = state[16];
		state[16] = state[19];
		state[19] = temp;
		
		temp = state[13];
		state[13] = state[18];
		state[18] = temp;
		return state;
	}
	
	public BitSet[] rotateOrange270DegreesRight(BitSet[] state) {
		
		// Stores { O Y B }
		// Do the corners, make sure to adjust orientations
		BitSet temp = state[2];
		
		state[2] = state[5];
		decrementCornerOrientation(state[2]);
		state[5] = state[4];
		incrementCornerOrientation(state[5]);
		state[4] = state[3];
		decrementCornerOrientation(state[4]);
		state[3] = temp;
		incrementCornerOrientation(state[3]);
		
		// Do the edges
		temp = state[16];
		state[16] = state[18];
		state[16].flip(4);
		state[18] = state[19];
		state[18].flip(4);
		state[19] = state[13];
		state[19].flip(4);
		state[13] = temp;
		state[13].flip(4);
		return state;
	}
	
	public BitSet[] rotateYellow90DegreesRight(BitSet[] state) {
		
		// Stores { B Y R }
		// Do the corners, make sure to adjust orientations
		BitSet temp = state[1];
		
		state[1] = state[0];
		state[0] = state[3];
		state[3] = state[2];
		state[2] = temp;
		
		// Do the edges
		temp = state[10];
		state[10] = state[12];
		state[12] = state[16];
		state[16] = state[15];
		state[15] = temp;
		
		return state;
	}
	
	public BitSet[] rotateYellow180DegreesRight(BitSet[] state) {
				
		// Stores { B Y R }
		// Do the corners, make sure to adjust orientations
		BitSet temp = state[1];
		
		state[1] = state[3];
		state[3] = temp;
		temp = state[2];
		state[2] = state[0];
		state[0] = temp;
		
		// Do the edges
		temp = state[10];
		state[10] = state[16];
		state[16] = temp;
		
		temp = state[15];
		state[15] = state[12];
		state[12] = temp;
		
		return state;
	}
	
	public BitSet[] rotateYellow270DegreesRight(BitSet[] state) {
		
		// Stores { B Y R }
		// Do the corners, make sure to adjust orientations
		BitSet temp = state[1];
		
		state[1] = state[2];
		state[2] = state[3];
		state[3] = state[0];
		state[0] = temp;
		
		// Do the edges
		temp = state[10];
		state[10] = state[15];
		state[15] = state[16];
		state[16] = state[12];
		state[12] = temp;
		
		return state;
	}
	
	public BitSet[] rotateWhite90DegreesRight(BitSet[] state) {
		
		// Stores { G W R }
		// Do the corners, make sure to adjust orientations
		BitSet temp = state[7];
		
		state[7] = state[6];
		state[6] = state[5];
		state[5] = state[4];
		state[4] = temp;
		
		// Do the edges
		temp = state[8];
		state[8] = state[17];
		state[17] = state[19];
		state[19] = state[14];
		state[14] = temp;
		
		return state;
	}
	
	public BitSet[] rotateWhite180DegreesRight(BitSet[] state) {
		
		// Stores { G W R }
		// Do the corners, make sure to adjust orientations
		BitSet temp = state[7];
		
		state[7] = state[5];
		state[5] = temp;
		temp = state[6];
		state[6] = state[4];
		state[4] = temp;
		
		// Do the edges
		temp = state[8];
		state[8] = state[19];
		state[19] = temp;
		
		temp = state[14];
		state[14] = state[17];
		state[17] = temp;
		
		return state;
	}
	
	public BitSet[] rotateWhite270DegreesRight(BitSet[] state) {
		
		// Stores { G W R }
		// Do the corners, make sure to adjust orientations
		BitSet temp = state[7];
		
		state[7] = state[4];
		state[4] = state[5];
		state[5] = state[6];
		state[6] = temp;
		
		// Do the edges
		temp = state[8];
		state[8] = state[14];
		state[14] = state[19];
		state[19] = state[17];
		state[17] = temp;
		
		return state;
	}
}
