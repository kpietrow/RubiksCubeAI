package rubik;

public class OldCube {
	
	/*
	 * Side Diagram
	 * ______________________
	 * 
	 * red[0-8]
	 ***[0-2]
	 ***[3-5]
	 ***[6-8]
	 *
	 * green[9-17]
	 ***[9-11]
	 ***[12-14]
	 ***[15-17]
	 *
	 * yellow[18-26]
	 ***[18-20]
	 ***[21-23]
	 ***[24-26]
	 *
	 * blue[27-35]
	 ***[27-29]
	 ***[30-32]
	 ***[33-35
	 *
	 * orange[36-44]
	 ***[36-38]
	 ***[39-41]
	 ***[42-44]
	 *
	 * white[45-53]
	 ***[45-47]
	 ***[48-50]
	 ***[51-53]
	 */
	public int[] sides = new int[54];
	
	public OldCube(){}
	
	public void init() {
		for (int i = 0; i < sides.length; i++) {
			sides[i] = 0;
		}
	}
	
	/*
	 * Functions for each turn
	 * From viewing the yellow side straight on. 
	 * Clockwise will always be in context of the orange and blue sides
	 */
	public void clockwiseColumnOne() {
		int[] yellow = {sides[18], sides[21], sides[24]};
		sides[18] = sides[36];
		sides[21] = sides[39];
	}
	
	

}
