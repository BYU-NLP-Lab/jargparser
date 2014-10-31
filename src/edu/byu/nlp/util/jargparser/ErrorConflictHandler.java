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
 * A conflict handler that throws an error on any conflict.
 * 
 * @see ResolveConflictHandler 
 * 
 * @author rah67
 *
 */
public class ErrorConflictHandler implements ConflictHandler {

	/* (non-Javadoc)
	 * @see edu.byu.nlp.util.optparse.ConflictHandler#handleConflict(edu.byu.nlp.util.optparse.OptionParser, java.lang.String, edu.byu.nlp.util.optparse.Option, edu.byu.nlp.util.optparse.Option)
	 */
	@Override
	public void handleConflict(ArgumentParser parser, String optString, OptionHandler newOpt, OptionHandler oldOpt) {
		throw new OptionConflictError(newOpt, parser);
	}

}
