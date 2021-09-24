package ast;

	/*
		WhileStat ::= "while" Expr StatList
	*/

public class WhileStat extends Stat {
	private Expr expr;
	private StatList statlist;
	
	public WhileStat(Expr expr, StatList statlist) {
		super();
		this.expr = expr;
		this.statlist = statlist;
	}

}
