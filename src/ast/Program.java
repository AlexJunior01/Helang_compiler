package ast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

	/*
		Program ::= VarList { Stat }
	*/

public class Program {
	
	public Program(VarList varlist, List<Stat> stat) {
			super();
			this.varlist = varlist;
			this.stat = stat;
	}

	public void eval() {
		Map<String, Integer> memory = new HashMap<>();

		this.varlist.eval(memory);

		for (Stat oneStat : this.stat) {
			oneStat.eval(memory);
		}
	}
	
	private VarList varlist;
	private List<Stat> stat;
}
