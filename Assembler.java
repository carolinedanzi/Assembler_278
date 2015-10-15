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
			// Prepare to read input from file
			String inputFile = args[0];
			BufferedReader br = new BufferedReader(new FileReader(inputFile));
			
			// Prepare to print output to an output file
			PrintWriter pw = new PrintWriter("output.txt");	
			pw.println(line);
			
			String line;
			String instruction;
			Integer instType;
			String hexString;
			
			while((line = br.readLine()) != null){
				if(line.length() != 0) {
					instruction = getInstruction(line);
					instType = instTypeTable.get(instruction);
				
					switch(instType) {
						case 1: 
							hexString = doR_Format(line);
							break;
						case 2:
							hexString = doI_Format(line);
							break;
						case 3:
							hexString = doJ_Format(line);
							break;
						default:
							hexString = line;
					}
					pw.println(hexString);
				}

			}	
			
			br.close();
			pw.close();
			
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
		instTypeTable.put("bne", 2);
		instTypeTable.put("move", 2);
		instTypeTable.put("j", 3);  		//Jump Commands
		instTypeTable.put("jal", 3); 
		instTypeTable.put("jr", 3);
		
		// The register table
		// The registers are translated from their common name, such as
		// t0, to their 5-bit binary representation.
		registerTable = new HashMap<String, Integer>();
		registerTable.put("$zero", 0b00000);
		registerTable.put("$at", 0b00001);
		registerTable.put("$v0", 0b00010);
		registerTable.put("$v1", 0b00011);
		registerTable.put("$a0", 0b00100);
		registerTable.put("$a1", 0b00101);
		registerTable.put("$a2", 0b00110);
		registerTable.put("$a3", 0b00111);
		registerTable.put("$t0", 0b01000);
		registerTable.put("$t1", 0b01001);
		registerTable.put("$t2", 0b01010);
		registerTable.put("$t3", 0b01011);
		registerTable.put("$t4", 0b01100);
		registerTable.put("$t5", 0b01101);
		registerTable.put("$t6", 0b01110);
		registerTable.put("$t7", 0b01111);
		registerTable.put("$s0", 0b10000);
		registerTable.put("$s1", 0b10001);
		registerTable.put("$s2", 0b10010);
		registerTable.put("$s3", 0b10011);
		registerTable.put("$s4", 0b10100);
		registerTable.put("$s5", 0b10101);
		registerTable.put("$s6", 0b10110);
		registerTable.put("$s7", 0b10111);
		registerTable.put("$t8", 0b11000);
		registerTable.put("$t9", 0b11001);
		registerTable.put("$k0", 0b11010);
		registerTable.put("$k1", 0b11011);
		registerTable.put("$gp", 0b11100);
		registerTable.put("$sp", 0b11101);
		registerTable.put("$fp", 0b11110);
		registerTable.put("$ra", 0b11111);
		
		// This table uses the instruction name as the key and
		// the value is the corresponding 6-bit opcode
		// TODO: put the instructions and their 6-bit opcodes 
		// (found on first page of book) in the hashmap
		opcodeTable = new HashMap<String, Integer>();
		opcodeTable.put("add", 0b000000);
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
		opcodeTable.put("bne", 2);
		opcodeTable.put("move", 2);
		opcodeTable.put("j", 3);  		
		opcodeTable.put("jal", 3);
		opcodeTable.put("jr", 3);
		
	}
	
	/**
	* Given a line of assembly code, returns the one-word
	* assembly instruction (for example, "add"). 
	*
	* @param line	one line of assembly code, represented as a String
	* @return 		the instruction as a String
	*/
	public static String getInstruction(String line) {
		return line.substring(0, line.indexOf(" "));
	}
	
	/**
	* Takes in a line of assembly code and returns the hexadecimal
	* representation of this instruction.  This line uses R-format.
	*
	* @param line	a line of assembly code, as a String
	* @return 		the hexadecimal representation of this instruction
	*/
	public static String doR_Format(String line) {
		// R-format has the following fields:
		// opcode:	6 bits - For R-format, this is always 000000
		// rs:		5 bits
		// rt: 		5 bits
		// rd: 		5 bits
		// shamt: 	5 bits
		// funct: 	6 bits - get this from the opcodeTable
		
		System.out.println("Made it to R-format " + line);
		return line;
	}
	
	/**
	* Takes in a line of assembly code and returns the hexadecimal
	* representation of this instruction.  This line uses I-format.
	*
	* @param line	a line of assembly code, as a String
	* @return 		the hexadecimal representation of this instruction
	*/
	public static String doI_Format(String line) {
		// I-format has the following fields:
		// opcode: 	6 bits
		// rs: 		5 bits
		// rt: 		5 bits
		// constant or address: 16 bits
		
		String inst = getInstruction(line);
		String rsReg;
		String rtReg;
		String address;
		
		// lw $t0, 4($sp)
		if(inst.equals("lw") || inst.equals("sw")) {
			// rs is found between the parentheses
			rsReg = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
		}
		
		int opcode = opcodeTable.get(inst);
		int rs = 0b0;
		int rt = 0b0;
		int constant = 0b0;
		
		int binary = opcode << 5;
		binary = binary | rs;
		binary = binary << 5;
		binary = binary | rs;
		binary = binary << 16;
		binary = binary | constant;
		
		return "0x" + Integer.toHexString(binary);
	}
	
	/**
	* Takes in a line of assembly code and returns the hexadecimal
	* representation of this instruction.  This line uses J-format.
	*
	* @param line	a line of assembly code, as a String
	* @return 		the hexadecimal representation of this instruction
	*/
	public static void doJ_Format(String line) {
		System.out.println("Made it to J-format " + line);
	}
	
	/**
	 * Prints a string line to a text file
	 * 
	 * @param hex	the line to print to the file
	 */
	private static void printToFile(String line) {
		try {
			PrintWriter pr = new PrintWriter("output.txt");	
			pr.println(line);
			pr.close();
			
		} catch(Exception e) {
			System.out.println(e);
		}
		
	}
}