package ast;

import java.util.List;
import java.util.Map;

/*
		StatList ::= "{" { Stat } "}"
	 */

public class StatList {
	private List<Stat> stat;

	public StatList(List<Stat> stat) {
		super();
		this.stat = stat;
	}

    public void eval(Map<String, Integer> memory) {
    	for(Stat oneStat : stat) {
			oneStat.eval(memory);
		}
	}
}