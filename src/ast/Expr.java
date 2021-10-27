package ast;


import java.util.List;
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
		String resultado;
		int lengthExprs = secondOrExpr.size();

		if(secondOrExpr.isEmpty()) {
			return first;
		}

		resultado = first.getValue().toString();
		for (int i = 0; i < lengthExprs; i++) {
			resultado = resultado + secondOrExpr.get(i).eval(memory).getValue().toString();
		}

		return new StringExpr(resultado);
    }


	public String genC() {
		String firstString = "teste";
		if(this.secondOrExpr.isEmpty()) {
			firstString = this.firstOrExpr.genC();
			return  firstString;
		} else {
			return firstString;
		}
	}
}
