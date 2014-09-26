package rubik;

/*
 * The main class in my Rubik's Cube Solver AI
 * 
 * Written by Kevin Pietrow
 *
 *
 * To serve as a reference:
 * I'll be converting every character into a number to save space
 * [R, G, Y, B, O, W]
 * [0, 1, 2, 3, 4, 5]
 * 
 */

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
