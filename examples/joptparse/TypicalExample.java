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

public class TypicalExample {

	@Description("A simple example of command line parsing")
	@Usage("%prog [-f] [-q]")
	@Version("%prog 1.0")
	private static class SimpleOptions {
		@Option(help="write report to FILENAME")
		String filename;
		
		@Option("print status messages to stdout")
		boolean verbose = true;
	}
	
	public static void main(String[] args) {
		SimpleOptions opts = new SimpleOptions();
		OptionParser parser = new OptionParser(opts);
		OptionValues ov = parser.parseArgs(args);
		System.out.println("Filename: " + opts.filename);
		System.out.println("Verbose: " + opts.verbose);
		System.out.println("Positional Args: " + Arrays.toString(ov.getPositionalArgs()));
	}

}
