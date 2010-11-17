/**
 * 
 */
package edu.byu.nlp.util.joptparse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

/**
 * Abstraction that allows properties (getter/setter pairs) and instance variables
 * to be treated the same.
 * 
 * @author rah67
 *
 */
public interface ReflectiveVariable {

	boolean hasValue();
	Object get(Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
	void set(Object obj, Object value) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
	Class<?> getType();
	String getName();
	Type getGenericType();

}
