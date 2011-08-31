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
import edu.byu.nlp.util.joptparse.annotations.Option;

public abstract class BaseOption implements OptionHandler {

	protected final Option opt;
	protected final List<String> optStrings;
	protected final Object optObject;
	protected final Class<?> cls;
	protected final int numArgs;

	public BaseOption(Option opt, Object optObject, List<String> optStrings, Class<?> cls, int numArgs) {
		this.opt = opt;
		this.optObject = optObject;
		this.optStrings = optStrings;
		this.cls = cls;
		this.numArgs = numArgs;		
	}

	@Override
	public String getHelp() {
		if (!opt.value().equals(""))
			return opt.value();
		return opt.help();
	}

	@Override
	public String getMetaVar() {
		if (opt.metavar().equals(""))
			return findBestOptString().toUpperCase();
		return opt.metavar();
	}

	@Override
	public int getNumArgs() {
		return numArgs;
	}

	protected String findBestOptString() {
		String best = null;
		for (String optString : optStrings) {
			if (OptionParser.isLongOpt(optString)) {
				best = OptionParser.stripPrefix(optString);
				break;
			}
		}
		if (best == null) { // No long options, so use first short option
			best = OptionParser.stripPrefix(optStrings.get(0));
		}
		return best;
	}

	@Override
	public List<String> getOptionStrings() {
		return optStrings;
	}

	@Override
	public Class<?> getType() {
		return cls;
	}

}