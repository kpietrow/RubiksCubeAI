package rubik;

import java.io.InputStream;
import java.util.Scanner;


public class Solvable {
	public static void main(String [ ] args) {
		
		String input = "";
		InputReader ir = new InputReader();
		
		for (int i = 0; i < args.length; i++) {
			input += args[i];
		}
		
		input = ir.inputFile(input);
		input = ir.sortInput(input);
		System.out.println(input);
	}
}
