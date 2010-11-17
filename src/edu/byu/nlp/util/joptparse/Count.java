/**
 * 
 */
package edu.byu.nlp.util.joptparse;

import java.util.List;

import edu.byu.nlp.util.joptparse.annotations.Option;
import edu.byu.nlp.util.joptparse.opthandlers.BaseReflectiveVariableOption;

/**
 * @author rah67
 *
 */
public class Count implements OptionHandlerFactory {

	/* (non-Javadoc)
	 * @see edu.byu.nlp.util.joptparse.OptionHandlerFactory#newHandler(edu.byu.nlp.util.joptparse.OptionParser, java.lang.reflect.Field, edu.byu.nlp.util.joptparse.Option, java.lang.Object, java.util.List)
	 */
	@Override
	public OptionHandler newHandler(OptionParser p, final ReflectiveVariable var, Option opt, Object optObject, List<String> optStrings) {
		if ( opt.nargs() > 0 )
			throw new IllegalArgumentException("Count option cannot take arguments!");
		
		// TODO : support other types
		if (var.getType() != Integer.TYPE && var.getType() != Integer.class)
			throw new IllegalArgumentException("Only int and Integer are supported by action count");
		
		return new BaseReflectiveVariableOption(opt, optObject, optStrings, Integer.TYPE, 0, var) {

			@Override
			public void performAction(String optName, Object[] optArgs, OptionParser p, ParserState state) {
				try {
				int nextValue = 1;								// Initialize to one in case of null
				Integer curValue = (Integer) var.get(optObject);
				if (curValue != null)
					nextValue = curValue + 1;
					var.set(optObject, nextValue);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			
		};
		
	}

}