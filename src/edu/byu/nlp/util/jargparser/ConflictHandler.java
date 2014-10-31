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

/**
 * Interface used to allow for customizable conflict handling.
 * 
 * @author rah67
 *
 */
public interface ConflictHandler {
	/**
	 * Handle a conflict
	 * 
	 * @param parser	the option parser
	 * @param optString	the string causing the conflict
	 * @param newOpt	the option to be added
	 * @param oldOpt	the option that already existed
	 */
	void handleConflict(ArgumentParser parser, String optString, OptionHandler newOpt, OptionHandler oldOpt);
}
