/**
 * 
 */
package edu.byu.nlp.util.joptparse;

import java.util.List;

/**
 * Attempts to resolve conflicts by removing the conflicting option string from the existing option.
 * If this removes all strings for the option, it is removed from the parser
 * 
 * @author rah67
 *
 */
public class ResolveConflictHandler implements ConflictHandler {

	@Override
	public void handleConflict(OptionParser parser, String optString, OptionHandler newOpt, OptionHandler oldOpt) {
		List<String> optStrings = oldOpt.getOptionStrings(); 
		optStrings.remove(optString);
		if (optStrings.isEmpty()) parser.remove(oldOpt);
	}

}
