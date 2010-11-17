/**
 * 
 */
package joptparse;

import java.util.Arrays;

import edu.byu.nlp.util.joptparse.OptionParser;
import edu.byu.nlp.util.joptparse.OptionValues;
import edu.byu.nlp.util.joptparse.annotations.Description;
import edu.byu.nlp.util.joptparse.annotations.Option;
import edu.byu.nlp.util.joptparse.annotations.Usage;
import edu.byu.nlp.util.joptparse.annotations.Version;

@Description("A simple example of command line parsing")
@Usage("%prog [-f] [-q]")
@Version("%prog 1.0")
public class StaticExample {

	@Option(optStrings={"-f","--file"},
			help="write report to FILE",
			metavar="FILE")
	private static String filename;
	
	@Option(optStrings={"-q","--quiet"},
			action=Option.STORE_FALSE,
			help="don't print status messages to stdout")
	private static boolean verbose = true;
	
	@Option("not a static variable")
	private int notAStaticVar;
	
	public static void main(String[] args) {
		OptionParser parser = new OptionParser(StaticExample.class);
		OptionValues ov = parser.parseArgs(args);
		System.out.println("Filename: " + filename);
		System.out.println("Verbose: " + verbose);
		System.out.println("Positional Args: " + Arrays.toString(ov.getPositionalArgs()));
	}

}
