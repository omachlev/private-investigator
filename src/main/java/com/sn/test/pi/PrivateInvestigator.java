package com.sn.test.pi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/** Represents the investigation management:
 * <br>{@link PrivateInvestigator#sentencesMaps} 
 * <br>This is the main object of the Private Investigator Application. in first part of application, the 
 * analyzed input and will be inserted into the Maps, and in the second part, the data will be read from this maps
 * in order to create the output file
 * 
 * <p>
 *   
 * @author Oved Machlev
*/
public class PrivateInvestigator {
	
	
	/**
	 * @param sentencesMaps - {@link SentencesMaps}  
	 * */
	private SentencesMaps sentencesMaps = null;


	/** Creates a PrivateInvestigator object and initialize the {@link SentencesMaps} field
	*/
	public PrivateInvestigator() {
		sentencesMaps = new SentencesMaps();		
	}
	
	
	/**
	 * The investigate method analyzes the received input list of rows.
	 * Each row is split to tokens by white space delimiter, it creates all permutations of the 
	 * sentence (each time with one word removed from the sentence), creates an AnalyzedRow with all 
	 * needed information and pushed to the permutationsMap in SentencesMap object.
	 * in addition, the original sentence is pushed to the sentencesMap, using the timestamp as the key
	 *   
	 * @param inputList {@link List<{@link String}>} - the list of read lined from the input file, to analyze
	 * @return {@link Boolean} - true on successful analysis, false on failure
	 */
	public boolean investigate(List<String> inputList) {

		System.out.println("- Starting to analyze received input...");

		//analyzed lines counter
		int i = 0;
		
		try {
			for (String row : inputList) {
				i++;
	
				//split the line to tokens by white space
				String[] tokens = tokenize(row);
	
				//making sure this line was in correct structure (time-stamp followed by sentence)
				if(validRow(tokens)) {
					//create all possible permutations from sentence
		        	List<Permutation> permutations = createPermutations(tokens);
		        	
		        	//for each permutation create AnalyzedRow object and push to sentencesMaps 
					for (Permutation permutation : permutations) {
						AnalyzedRow analyzedRow = new AnalyzedRow(row, tokens, permutation);
						sentencesMaps.updatePermutationsMap(permutation,analyzedRow);
						sentencesMaps.addToSentencesMap(analyzedRow.getTimestamp(), analyzedRow.getRow());
					}
				}
				else {
					System.out.println("\trow # " + i + " is not according to sentence structure and will not be analyzed: " + row);
				}
			}
		}
		catch (Exception e) {
			
			System.err.println("An exception occured while analyzing input file...");
			e.printStackTrace();
			return false;
		}
		
		System.out.println("- Finishid anlyzing Input file - Number of rows analyzed: " + i);
		return true;
	}

	
	
	/**
	 * the getResults() method iterates over all the permutations stored in {@link SentencesMaps#permutationsMap} 
	 * and extracts the similar sentences and the word that was changed between them.
	 * In addition, each extracted sentence is removed from  {@link SentencesMaps#sentencesMap}, so at the end
	 * of the process, it will contain only the sentences that havn't got similar sentences - these will 
	 * be grouped in a separate list
	 * 
	 * @return {@link List <{@link String}>} 
	 */
	public List<String> getResults() {	

		List<String> results = new ArrayList<String>();
		Set<String> keys = sentencesMaps.getPermutationKeys();
	
		//going over all permutations
		for (String key : keys) {
			List<AnalyzedRow> rowList = sentencesMaps.getByPermutationKey(key);
		
			//if this permutation contains more then one object (meaning we have at least 2 similar sentences)
			if (rowList.size() > 1) {
					
				List<String> changingWords = new ArrayList<String>(); 
				//for each sentence: add to results, add changing word to Array, remove from general sentences map
				for (AnalyzedRow row : rowList) {
					
					results.add(row.getRow());
					changingWords.add(row.getPermutation().getRemovedWord());
					sentencesMaps.removeFromSentencesMap(row.getTimestamp());
				}

				results.add("The changing word was: "+ changingWords.toString());
			}
		}
   
		//handle the remaining sentences that had no similarities
		Collection<String> noSimilarities = sentencesMaps.getSentences();
		if(noSimilarities != null && noSimilarities.size() > 0) {
			
			results.add("\nFollowing sentences had no similar sentences:");
			results.addAll(noSimilarities);
		}
		return results;
	}
	
	/**
	 * 
	 * The createPermutations() method creates all possible string permutations from an array of string,
	 * each time removing one ward from the array, and maintaining the order of strings.
	 * ignoring the first 2 strings in the array as they represent the time-stamp
	 * 
	 * @param tokens <{@link String}[]>
	 * @return {@link List <{@link Permutation}>}
	 */
	private static List<Permutation> createPermutations(String[] tokens) {

		List<Permutation> permutations = new ArrayList<>();
		
		//going over array of strings, ignoring first 2 (Date, time)
		for (int i = 2; i < tokens.length; i++) {
			
			StringBuilder perm = new StringBuilder();
			//each iteration, removing one word
			for (int j = 2; j < tokens.length; j++) {
				
				if (j != i)
					perm.append(tokens[j]);
			}
			permutations.add(new Permutation(perm.toString().toLowerCase(), tokens[i]));
		}
		
		return permutations;
	}
		
	/**
	 * 
	 * Split the received sentence into and array of strings, using white space as the delimiter
	 * @param row {@link String} - the sentence to tokenize
	 * @return tokens <{@link String}[]>
	 */
	private static String[] tokenize(String row) {
		return row.split("[ ]+");
	}
		
	/**
	 * 
	 * validate the structure of the received row - cannot be null and must be at 
	 * least 3 strings (Date, time and at least one word)
	 * @param tokens <{@link String}[]>
	 * @return boolean - true if valid, false if not
	 */
	private static boolean validRow(String[] tokens) {
		return (tokens == null || tokens.length < 3 ) ? false:true;
	}

	/**
	* Returns sentencesMaps
	*
	* @return      {@link SentencesMaps} 
	*/
	public SentencesMaps getSentencesMaps() {
		return sentencesMaps;
	}

}
