package ast;

import java.util.Map;


abstract public class Stat {

        public abstract void eval(Map<String, Variable> memory);

		public abstract void genC();
    }
