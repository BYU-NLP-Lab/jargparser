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

import java.util.List;

import edu.byu.nlp.util.joptparse.OptionHandler;
import edu.byu.nlp.util.joptparse.OptionParser;
import edu.byu.nlp.util.joptparse.ParserState;
import edu.byu.nlp.util.joptparse.ReflectiveVariable;
import edu.byu.nlp.util.joptparse.annotations.Option;

/**
 * @author rah67
 *
 */
public class StoreLongArrayFieldOption extends BaseReflectiveVariableOption
		implements OptionHandler {

	public StoreLongArrayFieldOption(ReflectiveVariable f, Option opt, Object optObject, List<String> optStrings, Class<?> cls, int numArgs) {
		super(opt, optObject, optStrings, cls, numArgs, f);
	}

	@Override
	public void performAction(String optName, Object[] optArgs, OptionParser p, ParserState state) {
		long[] longArgs = new long[optArgs.length];
		for (int i = 0; i < longArgs.length; i++) {
			longArgs[i] = (Long)optArgs[i];
		}
		try {
			f.set(optObject, longArgs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
