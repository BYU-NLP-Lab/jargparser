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

/**
 * Creates a new handler.
 * This interface is used to add new actions.
 * 
 * @author rah67
 *
 */
public interface OptionHandlerFactory {
	/**
	 * Create a new option handler
	 * 
	 * @param p an instance of the parser
	 * @param f the variable which was annotated with the action that invoked this method
	 * @param opt the option annotation with the action with which this factory is associated
	 * @param optObject the instance of the object being handled by the parser containing the annotations
	 * @param optStrings the strings to be associated with the new option handler
	 * @return a new option handler
	 */
	OptionHandler newHandler(ArgumentParser p, ReflectiveVariable f, Option opt, Object optObject, List<String> optStrings);
}
