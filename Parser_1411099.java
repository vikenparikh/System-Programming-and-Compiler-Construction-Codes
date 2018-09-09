/*

E->x+T|x*T|x-T|x/T

T->(E)|x

*/


import java.util.*;

import java.lang.*;

import java.io.*;


class Parser_1411099

{   static String s=new String();

    static int stringcount=0;

    static int slength=0;

    static String t="NO";

    static int brackets=0;

    public static void main (String[] args) throws java.lang.Exception

    {

    String p=new String();

    Scanner sc=new Scanner(System.in);

    p = sc.nextLine();

    slength=p.length();

    char[] s = p.toCharArray(); 

    t=parseE(s);

    System.out.println("The Grammer we have selected is :");

    System.out.println("E->x+T|x*T|x-T|x/T");

    System.out.println("T->(E)|x");

    if(t=="NO")

        System.out.println("\nThe input string " +p+ " DOESN'T SATISFY the grammer");

    else

        System.out.println("\nThe input string " +p+ " SATISFIES the grammer");


    }

    

    static String parseE(char[] s)

       {   if(Character.isLetter(s[stringcount]))

               {

               stringcount++;

               if(s[stringcount]=='+'||s[stringcount]=='-'||s[stringcount]=='*'||s[stringcount]=='/')

                   {

                   stringcount++;

                   t=parseT(s);

                   return t;

                   }

               else

                   return t;

               }

           else return t;

       }

    static String parseT(char[] s)

       {   if((Character.isLetter(s[stringcount]))&&(slength==stringcount+1+brackets))

               {  

                   t="YES";

                   return t;

               }

            else if((s[stringcount]=='(')&&(slength!=stringcount+2))

               {   brackets++;

                   stringcount++;

                   if(( Character.isLetter(s[stringcount]))&&(slength!=stringcount+2))

                        {

                            parseE(s);

                        }

                   else

                   return t;

               }

           else return t;

           return t;

       }


}

