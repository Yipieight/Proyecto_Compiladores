package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Lexer implements ILexer {
    private final Pattern pattern = Pattern.compile(
            "(int|float|string|bool)|" +               // TYPE
                    "(true|false)|" +                          // BOOL
                    "(function|Function)|" +                   // FUNCTION
                    "(if|If|else|Else|while|While|read|Read|write|Write|return)|" + // Palabras clave
                    "(\"[a-zA-Z]+\")|" +                       // STRING
                    "'([0-9]|[a-zA-Z]|[!@#$%^&*_+?:\"{}|])'|" + // CHARACTER
                    "'([0-9]|[a-zA-Z]|[!@#$%^&*_+?:\"{}|])+'|" + // MULTI CHARACTER ERROR
                    "\\b([a-zA-Z][a-zA-Z0-9]*)\\b|" +          // ID
                    "([0-9]+\\.[0-9]{1,4})|" +                 // FLOAT
                    "([0-9]+)|" +                              // DIGITO
                    "(==|!=|<=|>=|<|>)|" +                     // Operadores de comparación
                    "([+\\-*/=(),;{}])|" +                     // Operadores y símbolos
                    "([!@#$%^&*_+?:\"{}|])"                    // SYMBOL
    );


    @Override
    public List<TokenSymbol> getTokensFromString(String expresion) {
        System.out.println("\n[LEXER] Procesando entrada: " + expresion);
        List<TokenSymbol> tokens = new ArrayList<>();
        Matcher matcher = pattern.matcher(expresion);

        while (matcher.find()) {
            String match = matcher.group();
            TokenSymbol token = null;

            if (match.matches("(int|float|string|bool)")) {
                token = new TokenSymbol("TYPE", match);
            }
            else if (match.matches("function|Function")) {
                token = new TokenSymbol("FUNCTION", match);
            }
            else if (match.matches("(true|false)")) {
                token = new TokenSymbol("BOOL", match);
            }
            else if (match.equals("return")) token = new TokenSymbol("RETURN", match);
            else if (match.equals("if")) token = new TokenSymbol("IF", match);
            else if (match.equals("else")) token = new TokenSymbol("ELSE", match);
            else if (match.equals("while")) token = new TokenSymbol("WHILE", match);
            else if (match.equals("read")) token = new TokenSymbol("READ", match);
            else if (match.equals("write")) token = new TokenSymbol("WRITE", match);
            else if (match.equals("{")) token = new TokenSymbol("OPENKEY", match);
            else if (match.equals("}")) token = new TokenSymbol("CLOSEKEY", match);
            else if (match.equals(",")) token = new TokenSymbol("COMA", match);
            else if (match.equals("=")) token = new TokenSymbol("EQUAL", match);
            else if (match.equals("+")) token = new TokenSymbol("PLUS", match);
            else if (match.equals("-")) token = new TokenSymbol("MINUS", match);
            else if (match.equals("*")) token = new TokenSymbol("MULT", match);
            else if (match.equals("/")) token = new TokenSymbol("DIV", match);
            else if (match.equals("(")) token = new TokenSymbol("LPAR", match);
            else if (match.equals(")")) token = new TokenSymbol("RPAR", match);
            else if (match.equals(";")) token = new TokenSymbol("SEMI", match);
            else if (match.equals("==")) token = new TokenSymbol("EQUALS", match);
            else if (match.equals("!=")) token = new TokenSymbol("NOT_EQUALS", match);
            else if (match.equals("<=")) token = new TokenSymbol("LESS_EQUALS", match);
            else if (match.equals(">=")) token = new TokenSymbol("GREATER_EQUALS", match);
            else if (match.equals("<")) token = new TokenSymbol("LESS", match);
            else if (match.equals(">")) token = new TokenSymbol("GREATER", match);
            else if (match.matches("[!@#$%^&*_+?:\"{}|]")) {
                token = new TokenSymbol("SYMBOL", match);
            }
            else if (match.matches("[a-zA-Z][a-zA-Z0-9]*")) {
                token = new TokenSymbol("ID", match);
            }
            else if (match.matches("[0-9]+\\.[0-9]{1,4}")) {
                token = new TokenSymbol("FLOAT", match);
            }
            else if (match.matches("[0-9]+")) {
                token = new TokenSymbol("DIGIT", match);
            }
            else if (match.matches("\"[a-zA-Z]+\"")) {
                token = new TokenSymbol("STRING", match);
            }
            else if (match.matches("'([0-9]|[a-zA-Z]|[!@#$%^&*_+?:\"{}|])'")) {
                token = new TokenSymbol("CHARACTER", match);
            }

            if (token != null) {
                System.out.println("[LEXER] Token reconocido: " + token.getType() + " -> " + token.getValue());
                tokens.add(token);
            }
            else{
                System.out.println("[ERROR] Token no reconocido: " + match);
                tokens.add(token);
            }
        }

        tokens.add(new TokenSymbol("$", "$"));
        return tokens;
    }
}