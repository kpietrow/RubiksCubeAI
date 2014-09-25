package rubik;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;

public class InputReader {
	
	public InputStream is;
	public String input;

	// will read in the file here
	public InputStream inputFile(String file) {
		try {
			is = new FileInputStream("c://" + file);
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File not found.");
			System.exit(0);
		} catch (IOException e) {
			System.out.println("ERROR: Something went wrong importing the file.");
			System.exit(0);
		}
		
		return is;
	}
	
	// convert the file
	public String fileConverter(InputStream is) {
		String chr;
		int[] letters = new int[6];
		int index = 0;
		
		try {
			
			// scan in file
			java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
			
			// iterate through file
			while (s.hasNext()) {
				chr = s.next();
				
				// check characters, add to respective array
				if (chr == "R") {
					letters[0] += 1;
				} else if (chr == "G") {
					letters[1] += 1;
				} else if (chr == "Y") {
					letters[2] += 1;
				} else if (chr == "B") {
					letters[3] += 1;
				} else if (chr == "O") {
					letters[4] += 1;
				} else if (chr == "W") {
					letters[5] += 1;
					
				// don't stress over whitespace
				} else if (chr.matches("\\s")) {
				
				// IT'S A TRAP
				} else {
					System.out.println("ERROR: Invalid character.");
					System.exit(0);
				}
				
				index += 1;
				input += chr;
			}
		
		if (index != 54) {
			System.out.println("ERROR: Invalid number of characters.");
			System.exit(0);
		}
		
		if (letters[0] != 9 || letters[1] != 9 || letters[2] != 9 || letters[3] != 9
				|| letters[4] != 9 || letters[5] != 9) {
			System.out.println("ERROR: Invalid amounts of characters.");
			System.exit(0);
		}
		
		} finally {
			return input;
		}
	
	
	}
}
