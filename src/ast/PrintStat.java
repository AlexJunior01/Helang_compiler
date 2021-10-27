package ast;

import java.util.Map;


public class PrintStat extends Stat {
	private Expr expr;

	public PrintStat(Expr expr) {
		super();
		this.expr = expr;
	}

	@Override
	public void eval(Map<String, Variable> memory) {
		AbstractExpr e = expr.eval(memory);
		if(memory.get("PRINT_ON_EVAL") != null){
			System.out.print(e.getValue());
		}
	}

	@Override
	public void genC() {
		System.out.println("printf(\"%d\", " + expr.genC() + ");");
	}
}
