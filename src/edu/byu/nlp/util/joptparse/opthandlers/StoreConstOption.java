/**
 * 
 */
package edu.byu.nlp.util.joptparse.opthandlers;

import java.util.List;

import edu.byu.nlp.util.joptparse.OptionParser;
import edu.byu.nlp.util.joptparse.ParserState;
import edu.byu.nlp.util.joptparse.ReflectiveVariable;
import edu.byu.nlp.util.joptparse.annotations.Option;

/**
 * @author rah67
 *
 */
public class StoreConstOption extends BaseReflectiveVariableOption {

	private final Object constant;
	
	public StoreConstOption(ReflectiveVariable f, Option opt, Object optObject, List<String> optStrings, Class<?> cls, Object constant) {
		super(opt, optObject, optStrings, cls, 0, f);
		this.constant = constant;
	}

	@Override
	public void performAction(String optName, Object[] optArgs, OptionParser p, ParserState state) {
		try {
			f.set(optObject, constant);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
