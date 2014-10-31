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
package edu.byu.nlp.util.jargparser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * The return value of the parser, essentially a pair
 * of option values and positional arguments.
 * 
 * @author rah67
 *
 */
public class ArgumentValues {

	/**
	 * 
	 */
	private final ArgumentParser optionParser;
	private final String[] posArgs;
	
	public ArgumentValues(ArgumentParser optionParser, List<String> posArgs) {
		this.optionParser = optionParser;
		this.posArgs = posArgs.toArray(new String[posArgs.size()]);
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
		OptionHandler opt = this.optionParser.getOption(this.optionParser.toOptionString(name));
		if (opt == null)
			throw new IllegalArgumentException("Could not find option for " + name);
		if (!opt.hasValue())
			throw new IllegalArgumentException(name + " is not a stored value");
		return (T) opt.getValue();
	}
	
	/**
	 * @return the positional args found by the parser
	 */
	public String[] getPositionalArgs() {
		return posArgs;
	}
	
	/**
	 * @return a map of name, value pairs
	 * 
	 * @see #getValue(String)
	 */
	public Map<String,Object> optionsMap() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		for( OptionHandler o : optionParser.mainGroup ) {
			if (o.hasValue()) {
				for( String optString : o.getOptionStrings() ) {
					map.put(ArgumentParser.stripPrefix(optString), o.getValue());
				}
			}
		}
		return map;
	}
	
	/**
	 * Returns a property map of names and values contained in this map.
	 * Calls <code>toString()</code> on the values. 
	 * 
	 * @return properties of name, value pairs
	 * 
	 * @see #getValue(String)
	 */
	public Properties properties() {
		Properties props = new Properties();
		for( OptionHandler o : optionParser.mainGroup ) {
			if (o.hasValue()) {
				for( String optString : o.getOptionStrings() ) {
					props.setProperty(ArgumentParser.stripPrefix(optString), o.getValue().toString());
				}
			}
		}
		return props;
	}
}