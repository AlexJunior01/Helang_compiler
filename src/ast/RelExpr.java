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


    public AbstractExpr eval(Map<String, Variable> memory) {
		AbstractExpr first = this.firstAddExpr.eval(memory);
		AbstractExpr second;
		Boolean resultado = true;
		if (relOp != null) {
			second = this.secondAddExpr.eval(memory);
		} else {
			return first;
		}
		if(first.getType() != second.getType()) {
			throw new RuntimeException("Impossivel realizar comparacao entre tipos diferentes");
		}
		// Fazer comparador nas classes filhas
//		switch (relOp){
//			case MAIOR:
//				resultado = first.getValue() > second.getValue();
//			case MENOR:
//				resultado = first < second;
//			case MAIOR_IGUAL:
//				resultado = first >= second;
//			case MENOR_IGUAL:
//				resultado = first <= second;
//			case IGUAL:
//				resultado = first == second;
//			case DIFERENTE:
//				resultado = first != second;
//			default:
//				throw  new RuntimeException("Esperando um RelOp.");
//		}

		return new BooleanExpr(resultado);
    }

	public String genC() {
		String firstString = this.firstAddExpr.genC();
		
		if (this.secondAddExpr != null) {
			firstString = firstString + " " + relOp  + " " + this.secondAddExpr.genC();
		}
		
		return firstString;
	}
}
