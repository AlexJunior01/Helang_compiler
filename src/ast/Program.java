package ast;

import java.util.List;

	/*
		Program ::= VarList { Stat }
	*/

public class Program {
	
	public Program(VarList varlist, List<Stat> stat) {
			super();
			this.varlist = varlist;
			this.stat = stat;
		}
	
	private VarList varlist;
	private List<Stat> stat;
}
