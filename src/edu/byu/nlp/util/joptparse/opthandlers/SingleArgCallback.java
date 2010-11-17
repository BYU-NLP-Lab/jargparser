/**
 * 
 */
package edu.byu.nlp.util.joptparse.opthandlers;

import java.lang.reflect.Method;
import java.util.List;

import edu.byu.nlp.util.joptparse.OptionParser;
import edu.byu.nlp.util.joptparse.ParserState;
import edu.byu.nlp.util.joptparse.annotations.Option;

/**
 * @author rah67
 *
 */
public class SingleArgCallback extends BaseValuelessOption {

	private final Method m;
	
	public SingleArgCallback(Option opt, Object optObject, List<String> optStrings, Method m, int numArgs) {
		super(opt, optObject, optStrings, OptionParser.toWrapper(m.getParameterTypes()[0]), numArgs);
		this.m = m;
	}

	@Override
	public void performAction(String optName, Object[] optArgs, OptionParser p, ParserState state) {
		boolean b = m.isAccessible();
		m.setAccessible(true);
		try {
			if (getNumArgs() == 1)
				m.invoke(optObject, optArgs[0]);
			else
				m.invoke(optObject, optArgs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			m.setAccessible(b);
		}
	}

}
