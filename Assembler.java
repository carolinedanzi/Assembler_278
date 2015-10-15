/**
* CSE 278 Project 1
* Dr. Yue
* This program implements a simple MIPS assembler
* 
* @author Matthew Huberty and Caroline Danzi
* @version 1.0
*
* This code is our original work.  We had some hints from Dr. Yue, and used StackOverflow
* for help on the syntax for BufferedReader.
*/

import java.io.*;
import java.util.HashMap;

public class Assembler {
	
	public static HashMap<String, Integer> instTypeTable;
	// TODO: Decide on types for binary string
	public static HashMap<String, String> registers;
	
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
						System.out.println("default" + line);
				}

			}	
			
			br.close();
			
		} catch(Exception e) {
			System.out.println(e);
		}		
	}	
	
	/**
	* This sets up the HashTables for the instruction types and 
	* registers
	*/
	public static void setup() {
		instTypeTable = new HashMap<String, Integer>();
		// Instruction type 1 codes are Register codes
		// Instruction type 2 codes are Immediate codes
		// lw, sw, addi, ori, andi, slti, beq, e.g. are Immediate codes
		// add, sub, or, and, slt, sll, srl, jr e.g. are Register codes
		// j and jal are Jump Commands

		instTypeTable.put("add", 1);  	//Register codes
		instTypeTable.put("sub", 1);
		instTypeTable.put("or", 1);
		instTypeTable.put("and", 1);
		instTypeTable.put("slt", 1);
		instTypeTable.put("sll", 1);
		instTypeTable.put("srl", 1);
		instTypeTable.put("jr", 1);
		instTypeTable.put("addi", 2);  	//Immediate codes
		instTypeTable.put("ori", 2);
		instTypeTable.put("lw", 2);
		instTypeTable.put("sw", 2);
		instTypeTable.put("andi", 2);
		instTypeTable.put("slti", 2);
		instTypeTable.put("beq", 2);
		instTypeTable.put("j", 3);  		//Jump Commands
		instTypeTable.put("jal", 3);  
		
		// The register table
		registers = new HashMap<String, String>();
		
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
		// What if there is just loop: or something like that?
		return line.substring(0, line.indexOf(" "));
	}
	
	/**
	* Takes in a line of assembly code and prints the hexadecimal
	* representation of that line to a file.  This line uses R-format.
	*
	* @param line	a line of assembly code, as a String
	*/
	public static void doR_Format(String line) {
		// R-format has the following fields:
		// opcode:	6 bits
		// rs:		5 bits
		// rt: 		5 bits
		// rd: 		5 bits
		// shamt: 	5 bits
		// funct: 	6 bits
		System.out.println("Made it to R-format " + line);
		printToFile(line);
	}
	
	/**
	* Takes in a line of assembly code and prints the hexadecimal
	* representation of that line to a file.  This line uses I-format.
	*
	* @param line	a line of assembly code, as a String
	*/
	public static void doI_Format(String line) {
		// I-format has the following fields:
		// opcode: 	6 bits
		// rs: 		5 bits
		// rt: 		5 bits
		// constant or address: 16 bits
		System.out.println("Made it to I-format " + line);
	}
	
	/**
	* Takes in a line of assembly code and prints the hexadecimal
	* representation of that line to a file.  This line uses J-format.
	*
	* @param line	a line of assembly code, as a String
	*/
	public static void doJ_Format(String line) {
		System.out.println("Made it to J-format " + line);
	}
	
	/**
	* Takes in a binary string and converts it to
	* an equivalent hexadecimal string
	* NOTE: I just made these Strings for now, and I-format
	* am not sure what they should be.
	* 
	* @param binary	a string of 0's and 1's 
	* @return 		a hexadecimal string that equals the binary number
	*/
	private static String binaryToHex(String binary) {
		return "";
	}
	
	/**
	 * Prints a string line to a text file
	 * 
	 * @param hex	the line to print to the file
	 */
	private static void printToFile(String hex) {
		try {
			PrintWriter pr = new PrintWriter("output.txt");	
			pr.println(hex);
			pr.close();
			
		} catch(Exception e) {
			System.out.println(e);
		}
		
	}
}