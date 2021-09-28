package ast;

import lexer.Symbol;

import java.util.Map;

/*
		Number ::=  [�+�|�-�] Digit { Digit }
	 */

public class Numero {
	private Integer digit;
	private Symbol addOp;
	
	public Numero(int digit, Symbol addOp) {
		super();
		this.digit = digit;
		this.addOp = addOp;
	}

	public int eval(Map<String, Integer> memory) {
		if (addOp == Symbol.MENOS)
			return -digit;

		return digit;
	}

	public String genC() {
		if(addOp != null) {
			return addOp + digit.toString();
		}
		
		return digit.toString();
	}
}
