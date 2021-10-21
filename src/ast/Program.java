package ast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

	/*
		Program ::= Stat { Stat }
	*/

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
		 System.out.println("#include <stdio.h>\n");
	     System.out.println("void main() {");
	     
	     this.stat.genC();
	     
	     for(Stat oneStat: statList) {
	    	 oneStat.genC();
	     }
	     
	     System.out.println("\n}\n");
	}

	public void run(boolean print) {
		/*
			Nos adicionamos a variavel print para conseguirmos controlar quando o run ira printar os resultados ou nao.
			Fizemos isso pois queriamos utilizar o run() antes de gerar o codigo em C para validar se a entrada e um
			programa válido para a gramatica.
		 */

		Map<String, Integer> memory = new HashMap<>();

		if (print)
			memory.put("PRINT_ON_EVAL", 1);
		this.stat.eval(memory);

		for (Stat oneStat : this.statList) {
			oneStat.eval(memory);
		}
	}
}
