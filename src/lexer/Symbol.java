package lexer;

public enum Symbol {
	EOF("eof"),
	IDENT("ident"),
    NUMBER("Number"),
	VAR("Var"),
	INT("Int"),
	STRING("String"),
	BOOLEAN("boolean"),
	PONTO_VIRGULA(";"),
	ATRIBUICAO("="), 
	IF("if"), 
	ELSE("else"), 
	FOR("for"), 
	IN("in"),
	DOIS_PONTOS(".."),
	PRINT("print"),
	PRINTLN("println"),
	ABRE_CHAVES("{"),
	FECHA_CHAVES("}"),
	WHILE("while"),
	OR("||"),
	AND("&&"), 
	ABRE_PARANTESES("("),
	FECHA_PARENTESES(")"),
	EXCLAMACAO("!"),
	MENOR("<"),
	MENOR_IGUAL("<="),
	MAIOR(">"),
	MAIOR_IGUAL(">="),
	MAIS_MAIS("++"),
	IGUAL("=="),
	DIFERENTE("!="),
	MAIS("+"),
	MENOS("-"),
	MULTIPLICACAO("*"),
	DIVISAO("/"), 
	MOD("%");
	
    Symbol(String name) {
        this.nome = name;
    }
	
    @Override
	public String toString() { return nome; }
    
    public String nome;
}
