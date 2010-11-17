/**
 * 
 */
package edu.byu.nlp.util.joptparse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import edu.byu.nlp.util.joptparse.annotations.Option;
import edu.byu.nlp.util.joptparse.opthandlers.BaseValuelessOption;

/**
 * @author rah67
 *
 */
public class CallbackOptionHandler extends BaseValuelessOption {

	private final Method m;

	public CallbackOptionHandler(Method m, Option opt, Object optObject, List<String> optStrings) {
		super(opt, optObject, optStrings, null, 0);
		this.m = m;
	}

	@Override
	public void performAction(String optName, Object[] optArgs, OptionParser p, ParserState state) {
		try {
			m.invoke(optObject, new Object[]{optName,optArgs,p,state});
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new IllegalArgumentException("Problem with callback method");
		}
	}

}
