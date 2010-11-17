/**
 * 
 */
package edu.byu.nlp.util.joptparse.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Sets the usage string. "%prog" is a special string
 * that will be replaced with the appropriate value.
 * 
 * @author rah67
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Usage {
	String value();
}
