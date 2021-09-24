package ast;

	/*
		AndExpr ::= RelExpr [ "&&" RelExpr ]
	*/

public class AndExpr {
	private RelExpr firstRelExpr;
	private RelExpr secondRelExpr;
	
	public AndExpr(RelExpr firstRelExpr, RelExpr secondRelExpr) {
		super();
		this.firstRelExpr = firstRelExpr;
		this.secondRelExpr = secondRelExpr;
	}
}
