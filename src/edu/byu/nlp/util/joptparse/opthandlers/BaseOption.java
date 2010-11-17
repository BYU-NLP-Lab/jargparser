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