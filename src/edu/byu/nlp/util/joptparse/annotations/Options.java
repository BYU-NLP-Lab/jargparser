/**
 * 
 */
package edu.byu.nlp.util.joptparse.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows more than one action to be associated with 
 * Option.
 * 
 * This is experimental and may be altered or removed.
 * 
 * @author rah67
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
@Inherited
public @interface Options {
	/**
	 * The list of options.
	 * 
	 * @return the list of options
	 */
	Option[] value();
}
