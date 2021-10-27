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

//		int lengthExprs = secondOrExpr.size();
//		if (lengthExprs == 1) {
//
//
//			for (int i = 0; i < lengthExprs; i++) {
//				firstString += "plusPlus( ";
//			}
//
//			firstString += firstOrExpr.genC();
//
//			for (int i = 0; i < lengthExprs; i++) {
//				firstString += "," + secondOrExpr.get(i).genC() + ")";
//			}
//
//			return firstString;
//		}
		firstString = "\"Not implemented yet\"\n";
//
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
