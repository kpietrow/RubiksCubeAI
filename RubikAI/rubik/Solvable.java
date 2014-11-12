package rubik;

/*
 * The main class in my Rubik's Cube Solver AI
 * 
 * Written by Kevin Pietrow
 *
 *	(The true orientation)
 *	   0	          1          2          3          4          5          6          7
 * { R Y G }, { B Y R }, { O Y B }, { G Y O }, { O W G }, { B W O }, { R W B }, { G W R }
*	private int[][] validCorners = {{0, 2, 1}, {3, 2, 0}, {4, 2, 3}, {1, 2, 4}, {4, 5, 1}, {3, 5, 4}, {0, 5, 3}, {1, 5, 0}};
*	
*      8        9       10       11        12      13       14       15       16       17       18       19
 *  { R W }, { R B }, { R Y }, { R G }, { G Y }, { G O }, { G W }, { Y B }, { Y O }, { B W }, { B O }, { O W }
*   private int[][] validEdges = {{0, 5}, {0, 3}, {0, 2}, {0, 1}, {1, 2}, {1, 4}, {1, 5}, {2, 3}, {2, 4}, {3, 5}, {3, 4}, {4, 5}};
 *
 * To serve as a reference:
 * I'll be converting every character into a number to save space
 * [R, G, Y, B, O, W]
 * [0, 1, 2, 3, 4, 5]
 * 
 *

public class Solvable {
	public static void main(String [ ] args) {
		
		String input = "";
		InputReader ir = new InputReader();
		
		for (int i = 0; i < args.length; i++) {
			input += args[i];
		}
		
		input = ir.inputFile(input);
		input = ir.sortInput(input);
		
		Cube cube = new Cube(input);
		
		Solver solver = new Solver(cube.getSides());
		solver.centerCheck();
		solver.cornerCheck();
		solver.permutationParity();
		//solver.cornerParity();
		
		// Success!!!
		System.out.println("true");

	}
}

*/