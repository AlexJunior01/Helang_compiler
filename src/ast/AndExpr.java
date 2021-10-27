package ast;


import java.util.Map;

public class AndExpr extends AbstractExpr{
	private RelExpr firstRelExpr;
	private RelExpr secondRelExpr;
	private Type tipo;

	public AndExpr(RelExpr firstRelExpr, RelExpr secondRelExpr) {
		super();
		this.firstRelExpr = firstRelExpr;
		this.secondRelExpr = secondRelExpr;
	}

    public AbstractExpr eval(Map<String, Variable> memory) {
		AbstractExpr first = this.firstRelExpr.eval(memory);
		AbstractExpr second;

		if (this.secondRelExpr != null) {
			second = this.secondRelExpr.eval(memory);
			this.tipo = Type.booleanType;
			if ((Boolean) first.getValue() && (Boolean) second.getValue())
				return BooleanExpr.TRUE;
			return BooleanExpr.FALSE;
		}

		this.tipo = first.getType();
		return first;
    }

	public String genC() {
		String firstString = this.firstRelExpr.genC();
		
		if (this.secondRelExpr != null) {
			firstString = firstString + " && " + this.secondRelExpr.genC();
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
