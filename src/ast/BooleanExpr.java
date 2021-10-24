package ast;

public class BooleanExpr extends AbstractExpr{
    public static final AbstractExpr TRUE = new BooleanExpr(true);
    public static final AbstractExpr FALSE = new BooleanExpr(false);
    private Boolean value;

    public BooleanExpr(Boolean valor){
        this.value = valor;
    }

    @Override
    public Type getType() {
        return Type.booleanType;
    }

    @Override
    public Object getValue() {
        return this.value;
    }
}
