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

	@Override
	public int compareTo(AbstractExpr aExpr) {
		IntegerExpr outro = (IntegerExpr) aExpr;
		
		if(outro.getValue() == this.value) {
			return 0;
		} else if(this.value > outro.value) {
			return 1;
		} else {
			return -1;
		}
	}
}
