lexer grammar Jay_lexer;
// Have all code keywords above atleast ID so that it does not catch keywords like if as ID instead of code
// So far basic types such as integers and strings are able to be parsed
// Forget about comments for now
// Just worry about key words I geuss?

WHITESPACE: [ \t\r\n]+ -> skip;

RETURN: 'return' ;

BUNDLE: 'bundle'; // bundle(data1,data2...dataN)

PROCEDURE: '>>'; // func1 >> ((func2>>func3>>func4)>>(func5>>func6))>>func7

CONVERT :'->'; // "-(41+1)" -> Int.allowNegative() | Would turn into -42, whereas -(41+1)" -> Int.ignoreOp() would give a vector/list of [41,1]

IF : 'if';

ELSE : 'else';

WHILE : 'while';

FOR : 'for';

// Boolean Operators + Assign

EQUAL: '==';

ASSIGN: '=';

GREATER: '>';

LESSER: '<';

GREATEREQ: '>=';

LESSEREQ: '<=';

NOTEQ: '!=';

NOT: '!';

AND: 'and' | '&';

OR: 'or' | '|';

XOR: 'xor' | '||';

OPERATOR : '-' | '*' | '/' | '+';

NUMBER : INTEGER | SCIENTIFIC_NOTATION | DECIMAL |  HEXDECIMAL;

STRING: '"' (ESC | ~["])* '"' ;

// Types
TYPE_INT: 'Int';
TYPE_FLOAT: 'Float';
TYPE_STRING: 'String';
TYPE_BOOL: 'Bool';

//Delimiters
LPAREN: '(';
RPAREN: ')';
LBRACE: '{';
RBRACE: '}';
RSQAURE: ']';
LSQUARE: '[';
COMMA: ',';
SEMICOLON: ';';
COLON: ':';



// Comments
LINE_COMMENT: '//' ~[\n]*;

COMMENT_START: '/' '*' -> pushMode(MULTI_LINE_COMMENT);

ID: [a-zA-Z_][a-zA-Z0-9_]*;

fragment DIGIT: '0' .. '9';

fragment INTEGER: '-'? '1' .. '9' DIGIT* ;

fragment HEXDIGIT: ('a' .. 'f') | ('A' .. 'F') | DIGIT;

fragment HEXDECIMAL: '0' ('x'|'X') HEXDIGIT+;

fragment DECIMAL: (DIGIT '.' | '.' | INTEGER '.') DIGIT+;

fragment SCIENTIFIC_NOTATION: (INTEGER | DECIMAL) ('e'|'E') INTEGER;

fragment ESC: '\\' '"';


mode MULTI_LINE_COMMENT;
TAG_NAME: '@' [a-z]+;
TAG_VALUE: ':' ~[\n]*;  // Any char except newline
COMMENT_TEXT: ~[*@\n]+ -> skip;  // Any char except *, @, or newline
ASTERISK: '*' -> skip;
NEWLINE: '\n' -> skip;
COMMENT_END: '*' '/' -> popMode;