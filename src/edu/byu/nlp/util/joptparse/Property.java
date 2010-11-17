/**
 * 
 */
package edu.byu.nlp.util.joptparse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author rah67
 *
 */
public class Property implements ReflectiveVariable {
	
	private final Method getter;
	private final Method setter;

	public Property(Method getter, Method setter) {
		this.getter = getter;
		this.setter = setter;
	}

	@Override
	public Object get(Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		boolean b = getter.isAccessible();
		getter.setAccessible(true);
		Object ret = getter.invoke(obj, (Object[])null);
		getter.setAccessible(b);
		return ret;
	}

	private transient String name = null;
	@Override
	public String getName() {
		if ( name == null ) {
			name = Character.toString(Character.toLowerCase(setter.getName().charAt(3)));
			if (setter.getName().length() > 4)
				name += setter.getName().substring(4);
		}
		return name;
	}

	@Override
	public Class<?> getType() {
		return getter.getReturnType();
	}

	@Override
	public boolean hasValue() {
		return true;
	}

	@Override
	public void set(Object obj, Object value) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		boolean b = setter.isAccessible();
		setter.setAccessible(true);
		setter.invoke(obj, new Object[]{value});
		setter.setAccessible(b);
	}

	@Override
	public Type getGenericType() {
		return getter.getGenericReturnType();
	}
}
