/**
 * 
 */
package edu.byu.nlp.util.joptparse.opthandlers;

import java.util.List;

import edu.byu.nlp.util.joptparse.OptionHandler;
import edu.byu.nlp.util.joptparse.ReflectiveVariable;
import edu.byu.nlp.util.joptparse.annotations.Option;


/**
 * @author rah67
 *
 */
public abstract class BaseReflectiveVariableOption extends BaseOption implements OptionHandler {

	protected final ReflectiveVariable f;
	
	public BaseReflectiveVariableOption(Option opt, Object optObject, List<String> optStrings, Class<?> cls, int numArgs, ReflectiveVariable f) {
		super(opt,optObject,optStrings,cls,numArgs);
		this.f = f;
	}

	public Object getValue() {
		try {
			return f.get(optObject);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public boolean hasValue() {
		return f.hasValue();
	}

}
