package ast;


import java.util.Map;

public class AndExpr {
	private RelExpr firstRelExpr;
	private RelExpr secondRelExpr;
	
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

			if ((Boolean) first.getValue() && (Boolean) second.getValue())
				return BooleanExpr.TRUE;
			return BooleanExpr.FALSE;
		}

		return first;
    }

	public String genC() {
		String firstString = this.firstRelExpr.genC();
		
		if (this.secondRelExpr != null) {
			firstString = firstString + " && " + this.secondRelExpr.genC();
		}
		
		return firstString;
	}
}
