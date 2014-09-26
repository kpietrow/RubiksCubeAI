package rubik;

/*
 * This class will be a cube. Each side will be a 2D array.
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

import java.util.HashMap;



public class Cube {
	
	private int[][] red = new int[3][3];
	private int[][] green = new int[3][3];
	private int[][] yellow = new int[3][3];
	private int[][] blue = new int[3][3];
	private int[][] orange = new int[3][3];
	private int[][] white = new int[3][3];
	private HashMap<String, int[][]> sides = new HashMap<String, int[][]>();
	
	
	public Cube(String positions) {
		red = sideConstructor(red, positions.substring(0, 9));
		green = sideConstructor(green, positions.substring(9, 12) + positions.substring(18, 21) + positions.substring(27, 30));
		yellow = sideConstructor(yellow, positions.substring(12, 15) + positions.substring(21, 24) + positions.substring(30, 33));
		blue = sideConstructor(blue, positions.substring(15, 18) + positions.substring(24, 27) + positions.substring(33, 36));
		orange = sideConstructor(orange, positions.substring(36, 45));
		white = sideConstructor(white, positions.substring(45, 54));
		
		sides.put("Red", red);
		sides.put("Green", green);
		sides.put("Yellow", yellow);
		sides.put("Blue", blue);
		sides.put("Orange", orange);
		sides.put("White", white);
	}
	
	private int[][] sideConstructor(int[][] side, String positions) {
		int index = 0;
		char[] characters = positions.toCharArray();
		
		for (int outer = 0; outer < 3; outer++) {
			for (int inner = 0; inner < 3; inner++) {
				side[outer][inner] = ((int) characters[index]) - 48;
				index++;
			}	
		}
		
		return side;
	}
	
	public HashMap<String, int[][]> getSides() {
		return sides;
	}
}
