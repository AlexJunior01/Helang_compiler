package ast;

import java.util.Map;

/*
 		OrExpr ::= AndExpr [ "||" AndExpr ]
	*/

public class OrExpr {
	private AndExpr firstAndExpr;
	private AndExpr secondAndExpr;
	
	
	public OrExpr(AndExpr firstAndExpr, AndExpr secondAndExpr) {
		super();
		this.firstAndExpr = firstAndExpr;
		this.secondAndExpr = secondAndExpr;
	}


	public int eval(Map<String, Integer> memory) {
		int first = this.firstAndExpr.eval(memory);
		int second;

		if (this.secondAndExpr != null) {
			second = this.secondAndExpr.eval(memory);
			if (first != 0 || second != 0)
				return 1;
			return 0;
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
