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
 * Formats help message for the options contained in the parser.
 * 
 * @author rah67
 *
 */
public interface HelpFormatter {

	/**
	 * Create the help message
	 * 
	 * @param parser the instance from which the help message is built
	 * @return the help message
	 */
	String format(ArgumentParser parser);

}
