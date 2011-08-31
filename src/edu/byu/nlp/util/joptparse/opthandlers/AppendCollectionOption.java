/**
 * Copyright 2011 Brigham Young University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
public class AppendCollectionOption extends BaseReflectiveVariableOption {

	private CollectionFactory<Object> factory;

	public AppendCollectionOption(ReflectiveVariable f, Option opt, Object optObject,
			List<String> optStrings, CollectionFactory<Object> factory, Class<?> cls, int numArgs) {
		super(opt, optObject, optStrings, cls, numArgs, f);
		
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
			if (getNumArgs() == 1)
				coll.add(optArgs[0]);
			if (getNumArgs() > 1)
				coll.add(optArgs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


}
