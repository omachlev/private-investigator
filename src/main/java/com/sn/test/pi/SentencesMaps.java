package com.sn.test.pi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Represents analyzed input as two maps:
 * <br>{@link SentencesMaps#sentencesMap} 
 * <br>{@link SentencesMaps#permutationsMap}
 * <br>This is the object that holds the investigation results - the permutationMap will be used to get 
 * the similar sentences, and the sentencesMap will hold all sentences, and be used to find sentences 
 * with no similarities
 * 
 * <p>
 *   
 * @author Oved Machlev
*/
public class SentencesMaps {

	/**
	 * @param sentencesMap - {@link Map} is a map with the time-stamp as key and the entire row as the value
	 */
	private Map<String, String> sentencesMap = null;
	/**
	 * @param permutationsMap - {@link Map} is a map with sentence permutation (with one removed word) as key (see the 
	 * 	{@link Permutation} class) and all similar sentences as list of {@link AnalyzedRow} 
	 * */
	private Map<String, List<AnalyzedRow>> permutationsMap = null;


	/** Creates a SentencesMaps object with new {@link SentencesMaps#sentencesMap} and {@link SentencesMaps#permutationsMap}
	 *  {@link HashMap}s
	*/
	public SentencesMaps() {
		super();
		permutationsMap = new HashMap<String, List<AnalyzedRow>>();
		sentencesMap = new HashMap<String, String>();
	}

	
	/**
	* update the permutations map. in case permutation already exist, the analyzedRow will be added to 
	* the list of analyzedRows and in case it does not exist, new (key, value) is added to the map, 
	* where the permutation is the key and a list containing only the received analyzedRow is the value
	* 
	* @param      {@link Permutation} class) permutation
	* @param      {@link AnalyzedRow} class) analyzedRow 
	*  
	*/
	public void updatePermutationsMap(Permutation permutation, AnalyzedRow analyzedRow) {
		
		//check if this permutation key already exist
		List<AnalyzedRow> analyzedRowList = permutationsMap.get(permutation.getPermutation());
		if(analyzedRowList == null) {
			analyzedRowList  = new ArrayList<AnalyzedRow>();
		}

		//only if sentence is not already associated to this permutation, add to list, and put back to map 
		if(sentenceNotInList(analyzedRowList, analyzedRow)) {
			analyzedRowList.add(analyzedRow);
			permutationsMap.put(permutation.getPermutation(), analyzedRowList);
		}
	}

	/**
	* Returns false if analyzedRow was found in List of analyzed Rows 
	*
	* @return      {@link Boolean} 
	*/
	private boolean sentenceNotInList(List<AnalyzedRow> analyzedRowList, AnalyzedRow analyzedRow) {

		for (AnalyzedRow row : analyzedRowList) {
			if(row.equals(analyzedRow)) {
				return false;
			}
		}
		return true;
	}

	/**
	* add key,value pair to the sentencesMap
	*/
	public void addToSentencesMap (String key, String value) {
		sentencesMap.put(key, value);
	}

	/**
	* Remove item from sentencesMap by key
	*/
	public void removeFromSentencesMap (String key) {
		sentencesMap.remove(key);
	}

	/**
	* Returns all values in sentencesMap 
	*
	* @return      Collection<{@link String}>
	*/
	public Collection<String> getSentences() {
		return sentencesMap.values();
	}

	/**
	* Returns sentencesMap
	*
	* @return      Map<{@link String}, {@link String}> 
	*/
	public Map<String, String> getSentencesMap() {
		return sentencesMap;
	}

	/**
	* Returns the keySet of permutationsMap 
	*
	* @return      Set<{@link String}> 
	*/
	public Set<String> getPermutationKeys() {
		return permutationsMap.keySet();
	}

	/**
	* Returns List of AnalyzedRows from permutationsMap by key (permutation)) 
	*
	* @return      List<{@link AnalyzedRow}>  
	*/
	public List<AnalyzedRow> getByPermutationKey(String key) {
		return permutationsMap.get(key);
	}

}
