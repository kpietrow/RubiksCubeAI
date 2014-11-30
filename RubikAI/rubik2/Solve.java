package rubik2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;

public class Solve {
	public static void main(String [ ] args) {
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
		
		String result = idaSearch(cube.state, cubeManipulator);
		System.out.println(result);
		
	}
	// goal state: [{}, {2}, {1}, {1, 2}, {0}, {0, 2}, {0, 1}, {0, 1, 2}, {}, {3}, {2}, {2, 3}, {1}, {1, 3}, {1, 2}, {1, 2, 3}, {0}, {0, 3}, {0, 2}, {0, 2, 3}]
	// ^ In case I need it

	// (initial state, cube manipulator)
	private static String idaSearch(BitSet[] state, CubeManipulator cm) {
		
		CubeNode first = new CubeNode(state, null);
		cm.print(first.state);
		String result = "";
		
		for (int depth = 0; depth < 5; depth++){
			System.out.println(depth);
			
			// should turn into a child node for this
			result = recursiveDLS(first, cm, depth);
			
			if (!result.endsWith("cutoff") && !result.endsWith("failure")) {
				return result;
			}
		}
		
		return result;
	}
	
	private static String recursiveDLS(CubeNode node, CubeManipulator cm, int limit) {
		
		// Check for goal state
		if (Arrays.equals(node.state, cm.goalState)) {
			return "yeah we got it!"; // solution
		} else if (limit == 0) {
			return "cutoff";
		} else {
			boolean cutoffOccurred = false;
			CubeNode child;
			String result = "";
			
			// if it's the first node
			if (node.methodName == null) {
				System.out.println("first");
				// loop through the 18 methods
				for (int i = 0; i < 18; i++) {
					child = determineNullMove(node.state, cm, i);
					result = result + child.methodName + recursiveDLS(child, cm, limit - 1);
					
					if (result.endsWith("cutoff")) {
						cutoffOccurred = true;
						result = "";
					} else if (!result.endsWith("failure")) {
						return result;
					} else {
						result = "";
					}
				}
			} else {
				if (node.methodName.substring(0, 1).equals("R")) {
					for (int i = 0; i < 15; i++) {
						child = determineNonRedMove(node.state, cm, i);
						result = result + child.methodName + recursiveDLS(child, cm, limit - 1);
						
						if (result.endsWith("cutoff")) {
							cutoffOccurred = true;
							result = "";
						} else if (!result.endsWith("failure")) {
							return result;
						} else {
							result = "";
						}
					}
				} else if (node.methodName.substring(0, 1).equals("G")) {
					for (int i = 0; i < 15; i++) {
						child = determineNonGreenMove(node.state, cm, i);
						result = result + child.methodName + recursiveDLS(child, cm, limit - 1);
						
						if (result.endsWith("cutoff")) {
							cutoffOccurred = true;
							result = "";
						} else if (!result.endsWith("failure")) {
							return result;
						} else {
							result = "";
						}
					}
				} else if (node.methodName.substring(0, 1).equals("Y")) {
					for (int i = 0; i < 15; i++) {
						child = determineNonYellowMove(node.state, cm, i);
						result = result + child.methodName + recursiveDLS(child, cm, limit - 1);
						
						if (result.endsWith("cutoff")) {
							cutoffOccurred = true;
							result = "";
						} else if (!result.endsWith("failure")) {
							return result;
						} else {
							result = "";
						}
					}
				} else if (node.methodName.substring(0, 1).equals("B")) {
					for (int i = 0; i < 12; i++) {
						child = determineNonBlueMove(node.state, cm, i);
						result = result + child.methodName + recursiveDLS(child, cm, limit - 1);
						
						if (result.endsWith("cutoff")) {
							cutoffOccurred = true;
							result = "";
						} else if (!result.endsWith("failure")) {
							return result;
						} else {
							result = "";
						}
					}
				} else if (node.methodName.substring(0, 1).equals("O")) {
					for (int i = 0; i < 12; i++) {
						child = determineNonOrangeMove(node.state, cm, i);
						result = result + child.methodName + recursiveDLS(child, cm, limit - 1);
						
						if (result.endsWith("cutoff")) {
							cutoffOccurred = true;
							result = "";
						} else if (!result.endsWith("failure")) {
							return result;
						} else {
							result = "";
						}
					}
				} else if (node.methodName.substring(0, 1).equals("W")) {
					for (int i = 0; i < 12; i++) {
						child = determineNonWhiteMove(node.state, cm, i);
						result = result + child.methodName + recursiveDLS(child, cm, limit - 1);
						
						if (result.endsWith("cutoff")) {
							cutoffOccurred = true;
							result = "";
						} else if (!result.endsWith("failure")) {
							return result;
						} else {
							result = "";
						}
					}
				}
			}
			
			if (cutoffOccurred) {
				return "cutoff";
			} else {
				return "failure";
			}
		}
	}
		
