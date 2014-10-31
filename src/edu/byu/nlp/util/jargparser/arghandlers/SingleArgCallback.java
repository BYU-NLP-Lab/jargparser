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

import java.lang.reflect.Method;
import java.util.List;

import edu.byu.nlp.util.jargparser.ArgumentParser;
import edu.byu.nlp.util.jargparser.ParserState;
import edu.byu.nlp.util.jargparser.annotations.Option;

/**
 * @author rah67
 *
 */
public class SingleArgCallback extends BaseValuelessOption {

	private final Method m;
	
	public SingleArgCallback(Option opt, Object optObject, List<String> optStrings, Method m, int numArgs) {
		super(opt, optObject, optStrings, ArgumentParser.toWrapper(m.getParameterTypes()[0]), numArgs);
		this.m = m;
	}

	@Override
	public void performAction(String optName, Object[] optArgs, ArgumentParser p, ParserState state) {
		boolean b = m.isAccessible();
		m.setAccessible(true);
		try {
			if (getNumArgs() == 1)
				m.invoke(optObject, optArgs[0]);
			else
				m.invoke(optObject, optArgs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			m.setAccessible(b);
		}
	}

}
