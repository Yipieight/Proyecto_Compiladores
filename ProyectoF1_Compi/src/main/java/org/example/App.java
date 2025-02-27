package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class App {
    public static void main(String[] args) throws IOException {

        String input = Files.readString(Path.of("GRAMATICA.txt"));

        try {
            System.out.println("====== INICIO DEL PROCESO ======");
            ILexer lexer = new Lexer();
            IParser parser = new Parser();

            System.out.println("\n====== ANÁLISIS LÉXICO ======");
            List<TokenSymbol> tokens = lexer.getTokensFromString(input);


//            System.out.println("\n====== ANÁLISIS SINTÁCTICO/SEMÁNTICO ======");
//            boolean isValid = parser.validateExpresion(tokens);

//            System.out.println("\n====== RESULTADO FINAL ======");
//            System.out.println("Expresión " + (isValid ? "válida!" : "inválida!"));
        } catch (Exception e) {
            System.out.println("\n====== ERROR ======");
            System.out.println("Error: " + e.getMessage());
        }
    }
}