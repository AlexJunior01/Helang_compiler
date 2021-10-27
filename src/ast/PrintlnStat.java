package ast;


import java.util.Map;

public class PrintlnStat extends Stat {
	private Expr expr;

	public PrintlnStat(Expr expr) {
		super();
		this.expr = expr;
	}

	@Override
	public void eval(Map<String, Variable> memory) {
		AbstractExpr e = expr.eval(memory);

		if(memory.get("PRINT_ON_EVAL") != null){
			System.out.println(e.getValue());
		}
	}

	@Override
	public void genC() {
		if (expr.getType() == Type.booleanType)
			System.out.println("printf(\"%s\\n\", \" + expr.genC() + \"? \"true\" : \"false\");\"");
		else if (expr.getType() == Type.integerType)
			System.out.println("printf(\"%d\\n\", " + expr.genC() + ");");
		else
			System.out.println("printf(\"%s\\n\", " + expr.genC() + ");");
	}
}
