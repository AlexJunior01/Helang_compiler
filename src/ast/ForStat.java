package ast;

	/*
		ForStat ::= "for" Ident "in" Expr ".." Expr StatList
	 */

import java.util.Map;

public class ForStat extends Stat {
	private String ident;
	private Expr startExpr;
	private Expr endExpr;
	private StatList statlist;
	
	public ForStat(String ident, Expr startExpr, Expr endExpr, StatList statlist) {
		super();
		this.ident = ident;
		this.startExpr = startExpr;
		this.endExpr = endExpr;
		this.statlist = statlist;
	}

	@Override
	public void eval(Map<String, Integer> memory) {
		int start = this.startExpr.eval(memory);
		int end = this.endExpr.eval(memory);

		if (memory.get(this.ident) != null)
			throw new RuntimeException("Variável " + this.ident + "já foi declarada.");

		for (int i = start; i < end; i++) {
			memory.put(this.ident, i);
			this.statlist.eval(memory);
		}

		memory.remove(this.ident);
	}
}
