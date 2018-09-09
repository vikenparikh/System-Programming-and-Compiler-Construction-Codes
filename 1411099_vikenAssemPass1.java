import java.util.*;
import java.io.*;

class viknAssemPass1
    {
	static int lc;
	static List<table> mot;
	static List<String> pot;
	static List<symbol_table> symboltable;
	static List<literal_table> literaltable;
	static List<Integer> lclist;
	static PrintWriter out_pass2;
	static PrintWriter out_pass1;
	static int line_no;
	
	public static void main(String args[]) throws Exception
		{
		symboltable = new LinkedList<>();
		literaltable = new LinkedList<>();
		lclist = new ArrayList<>();
		mot = new LinkedList<>();
		pot = new LinkedList<>();
		String s;
		System.out.println(" \t   PASS 1 \n \n");
		pass1();
		}
	
	static void pass1() throws Exception 
		{
		BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
		out_pass1 = new PrintWriter(new FileWriter("output_pass1.txt"), true);
		PrintWriter out_symboltable = new PrintWriter(new FileWriter("out_symboltable.txt"), true);
		PrintWriter out_literaltable = new PrintWriter(new FileWriter("out_literaltable.txt"), true);
		String s;
		while((s = input.readLine()) != null) 
			{
			StringTokenizer st = new StringTokenizer(s, " ", false);
			String s_arr[] = new String[st.countTokens()];
			for(int i=0 ; i < s_arr.length ; i++) 
				{
				s_arr[i] = st.nextToken();
				}
			if(pot(s_arr) == false) 
				{
				mot(s_arr);
				out_pass1.println(s);
				}
			lclist.add(lc);
			}
		int j;
		String output = new String();
		System.out.println("\tSymbol Table:");
                System.out.println("____________________________________");
		System.out.println("Symbol    |  Value  | Length    | R/A");
                System.out.println("____________________________________");
		for(symbol_table i : symboltable) 
			{
			output = i.symbol;
			for(j=i.symbol.length() ; j < 10 ; j++) 
				{
				output += " ";
				}
                        output+="|  ";
			output += i.value;
			for(j=new Integer(i.value).toString().length() ; j < 7 ; j++) 
				{
				output += " ";
				}
			output += "|  "+i.length + "        " +"|  "+ i.ra;
			System.out.println(output);
			out_symboltable.println(output);
			}
                System.out.println("\n\n");
		System.out.println("\tLiteral Table:");
                System.out.println("____________________________________");
		System.out.println("Literal   |  Value  | Length    | R/A");
                System.out.println("____________________________________");
		for(literal_table i : literaltable) 
			{
			output = i.literal;
			for(j=i.literal.length() ; j < 10 ; j++) 
				{
				output += " ";
				}
			output+="|  ";
                        output += i.value;
                        for(j=new Integer(i.value).toString().length() ; j < 7 ; j++) 
				{
				output += " ";
				}
			output += "|  "+i.length + "        " +"|  "+i.ra;
			System.out.println(output);
			out_literaltable.println(output);
			}
		}
	
	static boolean pot(String[] s) 
		{
		int i = 0;
		int l = 0;
		int potval = 0;
		if(s.length == 3) 
			{
			i = 1;
			}
		List<String> temp = new LinkedList<>();
		for(int j=0 ; j<s.length-1 ; j++) 
			{
			temp.add(s[j]);
			}
		StringTokenizer st = new StringTokenizer(s[s.length-1], " ,", false);
		while(st.hasMoreTokens()) 
			{
			temp.add(st.nextToken());
			}
		s = temp.toArray(new String[0]);

		if(s[i].equalsIgnoreCase("DS") || s[i].equalsIgnoreCase("DC")) 
			{
			String x = s[i+1];
				int index = x.indexOf("F");
				if(i == 1) 
					{
					symboltable.add(new symbol_table(s[0], lc, 4, "R"));
					}
				if(index != 0) 
					{
					l = Integer.parseInt(x.substring(0, x.length()-1));
					l *= 4;
					} 
				else {
					for(int j=i+1 ; j<s.length ; j++)
						{
						l += 4;
						}
				     }
				lc += l;
				return true;
			}

		if(s[i].equalsIgnoreCase("EQU")) 
			{
			if(!s[2].equals("*")) 
				{
				symboltable.add(new symbol_table(s[0], Integer.parseInt(s[2]), 1, "A"));
				} 
			else   {
				symboltable.add(new symbol_table(s[0], lc, 1, "R"));
			       }
			return true;
			}

		if(s[i].equalsIgnoreCase("START")) 
			{
			symboltable.add(new symbol_table(s[0], Integer.parseInt(s[2]), 1, "R"));
			return true;
			}

		if(s[i].equalsIgnoreCase("LTORG")) 
			{
			ltorg(false);
			return true;
			}

		if(s[i].equalsIgnoreCase("END")) 
			{
			ltorg(true);
			return true;
			}
		
		return false;
		}
	
	static void mot(String[] s) 
		{
		table t = new table();
		int i = 0;
		if(s.length == 3) 
			{
			i = 1;
			}

		List<String> temp = new LinkedList<>();
		for(int j=0 ; j<s.length-1 ; j++) 
			{
			temp.add(s[j]);
			}
		StringTokenizer st = new StringTokenizer(s[s.length-1], " ,", false);
		while(st.hasMoreTokens()) 
			{
			temp.add(st.nextToken());
			}
		s = temp.toArray(new String[0]);


		for(int j=i+1 ; j < s.length ; j++) 
			{
			if(s[j].startsWith("=")) 
				{
				literaltable.add(new literal_table(s[j].substring(1, s[j].length()), -1, 4, "R"));
				}
			}
		if((i == 1) && (!s[0].equalsIgnoreCase("END"))) 
			{
			symboltable.add(new symbol_table(s[0], lc, 4, "R"));
			}
		for(table x : mot) 
			{
			if(s[i].equals(x.mnemonic)) 
				{
				t = x;
				break;
				}
			}
		lc += t.length;
		}
	
	static void ltorg(boolean isEnd) 
		{
		Iterator<literal_table> itr = literaltable.iterator();
		literal_table lt = new literal_table();
		boolean isBroken = false;
		while(itr.hasNext()) {
			lt = itr.next();
			if(lt.value == -1) {
				isBroken = true;
				break;
			}
		}
		if(!isBroken) {
			return;
		}
		if(!isEnd) {
			while(lc%8 != 0) {
				lc++;
			}
		}
		lt.value = lc;
		lc += 4;
		while(itr.hasNext())
                    {
			lt = itr.next();
			lt.value = lc;
			lc += 4;
                    }
                }
    }
 
class table 
	{
	int length;
	table() 
	     {}
	table(String mnemonic,String bin_opcode,String length,String type) 
		{
		length = Integer.parseInt(length);
		}
	}

class symbol_table 
    {
	String symbol, ra;
	int value, length;
	
	symbol_table(String s1, int i1, int i2, String s2) {
		symbol = s1;
		value = i1;
		length = i2;
		ra = s2;
	}
    }

class literal_table 
        {
	String literal, ra;
	int value, length;
	
	literal_table() {}
	
	literal_table(String s1, int i1, int i2, String s2) 
                {
		literal = s1;
		value = i1;
		length = i2;
		ra = s2;
                }
        }