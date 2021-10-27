package ast;

public class BooleanExpr extends AbstractExpr {
    public static final BooleanExpr TRUE = new BooleanExpr(true);
    public static final BooleanExpr FALSE = new BooleanExpr(false);
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

	@Override
	public int compareTo(AbstractExpr aExpr) {
		BooleanExpr outro = (BooleanExpr) aExpr;
		
		if(this.value.equals(outro.getValue())) {
			return 0;
		} else if(this.value && !outro.value) {
			return 1;
		} else {
			return -1;
		}
	}
}
