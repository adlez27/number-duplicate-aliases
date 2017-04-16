package numberduplicates;

import java.io.*;
import java.util.*;

import javax.swing.*;

public class NumberDuplicates {
	public static void main(String[]args){
		ArrayList<String> otoFile = new ArrayList<String>();
		ArrayList<String[]> otoMatrix = new ArrayList<String[]>();
		boolean windows, error = false;
	    String macHeader = "";
	    ImageIcon utau = new ImageIcon("utaulogo.png");
	      
	    while (true) {
	    	Object[] possibleValues = { "Windows", "Mac"};
	        Object os = JOptionPane.showInputDialog(null,"Which operating system?", "Number Duplicates",JOptionPane.INFORMATION_MESSAGE, utau,possibleValues, possibleValues[0]);
	        
	        if (os.equals("Windows")) {
	        	windows = true;
	        	break;
	        } else if (os.equals("Mac")) {
	        	windows = false;
	            break;
	        }   
	    }
	      
	    String inFile, outFile;
	    if (windows) {
	    	inFile = "oto.ini";
	    	outFile = "edited-oto.ini";
		} else {
	    	inFile = "oto_ini.txt";
	    	outFile = "edited-oto_ini.txt";
		}
	      
	    try (BufferedReader br = new BufferedReader(new FileReader(inFile))) {
			
			String line;
			while ((line = br.readLine()) != null) {
	    		otoFile.add(line);
	    	}
	         
	    	if (!windows) {
	        	macHeader = otoFile.remove(0);
	    	}
	    } catch (Exception ex) {
	    	error = true;
	    	ex.printStackTrace();
	    	JOptionPane.showMessageDialog(null, "Could not read oto.", "Number Duplicates Error", JOptionPane.ERROR_MESSAGE);
	    }
	      
	    if (!error) {
	    	for (int i = 0; i < otoFile.size(); i++) {
	    		otoFile.set(i, otoFile.get(i) + ",");
	    		String line = otoFile.get(i);
	            String[] arr = new String[7];
	            int start = 0;
	            int end = line.indexOf("=");
	            for (int j = 0; j < 7; j++) {
	            	arr[j] = line.substring(start,end);
	            	start = end + 1;
	            	end = line.indexOf(",",start);
	        	}
	            otoMatrix.add(arr);
	        }
	         
	        String maximum = JOptionPane.showInputDialog(null,"Enter maximum number of duplicates (0 = delete all duplicates)","Number Duplicates",JOptionPane.PLAIN_MESSAGE);
	        int maxDups = Integer.parseInt(maximum);
	        
	        try {
	        	PrintWriter writer = new PrintWriter(new File(outFile));
	        	HashMap<String,Integer> aliasCount = new HashMap<String,Integer>();
	            
	            if (!windows)
	            	writer.println(macHeader);
	            
	            for (String[] line : otoMatrix) {
	            	if (aliasCount.containsKey(line[1]) && aliasCount.get(line[1]) < maxDups){
	    				aliasCount.replace(line[1],aliasCount.get(line[1])+1);
	    				writer.println(line[0] + "=" + line[1] + aliasCount.get(line[1]) + "," + line[2] + "," + line[3] + "," + line[4] + "," + line[5] + "," + line[6]);
	    			} else if (!aliasCount.containsKey(line[1])) {
	    				aliasCount.put(line[1],0);
	    				writer.println(line[0] + "=" + line[1] + "," + line[2] + "," + line[3] + "," + line[4] + "," + line[5] + "," + line[6]);
	    			}
	            	
	            }
	        	writer.close();
	    	} catch (Exception ex) {
	    		ex.printStackTrace();
	    		JOptionPane.showMessageDialog(null, "Could not write oto.", "Number Duplicates Error", JOptionPane.ERROR_MESSAGE);
	    	}
	         
	        JOptionPane.showMessageDialog(null, "Done.", "Number Duplicates", JOptionPane.INFORMATION_MESSAGE,utau);
		}
	}
}
