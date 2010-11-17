/**
 * 
 */
package edu.byu.nlp.util.joptparse.opthandlers;

import java.util.Collection;
import java.util.List;

import edu.byu.nlp.util.joptparse.CollectionFactory;
import edu.byu.nlp.util.joptparse.OptionParser;
import edu.byu.nlp.util.joptparse.ParserState;
import edu.byu.nlp.util.joptparse.ReflectiveVariable;
import edu.byu.nlp.util.joptparse.annotations.Option;

/**
 * @author rah67
 *
 */
public class AppendConstCollectionOption extends BaseReflectiveVariableOption {

	private CollectionFactory<Object> factory;
	private Object constant;

	public AppendConstCollectionOption(ReflectiveVariable f, Option opt, Object optObject,
			List<String> optStrings, CollectionFactory<Object> factory, Class<?> cls, Object constant) {
		super(opt, optObject, optStrings, cls, 0, f);
		
		this.factory = factory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void performAction(String optName, Object[] optArgs, OptionParser p, ParserState state) {
		try {
			Collection<Object> coll = (Collection<Object>) f.get(optObject);
			if (coll == null) {
				coll = factory.newInstance();
				f.set(optObject, coll);
			}
			coll.add(constant);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


}
