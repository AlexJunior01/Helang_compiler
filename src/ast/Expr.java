package ast;


import java.util.List;
import java.util.Map;


public class Expr extends AbstractExpr{
	private OrExpr firstOrExpr;
	private List<OrExpr> secondOrExpr;
	private Type tipo;


	public Expr(OrExpr firstOrExpr, List<OrExpr> secondOrExpr) {
		super();
		this.firstOrExpr = firstOrExpr;
		this.secondOrExpr = secondOrExpr;
	}


	public AbstractExpr eval(Map<String, Variable> memory) {
		AbstractExpr first = this.firstOrExpr.eval(memory);
		String resultado;
		int lengthExprs = secondOrExpr.size();

		if(secondOrExpr.isEmpty()) {
			this.tipo = first.getType();
			return first;
		}

		resultado = first.getValue().toString();
		for (int i = 0; i < lengthExprs; i++) {
			resultado = resultado + secondOrExpr.get(i).eval(memory).getValue().toString();
		}

		this.tipo = Type.stringType;
		return new StringExpr(resultado);
    }


	public String genC() {
		String firstString = "";
		if(this.secondOrExpr.isEmpty()) {
			firstString = this.firstOrExpr.genC();
			return  firstString;
		}

		int lengthExprs = secondOrExpr.size();

		for (int i = 0; i < lengthExprs; i++) {
			firstString += "plusPlus( ";
		}

		firstString += this.genCType(firstOrExpr);

		for (int i = 0; i < lengthExprs; i++) {
			firstString += ", " + this.genCType(secondOrExpr.get(i)) + ")";
		}

		return firstString;
	}

	private String genCType(OrExpr orExpr) {
		if(orExpr.getType() == Type.booleanType) {
			return "castBoolean(" + orExpr.genC() + ")";
		} else if (orExpr.getType() == Type.integerType) {
			return "castInteger(" + orExpr.genC() + ")";
		} else
			return orExpr.genC();
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