	private static CubeNode determineNullMove(BitSet[] state, CubeManipulator cm, int i) {
		// this is in an attempt to lower the amount of needed if statements
		if (i < 9) {
			// further split the amount needed
			if (i < 4) {
				if (i == 0) {
					CubeNode newNode = new CubeNode(cm.rotateRed90DegreesRight(state), "R1");
					cm.rotateRed270DegreesRight(state);
					return newNode;

				} else if (i == 1) {
					CubeNode newNode = new CubeNode(cm.rotateRed180DegreesRight(state), "R2");
					cm.rotateRed180DegreesRight(state);
					return newNode;

				} else if (i == 2) {
					CubeNode newNode = new CubeNode(cm.rotateRed270DegreesRight(state), "R3");
					cm.rotateRed90DegreesRight(state);
					return newNode;

				} else {
					CubeNode newNode = new CubeNode(cm.rotateGreen90DegreesRight(state), "G1");
					cm.rotateGreen270DegreesRight(state);
					return newNode;

				}
			} else {
				if (i == 4) {
					CubeNode newNode = new CubeNode(cm.rotateGreen180DegreesRight(state), "G2");
					cm.rotateGreen180DegreesRight(state);
					return newNode;

				} else if (i == 5) {
					CubeNode newNode = new CubeNode(cm.rotateGreen270DegreesRight(state), "G3");
					cm.rotateGreen90DegreesRight(state);
					return newNode;

				} else if (i == 6) {
					CubeNode newNode = new CubeNode(cm.rotateYellow90DegreesRight(state), "Y1");
					cm.rotateYellow270DegreesRight(state);
					return newNode;

				} else if (i == 7) {
					CubeNode newNode = new CubeNode(cm.rotateYellow180DegreesRight(state), "Y2");
					cm.rotateYellow180DegreesRight(state);
					return newNode;

				} else {
					CubeNode newNode = new CubeNode(cm.rotateYellow270DegreesRight(state), "Y3");
					cm.rotateYellow90DegreesRight(state);
					return newNode;

				}
			}
		} else {
			if (i < 14) {
				if (i == 9) {
					CubeNode newNode = new CubeNode(cm.rotateBlue90DegreesRight(state), "B1");
					cm.rotateBlue270DegreesRight(state);
					return newNode;

				} else if (i == 10) {
					CubeNode newNode = new CubeNode(cm.rotateBlue180DegreesRight(state), "B2");
					cm.rotateBlue180DegreesRight(state);
					return newNode;

				} else if (i == 11) {
					CubeNode newNode = new CubeNode(cm.rotateBlue270DegreesRight(state), "B3");
					cm.rotateBlue90DegreesRight(state);
					return newNode;

				} else if (i == 12) {
					CubeNode newNode = new CubeNode(cm.rotateOrange90DegreesRight(state), "O1");
					cm.rotateOrange270DegreesRight(state);
					return newNode;

				} else {
					CubeNode newNode = new CubeNode(cm.rotateOrange180DegreesRight(state), "O2");
					cm.rotateOrange180DegreesRight(state);
					return newNode;

				}
			} else {
				if (i == 14) {
					CubeNode newNode = new CubeNode(cm.rotateOrange270DegreesRight(state), "O3");
					cm.rotateOrange90DegreesRight(state);
					return newNode;

				} else if (i == 15) {
					CubeNode newNode = new CubeNode(cm.rotateWhite90DegreesRight(state), "W1");
					cm.rotateWhite270DegreesRight(state);
					return newNode;

				} else if (i == 16) {
					CubeNode newNode = new CubeNode(cm.rotateWhite180DegreesRight(state), "W2");
					cm.rotateWhite180DegreesRight(state);
					return newNode;

				} else {
					CubeNode newNode = new CubeNode(cm.rotateWhite270DegreesRight(state), "W3");
					cm.rotateWhite90DegreesRight(state);
					return newNode;
				}
			}
		}
	}
	
