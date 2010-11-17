/**
 * 
 */
package edu.byu.nlp.util.joptparse.opthandlers;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import edu.byu.nlp.util.joptparse.OptionParser;
import edu.byu.nlp.util.joptparse.ParserState;
import edu.byu.nlp.util.joptparse.ReflectiveVariable;
import edu.byu.nlp.util.joptparse.annotations.Option;

/**
 * @author rah67
 *
 */
public class StoreChoicesOption extends BaseReflectiveVariableOption {

	private Set<Object> choices;
	
	public StoreChoicesOption(OptionParser p, ReflectiveVariable f, Option opt, Object optObject, List<String> optStrings, Class<?> cls, int numArgs) {
		super(opt, optObject, optStrings, cls, numArgs, f);
		this.choices = new LinkedHashSet<Object>(Arrays.asList(p.convert(getType(), opt.choices())));
	}

	@Override
	public void performAction(String optName, Object[] optArgs, OptionParser p, ParserState state) {
		if (!choices.contains(optArgs[0]))
			throw new IllegalArgumentException("Not a valid choice for option " + optName + "; choose from: " + choices);
		try {
			f.set(optObject, optArgs[0]);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
