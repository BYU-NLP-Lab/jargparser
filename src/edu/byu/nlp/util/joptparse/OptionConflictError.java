/**
 * 
 */
package edu.byu.nlp.util.joptparse;

import java.util.Set;

import edu.byu.nlp.util.Strings;

/**
 * Error created when two options conflict.
 * 
 * @author rah67
 *
 */
public class OptionConflictError extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;
	
	private final OptionParser parser;
	private final OptionHandler failedOpt;
	private final Set<String> conflictingOpts;

	public OptionConflictError(OptionHandler failedOpt, OptionParser parser) {
		super(buildMessage(failedOpt, parser));
		this.failedOpt = failedOpt;
		this.parser = parser;

		// don't know how to avoid doing this twice since super must be called first
		this.conflictingOpts = parser.existingOptions(failedOpt.getOptionStrings());
	}

	private static String buildMessage(OptionHandler failedOpt, OptionParser parser) {
		Set<String> conflictingOpts = parser.existingOptions(failedOpt.getOptionStrings());
		return String.format("option %s: conflicting option string(s): %s",
				Strings.join(failedOpt.getOptionStrings(), "/"),
				Strings.join(conflictingOpts, ", "));
	}

	public OptionParser getParser() {
		return parser;
	}

	public OptionHandler getFailedOpt() {
		return failedOpt;
	}

	public Set<String> getConflictingOpts() {
		return conflictingOpts;
	}
	
}
