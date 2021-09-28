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
			throw new RuntimeException("Numero de operadores diferente do numero de expressoes");

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

	public String genC() {
		String firstString = this.firstSimpleExpr.genC();
		
		if (this.multOp.isEmpty()) {
			return firstString;
		}
		
		int lengthmultOp = multOp.size();
		int lengthExprs = secondSimpleExpr.size();
		
		if (lengthmultOp != lengthExprs)
			throw new RuntimeException("Numero de operadores diferente do numero de expressoes");
		
		for (int i = 0; i < lengthmultOp; i++) {
			if(multOp.get(i) != Symbol.DIVISAO && multOp.get(i) != Symbol.MULTIPLICACAO &&
					multOp.get(i) != Symbol.MOD) {
				throw new RuntimeException("MultOp esperado");
			}
			
			firstString = firstString + " " + multOp.get(i) + " " + secondSimpleExpr.get(i).genC();
		}
		return firstString;
	}
}
