package ast;

import java.util.List;

/*
		StatList ::= "{" { Stat } "}"
	 */

public class StatList {
	private List<Stat> stat;

	public StatList(List<Stat> stat) {
		super();
		this.stat = stat;
	}

}
