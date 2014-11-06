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

import com.google.common.primitives.Ints;



public class Cube {
	
	private int[][] red = new int[3][3];
	private int[][] green = new int[3][3];
	private int[][] yellow = new int[3][3];
	private int[][] blue = new int[3][3];
	private int[][] orange = new int[3][3];
	private int[][] white = new int[3][3];
	
	private int[] firstSide = new int[3];
	
	private HashMap<String, int[][]> sides = new HashMap<String, int[][]>();


	
	public Cube(){}
	
	public void setState(int[] state) {
		for (int i = 0; i < state.length; i++) {
			if (i < 9) {
				red[i/3][i%3] = state[i];
			} else if (i < 18) {
				green[i/3][i%3] = state[i];
			} else if (i < 27) {
				yellow[i/3][i%3] = state[i];
			} else if (i < 36) {
				blue[i/3][i%3] = state[i];
			} else if (i < 45) {
				orange[i/3][i%3] = state[i];
			} else if (i < 54) {
				white[i/3][i%3] = state[i];
			}
		}
	}
	
	
	private int[][] sideResetter(int value) {
		int[][] side = new int[1][9];
		
		for (int i = 0; i < 9; i++) {
			side[0][i] = value;
		}
		
		return side;
		
	}
	
	public void init() {
		red = sideResetter(0);
		green = sideResetter(1);
		yellow = sideResetter(2);
		blue = sideResetter(3);
		orange = sideResetter(4);
		white = sideResetter(5);
	}
	
	public HashMap<String, int[][]> getState() {
		sides.put("Red", red);
		sides.put("Green", green);
		sides.put("Yellow", yellow);
		sides.put("Blue", blue);
		sides.put("Orange", orange);
		sides.put("White", white);
		return sides;
	}
	
	// He's the hero we ultimately need, but not the one we need right now
	/*public int[] getState() {
		int[] state = Ints.concat(Ints.concat(red), Ints.concat(green), Ints.concat(yellow), Ints.concat(blue), Ints.concat(orange), Ints.concat(white));
		return state;
	}*/
	
	/*
	 * These will be all of the different turns. There should be 18 of them
	 * From viewing the yellow side straight on. 
	 * Clockwise will always be in context of the orange and blue sides
	 * 
	 * These will all be on the X/Y axis, all involving moving cubies along the yellow face
	 * Going deeper into the cube will happen later
	 * Hijinks are necessary for the white side on row moves, as white has a different orientation
	 * 
	 */
	public void clockwiseRowOne() {
		for (int i = 0; i < 3; i++) {
			firstSide[i] = yellow[0][i];
			yellow[0][i] = green[0][i];
			green[0][(i - 2) * (-1)] = white[2][i]; // the hijinks begin. needed to get from 0 -> 2, and 2 -> 0
			white[2][(i - 2) * (-1)] = blue[0][i];
			blue[0][i] = firstSide[i];
		}
	}
	
	public void clockwiseRowThree() {
		for (int i = 0; i < 3; i++) {
			firstSide[i] = yellow[2][i];
			yellow[2][i] = green[2][i];
			green[2][(i - 2) * (-1)] = white[0][i];
			white[0][(i - 2) * (-1)] = blue[2][i];
			blue[2][i] = firstSide[i];
		}
	}
	
	
	
	
	public void counterClockwiseRowOne() {
		for (int i = 0; i < 3; i++) {
			firstSide[i] = yellow[0][i];
			yellow[0][i] = blue[0][i];
			blue[0][(i - 2) * (-1)] = white[2][i];
			white[2][(i - 2) * (-1)] = green[0][i];
			green[0][i] = firstSide[i];
		}
	}
	
	public void counterClockwiseRowThree() {
		for (int i = 0; i < 3; i++) {
			firstSide[i] = yellow[2][i];
			yellow[2][i] = blue[2][i];
			blue[2][(i - 2) * (-1)] = white[0][i];
			white[0][(i - 2) * (-1)] = green[2][i];
			green[2][i] = firstSide[i];
		}
	}
	
	
	
	
	public void clockwiseColumnOne() {
		for (int i = 0; i < 3; i++) {
			firstSide[i] = yellow[i][0];
			yellow[i][0] = orange[i][0];
			orange[i][0] = white[i][0];
			white[i][0] = red[i][0];
			red[i][0] = firstSide[i];
		}
	}
	
	public void clockwiseColumnThree() {
		for (int i = 0; i < 3; i++) {
			firstSide[i] = yellow[i][2];
			yellow[i][2] = orange[i][2];
			orange[i][2] = white[i][2];
			white[i][2] = red[i][2];
			red[i][2] = firstSide[i];
		}
	}
	
	
	
	
	public void counterClockwiseColumnOne() {
		for (int i = 0; i < 3; i++) {
			firstSide[i] = yellow[i][0];
			yellow[i][0] = red[i][0];
			red[i][0] = white[i][0];
			white[i][0] = orange[i][0];
			orange[i][0] = firstSide[i];
		}
	}
	
	public void counterClockwiseColumnThree() {
		for (int i = 0; i < 3; i++) {
			firstSide[i] = yellow[i][2];
			yellow[i][2] = red[i][2];
			red[i][2] = white[i][2];
			white[i][2] = orange[i][2];
			orange[i][2] = firstSide[i];
		}
	}
	
	
	
	
	/* 
	 * Here we rotate on the Z-Axis
	 * Yellow face is the 'front'
	 * White face is the 'back'
	 */
	
	public void clockwiseFrontFace() {
		for (int i = 0; i < 3; i++) {
			firstSide[i] = red[2][i];
			red[2][i] = green[(i - 2) * (-1)][2];
			green[(i - 2) * (-1)][2] = orange[0][(i - 2) * (-1)];
			orange[0][(i - 2) * (-1)] = blue[i][0];
			blue[i][0] = firstSide[i];
		}
	}
	
	public void clockwiseBackFace() {
		for (int i = 0; i < 3; i++) {
			firstSide[i] = red[0][i];
			red[0][i] = green[(i - 2) * (-1)][0];
			green[(i - 2) * (-1)][2] = orange[2][(i - 2) * (-1)];
			orange[2][(i - 2) * (-1)] = blue[i][2];
			blue[i][2] = firstSide[i];
		}
	}
	
	
	
	
	public void counterClockwiseFrontFace() {
		for (int i = 0; i < 3; i++) {
			firstSide[i] = red[2][i];
			red[2][i] = blue[i][0];
			blue[i][0] = orange[0][(i - 2) * (-1)];
			orange[0][(i - 2) * (-1)] = green[(i - 2) * (-1)][2];
			green[(i - 2) * (-1)][2] = firstSide[i];
		}
	}
	
	public void counterClockwiseBackFace() {
		for (int i = 0; i < 3; i++) {
			firstSide[i] = red[0][i];
			red[0][i] = blue[i][2];
			blue[i][2] = orange[2][(i - 2) * (-1)];
			orange[2][(i - 2) * (-1)] = green[(i - 2) * (-1)][2];
			green[(i - 2) * (-1)][2] = firstSide[i];
		}
	}
}
