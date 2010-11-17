/**
 * 
 */
package edu.byu.nlp.util.joptparse;

/**
 * Interface used to allow for customizable conflict handling.
 * 
 * @author rah67
 *
 */
public interface ConflictHandler {
	/**
	 * Handle a conflict
	 * 
	 * @param parser	the option parser
	 * @param optString	the string causing the conflict
	 * @param newOpt	the option to be added
	 * @param oldOpt	the option that already existed
	 */
	void handleConflict(OptionParser parser, String optString, OptionHandler newOpt, OptionHandler oldOpt);
}
