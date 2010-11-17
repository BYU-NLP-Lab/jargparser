/**
 * 
 */
package edu.byu.nlp.util.joptparse;


/**
 * Formats help message for the options contained in the parser.
 * 
 * @author rah67
 *
 */
public interface HelpFormatter {

	/**
	 * Create the help message
	 * 
	 * @param parser the instance from which the help message is built
	 * @return the help message
	 */
	String format(OptionParser parser);

}
