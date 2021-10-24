package ast;

import java.util.Map;

/*
		Stat ::= VarList | AssignStat | IfStat | ForStat | PrintStat |
		PrintlnStat | WhileStat
	*/
abstract public class Stat {

        public abstract void eval(Map<String, Variable> memory);

		public abstract void genC();
    }
