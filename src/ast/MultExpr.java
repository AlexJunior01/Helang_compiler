package ast;

import lexer.Symbol;

/*
		MultExpr ::= SimpleExpr { MultOp SimpleExpr }
	*/

public class MultExpr {
	private SimpleExpr firstSimpleExpr;
	private Symbol multOp;
	private SimpleExpr secondSimpleExpr;
	
	public MultExpr(SimpleExpr firstSimpleExpr, Symbol multOp, SimpleExpr secondSimpleExpr) {
		super();
		this.firstSimpleExpr = firstSimpleExpr;
		this.multOp = multOp;
		this.secondSimpleExpr = secondSimpleExpr;
	}
}
