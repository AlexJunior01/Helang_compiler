package ast;

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
}
