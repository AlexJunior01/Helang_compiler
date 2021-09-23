package main;

import lexer.Symbol;

import java.util.Hashtable;
import java.util.Map;

/* 
   		Program ::= VarList { Stat }
		VarList ::= { "var" Int Ident ";" }
		Stat ::= AssignStat | IfStat | ForStat | PrintStat |
		PrintlnStat | WhileStat
		AssignStat ::= Ident "=" Expr ";"
		IfStat ::= "if" Expr StatList [
		"else" StatList ]
		ForStat ::= "for" Id "in" Expr ".." Expr StatList
		PrintStat ::= "print" Expr ";"
		PrintlnStat ::= "println" Expr ";"
		StatList ::= "{" { Stat } "}"
		WhileStat ::= "while" Expr StatList
		Expr ::= AndExpr [ "||" AndExpr ]
		AndExpr ::= RelExpr [ "&&" RelExpr ]
		RelExpr ::= AddExpr [ RelOp AddExpr ]
		AddExpr ::= MultExpr { AddOp MultExpr }
		MultExpr ::= SimpleExpr { MultOp SimpleExpr }
		SimpleExpr ::= Number | �(� Expr �)� | "!" SimpleExpr
		| AddOp SimpleExpr
		RelOp ::= �<� | �<=� | �>� | �>=�| �==� | �!=�
		AddOp ::= �+�| �-�
		MultOp ::= �*� | �/� | �%�
		Number ::= [�+�|�-�] Digit { Digit }

 */

public class Compiler {
    static private Map<String, Symbol> keywordsTable;

    // this code will be executed only once for each program execution
    static {
        keywordsTable = new Hashtable<String, Symbol>();
        keywordsTable.put( "var", Symbol.VAR );
        keywordsTable.put( "Int", Symbol.INT );
        keywordsTable.put( "if", Symbol.IF );
        keywordsTable.put( "else", Symbol.ELSE );
        keywordsTable.put( "for", Symbol.FOR );
        keywordsTable.put( "while", Symbol.WHILE );
        keywordsTable.put( "print", Symbol.PRINT);
        keywordsTable.put( "println", Symbol.PRINTLN);
        keywordsTable.put( "in", Symbol.IN);
    }
	public void nextToken() {
		char ch;

		while ((ch = input[tokenPos]) == ' ' || ch == '\r' || ch == '\t' || ch == '\n') {
			++tokenPos;
		}
        if(ch == '\0') {
          token = Symbol.EOF;
        } else {
            if(Character.isLetter(ch)){
                StringBuffer ident = new StringBuffer();

                while (Character.isLetter(input[tokenPos]) || Character.isDigit(input[tokenPos])){
                    ident.append(input[tokenPos]);
                    ++tokenPos;
                }

                stringValue = ident.toString();
                Symbol value = keywordsTable.get(stringValue);
                if (value == null){
                    token = Symbol.IDENT;
                } else {
                    token = value;
                }
            } else if (Character.isDigit(ch)) {
                StringBuffer number = new StringBuffer();
                while (Character.isDigit(input[tokenPos])) {
                    number.append(input[tokenPos]);
                    tokenPos++;
                }
                token = Symbol.NUMBER;

                try {
                    numberValue = Integer.parseInt(number.toString());
                } catch (NumberFormatException e) {
                    error("Número muito grande");
                }
            } else {
                switch(ch){
                    case ';':
                        token = Symbol.PONTO_VIRGULA;
                        ++tokenPos;
                        break;
                    case '{':
                        token = Symbol.ABRE_CHAVES;
                        ++tokenPos;
                        break;
                    case '}':
                        token = Symbol.FECHA_CHAVES;
                        ++tokenPos;
                        break;
                    case '(':
                        token = Symbol.ABRE_PARANTESES;
                        ++tokenPos;
                        break;
                    case ')':
                        token = Symbol.FECHA_PARENTESES;
                        ++tokenPos;
                        break;
                    case '=':
                        if (input[tokenPos+1] == '='){
                            token = Symbol.IGUAL;
                            tokenPos += 2;
                            break;
                        }
                        token = Symbol.ATRIBUICAO;
                        ++tokenPos;
                        break;
                    case '.':
                        if (input[tokenPos +1 ] == '.'){
                            token = Symbol.DOIS_PONTOS;
                            tokenPos += 2;
                            break;
                        }
                        error("Esperado token = '.'");
                        break;
                    case '+':
                        token = Symbol.MAIS;
                        ++tokenPos;
                        break;
                    case '-':
                        token = Symbol.MENOS;
                        ++tokenPos;
                        break;
                    case '*':
                        token = Symbol.MULTIPLICACAO;
                        ++tokenPos;
                        break;
                    case '/':
                        token = Symbol.DIVISAO;
                        ++tokenPos;
                        break;
                    case '%':
                        token = Symbol.MOD;
                        ++tokenPos;
                        break;
                    case '!':
                        if(input[tokenPos + 1] == '='){
                            token = Symbol.DIFERENTE;
                            tokenPos += 2;
                            break;
                        }
                        token = Symbol.EXCLAMACAO;
                        ++tokenPos;
                        break;
                    case '<':
                        if(input[tokenPos + 1] == '='){
                            token = Symbol.MENOR_IGUAL;
                            tokenPos += 2;
                            break;
                        }
                        token = Symbol.MENOR;
                        ++tokenPos;
                        break;
                    case '>':
                        if(input[tokenPos + 1] == '='){
                            token = Symbol.MAIOR_IGUAL;
                            tokenPos += 2;
                            break;
                        }
                        token = Symbol.MAIOR;
                        ++tokenPos;
                        break;
                    case '|':
                        if(input[tokenPos + 1] == '|'){
                            token = Symbol.OR;
                            tokenPos += 2;
                            break;
                        }
                        error("Token '|' esperado.");
                        break;
                    case '&':
                        if(input[tokenPos + 1] == '&'){
                            token = Symbol.AND;
                            tokenPos += 2;
                            break;
                        }
                        error("Token '&' esperado.");
                        break;
                    default:
                        error("Invalida char");
                }
            }
        }
	}
	
