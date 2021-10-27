package ast;

import java.util.Map;

public class ForStat extends Stat {
	private String ident;
	private Expr startExpr;
	private Expr endExpr;
	private StatList statlist;
	
	public ForStat(String ident, Expr startExpr, Expr endExpr, StatList statlist) {
		super();
		this.ident = ident;
		this.startExpr = startExpr;
		this.endExpr = endExpr;
		this.statlist = statlist;
	}

	@Override
	public void eval(Map<String, Variable> memory) {
		AbstractExpr start = this.startExpr.eval(memory);
		AbstractExpr end = this.endExpr.eval(memory);
		Variable variableFor = new Variable(this.ident, Type.integerType);

		Integer startValue = (Integer) start.getValue();
		Integer endValue = (Integer) end.getValue();
		
		if(endValue >= startValue) {

			if (memory.get(this.ident) != null)
				throw new RuntimeException("Variavel " + this.ident + "já foi declarada.");

			for (int i = startValue; i <= endValue; i++) {
				variableFor.setValue(i);
				memory.put(this.ident, variableFor);
				this.statlist.eval(memory);
			}

			memory.remove(this.ident);
		}

	}

	@Override
	public void genC() {
		System.out.print("for(int " + ident + " = " + startExpr.genC() +  "; " + ident);
		System.out.println(" <= " + endExpr.genC() + "; " + ident + "++) {");

		statlist.genC();
		
		System.out.println("}");
	}
}
