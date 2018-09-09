%{
#include "stdio.h"
int result=0;
void yyerror(const char *str)
{
fprintf(stderr, "error:%s\n",str);
}

int yywrap(void)
{
return 1;
}

int cube_it(int a)
{
return (a*a*a);
}

int addRemainder(int a,int b)
{int c;
c=a;
return((a/b)+c);
]


%}
%token number plus minus multiply divide cube addremainder
%left cube plus minus addremainder
%left multiply divide
%nonassoc UMINUS

%%
ae: exp	{result=$1;}
	;
exp: number {	$$ = $1;}
   | exp minus exp {$$ = $1 - $3;}
   | exp plus exp {$$ = $1 + $3;}
   | exp multiply exp {$$ = $1 * $3;}
   | exp cube {$$ = cube_it($1);}
   | exp addremainder exp{$$ = addRemainder($1,$4);}
   | exp divide exp { if ( $3 == 0 ) yyerror("divide by zero"); else $$ = $1 / $3;}
   | minus exp %prec UMINUS {$$ = -$2; }
   ;
%%
#include "math.h"
int main(void)
{
yyparse();
printf("=%d",result);
getch();
return 0;
} 
	
/* Steps to execute the program

dell@dell-desktop:~/Desktop$ flex sample1.lex
dell@dell-desktop:~/Desktop$ yacc -d sample1.y
dell@dell-desktop:~/Desktop$ gcc lex.yy.c y.tab.c
dell@dell-desktop:~/Desktop$ ./a.out
1+2*3+4
=11dell@dell-desktop:~/Desktop$ gcc -o output lex.yy.c y.tab.c
dell@dell-desktop:~/Desktop$ ./output
1+2/3*
error:syntax error
=0dell@dell-desktop:~/Desktop$ 
*/
