package ast;

import java.util.Map;


public class OrExpr {
	private AndExpr firstAndExpr;
	private AndExpr secondAndExpr;
	
	
	public OrExpr(AndExpr firstAndExpr, AndExpr secondAndExpr) {
		super();
		this.firstAndExpr = firstAndExpr;
		this.secondAndExpr = secondAndExpr;
	}


	public AbstractExpr eval(Map<String, Variable> memory) {
		AbstractExpr first = this.firstAndExpr.eval(memory);
		AbstractExpr second;

		if (this.secondAndExpr != null) {
			second = this.secondAndExpr.eval(memory);
			if ((Boolean) first.getValue() || (Boolean) second.getValue())
				return BooleanExpr.TRUE;
			return BooleanExpr.FALSE;
		}

		return first;
	}

	public String genC() {
		String firstString = this.firstAndExpr.genC();
		
		if (this.secondAndExpr != null) {
			firstString = firstString + " || " + this.secondAndExpr.genC();
		}
		
		return firstString;
	}
}
