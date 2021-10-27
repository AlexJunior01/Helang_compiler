package ast;


public class IntegerType extends Type {
    
    public IntegerType() {
        super("integer");
    }
    
   public String getCname() {
      return "int";
   }

}