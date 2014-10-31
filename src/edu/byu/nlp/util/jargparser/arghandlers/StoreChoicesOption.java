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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import edu.byu.nlp.util.jargparser.ArgumentParser;
import edu.byu.nlp.util.jargparser.ParserState;
import edu.byu.nlp.util.jargparser.ReflectiveVariable;
import edu.byu.nlp.util.jargparser.annotations.Option;

/**
 * @author rah67
 *
 */
public class StoreChoicesOption extends BaseReflectiveVariableOption {

	private Set<Object> choices;
	
	public StoreChoicesOption(ArgumentParser p, ReflectiveVariable f, Option opt, Object optObject, List<String> optStrings, Class<?> cls, int numArgs) {
		super(opt, optObject, optStrings, cls, numArgs, f);
		this.choices = new LinkedHashSet<Object>(Arrays.asList(p.convert(getType(), opt.choices())));
	}

	@Override
	public void performAction(String optName, Object[] optArgs, ArgumentParser p, ParserState state) {
		if (!choices.contains(optArgs[0]))
			throw new IllegalArgumentException("Not a valid choice for option " + optName + "; choose from: " + choices);
		try {
			f.set(optObject, optArgs[0]);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
