package ast;

import lexer.Symbol;

/*
		Number ::=  [’+’|’-’] Digit { Digit }
	 */

public class Numero {
	private int digit;
	private Symbol addOp;
	
	public Numero(int digit, Symbol addOp) {
		super();
		this.digit = digit;
		this.addOp = addOp;
	}
}
