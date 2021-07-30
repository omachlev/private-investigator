package com.sn.test.pi;

import java.util.Arrays;


/** Represents the extracted information from a single line of input file <br>
 * {@link AnalyzedRow#row} <br>
 * {@link AnalyzedRow#tokens} <br>
 * {@link AnalyzedRow#sentence} <br>
 * {@link AnalyzedRow#timestamp} <br>
 * {@link AnalyzedRow#permutation} <br>
 * {@link Permutation} class) and all similar sentences as list of {@link AnalyzedRow} 
 * 
 * <p>
 *   
 * @author Oved Machlev
*/
public class AnalyzedRow {

	/**
	 * @param row - one line of input including the time-stamp and the sentence 
	 */
	private String row = null;
	/**
	 * @param tokens - the entire sentence without the proceeding time-stamp
	 */
	private String sentence = null;
	/**
	 * @param  tokens - row split to words using white space as delimiter
	 */
	private String[] tokens = null;
	/**
	 * @param timestamp - the first 2 tokens of a row represent the date and time
	 */
	private String timestamp = null;
	/**
	 * @param {@link Permutation} - single permutation of the sentence (with removed word)
	 */
	private Permutation permutation = null;

	
	/** Creates an AnalyzedRow object using received params:<br>
	 * <br>{@link AnalyzedRow#row} - the original row from the input file
	 * <br>{@link AnalyzedRow#tokens} - tokens received by splitting the by whitespace
	 * <br>{@link AnalyzedRow#permutation} - single permutation of the sentence
	 * <br>setting {@link AnalyzedRow#timestamp} using first 2 tokens
	 * <br>setting {@link AnalyzedRow#sentence} using all tokens except for first 2 
	 * @param row
	 * @param tokens
	 * @param permutation
	*/
	public AnalyzedRow(String row, String[] tokens, Permutation permutation) {
		
		super();
		this.row = row;
		this.tokens = tokens;
		this.timestamp = tokens[0]+" "+tokens[1];
		this.permutation = permutation;

		//sentence will be set by all tokens except for first 2 which represent the timestamp		
		StringBuilder tmpSentence = new StringBuilder();
		for (int i = 2; i < tokens.length; i++) {
			tmpSentence.append(tokens[i]);
		}			
		this.sentence = tmpSentence.toString();
	}
	
	
	/**
	 * @return {@link AnalyzedRow#row} 
	 */
	public String getRow() {
		return row;
	}

	/**
	 * @return {@link AnalyzedRow#tokens}
	 */
	public String[] getTokens() {
		return tokens;
	}

	/**
	 * @return {@link AnalyzedRow#timestamp}
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * @return {@link AnalyzedRow#permutation}
	 */
	public Permutation getPermutation() {
		return permutation;
	}

	/**
	 * @return {@link AnalyzedRow#sentence}
	 */
	public String getSentence() {
		return sentence;
	}

	@Override
	public String toString() {
		return "AnalyzedRow [row=" + row + ", tokens=" + Arrays.toString(tokens) + ", timestamp=" + timestamp
				+ ", permutation=" + permutation + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sentence == null) ? 0 : sentence.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AnalyzedRow other = (AnalyzedRow) obj;
		if (sentence == null) {
			if (other.sentence != null)
				return false;
		} else if (!sentence.equals(other.sentence))
			return false;
		return true;
	}

}
