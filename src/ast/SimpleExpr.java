package ast;

import lexer.Symbol;

/*
		SimpleExpr ::= Number |’(’ Expr ’)’ | "!" SimpleExpr
		| AddOp SimpleExpr | Ident
	*/

public class SimpleExpr {
	private Numero number;
	private Expr expr;
	private SimpleExpr simpleExpr;
	private Symbol addOp;
	private String ident;
	
	
	public SimpleExpr(Numero number, Expr expr, SimpleExpr simpleExpr, Symbol addOp, String ident) {
		super();
		this.number = number;
		this.expr = expr;
		this.simpleExpr = simpleExpr;
		this.addOp = addOp;
		this.ident = ident;
	}
}
