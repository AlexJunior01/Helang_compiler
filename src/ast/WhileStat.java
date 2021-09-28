package ast;

	/*
		WhileStat ::= "while" Expr StatList
	*/

import java.util.Map;

public class WhileStat extends Stat {
	private Expr expr;
	private StatList statlist;
	
	public WhileStat(Expr expr, StatList statlist) {
		super();
		this.expr = expr;

		this.statlist = statlist;
	}

	@Override
	public void eval(Map<String, Integer> memory) {
		while (expr.eval(memory) != 0) {
			statlist.eval(memory);
		}
	}

	@Override
	public void genC() {
		System.out.println("while(" + expr.genC() + ") {");
		this.statlist.genC();
		System.out.println("}");
	}
}
