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

import java.util.List;

import edu.byu.nlp.util.jargparser.ArgumentParser;
import edu.byu.nlp.util.jargparser.ParserState;
import edu.byu.nlp.util.jargparser.ReflectiveVariable;
import edu.byu.nlp.util.jargparser.annotations.Option;

/**
 * @author rah67
 *
 */
public class StoreConstOption extends BaseReflectiveVariableOption {

	private final Object constant;
	
	public StoreConstOption(ReflectiveVariable f, Option opt, Object optObject, List<String> optStrings, Class<?> cls, Object constant) {
		super(opt, optObject, optStrings, cls, 0, f);
		this.constant = constant;
	}

	@Override
	public void performAction(String optName, Object[] optArgs, ArgumentParser p, ParserState state) {
		try {
			f.set(optObject, constant);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
