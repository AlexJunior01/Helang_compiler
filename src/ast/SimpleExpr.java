package ast;

import lexer.Symbol;

import java.util.Map;

/*
		SimpleExpr ::= Number |�(� Expr �)� | "!" SimpleExpr
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

    public int eval(Map<String, Integer> memory) {
		int resultado;

		if (number != null) {
			return number.eval(memory);
		} else if (expr != null) {
			return expr.eval(memory);
		} else if (addOp != null) {
			resultado = simpleExpr.eval(memory);
			if(addOp == Symbol.MENOS)
				return resultado*(-1);
			return resultado;
		} else if (simpleExpr != null) {
			resultado =  simpleExpr.eval(memory);
			if (resultado != 0)
				return 0;

			return 1;
		} else if (ident != null) {
			if (memory.get(ident) == null) {
				throw new RuntimeException("Variável " + ident + " não declarada.");
			}
			return memory.get(ident);
		} else {
			throw new RuntimeException("Erro interno dentro do SimpleExpre");
		}
    }

	public String genC() {

		if (number != null) {
			return number.genC();
		} else if (expr != null) {
			return expr.genC();
		} else if (addOp != null) {
			return addOp + simpleExpr.genC();
		} else if (simpleExpr != null) {
			return "!" + simpleExpr.genC();
		} else if (ident != null) {
			return ident;
		} else {
			throw new RuntimeException("Erro interno dentro do SimpleExpre");
		}
	}
}
