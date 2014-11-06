package rubik;

import java.util.Arrays;
import java.util.HashMap;

public class Hashing {
	
	// { G Y R }, { R Y B}, { B Y O }, { O Y G }, { G W O }, { O W B }, { B W R }, { R W G }
	private int[][] validCorners = {{1, 2, 0}, {0, 2, 3}, {3, 2, 4}, {4, 2, 1}, {1, 5, 4}, {4, 5, 3}, {3, 5, 0}, {0, 5, 1}};
	
	private int[][] validEdges = {{1, 4}, {1, 5}, {0, 1}, {0, 5}, {0, 3}, {3, 5}, {3, 4}, {4, 5}, {2, 4}, {1, 2}, {0, 2}, {2, 3}};
	
	private int[][] red = new int[3][3];
	private int[][] green = new int[3][3];
	private int[][] yellow = new int[3][3];
	private int[][] blue = new int[3][3];
	private int[][] orange = new int[3][3];
	private int[][] white = new int[3][3];
	
	private int[][] givenCorners;
	private int[][] givenEdges;
	int[] givenCornerPositions;
	
	
	
	public void prepare(HashMap<String, int[][]> state) {
		
		setSides(state);
		setCorners(state);
		setEdges(state);
		
	}
	
	public int computeCornerHash() {
		
		int[][] cornerPositionAndOrientation = new int[1][8];
		int orientation = 0;
		int temp;
		int[] orientationIndex = {0,1,2,3,4,5,6,7};
		int[] positionIndex = {0,1,2,3,4,5,6,7};
		int positionScore = 0;
		int orientationScore = 0;
		
		for (int i = 0; i < 8; i++) {
			
			if (givenCorners[i][0] == 2 || givenCorners[i][0] == 5) {
				orientation += 1;
				temp = givenCorners[i][0];
				givenCorners[i][0] = givenCorners[i][2];
				givenCorners[i][2] = givenCorners[i][1];
				givenCorners[i][1] = temp;
				
			} else if (givenCorners[i][2] == 2 || givenCorners[i][2] == 5) {
				orientation += 2;
				temp = givenCorners[i][0];
				givenCorners[i][0] = givenCorners[i][1];
				givenCorners[i][1] = givenCorners[i][2];
				givenCorners[i][2] = temp;
				
			}
			
			for (int x = 0;  x < 8; x++) {
				if (Arrays.equals(givenCorners[i], validCorners[x])) {
					// Position, orientation
					int[] corner = {x, orientation};
					cornerPositionAndOrientation[i] = corner;
				}
			}
			
			orientation = 0;
		}
		
		for (int i = 0; i < 7; i++) {
			
			positionScore += positionIndex[cornerPositionAndOrientation[i][0]] * factorial(6 - i);
			for (int x = positionIndex[cornerPositionAndOrientation[i][0]] + 1; x < 8; x++) {
				positionIndex[x] -= 1;
			}
			
			orientationScore += (int) (cornerPositionAndOrientation[i][1] * Math.pow(3, 6 - i));
		}
		
		return orientationScore * 6 + positionScore;
	}
	
	// I was not able to get to this
	public int computeEdgeOneHash() {
		return 0;
	}
	
	// I was not able to get to this
	public int computeEdgeTwoHash() {
		return 0;
	}
	
	private int factorial(int number) {
		if (number == 0) {
			return 1;
		}
		return number * (number - 1);
	}
	
	
	
	// get the side veriables set
		private void setSides(HashMap<String, int[][]> state) {
			// set the sides
			red = state.get("Red");
			green = state.get("Green");
			yellow = state.get("Yellow");
			blue = state.get("Blue");
			orange = state.get("Orange");
			white = state.get("White");
		}
		
		// set the corners!
		private void setCorners(HashMap<String, int[][]> state) {
			
			int[][] givenCorners = {
					{green[0][2], yellow[0][0], red[2][0]}, 
					{red[2][2], yellow[0][2], blue[0][0]}, 
					{blue[2][0], yellow[2][2], orange[0][2]}, 
					{orange[0][0], yellow[2][0], green[2][2]}, 
					{green[2][0], white[0][0], orange[2][0]}, 
					{orange[2][2] , white[0][2], blue[2][2]}, 
					{blue[0][2], white[2][2], red[0][2]}, 
					{red[0][0], white[2][0], green[0][0]}};
			this.givenCorners = givenCorners;
		}
		
		private void setEdges(HashMap<String, int[][]> state) {
			
			int[][] givenEdges = {
					{green[2][1], orange[1][0]}, 
					{green[1][0], white[1][0]}, 
					{green[0][1], red[1][0]}, 
					{red[0][1], white[2][1]}, 
					{red[1][2], blue[0][1]}, 
					{blue[1][2], white[1][2]}, 
					{blue[2][1], orange[1][2]}, 
					{white[0][1], orange[2][1]}, 
					{orange[0][1], yellow[2][1]}, 
					{green[1][2], yellow[1][0]}, 
					{red[2][1], yellow[0][1]}, 
					{blue[1][0], yellow[1][2]}	
			};
			
			this.givenEdges = givenEdges;
		}
	
	

}
