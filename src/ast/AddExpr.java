package ast;

import lexer.Symbol;

/*
		AddExpr ::= MultExpr { AddOp MultExpr }
	*/
public class AddExpr {
	private MultExpr firstMultExpr;
	private Symbol addOp;
	private MultExpr secondMultExpr;
	
	public AddExpr(MultExpr firstMultExpr, Symbol addOp, MultExpr secondMultExpr) {
		super();
		this.firstMultExpr = firstMultExpr;
		this.addOp = addOp;
		this.secondMultExpr = secondMultExpr;
	}
}
