grammar HackScript;

start
  : translationUnit? EOF
  ;

translationUnit
  :   externalDeclaration
  |   translationUnit externalDeclaration
  ;

externalDeclaration
    :   functionDeclaration
    |   declarationStatement
    |   ';' // stray ;
    ;

/* EXPRESSIONS */

primaryExpression
    :   BoolLiteral
    |   CharLiteral
    |   IntLiteral
    |   StringLiteral+
    |   Identifier
    |   '(' expression ')'
    ;

postfixExpression
    :   primaryExpression
    |   postfixExpression '[' expression ']'
    |   postfixExpression '(' argumentExpressionList? ')'
    |   postfixExpression '++'
    |   postfixExpression '--'
    ;

argumentExpressionList
    :   assignmentExpression
    |   argumentExpressionList ',' assignmentExpression
    ;

unaryExpression
    : postfixExpression
    | unaryOperator castExpression
    | CompoundOperator unaryExpression
    ;

castExpression
    : '(' typeSpecifier ')' castExpression
    | unaryExpression
    ;

multiplicativeExpression
    :   castExpression
    |   multiplicativeExpression '*' castExpression
    |   multiplicativeExpression '/' castExpression
    |   multiplicativeExpression '%' castExpression
    ;

additiveExpression
    : multiplicativeExpression
    | additiveExpression '+' multiplicativeExpression
    | additiveExpression '-' multiplicativeExpression
    ;

relationalExpression
    :   additiveExpression
    |   relationalExpression '<' additiveExpression
    |   relationalExpression '>' additiveExpression
    |   relationalExpression '<=' additiveExpression
    |   relationalExpression '>=' additiveExpression
    ;

equalityExpression
    :   relationalExpression
    |   equalityExpression '==' relationalExpression
    |   equalityExpression '!=' relationalExpression
    ;

logicalAndExpression
    :   equalityExpression
    |   logicalAndExpression '&&' equalityExpression
    ;

logicalOrExpression
  : logicalAndExpression
  | logicalOrExpression '||' logicalAndExpression
  ;

assignmentExpression
    :   logicalOrExpression
    |   unaryExpression '=' assignmentExpression
    ;

expression
    :   assignmentExpression
    |   expression ',' assignmentExpression
    ;

/* STATEMENTS */

statement
  : compoundStatement
  | expressionStatement
  | ifStatement
  | iterationStatement
  | jumpStatement
  | declarationStatement
  ;

compoundStatement
  : '{' statementList? '}'
  ;

statementList
  : statement
  | statementList statement
  ;

expressionStatement
  : expression? ';'
  ;

ifStatement
  : 'if' '(' expression ')' statement
  | ifStatement 'else' statement
  ;

iterationStatement
  : 'while' '(' expression ')' statement
  ;

jumpStatement
  : 'continue' ';'
  | 'break' ';'
  | 'return' expression? ';'
  ;

/* DECLARATIONS */
functionDeclaration
  : typeSpecifier pointer? Identifier '(' parameterTypeList? ')' compoundStatement
  ;

declarationStatement
  : typeSpecifier initDeclarator ';'
  ;

initDeclarator
  : declarator
  | declarator '=' initializer
  ;

declarator
  : pointer? directDeclarator
  ;

directDeclarator
  : Identifier
  | directDeclarator '[' assignmentExpression? ']'
  ;

parameterTypeList
  : parameterList
  ;

parameterList
  : parameterDeclaration
  | parameterList ',' parameterDeclaration
  ;

parameterDeclaration
  : typeSpecifier declarator
  ;

initializer
  : assignmentExpression
  | '{' initializerList '}'
  | '{' initializerList ',' '}'
  ;

initializerList
  : initializer
  | initializerList ',' initializer
  ;

/* MISC */

unaryOperator
  : '&'
  | pointer
  | '+'
  | '-'
  | '!'
  | '++'
  | '--'
  ;

typeSpecifier
  : 'char'
  | 'bool'
  | 'double'
  | 'int'
  | 'void'
  ;

pointer  : '*';


/* Literals */
StringLiteral : '"' SCharSeq? '"';



BoolLiteral : 'true' | 'false';

ComparisonOperator
  : '!='
  | '>'
  | '<'
  | '>='
  | '<='
  | '=='
  ;

CompoundOperator
  : '++'
  | '--'
  ;

Identifier              : (Letter | '_') (AlphaNum | '_')*;

/* Lexer rules */
NewLine                 : ('\r' '\n' | '\n') -> skip;
Whitespace              : [ \t]+ -> skip;
LineComment             : '//' ~[\r\n]* -> skip;
BlockComment            : '/*' .*? '*/' -> skip;
IntLiteral              : Number;
CharLiteral             : '\'' SChar? '\'';
Number                  : Digit+;

fragment Letter         : [a-zA-Z];
fragment Word           : Letter+;
fragment AlphaNum       : Digit | Letter;
fragment AlphaNumSeq    : AlphaNum+;
fragment EscapeSeq      : '\\' ['"?abfnrtv\\];
fragment SChar          : ~["\\\r\n]
                        | EscapeSeq
                        | '\\\n'
                        | '\\\r\n'
                        ;
fragment SCharSeq       : SChar+;
fragment Digit          : [0-9];