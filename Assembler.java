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
	public static HashMap<String, Integer> registerTable;
	public static HashMap<String, Integer> opcodeTable;
	
	public static void main(String[] args) {
		setup();
		
		try{
			String inputFile = args[0];
			BufferedReader br = new BufferedReader(new FileReader(inputFile));
			
			String line;
			String instruction;
			Integer instType;
			
			while((line = br.readLine()) != null){
				if(line.length() != 0) {
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
		// TODO: We are missing a few instructions, such as bne and jr
		
		// Instruction type 1 codes are Register codes
		// Instruction type 2 codes are Immediate codes
		// lw, sw, addi, ori, andi, slti, beq, e.g. are Immediate codes
		// add, sub, or, and, slt, sll, srl, jr e.g. are Register codes
		// j and jal are Jump Commands
		instTypeTable = new HashMap<String, Integer>();
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
		// TODO: put all the registers and their binary values in table
		registerTable = new HashMap<String, Integer>();
		
		// This table uses the instruction name as the key and
		// the value is the corresponding opcode, in binary
		// TODO: put the instructions and their opcodes (found on first
		// page of book) in the hashmap
		opcodeTable = new HashMap<String, Integer>();
		opcodeTable.put("add", 1);
		opcodeTable.put("sub", 1);
		opcodeTable.put("or", 1);
		opcodeTable.put("and", 1);
		opcodeTable.put("slt", 1);
		opcodeTable.put("sll", 1);
		opcodeTable.put("srl", 1);
		opcodeTable.put("jr", 1);
		opcodeTable.put("addi", 2);  	
		opcodeTable.put("ori", 2);
		opcodeTable.put("lw", 2);
		opcodeTable.put("sw", 2);
		opcodeTable.put("andi", 2);
		opcodeTable.put("slti", 2);
		opcodeTable.put("beq", 2);
		opcodeTable.put("j", 3);  		
		opcodeTable.put("jal", 3);
		
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
		String inst = getInstruction(line);
		int opcode = opcodeTable.get(inst);
		int rs = 0;
		int rt = 0;
		int constant = 0;
		
		int binary = opcode << 5 | rs << 5 | rt << 16 | constant;
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