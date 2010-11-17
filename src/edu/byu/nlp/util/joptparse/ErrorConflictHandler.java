/**
 * 
 */
package edu.byu.nlp.util.joptparse;

/**
 * A conflict handler that throws an error on any conflict.
 * 
 * @see ResolveConflictHandler 
 * 
 * @author rah67
 *
 */
public class ErrorConflictHandler implements ConflictHandler {

	/* (non-Javadoc)
	 * @see edu.byu.nlp.util.optparse.ConflictHandler#handleConflict(edu.byu.nlp.util.optparse.OptionParser, java.lang.String, edu.byu.nlp.util.optparse.Option, edu.byu.nlp.util.optparse.Option)
	 */
	@Override
	public void handleConflict(OptionParser parser, String optString, OptionHandler newOpt, OptionHandler oldOpt) {
		throw new OptionConflictError(newOpt, parser);
	}

}
