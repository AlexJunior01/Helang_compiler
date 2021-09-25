package main;

import lexer.Symbol;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import ast.AddExpr;
import ast.AndExpr;
import ast.AssignStat;
import ast.Expr;
import ast.ForStat;
import ast.IfStat;
import ast.MultExpr;
import ast.Numero;
import ast.PrintStat;
import ast.PrintlnStat;
import ast.Program;
import ast.RelExpr;
import ast.SimpleExpr;
import ast.Stat;
import ast.StatList;
import ast.VarList;
import ast.WhileStat;

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
		SimpleExpr ::= Number |�(� Expr �)� | "!" SimpleExpr
		| AddOp SimpleExpr | Ident
		RelOp ::= �<� | �<=� | �>� | �>=�| �==� | �!=�
		AddOp ::=  �+�| �-�
		MultOp ::= �*� | �/� | �%�
		Number ::=  [�+�|�-�] Digit { Digit }

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
	
    public Program compile( char []p_input ) {
        input = p_input;
        tokenPos = 0;
        input[input.length - 1] = '\0';
        nextToken();
        Program p = program();

        if ( token != Symbol.EOF )
          error("Final do arquivo n�o encontrado");

        return p;
    }
    /*
        Program ::= VarList { Stat }
    */
    private Program program() {
        VarList varlist = varlist();
        
        List<Stat> stat = new ArrayList<>();

        while(token != Symbol.EOF) {
            stat.add(stat());
        }
        return new Program(varlist, stat);
    }

    /*
        Stat ::= AssignStat | IfStat | ForStat | PrintStat |
            		PrintlnStat | WhileStat
    */
    
    private Stat stat() {
        if(token == Symbol.IDENT) {
            return assignStat();
        } else if(token == Symbol.IF) {
            return ifStat();
        } else if(token == Symbol.FOR) {
            return forStat();
        } else if(token == Symbol.PRINT) {
            return printStat();
        } else if(token == Symbol.PRINTLN) {
        	return printlnStat();
        } else if(token == Symbol.WHILE) {
        	return whileStat();
        } else {
            error("Stat esperado");
            return null;
        }
    }

    private WhileStat whileStat() {
        nextToken();

        Expr expr = expr();
        StatList statList = statList();
        
        return new WhileStat(expr, statList);
    }

    /*
        PrintlnStat ::= "println" Expr ";"
    */
    private PrintlnStat printlnStat() {
        nextToken();
        Expr expr = expr();

        if(token != Symbol.PONTO_VIRGULA) {
            error("';' esperado");
        }
        nextToken();
        
        return new PrintlnStat(expr);
    }

    /*
        PrintStat ::= "print" Expr ";"
    */
    private PrintStat printStat() {
        nextToken();
        Expr expr = expr();

        if(token != Symbol.PONTO_VIRGULA) {
            error("';' esperado");
        }
        nextToken();
        
        return new PrintStat(expr);
    }

    /*
        ForStat ::= "for" Ident "in" Expr ".." Expr StatList
    */
    private ForStat forStat() {
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

        Expr startExpr = expr();
        if(token != Symbol.DOIS_PONTOS) {
            error("'..' esperado");
        }
        nextToken();

        Expr endExpr = expr();
        StatList statlist = statList();
        
        return new ForStat(iterador, startExpr, endExpr, statlist);
    }

    /*
        IfStat ::= "if" Expr StatList [
                    "else" StatList ]
     */
    private IfStat ifStat() {
        nextToken();
        Expr expr = expr();
        
        StatList elseStatList = null;
        
        StatList ifStatList = statList();

        if (token == Symbol.ELSE) {
            nextToken();
            elseStatList = statList();
        }
        
        return new IfStat(expr, ifStatList, elseStatList);
    }

    /*
        StatList ::= "{" { Stat } "}"
    */
    private StatList statList() {
        List<Stat> stats = new ArrayList<>();
        if(token != Symbol.ABRE_CHAVES) {
            error("'{' esperado");
        }
        nextToken();
        while(token != Symbol.FECHA_CHAVES){
            stats.add(stat());
        }
        nextToken();
        return new StatList(stats);
    }

    /*
        AssignStat ::= Ident "=" Expr ";"
    */
    private AssignStat assignStat() {
        String ident = stringValue;
        nextToken();

        if (token != Symbol.ATRIBUICAO) {
            error("'=' esperado");
        }
        nextToken();

        Expr expr = expr();
        if (token != Symbol.PONTO_VIRGULA) {
            error("';' esperado");
        }
        nextToken();
        
        return new AssignStat(ident, expr);
    }

    /*
        VarList ::= { "var" Int Ident ";" }
    */
    private VarList varlist() {
    	List<String> identList = new ArrayList<>();
        while (token == Symbol.VAR) {
            nextToken();

            if(token != Symbol.INT) {
                error("'Int' esperado.");
            }
            nextToken();

            if(token != Symbol.IDENT) {
                error("Identificador de variável esperado.");
            }
            String ident = stringValue;
            identList.add(ident);
            
            nextToken();

            if(token != Symbol.PONTO_VIRGULA) {
                error("';' esperado.");
            }
            nextToken();
        }
        return new VarList(identList);
    }
    
    /*
    	Expr ::= AndExpr [ "||" AndExpr ]
    */
    private Expr expr() {
    	AndExpr secondAndExpr = null;
    	AndExpr firstAndExpr = andExpr();

    	if(token == Symbol.OR) {
            nextToken();
            secondAndExpr = andExpr();
    	}
    	
    	return new Expr(firstAndExpr, secondAndExpr);
    }
    
    /*
     	AndExpr ::= RelExpr [ "&&" RelExpr ]
    */
    private AndExpr andExpr() {
    	RelExpr secondRel = null;
    	RelExpr firstRelExpr = relExpr();
		
		if(token == Symbol.AND) {
            nextToken();
            secondRel = relExpr();
    	}
		
		return new AndExpr(firstRelExpr, secondRel);
	}
    
    /*
    	RelExpr ::= AddExpr [ RelOp AddExpr ]
    */
    
	private RelExpr relExpr() {
		AddExpr secondAddExpr = null;
		Symbol relOp = null;
		AddExpr firstAddExpr = addExpr();
		
		if(token == Symbol.MENOR || token == Symbol.MAIOR || token == Symbol.MENOR_IGUAL || 
				token == Symbol.MAIOR_IGUAL || token == Symbol.IGUAL || token == Symbol.DIFERENTE) {
			
			relOp = token;
            nextToken();
            secondAddExpr = addExpr();
		}
		
		return new RelExpr(firstAddExpr, relOp, secondAddExpr);
	}
	
	/*
		AddExpr ::= MultExpr { AddOp MultExpr }
	*/
	private AddExpr addExpr() {
		MultExpr secondMultExpr = null;
		Symbol addOp = null;
		MultExpr firstMultExpr = multExpr();
		
		while(token == Symbol.MAIS || token == Symbol.MENOS ) {
			addOp = token;
            nextToken();
            secondMultExpr = multExpr();
		}
		
		return new AddExpr(firstMultExpr, addOp, secondMultExpr);
	}
	
	/*
		MultExpr ::= SimpleExpr { MultOp SimpleExpr }
	*/
	private MultExpr multExpr() {
		List<SimpleExpr> secondSimpleExpr = new ArrayList<>();
		List<Symbol> multOp = new ArrayList<>();
		SimpleExpr firstSimpleExpr = simpleExpr();
		
		while(token == Symbol.MULTIPLICACAO || token == Symbol.DIVISAO || token == Symbol.MOD) {
			multOp.add(token);
            nextToken();
            secondSimpleExpr.add(simpleExpr());
		}
		
		return new MultExpr(firstSimpleExpr, multOp, secondSimpleExpr);
	}
	
	/*
		SimpleExpr ::= Number | �(� Expr �)� | "!" SimpleExpr | AddOp SimpleExpr | Ident
	*/
	
	private SimpleExpr simpleExpr() {
		
		Numero number = null;
	    Expr expr = null;
	    SimpleExpr simpleExpr = null;
	    Symbol addOp = null;
	    String ident = null;
		
	    
	    if(token == Symbol.NUMBER) {
	    	number = number(addOp);
		} else if(token == Symbol.ABRE_PARANTESES) {
            nextToken();
            expr = expr();
			if(token != Symbol.FECHA_PARENTESES) {
				error("')' esperado");
			}
            nextToken();
		} else if(token == Symbol.EXCLAMACAO) {
            nextToken();
            simpleExpr = simpleExpr();
		} else if(token == Symbol.MAIS || token == Symbol.MENOS) {
			addOp = token;
            nextToken();
            
            if(token == Symbol.NUMBER) {
            	number = number(addOp);
            } else {
            	simpleExpr = simpleExpr();
            }
		} else if(token == Symbol.IDENT) {
			ident = this.stringValue;
            nextToken();
		} else {
			error("simpleExpr esperado");
		}
	    
    	return new SimpleExpr(number, expr, simpleExpr, addOp, ident);
	}

    private Numero number(Symbol addOp) {
    	
        if(token == Symbol.MAIS || token == Symbol.MENOS) {
            nextToken();
        }

        int numero = numberValue;
        nextToken();
        
        return new Numero(numero, addOp);
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
