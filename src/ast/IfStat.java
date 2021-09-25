package ast;

import java.util.Map;

/*
		IfStat ::= "if" Expr StatList [
		"else" StatList ]
	*/
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
	public void eval(Map<String, Integer> memory) {
		int e = expr.eval(memory);

		if (e != 0) {
			ifStatList.eval(memory);
		} else if (elseStatList != null){
			elseStatList.eval(memory);
		}
	}
}
