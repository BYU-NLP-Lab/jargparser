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

import edu.byu.nlp.util.jargparser.OptionHandler;
import edu.byu.nlp.util.jargparser.ReflectiveVariable;
import edu.byu.nlp.util.jargparser.annotations.Option;


/**
 * @author rah67
 *
 */
public abstract class BaseReflectiveVariableOption extends BaseOption implements OptionHandler {

	protected final ReflectiveVariable f;
	
	public BaseReflectiveVariableOption(Option opt, Object optObject, List<String> optStrings, Class<?> cls, int numArgs, ReflectiveVariable f) {
		super(opt,optObject,optStrings,cls,numArgs);
		this.f = f;
	}

	public Object getValue() {
		try {
			return f.get(optObject);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public boolean hasValue() {
		return f.hasValue();
	}

}
