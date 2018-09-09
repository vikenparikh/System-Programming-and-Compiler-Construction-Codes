import java.util.*;
import java.io.*;

public class vikenlex {
	static List<String> keywords;
	static List<String> functions;
	static List<String> special_symbols;
	static List<String> operators;
	static List<String> identifiers;
	static List<Integer> literals;
        static int ctr=0;

	public static void main(String args[]) throws Exception {
		keywords = new LinkedList<>();
		functions = new LinkedList<>();
		operators = new LinkedList<>();
		special_symbols = new LinkedList<>();
		identifiers = new LinkedList<>();
		literals = new LinkedList<>();
                
                keywords= Arrays.asList("auto","double","int","struct","break","else","long","switch","case","enum","register","typedef","char","extern","return","union","const","float","short","unsigned","continue","for","signed","void","default","goto","sizeof","volatile","do","if","static","while");
                functions= Arrays.asList("main()", "sqrt()");
                operators= Arrays.asList("+","=","*","/");
                special_symbols= Arrays.asList(",",";","{","}","<",">",".","_","(",")","$",":","[","]","#","?","'","&","{","}","//","^","!","|","~");
                
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("input.c"))));
		String s = new String();
		while((s = br.readLine()) != null) {
			
                    StringTokenizer st = new StringTokenizer(s, " ,+=-*%/#<>;", true);
			while(st.hasMoreTokens()) {
				printToken(st.nextToken());
			}
		}

	}

	static void printToken(String s) {
		if(!s.equals(" "))
                    {
			String x = getTokenType(s);
			System.out.println(s+"                "+x);
		}
	}

	static String getTokenType(String s) {
		int index = 0;
		if((index = Collections.binarySearch(keywords, s)) >= 0)
                        {
			return ("KEYWORD" + index);
                        }
		if((index = Collections.binarySearch(functions, s)) >= 0) 
                        {
			return ("FUNCTION" + index);
                        }
		if((index = Collections.binarySearch(operators, s)) >= 0) 
                        {
			return ("OPERATOR" + index);
                        }
		if((index = Collections.binarySearch(special_symbols, s)) >= 0) 
                        {
			return ("SPECIAL_SYMBOL" + index);
                        }
		try     {
			int lit = Integer.parseInt(s);
			literals.add(lit);
			return ("LITERALS"+(literals.size()-1));
                        } 
                catch(NumberFormatException e) 
                        {
			identifiers.add(s);
                        }
		return ("IDENTIFIER" + ctr++);
		}
	}
