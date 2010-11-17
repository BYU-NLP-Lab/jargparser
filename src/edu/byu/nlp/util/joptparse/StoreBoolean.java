/**
 * 
 */
package edu.byu.nlp.util.joptparse;

import java.util.List;

import edu.byu.nlp.util.joptparse.annotations.Option;
import edu.byu.nlp.util.joptparse.opthandlers.StoreConstOption;

abstract class StoreBoolean implements OptionHandlerFactory {
	@Override
	public OptionHandler newHandler(OptionParser p, ReflectiveVariable f, Option opt, Object optObject, List<String> optStrings) {
		if (f.getType() != Boolean.TYPE && f.getType() != Boolean.class)
			throw new IllegalArgumentException("This action is only valid for boolean fields!");
		if (opt.nargs() > 0)
			throw new IllegalArgumentException("Storing a constant requires 0 arguments");
		return new StoreConstOption(f, opt, optObject, optStrings, Boolean.class, getValue());
	}
	
	protected abstract Object getValue();
	
	static class True extends StoreBoolean {
		@Override
		public Object getValue() {
			return Boolean.TRUE;
		}
	}

	static class False extends StoreBoolean {
		@Override
		public Object getValue() {
			return Boolean.FALSE;
		}
	}
}