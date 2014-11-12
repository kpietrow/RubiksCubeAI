package rubik2;

import java.util.Arrays;

public class Rubik {
	public static void main(String [ ] args) {
		// Create variables and singletons
		String input = "";
		InputReader ir = new InputReader();
		CubeManipulator cubeManipulator = new CubeManipulator();
		ParityChecker parityChecker = new ParityChecker();

		// Gather input
		for (int i = 0; i < args.length; i++) {
			input += args[i];
		}
		
		// Read in file
		input = ir.inputFile(input);
		input = ir.sortInput(input);
		
		// Create the cube
		Cube cube = new Cube();
		
		// Construct the cube's state
		cube.state = cubeManipulator.constructCube(input);
		cube.print();
		
		// Check the cube's parity
		parityChecker.checkParities(cube.state);
		
		cubeManipulator.rotateRed90Degrees(cube.state);
		cube.print();
		
		
		
	}
}