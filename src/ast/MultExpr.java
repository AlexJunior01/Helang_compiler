package ast;

import lexer.Symbol;

import java.util.List;
import java.util.Map;

/*
		MultExpr ::= SimpleExpr { MultOp SimpleExpr }
	*/

public class MultExpr {
	private SimpleExpr firstSimpleExpr;
	private List<Symbol> multOp;
	private List<SimpleExpr> secondSimpleExpr;
	
	public MultExpr(SimpleExpr firstSimpleExpr, List<Symbol> multOp, List<SimpleExpr> secondSimpleExpr) {
		super();
		this.firstSimpleExpr = firstSimpleExpr;
		this.multOp = multOp;
		this.secondSimpleExpr = secondSimpleExpr;
	}

    public int eval(Map<String, Integer> memory) {
		int first = firstSimpleExpr.eval(memory);
		int resultado = 0;
		if(this.multOp.isEmpty()) {
			return first;
		}

		int lengthMult = multOp.size();
		int lengthExprs = secondSimpleExpr.size();

		if (lengthMult != lengthExprs)
			throw new RuntimeException("Número de operadores diferente do número de expressões");

		resultado = first;
		for (int i = 0; i < lengthMult; i++) {
			if (multOp.get(i) == Symbol.MULTIPLICACAO)
				resultado *= secondSimpleExpr.get(i).eval(memory);
			else if (multOp.get(i) == Symbol.DIVISAO)
				resultado /= secondSimpleExpr.get(i).eval(memory);
			else if (multOp.get(i) == Symbol.MOD)
				resultado %= secondSimpleExpr.get(i).eval(memory);
			else
				throw new RuntimeException("MultOp esperado");
		}
		return resultado;
    }
}
