package ast;
  
public class BooleanType extends Type {
    
   public BooleanType() {
      super("boolean");
   }
   
   public String getCname() {
      return "bool";
   }
   
}
