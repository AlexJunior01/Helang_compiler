package ast;

public class IntegerExpr extends AbstractExpr{
    private Integer value;

    public IntegerExpr(Integer valor) {
        this.value = valor;
    }

    @Override
    public Type getType() {
        return Type.integerType;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

}