	public static CubeNode determineNonRedMove(BitSet[] state, CubeManipulator cm, int i) {
		// this is in an attempt to lower the amount of needed if statements
		if (i < 9) {
			if (i == 0) {
				CubeNode newNode = new CubeNode(cm.rotateGreen90DegreesRight(state), "G1");
				cm.rotateGreen270DegreesRight(state);
				return newNode;
			} else if (i == 1) {
				CubeNode newNode = new CubeNode(cm.rotateGreen180DegreesRight(state), "G2");
				cm.rotateGreen180DegreesRight(state);
				return newNode;

			} else if (i == 2) {
				CubeNode newNode = new CubeNode(cm.rotateGreen270DegreesRight(state), "G3");
				cm.rotateGreen90DegreesRight(state);
				return newNode;

			} else if (i == 3) {
				CubeNode newNode = new CubeNode(cm.rotateYellow90DegreesRight(state), "Y1");
				cm.rotateYellow270DegreesRight(state);
				return newNode;

			} else if (i == 4) {
				CubeNode newNode = new CubeNode(cm.rotateYellow180DegreesRight(state), "Y2");
				cm.rotateYellow180DegreesRight(state);
				return newNode;

			} else if (i == 5) {
				CubeNode newNode = new CubeNode(cm.rotateYellow270DegreesRight(state), "Y3");
				cm.rotateYellow90DegreesRight(state);
				return newNode;
				
			} else if (i == 6) {
				CubeNode newNode = new CubeNode(cm.rotateBlue90DegreesRight(state), "B1");
				cm.rotateBlue270DegreesRight(state);
				return newNode;

			} else if (i == 7) {
				CubeNode newNode = new CubeNode(cm.rotateBlue180DegreesRight(state), "B2");
				cm.rotateBlue180DegreesRight(state);
				return newNode;

			} else {
				CubeNode newNode = new CubeNode(cm.rotateBlue270DegreesRight(state), "B3");
				cm.rotateBlue90DegreesRight(state);
				return newNode;

			}
			
		} else {
			if (i == 9) {
				CubeNode newNode = new CubeNode(cm.rotateOrange90DegreesRight(state), "O1");
				cm.rotateOrange270DegreesRight(state);
				return newNode;

			} else if (i == 10){
				CubeNode newNode = new CubeNode(cm.rotateOrange180DegreesRight(state), "O2");
				cm.rotateOrange180DegreesRight(state);
				return newNode;

			} else if (i == 11) {
				CubeNode newNode = new CubeNode(cm.rotateOrange270DegreesRight(state), "O3");
				cm.rotateOrange90DegreesRight(state);
				return newNode;

			} else if (i == 12) {
				CubeNode newNode = new CubeNode(cm.rotateWhite90DegreesRight(state), "W1");	
				cm.rotateWhite270DegreesRight(state);
				return newNode;

			} else if (i == 13) {
				CubeNode newNode = new CubeNode(cm.rotateWhite180DegreesRight(state), "W2");
				cm.rotateWhite180DegreesRight(state);
				return newNode;

			} else {
				CubeNode newNode = new CubeNode(cm.rotateWhite270DegreesRight(state), "W3");
				cm.rotateWhite90DegreesRight(state);
				return newNode;
			
			}
		}	
	}
	
