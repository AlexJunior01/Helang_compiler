package ast;

import lexer.Symbol;

import java.util.Map;

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

    public int eval(Map<String, Integer> memory) {
		int first = this.firstAddExpr.eval(memory);
		int second;

		if (relOp != null) {
			second = this.secondAddExpr.eval(memory);
		} else {
			return first;
		}

		switch (relOp){
			case MAIOR:
				if (first > second)
					return 1;
				else
					return 0;
			case MENOR:
				if (first < second)
					return 1;
				else
					return 0;
			case MAIOR_IGUAL:
				if (first >= second)
					return 1;
				else
					return 0;
			case MENOR_IGUAL:
				if (first <= second)
					return 1;
				else
					return 0;
			case IGUAL:
				if (first == second)
					return 1;
				else
					return 0;
			case DIFERENTE:
				if (first != second)
					return 1;
				else
					return 0;
			default:
				throw  new RuntimeException("Esperando um RelOp.");
		}
    }
}
