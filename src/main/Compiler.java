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
		SimpleExpr ::= Number | ï¿½(ï¿½ Expr ï¿½)ï¿½ | "!" SimpleExpr
		| AddOp SimpleExpr
		RelOp ::= ï¿½<ï¿½ | ï¿½<=ï¿½ | ï¿½>ï¿½ | ï¿½>=ï¿½| ï¿½==ï¿½ | ï¿½!=ï¿½
		AddOp ::= ï¿½+ï¿½| ï¿½-ï¿½
		MultOp ::= ï¿½*ï¿½ | ï¿½/ï¿½ | ï¿½%ï¿½
		Number ::= [ï¿½+ï¿½|ï¿½-ï¿½] Digit { Digit }

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
                error("NÃºmero muito grande");
            }
        }
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
	
    public void compile( char []p_input ) {
        input = p_input;
        tokenPos = 0;
        nextToken();
        program();
//        int result = expr();
        if ( token != Symbol.EOF )
          error("Final do arquivo nï¿½o encontrado");
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
                error("Identificador de variÃ¡vel esperado.");
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
    	nextToken();
    	
    	if(token == Symbol.OR) {
    		andExpr();
    	}
    }
    
    /*
     	AndExpr ::= RelExpr [ "&&" RelExpr ]
    */

    private void andExpr() {
		relExpr();
		nextToken();
		
		if(token == Symbol.AND) {
			relExpr();
    	}
	}
    
    /*
    	RelExpr ::= AddExpr [ RelOp AddExpr ]
    */
    
	private void relExpr() {
		addExpr();
		nextToken();
		
		if(token == Symbol.MENOR || token == Symbol.MAIOR || token == Symbol.MENOR_IGUAL || 
				token == Symbol.MAIOR_IGUAL || token == Symbol.IGUAL || token == Symbol.DIFERENTE) {
			addExpr();
		}
	}
	
	/*
		AddExpr ::= MultExpr { AddOp MultExpr }
	*/

	private void addExpr() {
		multExpr();
		nextToken();
		
		while(token == Symbol.MAIS || token == Symbol.MENOS ) {
			multExpr();
		}
	}
	
	/*
		MultExpr ::= SimpleExpr { MultOp SimpleExpr }
	*/

	private void multExpr() {
		simpleExpr();
		nextToken();
		
		while(token == Symbol.MULTIPLICACAO || token == Symbol.DIVISAO || token == Symbol.MOD) {
			simpleExpr();
		}
	}
	
	/*
		SimpleExpr ::= Number | ’(’ Expr ’)’ | "!" SimpleExpr | AddOp SimpleExpr | Ident
	*/
	
	private void simpleExpr() {
		
		if(token == Symbol.NUMBER) {
			number();
		} else if(token == Symbol.ABRE_PARANTESES) {
			expr();
			if(token != Symbol.FECHA_PARENTESES) {
				error("')' esperado");
			}
		} else if(token == Symbol.EXCLAMACAO) {
			simpleExpr();
		} else if(token == Symbol.MAIS || token == Symbol.MENOS) {
			simpleExpr();
		} else if(token == Symbol.IDENT) {
			
		} else {
			error("simpleExpr esperado");
		}	
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
