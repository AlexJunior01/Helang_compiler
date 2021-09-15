package main;

public class Main {
	
	public static void main( String []args ) {
        char []input = ("var Int s;\r\n"
        		+ "var Int i;\r\n"
        		+ "s = 0;\r\n"
        		+ "for j in 1..100 {\r\n"
        		+ "s = s + j\r\n"
        		+ "}\r\n"
        		+ "println s;").toCharArray();
        
        Compiler compiler = new Compiler();

        compiler.compile(input);
    }
}
