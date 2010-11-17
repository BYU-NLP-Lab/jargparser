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
public class StoreByteArrayFieldOption extends BaseReflectiveVariableOption
		implements OptionHandler {

	public StoreByteArrayFieldOption(ReflectiveVariable f, Option opt, Object optObject, List<String> optStrings, Class<?> cls, int numArgs) {
		super(opt, optObject, optStrings, cls, numArgs, f);
	}

	@Override
	public void performAction(String optName, Object[] optArgs, OptionParser p, ParserState state) {
		byte[] byteArgs = new byte[optArgs.length];
		for (int i = 0; i < byteArgs.length; i++) {
			byteArgs[i] = (Byte)optArgs[i];
		}
		try {
			f.set(optObject, byteArgs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
