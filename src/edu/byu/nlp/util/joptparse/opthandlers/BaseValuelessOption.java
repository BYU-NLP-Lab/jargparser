/**
 * 
 */
package edu.byu.nlp.util.joptparse.opthandlers;

import java.util.List;

import edu.byu.nlp.util.joptparse.annotations.Option;

/**
 * @author rah67
 *
 */
public abstract class BaseValuelessOption extends BaseOption {

	public BaseValuelessOption(Option opt, Object optObject, List<String> optStrings, Class<?> cls, int numArgs) {
		super(opt, optObject, optStrings, cls, numArgs);
	}

	/* (non-Javadoc)
	 * @see edu.byu.nlp.util.joptparse.OptionHandler#getValue()
	 */
	@Override
	public Object getValue() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see edu.byu.nlp.util.joptparse.OptionHandler#hasValue()
	 */
	@Override
	public boolean hasValue() {
		return false;
	}


}
