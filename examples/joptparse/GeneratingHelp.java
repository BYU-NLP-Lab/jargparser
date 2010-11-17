/**
 * 
 */
package joptparse;

import edu.byu.nlp.util.joptparse.OptionParser;
import edu.byu.nlp.util.joptparse.annotations.Option;
import edu.byu.nlp.util.joptparse.annotations.Options;

/**
 * @author rah67
 *
 */
public class GeneratingHelp {

	@Options( {
			@Option(optStrings = { "-v", "--verbose" },
					action = Option.STORE_TRUE,
					help = "make lots of noise [default]"),
			@Option(optStrings = { "-q", "--quiet" },
					action = Option.STORE_FALSE,
					help = "be vewwy quiet (I'm hunting wabbits)") })
	private boolean verbose = true;

	@Option(optStrings = { "-f","--filename"},
			metavar="FILE",
			help="write output to FILE")
	private String filename;
	
	@Option(optStrings = { "-m","--mode"},
			help = "interaction mode: novice, intermediate, " +
				   "or expert [default: %default]")
	private String mode = "intermediate";
	
	public static void main(String[] args) {
		GeneratingHelp opts = new GeneratingHelp();
		OptionParser parser = new OptionParser(opts);
		parser.printHelp();
	}
			
}
