package ast;


public class Variable {

    private Integer integerValue = null;
    private String stringValue = null;
    private Boolean boolValue = null;
    private String name;
    private Type type;


    public Variable(String name, Type type ) {
        this.name = name;
        this.type = type;
    }

    public Variable( String name, Type type, Boolean valor ) {
        this.name = name;
        this.type = type;
        this.boolValue = valor;
    }

    public Variable( String name, Type type, String valor ) {
        this.name = name;
        this.type = type;
        this.stringValue = valor;
    }

    public Variable( String name, Type type, Integer valor ) {
        this.name = name;
        this.type = type;
        this.integerValue = valor;
    }

    public Variable( String name ) {
        this.name = name;
    }

    public Object getValor() {
        if (this.type == Type.booleanType) {
            return this.boolValue;
        } else if (this.type == Type.integerType) {
            return this.integerValue;
        } else {
            return this.stringValue;
        }
    }

    public void setValue( Object valor ) {
        if (this.type == Type.booleanType) {
            this.boolValue = (Boolean) valor;
        } else if (this.type == Type.integerType) {
             this.integerValue = (Integer) valor;
        } else {
             this.stringValue = (String) valor;
        }
    }

    public String getName() { return name; }

    public Type getType() {
        return type;
    }
}