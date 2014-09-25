package rubik;

/*
 * This class will be a cube. Each side will be a 2D array.
 * 
 * Written by Kevin Pietrow
 *
 * 
 * To serve as a reference. 
 * I'll be converting every character into a number to save space
 * [R, G, Y, B, O, W]
 * [0, 1, 2, 3, 4, 5]
 * 
 */


public class Cube {
	
	public int[][] red = new int[3][3];
	public int[][] green = new int[3][3];
	public int[][] yellow = new int[3][3];
	public int[][] blue = new int[3][3];
	public int[][] orange = new int[3][3];
	public int[][] white = new int[3][3];
	
	public Cube(String positions) {
		red = sideConstructor(red, positions.substring(0, 9));
		green = sideConstructor(green, positions.substring(9, 18));
		yellow = sideConstructor(yellow, positions.substring(18, 27));
		blue = sideConstructor(blue, positions.substring(27, 36));
		orange = sideConstructor(orange, positions.substring(36, 45));
		white = sideConstructor(white, positions.substring(45, 54));

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

}
