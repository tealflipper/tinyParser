grammar Whilelang;

program: seqStatement;

seqStatement: statement (';' statement)* ;

statement:
	ID ':=' expression								# attrib
	| 'skip'										# skip
	| 'if' bool 'then' statement 'else' statement	# if
	| 'while' bool 'do' statement					# while
	| 'print' Text									# print
	| 'print' expression							# write
	| '{' seqStatement '}'							# block;

expression:
	INT										# int
	| 'read'								# read
	| expression op = ('*' | '/' | '^') expression		# MulDivPow
	| expression op = ('+' | '-') expression			# AddSub
	| op = ('log' | 'ln') expression				# log
	| op = ('sin' | 'cos' | 'tan') expression		# trig
	| op = ('asin' | 'acos' | 'atan') expression	# invTrig
	| 'sqrt' expression							# sqrt
	| FLOAT									# float
	| ID									# id
	| PI									# pi
	| E										# euler
	| '(' expression ')'							# parens
    ;

bool: ('true' | 'false')			# boolean
	| expression op='=' expression		# relOp
	| expression op='<=' expression	# relOp
	| 'not' bool					# not
	| bool 'and' bool				# and
	| '(' bool ')'					# boolParen;

FLOAT:  ('0'..'9')+
    |   ('0'..'9')+ '.' ('0'..'9')* EXPONENT?
    |   '.'  ('0'..'9')+ EXPONENT?
    |   ('0'..'9')+ EXPONENT
    ;

EXPONENT: ('e'|'E') ('+'|'-')? ('0'..'9')+ ;


MUL: '*';
DIV: '/';
ADD: '+';
SUB: '-';
POW: '^';
LOG10: 'log';
LN: 'ln';
COS: 'cos';
TAN: 'tan';
SIN: 'sin';
ACOS: 'acos';
ASIN: 'asin';
ATAN: 'atan';
SQRT: 'sqrt';
PI: ('pi');
E: 'e';
INT: ('0' ..'9')+;
ID: ('a' ..'z')+;
Text: '"' .*? '"';
SPACE: [ \t\n\r]+ -> skip;
