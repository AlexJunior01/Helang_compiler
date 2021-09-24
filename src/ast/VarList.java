package ast;

import java.util.List;

/*
		VarList ::= { "var" Int Ident ";" }
	*/
public class VarList {
	private List<String> ident;

	public VarList(List<String> ident) {
		super();
		this.ident = ident;
	}
}
