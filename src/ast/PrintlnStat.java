package ast;

	/*
		PrintlnStat ::= "println" Expr ";"
	 */

import java.util.Map;

public class PrintlnStat extends Stat {
	private Expr expr;

	public PrintlnStat(Expr expr) {
		super();
		this.expr = expr;
	}

	@Override
	public void eval(Map<String, Integer> memory) {
		int e = expr.eval(memory);

		System.out.println(e);
	}
}
