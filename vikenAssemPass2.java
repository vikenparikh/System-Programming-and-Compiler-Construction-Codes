import java.util.*;
import java.io.*;

class viknAssemPass2
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
                System.out.println(" \t   PASS 2 \n \n");
                pass2();
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
    
static void pass2() throws Exception {
		line_no = 0;
		out_pass2 = new PrintWriter(new FileWriter("output_pass2.txt"), true);
		BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream("output_pass1.txt")));
		String s;
		System.out.println("Pass 2 input:");
		while((s = input.readLine()) != null) {
			System.out.println(s);
			StringTokenizer st = new StringTokenizer(s, " ", false);
			String s_arr[] = new String[st.countTokens()];
			for(int i=0 ; i < s_arr.length ; i++) {
				s_arr[i] = st.nextToken();
			}
			if(searchPot2(s_arr) == false) {
				searchMot2(s_arr);
			}
			line_no++;
		}
		System.out.println("\nPass 2 output:");
		input = new BufferedReader(new InputStreamReader(new FileInputStream("output_pass2.txt")));
		while((s = input.readLine()) != null) {
			System.out.println(s);
		}
	}


static boolean searchPot2(String[] s) {
		int i = 0;
		
		if(s.length == 3) {
			i = 1;
		}
		if(Collections.binarySearch(pot, s[i]) >= 0) {
			if(s[i].equalsIgnoreCase("USING")) {
				s = tokenizeOperands(s);
                                
                                List<String> temp = new LinkedList<>();
                                for(int j=0 ; j<s.length-1 ; j++) {
                                    temp.add(s[j]);
                                }
                                StringTokenizer st = new StringTokenizer(s[s.length-1], " ,", false);
                                while(st.hasMoreTokens()) {
                            	temp.add(st.nextToken());
                                }
                                s = temp.toArray(new String[0]);
                                
                                
				
				if(s[i+1].equals("*")) {
					s[i+1] = lclist.get(line_no) + "";
				} else {
					for(int j=i+1 ; j<s.length ; j++) {
						int value = getSymbolValue(s[j]);
						if(value != -1) {
							s[j] = value + "";
						}
					}
				}
				basetable.put(new Integer(s[i+2].trim()), new Integer(s[i+1].trim()));
			}
			return true;
		}
		return false;
	}
	
	static void searchMot2(String[] s) {
		Tuple t = new Tuple();
		int i = 0;
		int j;
		
		if(s.length == 3) {
			i = 1;
		}
		s = tokenizeOperands(s);
		
		for(Tuple x : mot) {
			if(s[i].equals(x.mnemonic)) {
				t = x;
				break;
			}
		}
		
		String output = new String();
		String mask = new String();
		if(s[i].equals("BNE")) {
			mask = "7";
		} else if(s[i].equals("BR")) {
			mask = "15";
		} else {
			mask = "0";
		}
		if(s[i].startsWith("B")) {
			if(s[i].endsWith("R")) {
				s[i] = "BCR";
			} else {
				s[i] = "BC";
			}
			List<String> temp = new ArrayList<>();
			for(String x : s) {
				temp.add(x);
			}
			temp.add(i+1, mask);
			s = temp.toArray(new String[0]);
		}
		if(t.type.equals("RR")) {
			output = s[i];
			for(j=s[i].length() ; j<6 ; j++) {
				output += " ";
			}
			for(j=i+1 ; j<s.length ; j++) {
				int value = getSymbolValue(s[j]);
				if(value != -1) {
					s[j] = value + "";
				}
			}
			output += s[i+1];
			for(j=i+2 ; j<s.length ; j++) {
				output += ", " + s[j];
			}
		} else {
			output = s[i];
			for(j=s[i].length() ; j<6 ; j++) {
				output += " ";
			}
			for(j=i+1 ; j<s.length-1 ; j++) {
				int value = getSymbolValue(s[j]);
				if(value != -1) {
					s[j] = value + "";
				}
			}
			
                        String original = s;
		Integer[] key = basetable.keySet().toArray(new Integer[0]);
		int offset, new_offset;
		int index = 0;
		int value = -1;
		int index_reg = 0;
		if(s.startsWith("=")) {
			value = getLiteralValue(s);
		} else {
			int paranthesis = s.indexOf("(");
			String index_string = new String();
			if(paranthesis != -1) {
				s = s.substring(0, s.indexOf("("));
				index_string = original.substring(original.indexOf("(")+1, original.indexOf(")"));
				index_reg = getSymbolValue(index_string);
			}
			value = getSymbolValue(s);
		}
		offset = Math.abs(value - basetable.get(key[index]));
		for(int i=1 ; i<key.length ; i++) {
			new_offset = Math.abs(value - basetable.get(key[i]));
			if(new_offset < offset) {
				offset = new_offset;
				index = i;
			}
		}
		s[j] = offset + "(" + index_reg + ", " + key[index] + ")";
                        
                        
			output += s[i+1];
			for(j=i+2 ; j<s.length ; j++) {
				output += ", " + s[j];
			}
		}
		out_pass2.println(output);
	}


static int getSymbolValue(String s) {
		for(SymTuple st : symtable) {
			if(s.equalsIgnoreCase(st.symbol)) {
				return st.value;
			}
		}
		return -1;
	}
	
	static int getLiteralValue(String s) {
		s = s.substring(1, s.length());
		for(LitTuple lt : littable) {
			if(s.equalsIgnoreCase(lt.literal)) {
				return lt.value;
			}
		}
		return -1;
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