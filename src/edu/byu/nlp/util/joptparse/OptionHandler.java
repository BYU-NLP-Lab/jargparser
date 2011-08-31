/**
 * Copyright 2011 Brigham Young University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.byu.nlp.util.joptparse;

import java.util.List;

/**
 * Contains information about an option, including the ability to perform an action when
 * encountered in an argument string.
 * 
 * <p>Arguably, two functions are conflated: information about the option, and
 * the actual handler. However, in most cases, these things are highly related</p> 
 * 
 * @author rah67
 *
 */
public interface OptionHandler {
	/**
	 * @return the number of arguments this option takes
	 */
	int	getNumArgs();
	
	/**
	 * @return the type of arguments this options handles
	 */
	Class<?> getType();
	
	/**
	 * @return the text to be displayed in the help message
	 */
	String getHelp();
	
	/**
	 * Not applicable if {@link #getNumArgs()} == 0.
	 * 
	 * @return the metavar used in the help string 
	 */
	String getMetaVar();
	
	/**
	 * @return true if this option is associated with a value
	 * 
	 * @see #getValue()
	 */
	boolean hasValue();
	
	/**
	 * Get the value of this option, if this option is associated with one.
	 * If not, the behavior of this method is undefined and will
	 * typically throw an <code>UnsupportedOperationException</code>.
	 * First check <code>hasValue()</code>
	 * 
	 * @return the value of this option
	 * 
	 * @see #hasValue()
	 */
	Object getValue();
	
	/**
	 * The list of option strings that when encountered will case
	 * <code>performAction</code> to be called.
	 * This is a list because order is weakly important, e.g. 
	 * when choosing default names for metavar and dest
	 * order is important
	 * 
	 * @return
	 */
	List<String> getOptionStrings();

	/**
	 * This method is called when the parser finds an instance of this option.
	 * 
	 * @param optName the name of the option
	 * @param optArgs the arguments for the option
	 * @param p a reference to the parser that encountered the option
	 * @param state the state of the parser
	 */
	void performAction(String optName, Object[] optArgs, OptionParser p, ParserState state);
}
