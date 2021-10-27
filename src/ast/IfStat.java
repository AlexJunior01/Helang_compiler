package ast;

import java.util.Map;


public class IfStat extends Stat {
	private Expr expr;
	private StatList ifStatList;
	private StatList elseStatList;
	
	public IfStat(Expr expr, StatList ifStatList, StatList elseStatList) {
		super();
		this.expr = expr;
		this.ifStatList = ifStatList;
		this.elseStatList = elseStatList;
	}

	@Override
	public void eval(Map<String, Variable> memory) {
		AbstractExpr e = expr.eval(memory);
		Boolean valor = (Boolean) e.getValue();

		if (valor) {
			ifStatList.eval(memory);
		} else if (elseStatList != null){
			elseStatList.eval(memory);
		}
	}

	@Override
	public void genC() {
		System.out.println("if(" + expr.genC() + ") {" );
		this.ifStatList.genC();
		System.out.println("}");
		
		if(elseStatList != null) {
			System.out.println("else {" );
			this.elseStatList.genC();
			System.out.println("}");
		}
	}
}
