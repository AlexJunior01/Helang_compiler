package ast;

	/*
		AndExpr ::= RelExpr [ "&&" RelExpr ]
	*/

import java.util.Map;

public class AndExpr {
	private RelExpr firstRelExpr;
	private RelExpr secondRelExpr;
	
	public AndExpr(RelExpr firstRelExpr, RelExpr secondRelExpr) {
		super();
		this.firstRelExpr = firstRelExpr;
		this.secondRelExpr = secondRelExpr;
	}

    public int eval(Map<String, Integer> memory) {
		int first = this.firstRelExpr.eval(memory);
		int second;

		if (this.secondRelExpr != null) {
			second = this.secondRelExpr.eval(memory);
			if (first != 0 && second != 0)
				return 1;
			return 0;
		}

		return first;
    }
}
