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
