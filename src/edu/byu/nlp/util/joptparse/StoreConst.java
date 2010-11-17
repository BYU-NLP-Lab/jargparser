/**
 * 
 */
package edu.byu.nlp.util.joptparse;

import java.util.List;

import edu.byu.nlp.util.joptparse.annotations.Option;
import edu.byu.nlp.util.joptparse.opthandlers.StoreConstOption;

public class StoreConst implements OptionHandlerFactory {
	@Override
	public OptionHandler newHandler(OptionParser p, ReflectiveVariable f, Option opt, Object optObject, List<String> optStrings) {
		if (opt.nargs() > 0)
			throw new IllegalArgumentException("Storing a constant requires 0 arguments");
		
		// Convert the constant to the appropriate type
		Object constant = p.getOptionArgumentParser(optObject.getClass());
		return new StoreConstOption(f, opt, optObject, optStrings, Boolean.class, constant);
	}
	
}