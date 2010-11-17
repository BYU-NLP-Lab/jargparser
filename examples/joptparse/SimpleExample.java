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
public class SimpleExample {

	private static class SimpleOptions {
		@Option(optStrings={"-f","--file"},
				help="write report to FILE",
				metavar="FILE")
		String filename;
		
		@Option(optStrings={"-q","--quiet"},
				action=Option.STORE_FALSE,
				help="don't print status messages to stdout")
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
