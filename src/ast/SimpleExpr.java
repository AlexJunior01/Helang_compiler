package ast;

import lexer.Symbol;

import java.util.Map;


public class SimpleExpr  extends AbstractExpr{
	private Numero number;
	private Expr expr;
	private SimpleExpr simpleExpr;
	private Symbol addOp;
	private String ident;
	private String literalString;
	private Boolean boolVar;
	private Type tipo;
	
	public SimpleExpr(Numero number, Expr expr, SimpleExpr simpleExpr, Symbol addOp, String literalString, String ident, Boolean boolVar) {
		super();
		this.number = number;
		this.expr = expr;
		this.simpleExpr = simpleExpr;
		this.addOp = addOp;
		this.ident = ident;
		this.boolVar = boolVar;
		this.literalString = literalString;
	}

    public AbstractExpr eval(Map<String, Variable> memory) {
		AbstractExpr resultado;
		Variable variavel;

		if (number != null) {
			this.tipo = Type.integerType;
			return number.eval(memory);
		} else if (expr != null) {
			return expr.eval(memory);
		} else if (addOp != null) {
			resultado = simpleExpr.eval(memory);
			if(addOp == Symbol.MENOS)
				return new IntegerExpr((Integer)resultado.getValue()*(-1));
			return resultado;
		} else if (simpleExpr != null) {
			resultado =  simpleExpr.eval(memory);
			if ((Boolean) resultado.getValue())
				return BooleanExpr.FALSE;

			return BooleanExpr.TRUE;
		} else if (ident != null) {
			if (memory.get(ident) == null) {
				throw new RuntimeException("Variável " + ident + " não declarada.");
			}
			variavel = memory.get(ident);
			if (variavel.getType() == Type.booleanType) {
				this.tipo = Type.booleanType;
				return new BooleanExpr((Boolean) variavel.getValor());
			} else if (variavel.getType() == Type.integerType) {
				this.tipo = Type.integerType;
				return new IntegerExpr((Integer) variavel.getValor());
			} else {
				this.tipo = Type.stringType;
				return new StringExpr((String) variavel.getValor());
			}
		} else if(literalString != null) {
			this.tipo = Type.stringType;
			return new StringExpr(literalString);
		} else if(boolVar != null) {
			this.tipo = Type.booleanType;
			return new BooleanExpr(boolVar);
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
		} else if(literalString != null) {
			return '"'+literalString+'"';
		} else if(boolVar != null) {
			return boolVar.toString();
		} else {
			throw new RuntimeException("Erro interno dentro do SimpleExprE");
		}
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
