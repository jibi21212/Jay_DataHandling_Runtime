parser grammar Jay_parser;
options { tokenVocab = Jay_lexer; }

start: ID | NUMBER | STRING;


