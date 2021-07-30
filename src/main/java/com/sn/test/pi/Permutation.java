package com.sn.test.pi;


/** Represents a permutation of the given sentence and one removed word.
 * <br>{@link Permutation#permutation} 
 * <br>{@link Permutation#removedWord}
 * <br>Each sentence will result in n permutations, where n is the number of words in the original sentence
 *   
 * @author Oved Machlev
*/
public class Permutation {

	/**
	 * @param permutation - {@link String} Representing one permutation of the original sentence 
	 * The permutation string is the original sentence, lower-cased, no white space and with one removed word
	 */
	private String permutation = null;
	/**
	 * @param removedWord - {@link String} The one word removed from original sentence to create this permutation  
	 */
	private String removedWord = null;

	
	/** Creates a permutation object with the specified permutation and removed word.
	 * @param perm - One specific permutation.
	 * @param removedWord - The word that was removed from the original sentence.
	*/
	public Permutation(String perm, String removedWord) {
		super();
		this.permutation = perm;
		this.removedWord = removedWord;
	}


	/**
	* Returns the permutation string in this object.
	*
	* @return      String permutation
	*/
	public String getPermutation() {
		return permutation.toString();
	}

	/**
	* Returns the removed word from the permutation string in this object. 
	*
	* @return      String removedWord
	*/
	public String getRemovedWord() {
		return removedWord;
	}

	@Override
	public String toString() {
		return "Permutations [permutation=" + permutation + ", removedWord=" + removedWord + "]";
	}

}