    public void compile( char []p_input ) {
        input = p_input;
        tokenPos = 0;
        input[input.length - 1] = '\0';
        nextToken();
        program();
//        int result = expr();
        if ( token != Symbol.EOF )
          error("Final do arquivo n�o encontrado");
//        return result;
    }
    /*
        Program ::= VarList { Stat }
    */
    private void program() {
        varlist();

        while(token != Symbol.EOF) {
            stat();
        }
    }

    /*
        Stat ::= AssignStat | IfStat | ForStat | PrintStat |
            		PrintlnStat | WhileStat
     */
    private void stat() {
        if(token == Symbol.IDENT) {
            assignStat();
        } else if(token == Symbol.IF) {
            ifStat();
        } else if(token == Symbol.FOR) {
            forStat();
        } else if(token == Symbol.PRINT) {
            printStat();
        } else if(token == Symbol.PRINTLN) {
            printlnStat();
        } else if(token == Symbol.WHILE) {
            whileStat();
        } else {
            error("Stat esperado");
        }
    }

    private void whileStat() {
        nextToken();

        expr();
        statList();
    }

    /*
        PrintlnStat ::= "println" Expr ";"
     */
    private void printlnStat() {
        nextToken();
        expr();

        if(token != Symbol.PONTO_VIRGULA) {
            error("';' esperado");
        }
        nextToken();
    }

    /*
        PrintStat ::= "print" Expr ";"
     */
    private void printStat() {
        nextToken();
        expr();

        if(token != Symbol.PONTO_VIRGULA) {
            error("';' esperado");
        }
        nextToken();
    }

    /*
        ForStat ::= "for" Id "in" Expr ".." Expr StatList
     */
    private void forStat() {
        nextToken();

        if(token != Symbol.IDENT) {
            error("Variável esperado");
        }
        String iterador = stringValue;
        nextToken();

        if(token != Symbol.IN) {
            error("'in' esperado");
        }
        nextToken();

        expr();
        if(token != Symbol.DOIS_PONTOS) {
            error("'..' esperado");
        }
        nextToken();

        expr();
        statList();
    }

    /*
        IfStat ::= "if" Expr StatList [
                    "else" StatList ]
     */
    private void ifStat() {
        nextToken();
        expr();

        statList();

        if (token == Symbol.ELSE) {
            nextToken();
            statList();
        }
    }

