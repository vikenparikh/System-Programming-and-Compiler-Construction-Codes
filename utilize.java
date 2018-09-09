import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
public class optimize {
	public static void main(String[] args) {
		        try{
	  		FileReader fr=new FileReader("D:\\loop.txt");
	  		BufferedReader br=new BufferedReader(fr);
	  		//FileWriter fw = new FileWriter("D:\\output.txt", true);
            //BufferedWriter bw=new BufferedWriter(fw);
            String loop[]={"for","while","do"};
	  		String input;
	  		int flag=0,c=0;
	  	    String queue[]=new String [20];
           // System.out.println("nitin");
	  		while((input=br.readLine())!=null)
	  		{ input.trim();
	  		  if(flag>0 && input.equals("}"))
	  		  {
	  			 System.out.println(input);
	  			 flag--;
	  		  }
	  		  else if(flag>0)
	  		  {int n=0;
	  			 String temp[]=input.split("[=+-/%;]");
	  			 for(int i=0;i<temp.length;i++)
	  			 {
	  			   for(int j=0;j<queue.length;j++)
	  			   {
	  				   if(temp[i].equals(queue[j]))
	  				   {n=1;
	  					 queue[c++]=temp[0];
	  					 System.out.println(input);
	  		  			 i=temp.length;
	  					 break;
	  				   }
	  			   }
	  			 }
	  			 if(n==0)
	  			 {
	  			
	  			  String temp1[]=input.split("[(;)]");
	  			  for(int i=0;i<loop.length;i++)
	  			  {
	  				  if(temp1[0].equals(loop[i]))
	  				  { 
	  					flag++;
	  					queue[c++]=temp[2].charAt(0)+"";
	  					 System.out.println(input);
	  					System.out.println("{");
	  					break;
	  				  }
	  			  }
	  			  
	  			 }

	  		  }
	  		  else if(input.equals("{"))
	  		  {
	  			  System.out.println(input);
	  		  }
	  		  else
	  		  {int k=0;
	  			  String temp[]=input.split("[(;)]");
	  			  for(int i=0;i<loop.length;i++)
	  			  {
	  				  if(temp[0].equals(loop[i]))
	  				  { k=1;
	  					flag++;
	  					queue[c++]=temp[2].charAt(0)+"";
	  					 System.out.println(input);
	  					System.out.println("{");
	  					break;
	  				  }
	  			  }
	  			  if(k==0)
	  			  {
	  				  System.out.println(input);
	  			  }
	  		  }	
	  		}
	    }catch(Exception e){}
	}
}




/*input
 #include <conio.h>
void main
{
int a,i,k;
for(i=0;i<10;i++)
{
k=k;
a=i;
for(j=1;j<n;j++)
{
a=j;
s=k;
}
}
}
 */
/*output:-
 #include <conio.h>
void main
{
int a,i,k;
for(i=0;i<10;i++)
{
a=i;
for(j=1;j<n;j++)
{
a=j;
}
}
}

 */
 