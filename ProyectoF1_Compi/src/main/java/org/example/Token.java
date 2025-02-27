package org.example;

import java.util.List;

public class Token {
    public enum Type {
        BIN, OCT, HEX, IDENTIFIER, NUMBER,
        PLUS, MINUS, MULTIPLY, DIVIDE,
        LPAREN, RPAREN, SEMICOLON, EOF
    }

    private final Type type;
    private final String value;

    public Token(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    public Type getType() { return type; }
    public String getValue() { return value; }

    @Override
    public String toString() {
        return "Token{" + type + ", '" + value + "'}";
    }
}

