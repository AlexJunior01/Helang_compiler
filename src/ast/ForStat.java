package ast;

	/*
		ForStat ::= "for" Ident "in" Expr ".." Expr StatList
	 */

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
}
