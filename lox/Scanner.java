package com.craftinginterpreters.lox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.craftinginterpreters.lox.TokenType.*;

class Scanner {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;

    Scanner(String source) {
        this.source = source;
    }
    List<Token> scanTokens() {
        while(!isAtEnd()) {
            // We are at the beginning of the next lexeme
            start = current;
            scanToken();
        }
        tokens.add(new Tokens(EOF, "", null, line));
        return tokens;
    }
    private boolean isAtEnd() {
        return current >= source.length();
    }

    private void scanToken() {
        char ch = advance();
        switch (ch) {
            case '(' : addToken(LEFT_PARENT); break;
            case ')' : addToken(RIGHT_PAREN); break;
            case '{' : addToken(LEFT_BRACE); break;
            case '}' : addToken(RIGHT_BRACE); break;
            case ',' : addToken(COMMA); break;
            case '.' : addToken(DOT); break;
            case '-' : addToken(MINUS); break;
            case '+' : addToken(PLUS); break;
            case ';' : addToken(SEMICOLON); break;
            case '*' : addToken(STAR); break;
        }
    }
}