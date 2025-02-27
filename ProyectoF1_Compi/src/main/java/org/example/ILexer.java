package org.example;

import java.util.List;

public interface ILexer {
    List<TokenSymbol> getTokensFromString(String expresion);
}