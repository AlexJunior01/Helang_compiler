package ast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

	/*
		Program ::= VarList { Stat }
	*/

public class Program {
	
	private VarList varlist;
	private List<Stat> stat;
	
	public Program(VarList varlist, List<Stat> stat) {
			super();
			this.varlist = varlist;
			this.stat = stat;
	}
	
	public void genC() {
		 System.out.println("#include <stdio.h>\n");
	     System.out.println("void main() {");
	     
	     this.varlist.genC();
	     
	     for(Stat oneStat: stat) {
	    	 oneStat.genC();
	     }
	     
	     System.out.println("\n}\n");
	}

	public void eval() {
		Map<String, Integer> memory = new HashMap<>();

		this.varlist.eval(memory);

		for (Stat oneStat : this.stat) {
			oneStat.eval(memory);
		}
	}
}
