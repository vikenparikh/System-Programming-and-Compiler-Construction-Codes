%{
#include "stdio.h"
#include "y.tab.h"
extern int yylval;
%}
%%
[0-9]+ {yylval=atoi(yytext);return number;}
\+ {return plus;}
\- {return minus;}
\* {return multiply;}
\/ {return divide;}
\*\* {return cube;}
\(\*( {return addremainder;}
. {return yytext[0];}
[ \t]+ ;
\n return 0;
%%
