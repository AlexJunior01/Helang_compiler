package ast;

import lexer.Symbol;

import java.util.Map;

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

    public int eval(Map<String, Integer> memory) {
		int first = this.firstMultExpr.eval(memory);
		int second;

		if (addOp == null) {
			return first;
		}

		second = this.secondMultExpr.eval(memory);
		if(addOp == Symbol.MAIS)
			return first + second;
		else if (addOp == Symbol.MENOS)
			return  first - second;
		else
			throw new RuntimeException("AddOp esperado.");

    }
}