    /*
        StatList ::= "{" { Stat } "}"
     */
    private void statList() {
        if(token != Symbol.ABRE_CHAVES) {
            error("'{' esperado");
        }
        nextToken();
        while(token != Symbol.FECHA_CHAVES){
            stat();
        }
        nextToken();
    }

    /*
        AssignStat ::= Ident "=" Expr ";"
     */
    private void assignStat() {
        String ident = stringValue;
        nextToken();

        if (token != Symbol.ATRIBUICAO) {
            error("'=' esperado");
        }
        nextToken();

        expr();
        if (token != Symbol.PONTO_VIRGULA) {
            error("';' esperado");
        }
        nextToken();
    }

    /*
        VarList ::= { "var" Int Ident ";" }
     */
    private void varlist() {
        while (token == Symbol.VAR) {
            nextToken();

            if(token != Symbol.INT) {
                error("'Int' esperado.");
            }
            nextToken();

            if(token != Symbol.IDENT) {
                error("Identificador de variável esperado.");
            }
            nextToken();

            if(token != Symbol.PONTO_VIRGULA) {
                error("';' esperado.");
            }
            nextToken();
        }
    }
    
    /*
    	Expr ::= AndExpr [ "||" AndExpr ]
    */
    private void expr() {
    	andExpr();

    	if(token == Symbol.OR) {
            nextToken();
    		andExpr();
    	}
    }
    
    /*
     	AndExpr ::= RelExpr [ "&&" RelExpr ]
    */
    private void andExpr() {
		relExpr();
		
		if(token == Symbol.AND) {
            nextToken();
			relExpr();
    	}
	}
    
    /*
    	RelExpr ::= AddExpr [ RelOp AddExpr ]
    */
    
	private void relExpr() {
		addExpr();
		
		if(token == Symbol.MENOR || token == Symbol.MAIOR || token == Symbol.MENOR_IGUAL || 
				token == Symbol.MAIOR_IGUAL || token == Symbol.IGUAL || token == Symbol.DIFERENTE) {
            nextToken();
			addExpr();
		}
	}
	
	/*
		AddExpr ::= MultExpr { AddOp MultExpr }
	*/
	private void addExpr() {
		multExpr();
		
		while(token == Symbol.MAIS || token == Symbol.MENOS ) {
            nextToken();
			multExpr();
		}
	}
	
	/*
		MultExpr ::= SimpleExpr { MultOp SimpleExpr }
	*/
	private void multExpr() {
		simpleExpr();
		
		while(token == Symbol.MULTIPLICACAO || token == Symbol.DIVISAO || token == Symbol.MOD) {
            nextToken();
			simpleExpr();
		}
	}
	
	/*
		SimpleExpr ::= Number | �(� Expr �)� | "!" SimpleExpr | AddOp SimpleExpr | Ident
	*/
	
	private void simpleExpr() {
		
		if(token == Symbol.NUMBER) {
			number();
		} else if(token == Symbol.ABRE_PARANTESES) {
            nextToken();
			expr();
			if(token != Symbol.FECHA_PARENTESES) {
				error("')' esperado");
			}
            nextToken();
		} else if(token == Symbol.EXCLAMACAO) {
            nextToken();
			simpleExpr();
		} else if(token == Symbol.MAIS || token == Symbol.MENOS) {
            nextToken();
			simpleExpr();
		} else if(token == Symbol.IDENT) {
			String ident = this.stringValue;
            nextToken();
		} else {
			error("simpleExpr esperado");
		}	
	}

    private void number() {
        if(token == Symbol.MAIS || token == Symbol.MENOS) {
            nextToken();
        }

        int numero = numberValue;
        nextToken();
    }

    private void error(String msg) {
        if ( tokenPos == 0 )
          tokenPos = 1;
        else
          if ( tokenPos >= input.length )
            tokenPos = input.length;

        String strInput = new String( input, tokenPos - 1, input.length - tokenPos + 1 );
        String strError = "Error at \"" + strInput + "\"";
        System.out.println(msg);
        System.out.print( strError );
        throw new RuntimeException(strError);
    }
	
	
	 private Symbol token;
	 private String stringValue;
	 private int numberValue;
	 private int  tokenPos;
	 
	 private char []input;
}
