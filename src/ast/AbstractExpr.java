package ast;

abstract public class AbstractExpr{
    abstract public Type getType();

    abstract public Object getValue();
    
    abstract public int compareTo(AbstractExpr aExpr);
}
