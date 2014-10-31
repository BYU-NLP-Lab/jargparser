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
package edu.byu.nlp.util.jargparser;

import java.util.List;

import edu.byu.nlp.util.jargparser.annotations.Option;
import edu.byu.nlp.util.jargparser.arghandlers.StoreConstOption;

public class StoreConst implements OptionHandlerFactory {
	@Override
	public OptionHandler newHandler(ArgumentParser p, ReflectiveVariable f, Option opt, Object optObject, List<String> optStrings) {
		if (opt.nargs() > 0)
			throw new IllegalArgumentException("Storing a constant requires 0 arguments");
		
		// Convert the constant to the appropriate type
		Object constant = p.getOptionArgumentParser(optObject.getClass());
		return new StoreConstOption(f, opt, optObject, optStrings, Boolean.class, constant);
	}
	
}