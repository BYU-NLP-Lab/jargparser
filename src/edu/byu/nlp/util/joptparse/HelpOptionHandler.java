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
package edu.byu.nlp.util.joptparse;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rah67
 *
 */
public class HelpOptionHandler implements OptionHandler {

	private String helpString;
	private List<String> optStrings;
	
	public HelpOptionHandler(String helpString, ArrayList<String> optStrings) {
		this.helpString = helpString;
		this.optStrings = optStrings;
	}

	/* (non-Javadoc)
	 * @see edu.byu.nlp.util.joptparse.OptionHandler#getHelp()
	 */
	@Override
	public String getHelp() {
		return helpString;
	}

	/* (non-Javadoc)
	 * @see edu.byu.nlp.util.joptparse.OptionHandler#getMetaVar()
	 */
	@Override
	public String getMetaVar() {
		return null;
	}

	/* (non-Javadoc)
	 * @see edu.byu.nlp.util.joptparse.OptionHandler#getNumArgs()
	 */
	@Override
	public int getNumArgs() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see edu.byu.nlp.util.joptparse.OptionHandler#getOptionStrings()
	 */
	@Override
	public List<String> getOptionStrings() {
		return optStrings;
	}

	/* (non-Javadoc)
	 * @see edu.byu.nlp.util.joptparse.OptionHandler#getType()
	 */
	@Override
	public Class<?> getType() {
		return null;
	}

	/* (non-Javadoc)
	 * @see edu.byu.nlp.util.joptparse.OptionHandler#performAction(java.lang.String, java.lang.Object[], edu.byu.nlp.util.joptparse.OptionParser, edu.byu.nlp.util.joptparse.ParserState)
	 */
	@Override
	public void performAction(String optName, Object[] optArgs, OptionParser p, ParserState state) {
		System.out.println(p.helpString());
		System.exit(0);
	}

	@Override
	public Object getValue() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasValue() {
		return false;
	}

}
