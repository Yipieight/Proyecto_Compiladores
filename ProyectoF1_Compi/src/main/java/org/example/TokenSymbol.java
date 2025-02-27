package org.example;

public class TokenSymbol {
    private String type;
    private String value;

    public TokenSymbol(String type, String value){
        setType(type);
        setValue(value);
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}