package ast;

import java.util.Map;

public class AssignStat extends Stat {
	private String ident;
	private Expr expr;
	
	public AssignStat(String ident, Expr expr) {
		super();
		this.ident = ident;
		this.expr = expr;
	}

	@Override
	public void eval(Map<String, Variable> memory) {
		Variable variavel = memory.get(this.ident);
		if(variavel == null) {
			throw new RuntimeException("Variável não declarada!");
		}

		AbstractExpr valor = expr.eval(memory);

		if (variavel.getType() != valor.getType()) {
			throw new RuntimeException("Tipos diferente");
		}

		variavel.setValue(valor.getValue());
		memory.put(this.ident, variavel);
	}

	@Override
	public void genC() {
		System.out.println(ident + " = " + expr.genC() + ";");
	}
}
