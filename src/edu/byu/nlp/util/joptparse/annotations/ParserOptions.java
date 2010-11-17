/**
 * 
 */
package edu.byu.nlp.util.joptparse.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import edu.byu.nlp.util.joptparse.OptionParser;

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
	 * @see OptionParser#OptionParser(boolean)
	 * @see OptionParser#addHelpOption(String, String...)
	 */
	boolean addHelpOption() default OptionParser.DEFAULT_ADD_HELP_OPTION;
	
	/**
	 * Specifies whether or not interspersed arguments are allowed.
	 * 
	 * @return true if interspersed should be arguments allowed
	 * 
	 * @see OptionParser#isAllowInterspersedArgs()
	 */
	boolean allowInterspersedArgs() default OptionParser.DEFAULT_ALLOW_INTERSPERSED_ARGS;
	
	/**
	 * Specifies whether or not camel case is allowed.
	 * 
	 * @return true if camel case is allowed
	 * 
	 * @see OptionParser#isCamelCaseAllowed()
	 */
	boolean allowCamelCase() default OptionParser.DEFAULT_CAMEL_CASE_ALLOWED;
}
