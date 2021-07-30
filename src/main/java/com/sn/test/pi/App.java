package com.sn.test.pi;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;



/**
 * This application receives an input file describing the notes of a private investigator following his target.
 * The file has many sentences, ordered by time, one in a row, using consistent phrasing to describe what is going on. 
 * Only one word can change in a specific phrase/pattern.
 * The input may look like: 01-01-2012 20:12:39 Naomi is eating at a restaurant
 * the output of the application is groups of similar sentences (sentences where only a single word differ 
 * between them) and extracts the changes, then outputs them to a file in the following format: 
 * 	02-01-2012 10:14:00 George is eating at a diner
 * 	03-01-2012 10:15:00 Naomi is eating at a diner
 * 	The changing word was: Naomi, George
 */
public class App 
{

	private static final String DEFAULT_FILE_PATH = "../input/input.txt";
	private String filePath = null;


	public static void main( String[] args )
	{
		new App(args).run();
	}


	/** Creates the App object which manages the private-investigator application
	 * and retrives the input file path
	 * 
	 * @param args - arguments received from application invocation
	 */
	public App( String[] args ) {
		super();
		filePath = getInputFilePath(args);
	}

	/**
	 * run the main application flow
	 * 1. read the input file
	 * 2. create the  {@link PrivateInvestigator} object that will manage and maintain the analyzed information
	 * by invoking the  {@link PrivateInvestigator#investigate(List)} method
	 * 3. get the results from {@link PrivateInvestigator} object and write to output file
	 * 
	 */
	private void run() {

		System.out.println("\n*** Application Private-Investigator started ***");
		
		List<String> inputList = readFile(filePath);

		//if input data was retrieved, start the analysis and write to output file
		if(inputList != null && inputList.size() != 0) {

			PrivateInvestigator pi = new PrivateInvestigator();
			if(pi.investigate(inputList)) {

				writeToFile(pi.getResults());
			}
			else {
				System.err.println("Input file investigation failed... aborting...");
			}
		}
		else {
			System.err.println("Reading input failed, or empty input file... aborting...");
		}

		System.out.println("*** Application Private-Investigator finished ***");
	}

	/**
	 * Utility method to retrieve the input file name and path:
	 * <br> If it was not provided in application invocation, take it from default location
	 * @param args - arguments received from application invocation
	 * @return filePath {@link String}
	 */
	private static String getInputFilePath(String[] args) {

		String filePath;
		if(args.length == 0) {
			filePath = DEFAULT_FILE_PATH;
			System.out.println("Input file path not provided, using defualt input file: " + filePath);
		}
		else {
			filePath = args[0];
			System.out.println("Input file will be analyzed: " + filePath);
		}

		return filePath;
	}


	/**
	 * read the input file line by line and insert into the List of Strings
	 * @param filePath {@link String}
	 * @return inputList - {@link List <{@link String}>  String list of input. null if failed to read file
	 */
	private static List<String> readFile(String filePath) {

		System.out.println("- Start reading information from input file: " + filePath);
		
		List<String> inputList = null;

		//try-with-resources statement ensures that each resource is closed at the end of the statement
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			inputList = new ArrayList<>();
			while (br.ready()) {
				inputList.add(br.readLine());
			}
			
			System.out.println("- Finished reading information from input file: " + filePath);
			
		} catch (FileNotFoundException e) {
			System.err.println("File not found:" + filePath);

		} catch (IOException e) {
			System.err.println("Could not read file: "+ filePath);
		}       
		return inputList;
	}

	/**
	 * this method receives the analysis results and writes it into the output file, line by line
	 * 
	 * @param results - {@link List <{@link String}> - the final analysis results 
	 */
	private static void writeToFile(List<String> results) {

		System.out.println("- Starting to write results to output file");
		//String currentPath = new java.io.File(".").getCanonicalPath();
		String outputFileName = "./output_" + System.currentTimeMillis() + ".txt";
		
		//try-with-resources statement ensures that each resource is closed at the end of the statement
		try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(outputFileName)))) {
			
			for (String result : results) {
				printWriter.println(result);
			}

		} catch (FileNotFoundException e) {
			System.out.println("File not found:" + outputFileName);
			return;
		} catch (IOException e) {
			System.out.println("Could not write to file... aborting");
			return;
		}       

		System.out.println("- Finished writing results to output file: " + outputFileName);
	}
}
