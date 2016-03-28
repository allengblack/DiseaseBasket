package marketBasket;

import java.util.ArrayList;
import java.util.Collections;

import static com.wagnerandade.coollection.Coollection.*;;

public class Symptom {
	
	public String name;
	public String code;
	public int index = 0;
	
	 public Symptom(String name, String code, int index)
     {
         this.name = name;
         this.code = code;
         this.index = index;
     }
	 
	 public ArrayList<Short> getSymptomValues(ArrayList<Transaction> transactions)
     {
         if (transactions == null)
         {
             transactions = DataMiner.transactions;
         }
         ArrayList<Short> byteArrayList = new ArrayList<Short>(); 
         for (int i = 0; i<transactions.size();i++) {
        	 Transaction pp = transactions.get(i);
        	 if (pp != null) {
 				byteArrayList.add(i, (short)pp.symptomPrescence.get(index));
 			}
         }
         return byteArrayList;
     }
	 
	 public static ArrayList<String> getAllCodes(ArrayList<Symptom> symptoms)
     {
		 ArrayList<String> symptomCodeList = new ArrayList<String>();
         if (symptoms == null)
         {
             symptoms = DataMiner.symptoms;
         }
         for (Symptom ss : symptoms) {
        	 symptomCodeList.add(ss.code);
		}
        return symptomCodeList; 
     }
	 
	 public static Symptom getSymptomByCode(String newCode, ArrayList<Symptom> symptoms)
     {
		 Symptom ret = null;
		 if (symptoms == null)
             symptoms = DataMiner.symptoms;
         for (Symptom symptom : symptoms) {
			if ((symptom.code).equalsIgnoreCase(newCode))
				ret = symptom;
         }
		return ret;
     }

     public static ArrayList<String> getValidSymptomCodes(ArrayList<String> symptomcodes, double minSupportCount)
     {
         if (minSupportCount == 0)
             minSupportCount = DataMiner.minsupportcount;
         ArrayList<String> ret = new ArrayList<String>();
         
         for (String codes : symptomcodes)
         {
             ArrayList<Short> values = getValues(codes);
             double symptomSupportCount = sumList(values) / (double)values.size();
             if (symptomSupportCount > minSupportCount)
             {
                 ret.add(codes);
             }
         }
         return ret;
     }
     
     public static double sumList(ArrayList<Short> values) {
    	 double d = 0.0;
    	 for(Short val : values){
    		 d += (double)val;
    	}
		return d;
	}

	public static ArrayList<Short> getValues(String codes)
     {
         ArrayList<ArrayList<Short>> values = new ArrayList<ArrayList<Short>>();
         for (String code : DataMiner.split(codes))
         {
             Symptom s = Symptom.getSymptomByCode(code, null);
             values.add(s.getSymptomValues(null));
         }
         ArrayList<Short> ret = new ArrayList<Short>();
         for (int y = 0; y < values.size(); y++)
         {
             short prod = 1;
             for (int x = 0; x < values.size(); x++)
             {
                 prod *= values.get(x).get(y);
             }
             ret.add(prod);
         }
         return ret;
     }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
}