package ast;

import java.util.List;
import java.util.Map;

/*
    VarList ::= { "var" Int Ident ";" }
*/
public class VarList {
	private List<String> ident;

	public VarList(List<String> ident) {
		super();
		this.ident = ident;
	}

		public void eval(Map<String, Integer> memory) {
			for (String identificador : ident) {
				memory.put(identificador, 0);
			}
		}

		public void genC() {
			for(String identificador: ident) {
				System.out.println("Int " + identificador + ";");
			}
	
		}
	}
