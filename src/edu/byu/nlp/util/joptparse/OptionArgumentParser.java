/**
 * 
 */
package edu.byu.nlp.util.joptparse;

/**
 * Given an argument to an option, produces an object of type <code>T</code>
 * 
 * @author rah67
 *
 * @param <T> the type of object created by this parser
 */
public interface OptionArgumentParser<T> {

	/**
	 * Given an argument to an option, produces an object of type <code>T</code>
	 * 
	 * @param arg the argument to the command-line option
	 * @return the object created from the argument
	 */
	T parse(String arg);

}
