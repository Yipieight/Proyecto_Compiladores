package org.example;

import java.util.List;

public interface IParser {
    boolean validateExpresion(List<TokenSymbol> tokens) throws Exception;

    List<String> getActionsList(List<TokenSymbol> tokens) throws Exception;
}