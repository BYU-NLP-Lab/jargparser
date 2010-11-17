/**
 * 
 */
package edu.byu.nlp.util.joptparse;

import java.util.ArrayList;
import java.util.List;

import edu.byu.nlp.util.Strings;

/**
 * Uses indentation to format help message.
 * 
 * @author rah67
 *
 */
public class IndentedHelpFormatter implements HelpFormatter {

	private static final int TAB_SIZE = 2;
	private static final int MAX_OPT_STRINGS_LENGTH = 20;
	// FIXME : actually worry about the number of columns
	private int numColumns;
	
	public IndentedHelpFormatter() {
		String colsEnv = System.getenv("COLUMNS");
		try {
			this.numColumns = Integer.parseInt(colsEnv);
		} catch (Exception e) {
			this.numColumns = 80;
		}
	}
	

	public String format(OptionParser parser) {
		
		// Print usage
		StringBuilder sb = new StringBuilder();
		sb.append("Usage: ");
		wrapWords(sb, parser.getUsageString(),0,8);
		sb.append("\n\n");
		
		// Print description
		if (parser.getDescription() != null) {
			wrapWords(sb, parser.getDescription(),0);
			sb.append("\n\n");
		}
		
		// Print options
		sb.append("options:\n");
		
		for( OptionHandler opt : parser.getOptions() ) {
			String optionStrings = formatOptionStrings(opt);
			sb.append(Strings.repeat(" ", "", TAB_SIZE));
			wrapWords(sb, optionStrings, TAB_SIZE);
			// TODO : decide if null should be supported
			String helpString = opt.getHelp();
			if (helpString != null) {
				if (optionStrings.length() > MAX_OPT_STRINGS_LENGTH) {
					sb.append("\n");
					sb.append(Strings.repeat(" ", "", MAX_OPT_STRINGS_LENGTH + 2*TAB_SIZE));
				}
				else {
					sb.append(Strings.repeat(" ","", MAX_OPT_STRINGS_LENGTH - optionStrings.length() + TAB_SIZE));
				}
				if (helpString.contains("%default") && opt.hasValue() ) {
					// FIXME : if parsing fails part-way through this might not be the actual default
					// We will need hooks for storing the default
					helpString = helpString.replaceAll("%default", opt.getValue().toString());
				}
				wrapWords(sb, helpString, MAX_OPT_STRINGS_LENGTH + 2*TAB_SIZE);
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}


	/**
	 * PreCondition: "cursor" already at tabStop
	 * 
	 * @param sb
	 * @param helpString
	 * @param tabStop
	 */
	private void wrapWords(StringBuilder sb, String str, int tabStop) {
		wrapWords(sb, str, tabStop, tabStop);
	}

	private void wrapWords(StringBuilder sb, String str, int tabStop, int startCol) {
		String[] words = str.split("\\s+");
		int col = startCol;
		for( String word : words ) {
			int space = 0;
			if (col > tabStop)
				space = 1;
			if (col + word.length() + space > numColumns) {
				if (tabStop + word.length() + space > numColumns) {	// always too big, split word specially
					int beginIndex = 0;
					do {
						int endIndex = Math.min(numColumns - col - space, word.length());
						sb.append(word.substring(beginIndex,endIndex));
						col = tabStop;
						if (endIndex != word.length()) {
							sb.append("\n");
							sb.append(Strings.repeat(" ", "", tabStop));
						} else {
							col += endIndex - beginIndex; 
							break;
						}
						beginIndex = endIndex;
					} while(true);
				} else {
					sb.append("\n");
					sb.append(Strings.repeat(" ", "", tabStop));
					sb.append(word);
					col = tabStop + word.length();
				}
			} else {
				if (space > 0)
					sb.append(" ");
				sb.append(word);
				col += word.length() + 1;
			}
		}
	}

	/**
	 * Return a stringified csv list of option strings paired with the metavar 
	 * 
	 * @param opt
	 * @return
	 */
	protected String formatOptionStrings(OptionHandler opt) {
		List<String> shortOpts = new ArrayList<String>();
		List<String> longOpts = new ArrayList<String>();
		
		if ( opt.getNumArgs() > 0) {
			for( String optString : opt.getOptionStrings() ) {
				// TODO : what is the best way to not have to be always repeating this if statement (okay, well twice)
				if (OptionParser.isLongOpt(optString)) {
					longOpts.add(String.format(getLongFormatSpecifier(),optString,opt.getMetaVar()));
				} else {
					shortOpts.add(String.format(getShortFormatSpecifier(),optString,opt.getMetaVar()));
				}
			}
		} else { // no parameters to the option
			for( String optString : opt.getOptionStrings() ) {
				if (OptionParser.isLongOpt(optString)) {
					longOpts.add(optString);
				} else {
					shortOpts.add(optString);
				}
			}
		}
		
		shortOpts.addAll(longOpts);
		return Strings.join(shortOpts, ", ");
	}

	protected String getShortFormatSpecifier() {
		return "%s%s";
	}

	protected String getLongFormatSpecifier() {
		return "%s=%s";
	}
	
}
