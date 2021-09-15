package main;

import lexer.Symbol;

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
		SimpleExpr ::= Number | ’(’ Expr ’)’ | "!" SimpleExpr
		| AddOp SimpleExpr
		RelOp ::= ’<’ | ’<=’ | ’>’ | ’>=’| ’==’ | ’!=’
		AddOp ::= ’+’| ’-’
		MultOp ::= ’*’ | ’/’ | ’%’
		Number ::= [’+’|’-’] Digit { Digit }

 */

public class Compiler {
	
	public void nextToken() {
		char ch;
		
		while ((ch = input[tokenPos]) == ' ' || ch == '\r' || ch == '\t' || ch == '\n') {
			tokenPos++;
		}
	}
	
    public void compile( char []p_input ) {
        input = p_input;
        tokenPos = 0;
        nextToken();
//        int result = expr();
        if ( token != Symbol.EOF )
          error("Final do arquivo não encontrado");
//        return result;
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
