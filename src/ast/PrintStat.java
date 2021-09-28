package ast;

import java.util.Map;

/*
		PrintStat ::= "print" Expr ";"
	 */
public class PrintStat extends Stat {
	private Expr expr;

	public PrintStat(Expr expr) {
		super();
		this.expr = expr;
	}

	@Override
	public void eval(Map<String, Integer> memory) {
		int e = expr.eval(memory);

		System.out.print(e);
	}

	@Override
	public void genC() {
		System.out.println("printf(\"%d\", " + expr.genC() + ");");
	}
}
