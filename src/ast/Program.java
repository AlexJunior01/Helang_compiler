package ast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Program {
	
	private Stat stat;
	private List<Stat> statList;
	
	public Program(Stat stat, List<Stat> statList) {
		super();
		this.stat = stat;
		this.statList = statList;
	}

	public void genC() {
		 this.run(false);
		 System.out.println("#include <stdio.h>");
		 System.out.println("#include <stdbool.h>");
		 System.out.println("#include <string.h>");
		 System.out.println("char aux[255];");
		 System.out.println("char buffer[255];");

		 System.out.println("char * plusPlus(char * var1, char * var2) {\n" +
				 "\tstrcat(buffer, var1);\n" +
				 "\tstrcat(buffer, var2);\n" +
				 "\treturn buffer;\n" +
				 "}");

		 System.out.println("char* castBoolean(bool val) {\n" +
				 "\tval == true ? strcpy(aux, \"true\") : strcpy(aux, \"false\");\n" +
				 "\treturn aux;\n" +
				 "}");

		System.out.println("char* castInteger(int val) {\n" +
				"\tsprintf(aux, \"%d\", val);\n" +
				"\n" +
				"\treturn aux;\n" +
				"}");

	     System.out.println("void main() {");
	     
	     this.stat.genC();
	     
	     for(Stat oneStat: statList) {
	    	 oneStat.genC();
	     }
	     
	     System.out.println("\n}\n");
	}

	public void run(boolean print) {

		Map<String, Variable> memory = new HashMap<>();

		if (print) {
			Variable printar = new Variable("PRINT_ON_EVAL", Type.booleanType);
			memory.put("PRINT_ON_EVAL", printar);
		}

		this.stat.eval(memory);

		for (Stat oneStat : this.statList) {
			oneStat.eval(memory);
		}
	}
}