	public static CubeNode determineNonGreenMove(BitSet[] state, CubeManipulator cm, int i) {
		// this is in an attempt to lower the amount of needed if statements
		if (i < 9) {
			if (i == 0) {
				CubeNode newNode = new CubeNode(cm.rotateRed90DegreesRight(state), "R1");
				cm.rotateRed270DegreesRight(state);
				return newNode;
				
			} else if (i == 1) {
				CubeNode newNode = new CubeNode(cm.rotateRed180DegreesRight(state), "R2");
				cm.rotateRed180DegreesRight(state);
				return newNode;
				
			} else if (i == 2) {
				CubeNode newNode = new CubeNode(cm.rotateRed270DegreesRight(state), "R3");
				cm.rotateRed90DegreesRight(state);
				return newNode;

			} else if (i == 3) {
				CubeNode newNode = new CubeNode(cm.rotateYellow90DegreesRight(state), "Y1");
				cm.rotateYellow270DegreesRight(state);
				return newNode;

			} else if (i == 4) {
				CubeNode newNode = new CubeNode(cm.rotateYellow180DegreesRight(state), "Y2");
				cm.rotateYellow180DegreesRight(state);
				return newNode;

			} else if (i == 5) {
				CubeNode newNode = new CubeNode(cm.rotateYellow270DegreesRight(state), "Y3");
				cm.rotateYellow90DegreesRight(state);
				return newNode;
				
			} else if (i == 6) {
				CubeNode newNode = new CubeNode(cm.rotateBlue90DegreesRight(state), "B1");
				cm.rotateBlue270DegreesRight(state);
				return newNode;

			} else if (i == 7) {
				CubeNode newNode = new CubeNode(cm.rotateBlue180DegreesRight(state), "B2");
				cm.rotateBlue180DegreesRight(state);
				return newNode;

			} else {
				CubeNode newNode = new CubeNode(cm.rotateBlue270DegreesRight(state), "B3");
				cm.rotateBlue90DegreesRight(state);
				return newNode;

			}
			
		} else {
			if (i == 9) {
				CubeNode newNode = new CubeNode(cm.rotateOrange90DegreesRight(state), "O1");
				cm.rotateOrange270DegreesRight(state);
				return newNode;

			} else if (i == 10){
				CubeNode newNode = new CubeNode(cm.rotateOrange180DegreesRight(state), "O2");
				cm.rotateOrange180DegreesRight(state);
				return newNode;

			} else if (i == 11) {
				CubeNode newNode = new CubeNode(cm.rotateOrange270DegreesRight(state), "O3");
				cm.rotateOrange90DegreesRight(state);
				return newNode;

			} else if (i == 12) {
				CubeNode newNode = new CubeNode(cm.rotateWhite90DegreesRight(state), "W1");				
				cm.rotateWhite270DegreesRight(state);
				return newNode;

			} else if (i == 13) {
				CubeNode newNode = new CubeNode(cm.rotateWhite180DegreesRight(state), "W2");
				cm.rotateWhite180DegreesRight(state);
				return newNode;

			} else {
				CubeNode newNode = new CubeNode(cm.rotateWhite270DegreesRight(state), "W3");
				cm.rotateWhite90DegreesRight(state);
				return newNode;
			
			}
		}	
	}
	
	public static CubeNode determineNonYellowMove(BitSet[] state, CubeManipulator cm, int i) {
		// this is in an attempt to lower the amount of needed if statements
		if (i < 9) {
			if (i == 0) {
				CubeNode newNode = new CubeNode(cm.rotateRed90DegreesRight(state), "R1");
				cm.rotateRed270DegreesRight(state);
				return newNode;
				
			} else if (i == 1) {
				CubeNode newNode = new CubeNode(cm.rotateRed180DegreesRight(state), "R2");
				cm.rotateRed180DegreesRight(state);
				return newNode;

			} else if (i == 2) {
				CubeNode newNode = new CubeNode(cm.rotateRed270DegreesRight(state), "R3");
				cm.rotateRed90DegreesRight(state);
				return newNode;

			} else if (i == 3) {
				CubeNode newNode = new CubeNode(cm.rotateGreen90DegreesRight(state), "G1");
				cm.rotateGreen270DegreesRight(state);
				return newNode;

			} else if (i == 4) {
				CubeNode newNode = new CubeNode(cm.rotateGreen180DegreesRight(state), "G2");
				cm.rotateGreen180DegreesRight(state);
				return newNode;

			} else if (i == 5) {
				CubeNode newNode = new CubeNode(cm.rotateGreen270DegreesRight(state), "G3");
				cm.rotateGreen90DegreesRight(state);
				return newNode;
				
			} else if (i == 6) {
				CubeNode newNode = new CubeNode(cm.rotateBlue90DegreesRight(state), "B1");
				cm.rotateBlue270DegreesRight(state);
				return newNode;

			} else if (i == 7) {
				CubeNode newNode = new CubeNode(cm.rotateBlue180DegreesRight(state), "B2");
				cm.rotateBlue180DegreesRight(state);
				return newNode;

			} else {
				CubeNode newNode = new CubeNode(cm.rotateBlue270DegreesRight(state), "B3");
				cm.rotateBlue90DegreesRight(state);
				return newNode;

			}
			
		} else {
			if (i == 9) {
				CubeNode newNode = new CubeNode(cm.rotateOrange90DegreesRight(state), "O1");
				cm.rotateOrange270DegreesRight(state);
				return newNode;

			} else if (i == 10){
				CubeNode newNode = new CubeNode(cm.rotateOrange180DegreesRight(state), "O2");
				cm.rotateOrange180DegreesRight(state);
				return newNode;

			} else if (i == 11) {
				CubeNode newNode = new CubeNode(cm.rotateOrange270DegreesRight(state), "O3");
				cm.rotateOrange90DegreesRight(state);
				return newNode;

			} else if (i == 12) {
				CubeNode newNode = new CubeNode(cm.rotateWhite90DegreesRight(state), "W1");
				cm.rotateWhite270DegreesRight(state);
				return newNode;

			} else if (i == 13) {
				CubeNode newNode = new CubeNode(cm.rotateWhite180DegreesRight(state), "W2");
				cm.rotateWhite180DegreesRight(state);
				return newNode;

			} else {
				CubeNode newNode = new CubeNode(cm.rotateWhite270DegreesRight(state), "W3");
				cm.rotateWhite90DegreesRight(state);
				return newNode;
			
			}
		}	
	}
	
