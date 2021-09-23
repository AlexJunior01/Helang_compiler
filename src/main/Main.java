package main;

public class Main {
	
	public static void main( String []args ) {
        char []input = ("var Int i;\n" +
				"var Int soma;\n" +
				"var Int somaFor;\n" +
				"var Int n;\n" +
				"var Int verd;\n" +
				"n = 100;\n" +
				"soma = 0;\n" +
				"i = 0;\n" +
				"verd = 2;\n" +
				"while i < n && !!verd { \n" +
				"  if i%2 == (0+0*0/1) {\n" +
				"\tsoma = (--soma) + i*i;\n" +
				"  }\n" +
				"  i = i + 1;\n" +
				"}\n" +
				"somaFor = 0;\n" +
				"for k in 0..100 {\n" +
				"  if i%2 == 0 {\n" +
				"\tsomaFor = somaFor + k*k;\n" +
				"  }\n" +
				"}\n" +
				"println soma;\n" +
				"println somaFor; ").toCharArray();
        
        Compiler compiler = new Compiler();

        compiler.compile(input);
    }
}
