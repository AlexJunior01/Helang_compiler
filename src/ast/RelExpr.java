package ast;

import lexer.Symbol;

/*
		RelExpr ::= AddExpr [ RelOp AddExpr ]
	*/

public class RelExpr {
	private AddExpr firstAddExpr;
	private Symbol relOp;
	private AddExpr secondAddExpr;
	
	public RelExpr(AddExpr firstAddExpr, Symbol relOp, AddExpr secondAddExpr) {
		super();
		this.firstAddExpr = firstAddExpr;
		this.relOp = relOp;
		this.secondAddExpr = secondAddExpr;
	}
}
