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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/**
 * This object contains most of the state of the parser.
 * This includes the positional arguments so far
 * and the remaining arguments to parse.
 * Callback methods can freely access these variables
 * to modify the behavior of the parser.
 * 
 * @author rah67
 *
 */
public class ParserState {
	
	/**
	 * The list of positional arguments found so far. 
	 */
	public final List<String> posArgs;
	
	/**
	 * A <code>Deque</code> containing the remaining unparsed strings.
	 * Callbacks are welcome to modify this as necessary, usually
	 * via <code>removeFirst()</code> and related methods.
	 * However, callbacks are encouraged to maintain predictable,
	 * GNU-compatible behavior.
	 */
	public final Deque<String> remainingArgs;
	
	private final OptionParser optionParser;
	
	public ParserState(OptionParser optionParser, String[] args) {
		this.optionParser = optionParser;
		this.posArgs = new ArrayList<String>();
		this.remainingArgs = new ArrayDeque<String>(Arrays.asList(args));
	}
	
	/**
	 * Returns the value associated with the requested option.
	 * The name is actually the option string, less the leading dashes.
	 * 
	 * @param <T> the type of the value to be returned
	 * @param name the name of the option for which the value is desired
	 * @return the value associated with the requested option; null if it doesn't exist
	 */
	@SuppressWarnings("unchecked")
	public <T> T getValue(String name) {
		OptionHandler opt = optionParser.getOption(optionParser.toOptionString(name));
		if (opt == null)
			throw new IllegalArgumentException("Could not find option for " + name);
		if (!opt.hasValue())
			throw new IllegalArgumentException(name + " is not a stored value");
		return (T) opt.getValue();
	}
	

}
