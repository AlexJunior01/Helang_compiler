package ast;

import lexer.Symbol;

import java.util.List;
import java.util.Map;


public class AddExpr {
	private MultExpr firstMultExpr;
	private List<Symbol> addOp;
	private List<MultExpr> secondMultExpr;
	
	public AddExpr(MultExpr firstMultExpr, List<Symbol> addOp, List<MultExpr> secondMultExpr) {
		super();
		this.firstMultExpr = firstMultExpr;
		this.addOp = addOp;
		this.secondMultExpr = secondMultExpr;
	}

    public AbstractExpr eval(Map<String, Variable> memory) {
		AbstractExpr first = this.firstMultExpr.eval(memory);
		int resultado = 0;

		if (this.addOp.isEmpty()) {
			return first;
		}
		
		int lengthaddOp = addOp.size();
		int lengthExprs = secondMultExpr.size();
		
		if (lengthaddOp != lengthExprs)
			throw new RuntimeException("Numero de operadores diferente do numero de expressoes");

		resultado = (Integer) first.getValue();
		
		for (int i = 0; i < lengthaddOp; i++) {
			if (addOp.get(i) == Symbol.MAIS)
				resultado += (Integer) secondMultExpr.get(i).eval(memory).getValue();
			else if (addOp.get(i) == Symbol.MENOS)
				resultado -= (Integer) secondMultExpr.get(i).eval(memory).getValue();
			else
				throw new RuntimeException("AddOp esperado");
		}
		return new IntegerExpr(resultado);
    }

	public String genC() {
		String firstString = this.firstMultExpr.genC();
		
		if (this.addOp.isEmpty()) {
			return firstString;
		}
		
		int lengthaddOp = addOp.size();
		int lengthExprs = secondMultExpr.size();
		
		if (lengthaddOp != lengthExprs)
			throw new RuntimeException("Numero de operadores diferente do numero de expressoes");
		
		for (int i = 0; i < lengthaddOp; i++) {
			if(addOp.get(i) != Symbol.MAIS && addOp.get(i) != Symbol.MENOS) {
				throw new RuntimeException("AddOp esperado");
			}
			
			firstString = firstString + " " + addOp.get(i) + " " + secondMultExpr.get(i).genC();
		}
		return firstString;
	}
}
