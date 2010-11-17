/**
 * 
 */
package edu.byu.nlp.util.joptparse.opthandlers;

import java.util.List;

import edu.byu.nlp.util.joptparse.OptionHandler;
import edu.byu.nlp.util.joptparse.OptionParser;
import edu.byu.nlp.util.joptparse.ParserState;
import edu.byu.nlp.util.joptparse.ReflectiveVariable;
import edu.byu.nlp.util.joptparse.annotations.Option;

/**
 * @author rah67
 *
 */
public class StoreBooleanArrayFieldOption extends BaseReflectiveVariableOption
		implements OptionHandler {

	public StoreBooleanArrayFieldOption(ReflectiveVariable f, Option opt, Object optObject, List<String> optStrings, Class<?> cls, int numArgs) {
		super(opt, optObject, optStrings, cls, numArgs, f);
	}

	@Override
	public void performAction(String optName, Object[] optArgs, OptionParser p, ParserState state) {
		boolean[] boolArgs = new boolean[optArgs.length];
		for (int i = 0; i < boolArgs.length; i++) {
			boolArgs[i] = (Boolean)optArgs[i];
		}
		try {
			f.set(optObject, boolArgs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
