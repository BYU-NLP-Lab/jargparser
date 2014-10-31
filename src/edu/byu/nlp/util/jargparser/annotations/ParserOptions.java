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
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import edu.byu.nlp.util.jargparser.ArgumentParser;

/**
 * Options that allow easy configuration of the option parser.
 * 
 * @author rah67
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ParserOptions {
	/**
	 * Specifies whether or not a help option should be added to the parser.
	 * 
	 * @return true if a help option should be added to the parser
	 *
	 * @see ArgumentParser#OptionParser(boolean)
	 * @see ArgumentParser#addHelpOption(String, String...)
	 */
	boolean addHelpOption() default ArgumentParser.DEFAULT_ADD_HELP_OPTION;
	
	/**
	 * Specifies whether or not interspersed arguments are allowed.
	 * 
	 * @return true if interspersed should be arguments allowed
	 * 
	 * @see ArgumentParser#isAllowInterspersedArgs()
	 */
	boolean allowInterspersedArgs() default ArgumentParser.DEFAULT_ALLOW_INTERSPERSED_ARGS;
	
	/**
	 * Specifies whether or not camel case is allowed.
	 * 
	 * @return true if camel case is allowed
	 * 
	 * @see ArgumentParser#isCamelCaseAllowed()
	 */
	boolean allowCamelCase() default ArgumentParser.DEFAULT_CAMEL_CASE_ALLOWED;
}
