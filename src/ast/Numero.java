package ast;

import lexer.Symbol;

import java.util.Map;


public class Numero {
	private Integer digit;
	private Symbol addOp;
	
	public Numero(int digit, Symbol addOp) {
		super();
		this.digit = digit;
		this.addOp = addOp;
	}

	public AbstractExpr eval(Map<String, Variable> memory) {
		if (addOp == Symbol.MENOS)
			return new IntegerExpr(-digit);

		return new IntegerExpr(digit);
	}

	public String genC() {
		if(addOp != null) {
			return addOp + digit.toString();
		}
		
		return digit.toString();
	}
}
