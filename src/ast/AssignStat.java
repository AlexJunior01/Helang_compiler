package ast;

	/*
		AssignStat ::= Ident "=" Expr ";"
	*/

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
	public void eval(Map<String, Integer> memory) {
		if(memory.get(this.ident) == null) {
			throw new RuntimeException("Variável não declarada!");
		}

		int valor = expr.eval(memory);

		memory.put(this.ident, valor);
	}
}
