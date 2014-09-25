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
		
		InputStream is = ir.inputFile(input);
		input = ir.fileConverter(is);
		System.out.println(input);
	}
}
