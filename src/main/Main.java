package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import ast.Program;

public class Main {		
	
	public static void main( String []args ) {
		File file;
		FileReader stream;
		int numChRead;
		String option = args[0];

		if ( args.length > 1 ){
			file = new File(args[1]);
			if (!file.exists() || !file.canRead()) {
				System.out.println("Either the file " + args[1] + " does not exist or it cannot be read");
				throw new RuntimeException();
			}
			try {
				stream = new FileReader(file);
			} catch (FileNotFoundException e) {
				System.out.println("Something wrong: file does not exist anymore");
				throw new RuntimeException();
			}
			// one more character for '\0' at the end that will be added by the
			// compiler
			char[] input = new char[(int) file.length() + 1];

			try {
				numChRead = stream.read(input, 0, (int) file.length());
			} catch (IOException e) {
				System.out.println("Error reading file " + args[0]);
				throw new RuntimeException();
			}

			if (numChRead != file.length()) {
				System.out.println("Read error");
				throw new RuntimeException();
			}
			try {
				stream.close();
			} catch (IOException e) {
				System.out.println("Error in handling the file " + args[0]);
				throw new RuntimeException();
			}


			Compiler compiler = new Compiler();
			Program p = compiler.compile(input);
	

			if (option.equals("-gen")){
				p.genC();
			} else if (option.equals("-run")) {
				p.run(true);
			}
		}
    }
}
