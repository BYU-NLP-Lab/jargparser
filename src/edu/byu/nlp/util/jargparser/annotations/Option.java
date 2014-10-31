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
package edu.byu.nlp.util.jargparser.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author rah67
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
@Inherited
public @interface Option {
	
	// Constants allowing for easier/safer access to standard actions
	static final String STORE = "store";
	static final String STORE_TRUE = "store_true";
	static final String STORE_FALSE = "store_false";
	static final String STORE_CONST = "store_const";
	static final String APPEND = "append";
	static final String APPEND_CONST = "append_const";
	static final String COUNT = "count";
	
	/**
	 * This is an alias for help that cuts down on the syntax required
	 * for simple cases.
	 * 
	 * @return the help message
	 * 
	 * @see #help()
	 */
	String value() default "";
	
	/**
	 * The list of strings that, when encountered on a command-line,
	 * invoke the action associated with this option.
	 * This should include the leading dashes, e.g.
	 * <code>optStrings={"-n","--numClusters"}</code>
	 * 
	 * @return the list of option strings
	 */
	String[] optStrings() default "";
	
	/**
	 * The name of the action that should be performed with this option.
	 * 
	 * @return the name of the action that should be performed with this option
	 */
	String action() default "";
	
	/**
	 * The constant to store for STORE_CONST and APPEND_CONST actions.
	 * Although it is a string, the appropriate conversion is made by the parser.
	 * 
	 * @return the constant to store
	 */
	String constant() default "";
	
	/**
	 * The message to display alongside the option in the help message
	 * 
	 * @return the message to display alongside the option in the help message
	 * 
	 * @see #value()
	 */
	String help() default "";
	
	/**
	 * Limits the possible values for arguments to the option to the specified set.
	 * Although these are specified as strings, the are converted to the appropriate type. 
	 * 
	 * @return
	 */
	String[] choices() default "";
	
	/**
	 * @return the number of arguments needed by this option
	 */
	int nargs() default -1;
	
	/**
	 * The meta variables is the variables that represents the
	 * argument(s) to an option in the help message, e.g.
	 * <code>--filename=FILE</code>.
	 * 
	 * @return the meta variable
	 */
	String metavar() default "";
}
