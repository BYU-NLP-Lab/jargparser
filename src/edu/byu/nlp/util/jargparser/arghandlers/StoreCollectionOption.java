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
package edu.byu.nlp.util.jargparser.arghandlers;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import edu.byu.nlp.util.jargparser.CollectionFactory;
import edu.byu.nlp.util.jargparser.ArgumentParser;
import edu.byu.nlp.util.jargparser.ParserState;
import edu.byu.nlp.util.jargparser.ReflectiveVariable;
import edu.byu.nlp.util.jargparser.annotations.Option;

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
	public void performAction(String optName, Object[] optArgs, ArgumentParser p, ParserState state) {
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
