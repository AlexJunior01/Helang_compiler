package ast;

import java.util.List;
import java.util.Map;


public class VarList extends Stat{
	private List<Variable> ident;

	public VarList(List<Variable> ident) {
		super();
		this.ident = ident;
	}

		public void eval(Map<String, Variable> memory) {
			for (Variable var : ident) {
				if(memory.get(var.getName()) != null)
					throw new RuntimeException("Variavel ja declarada.");

				memory.put(var.getName(), var);
			}
		}

		public void genC() {
			String tipo;
			for(Variable identificador: ident) {
				tipo = identificador.getType().getCname();
				System.out.println(tipo + " " + identificador.getName() + ";");
			}
	
		}
	}
