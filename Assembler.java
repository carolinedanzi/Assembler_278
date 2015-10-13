/**
* CSE 278 Project 1
* Dr. Yue
* This program implements a simple MIPS assembler
* @author Matthew Huberty and Caroline Danzi
* @version 1.0
*
* This code is our original work.  We had some hints from Dr. Yue, and used StackOverflow
* for help on the syntax for BufferedReader.
*/

import java.io.*;
import java.util.Hashtable;

public class Assembler {
	
	public static Hashtable<String, Integer> instTypeTable;
	//TODO: Decide on type for binary representation of register
	public static Hashtable<String, String> registers;
	
	public static void main(String[] args) {
		setup();
		
		try{
			String inputFile = args[0];
			BufferedReader br = new BufferedReader(new FileReader(inputFile));
			
			String line;
			String instruction;
			Integer instType;
			
			while((line = br.readLine()) != null){
				instruction = getInstruction(line);
				instType = instTypeTable.get(instruction);
				
				switch(instType) {
					case 1: 
						doR_Format(line);
						break;
					case 2:
						doI_Format(line);
						break;
					case 3:
						doJ_Format(line);
						break;
					default:
						System.out.println(line);
				}
				System.out.println(line);
			}	
			
		} catch(Exception e) {
			System.out.println(e);
		}		
	}	
	
	/**
	* This sets up the HashTables for the instruction types and 
	* registers
	*/
	public static void setup() {
		// The instruction type table
		instTypeTable = new Hashtable<String, Integer>();
		
		//TODO: put all the instructions in here with proper types
		// R-format = 1
		// I-format = 2
		// J-format = 3
		instTypeTable.put("add", 1);
		
		// The register table
		registers = new Hashtable<String, String>();
		
		// TODO: put all the registers and their binary values in table
	}
	
	/**
	* Given a line of assembly code, returns the one-word
	* assembly instruction (for example, "add"). 
	*
	* @param line	one line of assembly code, represented as a String
	* @return 		the instruction as a String
	*/
	public static String getInstruction(String line) {
		return "";
	}
	
	/**
	* Takes in a line of assembly code and prints the hexadecimal
	* representation of that line to a file.  This line uses R-format.
	*
	* @param line	a line of assembly code, as a String
	*/
	public static void doR_Format(String line) {
		
	}
	
	/**
	* Takes in a line of assembly code and prints the hexadecimal
	* representation of that line to a file.  This line uses I-format.
	*
	* @param line	a line of assembly code, as a String
	*/
	public static void doI_Format(String line) {
		
	}
	
	/**
	* Takes in a line of assembly code and prints the hexadecimal
	* representation of that line to a file.  This line uses J-format.
	*
	* @param line	a line of assembly code, as a String
	*/
	public static void doJ_Format(String line) {
		
	}
}