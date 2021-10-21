package ast;

import java.io.*;

public class StringType extends Type {
    
    public StringType() {
        super("string");
    }
    
   public String getCname() {
      return "char*";
   }
   
}