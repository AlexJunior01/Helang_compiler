package ast;

import lexer.Symbol;

import java.util.Map;


public class RelExpr  extends AbstractExpr{
	private AddExpr firstAddExpr;
	private Symbol relOp;
	private AddExpr secondAddExpr;
	private Type tipo;

	public RelExpr(AddExpr firstAddExpr, Symbol relOp, AddExpr secondAddExpr) {
		super();
		this.firstAddExpr = firstAddExpr;
		this.relOp = relOp;
		this.secondAddExpr = secondAddExpr;
	}


    public AbstractExpr eval(Map<String, Variable> memory) {
		AbstractExpr first = this.firstAddExpr.eval(memory);
		AbstractExpr second;
		int compare;
		
		if (relOp != null) {
			second = this.secondAddExpr.eval(memory);
		} else {
			this.tipo = first.getType();
			return first;
		}
		
		if(first.getType() != second.getType()) {
			throw new RuntimeException("Impossivel realizar comparacao entre tipos diferentes");
		}
		
		compare = first.compareTo(second);
		this.tipo = Type.booleanType;
		switch (relOp){
			case MAIOR:
				return new BooleanExpr(compare > 0);
			case MENOR:
				return new BooleanExpr(compare < 0);
			case MAIOR_IGUAL:
				return new BooleanExpr(compare >= 0);
			case MENOR_IGUAL:
				return new BooleanExpr(compare <= 0);
			case IGUAL:
				return new BooleanExpr(compare == 0);
			case DIFERENTE:
				return new BooleanExpr(compare != 0);
			default:
				throw  new RuntimeException("Esperando um RelOp.");
		}
    }

	public String genC() {
		String firstString = this.firstAddExpr.genC();
		
		if (this.secondAddExpr != null) {
			firstString = firstString + " " + relOp  + " " + this.secondAddExpr.genC();
		}
		
		return firstString;
	}

	@Override
	public Type getType() {
		return this.tipo;
	}

	@Override
	public Object getValue() {
		return null;
	}

	@Override
	public int compareTo(AbstractExpr aExpr) {
		return 0;
	}
}
