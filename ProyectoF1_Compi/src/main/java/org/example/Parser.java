package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Parser implements IParser {
    private Map<String, Map<String, List<String>>> parsingTable = new HashMap<>();
    private Map<String, String> symbolTable = new HashMap<>();
    private String currentDeclarationType = null;

    public Parser() {
        initParsingTable();
        System.out.println("\n[PARSER] Tabla de análisis inicializada");
    }

    private void initParsingTable() {
        parsingTable.put("S", Map.of(
                "TYPE", List.of("DECLARATIONS", "EXPRESSION"),
                "LPAR", List.of("DECLARATIONS", "EXPRESSION"),
                "ID", List.of("DECLARATIONS", "EXPRESSION"),
                "NUMBIN", List.of("DECLARATIONS", "EXPRESSION"),
                "NUMOCT", List.of("DECLARATIONS", "EXPRESSION"),
                "NUMHEX", List.of("DECLARATIONS", "EXPRESSION")
        ));

        parsingTable.put("DECLARATIONS", Map.of(
                "TYPE", List.of("DECLARATION", "DECLARATIONS"),
                "LPAR", List.of("ε"),
                "ID", List.of("ε"),
                "NUMBIN", List.of("ε"),
                "NUMOCT", List.of("ε"),
                "NUMHEX", List.of("ε"),
                "$", List.of("ε")
        ));

        parsingTable.put("DECLARATION", Map.of(
                "TYPE", List.of("TYPE", "ID", "NUM", "SEMI")
        ));

        parsingTable.put("NUM", Map.of(
                "NUMBIN", List.of("NUMBIN"),
                "NUMOCT", List.of("NUMOCT"),
                "NUMHEX", List.of("NUMHEX")
        ));

        parsingTable.put("EXPRESSION", Map.of(
                "LPAR", List.of("TERM", "EXPRESSION_PRIME"),
                "ID", List.of("TERM", "EXPRESSION_PRIME"),
                "NUMBIN", List.of("TERM", "EXPRESSION_PRIME"),
                "NUMOCT", List.of("TERM", "EXPRESSION_PRIME"),
                "NUMHEX", List.of("TERM", "EXPRESSION_PRIME")
        ));

        parsingTable.put("TERM", Map.of(
                "LPAR", List.of("FACTOR", "TERM_PRIME"),
                "ID", List.of("FACTOR", "TERM_PRIME"),
                "NUMBIN", List.of("FACTOR", "TERM_PRIME"),
                "NUMOCT", List.of("FACTOR", "TERM_PRIME"),
                "NUMHEX", List.of("FACTOR", "TERM_PRIME")
        ));

        parsingTable.put("FACTOR", Map.of(
                "LPAR", List.of("LPAR", "EXPRESSION", "RPAR"),
                "ID", List.of("ID"),
                "NUMBIN", List.of("NUMBIN"),
                "NUMOCT", List.of("NUMOCT"),
                "NUMHEX", List.of("NUMHEX")
        ));

        parsingTable.put("TERM_PRIME", Map.of(
                "MULT", List.of("MULT", "FACTOR", "TERM_PRIME"),
                "DIV", List.of("DIV", "FACTOR", "TERM_PRIME"),
                "PLUS", List.of("ε"),
                "MINUS", List.of("ε"),
                "RPAR", List.of("ε"),
                "$", List.of("ε")
        ));

        parsingTable.put("EXPRESSION_PRIME", Map.of(
                "PLUS", List.of("PLUS", "TERM", "EXPRESSION_PRIME"),
                "MINUS", List.of("MINUS", "TERM", "EXPRESSION_PRIME"),
                "RPAR", List.of("ε"),
                "$", List.of("ε")
        ));
    }

    @Override
    public boolean validateExpresion(List<TokenSymbol> tokens) throws Exception {
        System.out.println("\nIniciando análisis sintáctico");
        Stack<String> stack = new Stack<>();
        stack.push("S");
        int index = 0;
        symbolTable.clear();

        while (!stack.isEmpty()) {
            String top = stack.pop();
            TokenSymbol currentToken = index < tokens.size() ? tokens.get(index) : new TokenSymbol("$", "$");

            System.out.println("\nStack: " + stack);
            System.out.println("Token actual: " + currentToken.getType() + " -> " + currentToken.getValue());

            if (top.equals(currentToken.getType())) {
                handleSemantics(top, currentToken.getValue());
                index++;
            }
            else if (parsingTable.containsKey(top)) {
                List<String> production = parsingTable.get(top).get(currentToken.getType());
                if (production == null) throw new Exception("Error sintáctico en: " + currentToken.getType());

//                System.out.println("[PARSER] Aplicando producción: " + top + " -> " + production);
                System.out.println("REDUCE: " + top + " -> " + production);
                for (int i = production.size() - 1; i >= 0; i--) {
                    if (!production.get(i).equals("ε")) stack.push(production.get(i));
                }
            }
            else {
                throw new Exception("Error inesperado: " + top);
            }
        }
        return true;
    }

    private void handleSemantics(String tokenType, String value) {
//        System.out.println("[SEMÁNTICO] Procesando: " + tokenType + " -> " + value);
        System.out.println("ACCEPT: " + tokenType + " -> " + value);

        switch (tokenType) {
            case "TYPE":
                currentDeclarationType = value;
                break;

            case "ID":
                if (currentDeclarationType != null) {
                    System.out.println("Declarando variable: " + value + " tipo: " + currentDeclarationType);
                    symbolTable.put(value, currentDeclarationType);
                } else if (!symbolTable.containsKey(value)) {
                    throw new RuntimeException("Variable no declarada: " + value);
                }
                break;

            case "NUMBIN":
            case "NUMOCT":
            case "NUMHEX":
                if (currentDeclarationType != null) {
                    String expectedType = tokenType.replace("NUM", "").toLowerCase();
                    if (!currentDeclarationType.equals(expectedType)) {
                        throw new RuntimeException("Tipo incorrecto para " + value + ". Esperaba: " + currentDeclarationType);
                    }
                    System.out.println("Valor inicial válido: " + value + " para tipo " + currentDeclarationType);
                }
                break;

            case "SEMI":
                currentDeclarationType = null;
                break;
        }
    }
    @Override
    public List<String> getActionsList(List<TokenSymbol> tokens) {
        // Implementación opcional para trazas
        return List.of();
    }
}