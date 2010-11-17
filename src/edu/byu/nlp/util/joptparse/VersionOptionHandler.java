/**
 * 
 */
package edu.byu.nlp.util.joptparse;

import java.util.List;

/**
 * @author rah67
 *
 */
public class VersionOptionHandler implements OptionHandler {

	private final String helpString;
	private final String versionString;
	private final List<String> optStrings;
	
	public VersionOptionHandler(String versionString, String helpString, List<String> optStrings) {
		this.helpString = helpString;
		this.versionString = versionString;
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
		System.out.println(versionString);
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
