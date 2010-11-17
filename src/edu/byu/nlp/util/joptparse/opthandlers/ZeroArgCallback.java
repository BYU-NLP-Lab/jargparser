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
public class ZeroArgCallback extends BaseValuelessOption {

	private final Method m;

	public ZeroArgCallback(Option opt, Object optObject, List<String> optStrings, Method m) {
		super(opt, optObject, optStrings, null, 0);
		this.m = m;
	}

	@Override
	public void performAction(String optName, Object[] optArgs, OptionParser p, ParserState state) {
		boolean b = m.isAccessible();
		m.setAccessible(true);
		try {
			m.invoke(optObject, (Object[])null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			m.setAccessible(b);
		}
	}

}
