package ast;

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


	public int eval(Map<String, Integer> memory) {
		int first = this.firstOrExpr.eval(memory);
		int second;

		if (this.secondOrExpr != null) {
			second = this.secondOrExpr.eval(memory);
			if (first != 0 || second != 0)
				return 1;
			return 0;
		}

		return first;
    }


	public String genC() {
		String firstString = this.firstOrExpr.genC();
		
		if (this.secondOrExpr != null) {
			firstString = firstString + " || " + this.secondOrExpr.genC();
		}
		
		return firstString;
	}
}
