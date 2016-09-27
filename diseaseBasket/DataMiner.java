package diseaseBasket;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DataMiner {
	public static Scanner console = new Scanner(System.in);
	public static ArrayList<Transaction> transactions = new ArrayList<Transaction>();
	public static ArrayList<Symptom> symptoms = new ArrayList<Symptom>();
	public static double minsupportcount = 0.0;
	public static String diseaseName = null;
	public static String[] diseaseList = null;

	public static void main(String[] args) throws Exception {
		setDiseaseName();
		setSupportCount();
		getProblemFromFile();
        start();
        pauseConsole();
	}
	
	private static void setSupportCount(){
		System.out.println("Enter minimum support count: ");
		minsupportcount = console.nextDouble();
	}
	
	private static void setDiseaseName() {
		System.out.println("Enter disease name: ");
		diseaseName = console.next();
		
		diseaseName = diseaseName.toLowerCase() + ".csv";
	}

	public static void pauseConsole()
    {
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public static void start()
    {
        ArrayList<ArrayList<String>> parents = new ArrayList<ArrayList<String>>();
        ArrayList<String> validPreviousValues = new ArrayList<String>();
        ArrayList<String> symptomCodes = new ArrayList<String>();
        ArrayList<String> otherCodes = new ArrayList<String>();
        
        symptomCodes = Symptom.getAllCodes(symptoms);
        symptomCodes = Symptom.getValidSymptomCodes(symptomCodes, minsupportcount);
        
        otherCodes = symptomCodes;
//        System.out.println(otherCodes);
        int lengthlimit = 1;
        
        for (int i = 1; i <= symptoms.size(); i++)
        {
            lengthlimit++;
            if (otherCodes.size() > 1)
            {
                if (otherCodes.size() > 0) 
                	validPreviousValues = otherCodes;
                otherCodes = combine(symptomCodes, otherCodes, lengthlimit);
                otherCodes = Symptom.getValidSymptomCodes(otherCodes, minsupportcount);
                if (otherCodes.size() > 0)
                	parents.add(otherCodes);
            }
        }
        
        System.out.println(validPreviousValues);
        for (String codes : validPreviousValues)
        {
            ArrayList<Short> values = Symptom.getValues(codes);
            System.out.println(codes + ": " + (Symptom.sumList(values)) / (double)values.size());
        }
    }

    public static void getProblemFromFile() throws Exception
    {
        int linecount = 1;

        try{
        	@SuppressWarnings("resource")
        	BufferedReader br = new BufferedReader(new FileReader(diseaseName));
        	String line = "";
            while ((line = br.readLine()) != null) {
            	String[] nodes = line.split(",");
            		if (nodes.length > 0) {
            			if (linecount == 1)
                        {
                            for (int i = 1; i < nodes.length; i++)
                            {
                                symptoms.add(new Symptom("Symptom" + i, nodes[i], i - 1));
                            }
                        }
                        else
                        {
                            Transaction transaction = new Transaction();
                            for (int i = 1; i < nodes.length; i++)
                            {
                                transaction.symptomPrescence.add(Byte.parseByte(nodes[i]));
                            }
                            transactions.add(transaction);
                        }
                        linecount++;
            		}
                }
        }
        catch(FileNotFoundException e){
        	System.out.println("Disease not found in database. Check your spelling or try another disease.");
        	setDiseaseName();
        	setSupportCount();
        	getProblemFromFile();
        }
        
    }

    public static ArrayList<String> split(String str)
    {
        ArrayList<String> ret = new ArrayList<String>();
        for (int i = 0; i < str.length(); i++)
        {
            ret.add(Character.toString(str.charAt(i)));
        }
        return ret;
    }

    static ArrayList<String> combine(ArrayList<String> items1, List<String> items2, int lengthlimit)
    {
        ArrayList<String> ret = new ArrayList<String>();
        for (int i = 0; i < items1.size(); i++)
        {
            for (int j = 0; j < items2.size(); j++)
            {
                if (j != i)
                {
                    String newItem = "";
                    newItem += items1.get(i) + items2.get(j);
                    
                    ArrayList<String> something = new ArrayList<String>();
                    newItem = sortList(newItem);
                    newItem = makeArrayDistinct(split(newItem)).toString().replace(", ", "").replace("[", "").replace("]", "");
                    something.add(newItem);
                    
                    something = makeArrayDistinct(something);
                    if (lengthlimit > 0 && lengthlimit == newItem.length())
                    {
                        ret.add(newItem);
                    }
                    else
                    {
                        if (lengthlimit == 0) 
                        	ret.add(newItem);
                    }
                }
            }
        }
        ret = makeArrayDistinct(ret);
        return ret;
    }
    
    public static ArrayList<String> makeArrayDistinct(ArrayList<String> s1) {
		Set<String> s2 = new HashSet<String>(s1);	
		ArrayList<String> s3 = new ArrayList<String>();
		for (String string : s2) {
			s3.add(string);
		}
		return s3;
	}
    
    public static ArrayList<String> makeArrayDistinct(Character[] s1) {
    	Set<Character> s2 = new HashSet<Character>();
    	for (int i = 0; i < s1.length; i++) {
			s2.add(s1[i]);
		}
		ArrayList<String> s3 = new ArrayList<String>();
		for (Character cc : s2) {
			s3.add(cc.toString());
		}
		return s3;
	}
	
	public static String sortList(String s){
		char[] a = s.toCharArray();
		Arrays.sort(a);
		String sorted = new String(a);
		return sorted;
	}
	
}
