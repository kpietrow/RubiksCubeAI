package rubik;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;

public class InputReader {
	
	public InputStream is;

	// will read in the file here
	public String inputFile(String file) {
		String input = "";
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append(System.lineSeparator());
	            line = br.readLine();
	        }
	        input = sb.toString();
	    } catch (IOException e) {
	    	System.out.println("ERROR: File could not be read properly");
	    }
		
		return input;
	}
	
	// convert the file
	public String sortInput(String file) {
		String chr;
		int[] letters = new int[6];
		int index = 0;
		String sorted = "";
		
		try {
			// iterate through file
			for (int i = 0; i < file.length(); i++) {
				chr = file.substring(i, i + 1);
				
				// check characters, add to respective array
				if (chr.equals("R")) {
					letters[0] += 1;
					index += 1;
					sorted += "0";
				} else if (chr.equals("G")) {
					letters[1] += 1;
					index += 1;
					sorted += "1";
				} else if (chr.equals("Y")) {
					letters[2] += 1;
					index += 1;
					sorted += "2";
				} else if (chr.equals("B")) {
					letters[3] += 1;
					index += 1;
					sorted += "3";
				} else if (chr.equals("O")) {
					letters[4] += 1;
					index += 1;
					sorted += "4";
				} else if (chr.equals("W")) {
					letters[5] += 1;
					index += 1;
					sorted += "5";
					
				// don't stress over whitespace
				} else if (chr.matches("\\s")) {
				
				// IT'S A TRAP
				} else {
					System.out.println("ERROR: Invalid character: " + chr);
					System.exit(0);
				}
				
			}
		
		if (index != 54) {
			System.out.println("ERROR: Invalid number of characters: " + index);
			System.exit(0);
		}
		
		if (letters[0] != 9 || letters[1] != 9 || letters[2] != 9 || letters[3] != 9
				|| letters[4] != 9 || letters[5] != 9) {
			System.out.println("ERROR: Invalid amounts of characters.");
			System.exit(0);
		}
		
		} finally {
			return sorted;
		}
	
	
	}
}
