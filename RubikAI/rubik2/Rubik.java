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
		cubeManipulator.print(cube.state);
		
		// Check the cube's parity
		parityChecker.checkParities(cube.state);
		
		cube.state = cubeManipulator.rotateGreen180DegreesRight(cube.state);
		cubeManipulator.print(cube.state);
		
		
		
	}
}
