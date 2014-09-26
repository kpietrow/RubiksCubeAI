package rubik;

import java.util.Arrays;
import java.util.HashMap;

/*
 *  Contains the checks to validate a cube's state
 * Written by Kevin Pietrow
 *
 * 
 * To serve as a reference. 
 * I'll be converting every character into a number to save space
 * [R, G, Y, B, O, W]
 * [0, 1, 2, 3, 4, 5]
 * 
 */


public class Solver {
	
	private int[][] red = new int[3][3];
	private int[][] green = new int[3][3];
	private int[][] yellow = new int[3][3];
	private int[][] blue = new int[3][3];
	private int[][] orange = new int[3][3];
	private int[][] white = new int[3][3];
	private int[][] givenCorners;
	
	private int[][] corners = {{0, 2, 3}, {2, 3, 4}, {1, 2, 4}, {0, 1, 2}, {3, 4, 5}, {0, 3, 5}, {1, 4, 5}, {0, 1, 5}};
	
	private HashMap<String, int[][]> sides = new HashMap<String, int[][]>();

	public Solver(HashMap<String, int[][]> sides) {
		this.sides = sides;
		setSides();
		setCorners();
	}
	
	
	public void centerCheck() {
		
		if (red[1][1] != 0) {
			System.out.println("false");
			System.exit(0);
		} else if (green[1][1] != 1) {
			System.out.println("false");
			System.exit(0);
		} else if (yellow[1][1] != 2) {
			System.out.println("false");
			System.exit(0);
		} else if (blue[1][1] != 3) {
			System.out.println("false");
			System.exit(0);
		} else if (orange[1][1] != 4) {
			System.out.println("false");
			System.exit(0);
		} else if (white[1][1] != 5) {
			System.out.println("false");
			System.exit(0);
		}
	}
	
	public void simpleCornerCheck() {
		
		boolean valid = false;
		int[] corner;
		
		for (int i = 0; i < corners.length; i++) {
			corner = corners[i];
			
			for (int x = 0; x < givenCorners.length; x++) {
				Arrays.sort(givenCorners[x]);
				
				if (Arrays.equals(givenCorners[x], corner)) {
					valid = true;
					break;
				}
			}
			
			if (!valid) {
				System.out.println("false. Invalid corner");
				System.exit(0);
			}
			
			valid = false;
		}
	}
	
	
	
	

	private void setSides() {
		// set the sides
		red = sides.get("Red");
		green = sides.get("Green");
		yellow = sides.get("Yellow");
		blue = sides.get("Blue");
		orange = sides.get("Orange");
		white = sides.get("White");
	}
	
	// set the corners!
	private void setCorners() {
		
		int[][] givenCorners = {{red[2][2], yellow[0][2], blue[0][0]}, {yellow[2][2], blue[2][0], orange[0][2]}, 
				{green[2][2], yellow[2][0], orange[0][0]}, {red[2][0], green[0][2], yellow[0][0]}, 
				{blue[2][2], orange[2][2], white[0][2]}, {red[0][2], blue[0][2], white[2][2]}, 
				{green[2][0], orange[0][0], white[0][0]}, {red[0][0], green[0][0], white[2][0]}};
		this.givenCorners = givenCorners;
		
	}
}
