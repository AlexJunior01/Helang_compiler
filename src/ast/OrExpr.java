package ast;

import java.util.Map;


public class OrExpr extends AbstractExpr{
	private AndExpr firstAndExpr;
	private AndExpr secondAndExpr;
	private Type tipo;


	public OrExpr(AndExpr firstAndExpr, AndExpr secondAndExpr) {
		super();
		this.firstAndExpr = firstAndExpr;
		this.secondAndExpr = secondAndExpr;
	}


	public AbstractExpr eval(Map<String, Variable> memory) {
		AbstractExpr first = this.firstAndExpr.eval(memory);
		AbstractExpr second;

		if (this.secondAndExpr != null) {
			second = this.secondAndExpr.eval(memory);
			this.tipo = Type.booleanType;
			if ((Boolean) first.getValue() || (Boolean) second.getValue())
				return BooleanExpr.TRUE;
			return BooleanExpr.FALSE;
		}

		this.tipo = first.getType();
		return first;
	}

	public String genC() {
		String firstString = this.firstAndExpr.genC();
		
		if (this.secondAndExpr != null) {
			firstString = firstString + " || " + this.secondAndExpr.genC();
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
