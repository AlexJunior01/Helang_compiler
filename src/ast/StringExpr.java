package ast;

public class StringExpr extends AbstractExpr{
    private String value;

    public StringExpr(String valor) {
        this.value = valor;
    }

    @Override
    public Type getType() {
        return Type.stringType;
    }

    @Override
    public Object getValue() {
        return this.value;
    }
    
	@Override
	public int compareTo(AbstractExpr aExpr) {
		StringExpr outro = (StringExpr) aExpr;
        String outraString = (String) outro.getValue();
		return this.value.compareTo(outraString);
	}
}
