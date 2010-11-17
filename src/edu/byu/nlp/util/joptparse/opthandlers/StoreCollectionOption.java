/**
 * 
 */
package edu.byu.nlp.util.joptparse.opthandlers;

import java.util.Arrays;
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
public class StoreCollectionOption extends BaseReflectiveVariableOption {

	private final CollectionFactory<Object> factory;

	public StoreCollectionOption(ReflectiveVariable f, Option opt, Object optObject,
			List<String> optStrings, CollectionFactory<Object> factory, Class<?> cls, int numArgs) {
		super(opt, optObject, optStrings, cls, numArgs, f);
		
		this.factory = factory;
	}

	@SuppressWarnings("unchecked")	// necessary for reflective get call since we cast to Collection<Object>
	@Override
	public void performAction(String optName, Object[] optArgs, OptionParser p, ParserState state) {
		Collection<Object> coll;
		try {
			coll = (Collection<Object>) f.get(optObject);
			if (coll == null) {
				coll = factory.newInstance();
			}
			coll.addAll(Arrays.asList(optArgs));
			f.set(optObject, coll);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


}