	public static CubeNode determineNonBlueMove(BitSet[] state, CubeManipulator cm, int i) {
		// this is in an attempt to lower the amount of needed if statements
		if (i < 6) {
			if (i == 0) {
				CubeNode newNode = new CubeNode(cm.rotateRed90DegreesRight(state), "R1");
				cm.rotateRed270DegreesRight(state);
				return newNode;
				
			} else if (i == 1) {
				CubeNode newNode = new CubeNode(cm.rotateRed180DegreesRight(state), "R2");
				cm.rotateRed180DegreesRight(state);
				return newNode;

			} else if (i == 2) {
				CubeNode newNode = new CubeNode(cm.rotateRed270DegreesRight(state), "R3");
				cm.rotateRed90DegreesRight(state);
				return newNode;

			} else if (i == 3) {
				CubeNode newNode = new CubeNode(cm.rotateYellow90DegreesRight(state), "Y1");
				cm.rotateYellow270DegreesRight(state);
				return newNode;

			} else if (i == 4) {
				CubeNode newNode = new CubeNode(cm.rotateYellow180DegreesRight(state), "Y2");
				cm.rotateYellow180DegreesRight(state);
				return newNode;

			} else {
				CubeNode newNode = new CubeNode(cm.rotateYellow270DegreesRight(state), "Y3");
				cm.rotateYellow90DegreesRight(state);
				return newNode;
				
			}
			
		} else {
			if (i == 6) {
				CubeNode newNode = new CubeNode(cm.rotateOrange90DegreesRight(state), "O1");
				cm.rotateOrange270DegreesRight(state);
				return newNode;

			} else if (i == 7){
				CubeNode newNode = new CubeNode(cm.rotateOrange180DegreesRight(state), "O2");
				cm.rotateOrange180DegreesRight(state);
				return newNode;

			} else if (i == 8) {
				CubeNode newNode = new CubeNode(cm.rotateOrange270DegreesRight(state), "O3");
				cm.rotateOrange90DegreesRight(state);
				return newNode;

			} else if (i == 9) {
				CubeNode newNode = new CubeNode(cm.rotateWhite90DegreesRight(state), "W1");
				cm.rotateWhite270DegreesRight(state);
				return newNode;

			} else if (i == 10) {
				CubeNode newNode = new CubeNode(cm.rotateWhite180DegreesRight(state), "W2");
				cm.rotateWhite180DegreesRight(state);
				return newNode;

			} else {
				CubeNode newNode = new CubeNode(cm.rotateWhite270DegreesRight(state), "W3");
				cm.rotateWhite90DegreesRight(state);
				return newNode;
			
			}
		}	
	}
	
