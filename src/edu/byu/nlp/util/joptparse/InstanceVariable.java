/**
 * 
 */
package edu.byu.nlp.util.joptparse;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * Wrapper around an instance variable.
 * 
 * @author rah67
 *
 */
public class InstanceVariable implements ReflectiveVariable {

	private final Field f;
	
	public InstanceVariable(Field f) {
		this.f = f;
	}

	/* (non-Javadoc)
	 * @see edu.byu.nlp.util.joptparse.ReflectiveVariable#get(java.lang.Object)
	 */
	@Override
	public Object get(Object obj) throws IllegalAccessException {
		boolean b = f.isAccessible();
		f.setAccessible(true);
		Object ret = f.get(obj);
		f.setAccessible(b);
		return ret;
	}

	/* (non-Javadoc)
	 * @see edu.byu.nlp.util.joptparse.ReflectiveVariable#hasValue()
	 */
	@Override
	public boolean hasValue() {
		return true;
	}

	/* (non-Javadoc)
	 * @see edu.byu.nlp.util.joptparse.ReflectiveVariable#set(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void set(Object obj, Object value) throws IllegalAccessException {
		boolean b = f.isAccessible();
		f.setAccessible(true);
		f.set(obj, value);
		f.setAccessible(b);
	}

	@Override
	public Class<?> getType() {
		return f.getType();
	}

	@Override
	public String getName() {
		return f.getName();
	}

	@Override
	public Type getGenericType() {
		return f.getGenericType();
	}

}
