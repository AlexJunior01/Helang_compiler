package ast;

	import lexer.Symbol;

	import java.util.List;

/*
		Expr ::= OrExpr { "++" OrExpr }
	*/

import java.util.Map;


public class Expr {
	private OrExpr firstOrExpr;
	private List<OrExpr> secondOrExpr;
	

    public Expr(OrExpr firstOrExpr, List<OrExpr> secondOrExpr) {
		super();
		this.firstOrExpr = firstOrExpr;
		this.secondOrExpr = secondOrExpr;
	}


	public AbstractExpr eval(Map<String, Variable> memory) {
		AbstractExpr first = this.firstOrExpr.eval(memory);
		AbstractExpr second;
		String resultado;
		int lengthExprs = secondOrExpr.size();

		if(secondOrExpr.isEmpty()) {
			return first;
		}

		resultado = (String) first.getValue();
		for (int i = 0; i < lengthExprs; i++) {
			resultado = resultado + secondOrExpr.get(i).eval(memory).getValue().toString();
		}

		return new StringExpr(resultado);
    }


	public String genC() {
		String firstString = this.firstOrExpr.genC();
		int lengthExprs = secondOrExpr.size();

		for (OrExpr orExpr : secondOrExpr) {
			firstString += orExpr.genC();
		}

		return firstString;
	}
}
