package rubik;

import java.util.HashMap;

public class Rubik {
	public static void main(String [ ] args) {
		
		Cube cube = new Cube();
		cube.init();
		
		Hashing solver = new Hashing();
		
		// Functions to retrieve 
		solver.prepare(cube.getState());
		solver.computeCornerHash();
		solver.computeEdgeOneHash();
		solver.computeEdgeTwoHash();
		
		/*
		 * The next step is to branch the cube states with a depth first traversal,
		 * until we reach a depth of 11.
		 * 
		 * Along the way, the hashes would be calculated, and their positions in the file checked
		 * for a previous entry. If no entry exists, add at that location the current depth of 
		 * the tree.
		 */
		
	}
}
