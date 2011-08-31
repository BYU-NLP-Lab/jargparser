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