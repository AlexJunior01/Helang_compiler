package ast;

	/*
		Expr ::= AndExpr [ "||" AndExpr ]
	 */

public class Expr {
	private AndExpr firstAndExpr;
	private AndExpr secondAndExpr;
	
	public Expr(AndExpr firstAndExpr, AndExpr secondAndExpr) {
		super();
		this.firstAndExpr = firstAndExpr;
		this.secondAndExpr = secondAndExpr;
	}
	

}