	public static CubeNode determineNonOrangeMove(BitSet[] state, CubeManipulator cm, int i) {
		// this is in an attempt to lower the amount of if statements needed to search
		if (i < 6) {
			if (i == 0) {
				CubeNode newNode = new CubeNode(cm.rotateGreen90DegreesRight(state), "G1");
				cm.rotateGreen270DegreesRight(state);
				return newNode;
				
			} else if (i == 1) {
				CubeNode newNode = new CubeNode(cm.rotateGreen180DegreesRight(state), "G2");
				cm.rotateGreen180DegreesRight(state);
				return newNode;

			} else if (i == 2) {
				CubeNode newNode = new CubeNode(cm.rotateGreen270DegreesRight(state), "G3");
				cm.rotateGreen90DegreesRight(state);
				return newNode;

			} else if (i == 3) {
				CubeNode newNode = new CubeNode(cm.rotateYellow90DegreesRight(state), "Y1");
				cm.rotateYellow270DegreesRight(state);
				return newNode;

			} else if (i == 4) {
				CubeNode newNode = new CubeNode(cm.rotateYellow180DegreesRight(state), "Y2");
				cm.rotateYellow180DegreesRight(state);
				return newNode;

			} else {
				CubeNode newNode = new CubeNode(cm.rotateYellow270DegreesRight(state), "Y3");
				cm.rotateYellow90DegreesRight(state);
				return newNode;
				
			}
			
		} else {
			if (i == 6) {
				CubeNode newNode = new CubeNode(cm.rotateOrange90DegreesRight(state), "O1");
				cm.rotateOrange270DegreesRight(state);
				return newNode;

			} else if (i == 7){
				CubeNode newNode = new CubeNode(cm.rotateOrange180DegreesRight(state), "O2");
				cm.rotateOrange180DegreesRight(state);
				return newNode;

			} else if (i == 8) {
				CubeNode newNode = new CubeNode(cm.rotateOrange270DegreesRight(state), "O3");
				cm.rotateOrange90DegreesRight(state);
				return newNode;

			} else if (i == 9) {
				CubeNode newNode = new CubeNode(cm.rotateWhite90DegreesRight(state), "W1");
				cm.rotateWhite270DegreesRight(state);
				return newNode;

			} else if (i == 10) {
				CubeNode newNode = new CubeNode(cm.rotateWhite180DegreesRight(state), "W2");
				cm.rotateWhite180DegreesRight(state);
				return newNode;

			} else {
				CubeNode newNode = new CubeNode(cm.rotateWhite270DegreesRight(state), "W3");
				cm.rotateWhite90DegreesRight(state);
				return newNode;
			
			}
		}	
	}
	
	public static CubeNode determineNonWhiteMove(BitSet[] state, CubeManipulator cm, int i) {
		// this is in an attempt to lower the amount of needed if statements
		if (i < 6) {
			if (i == 0) {
				CubeNode newNode = new CubeNode(cm.rotateRed90DegreesRight(state), "R1");
				cm.rotateRed270DegreesRight(state);
				return newNode;
				
			} else if (i == 1) {
				CubeNode newNode = new CubeNode(cm.rotateRed180DegreesRight(state), "R2");
				cm.rotateRed180DegreesRight(state);
				return newNode;

			} else if (i == 2) {
				CubeNode newNode = new CubeNode(cm.rotateRed270DegreesRight(state), "R3");
				cm.rotateRed90DegreesRight(state);
				return newNode;

			} else if (i == 3) {
				CubeNode newNode = new CubeNode(cm.rotateGreen90DegreesRight(state), "G1");
				cm.rotateGreen270DegreesRight(state);
				return newNode;

			} else if (i == 4) {
				CubeNode newNode = new CubeNode(cm.rotateGreen180DegreesRight(state), "G2");
				cm.rotateGreen180DegreesRight(state);
				return newNode;

			} else {
				CubeNode newNode = new CubeNode(cm.rotateGreen270DegreesRight(state), "G3");
				cm.rotateGreen90DegreesRight(state);
				return newNode;
				
			}
			
		} else {
			if (i == 6) {
				CubeNode newNode = new CubeNode(cm.rotateBlue90DegreesRight(state), "B1");
				cm.rotateBlue270DegreesRight(state);
				return newNode;

			} else if (i == 7) {
				CubeNode newNode = new CubeNode(cm.rotateBlue180DegreesRight(state), "B2");
				cm.rotateBlue180DegreesRight(state);
				return newNode;

			} else if (i == 8) {
				CubeNode newNode = new CubeNode(cm.rotateBlue270DegreesRight(state), "B3");
				cm.rotateBlue90DegreesRight(state);
				return newNode;
			
			} else if (i == 9) {
				CubeNode newNode = new CubeNode(cm.rotateOrange90DegreesRight(state), "O1");
				cm.rotateOrange270DegreesRight(state);
				return newNode;

			} else if (i == 10){
				CubeNode newNode = new CubeNode(cm.rotateOrange180DegreesRight(state), "O2");
				cm.rotateOrange180DegreesRight(state);
				return newNode;

			} else if (i == 11) {
				CubeNode newNode = new CubeNode(cm.rotateOrange270DegreesRight(state), "O3");
				cm.rotateOrange90DegreesRight(state);
				return newNode;

			} 
		}
		return null;	
	}
	
}
