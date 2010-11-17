/**
 * 
 */
package edu.byu.nlp.util.joptparse;

import java.util.List;

import edu.byu.nlp.util.joptparse.annotations.Option;

/**
 * Creates a new handler.
 * This interface is used to add new actions.
 * 
 * @author rah67
 *
 */
public interface OptionHandlerFactory {
	/**
	 * Create a new option handler
	 * 
	 * @param p an instance of the parser
	 * @param f the variable which was annotated with the action that invoked this method
	 * @param opt the option annotation with the action with which this factory is associated
	 * @param optObject the instance of the object being handled by the parser containing the annotations
	 * @param optStrings the strings to be associated with the new option handler
	 * @return a new option handler
	 */
	OptionHandler newHandler(OptionParser p, ReflectiveVariable f, Option opt, Object optObject, List<String> optStrings);
}
