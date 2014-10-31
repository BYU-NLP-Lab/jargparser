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
package jargparser;

import java.util.Arrays;

import edu.byu.nlp.util.jargparser.ArgumentParser;
import edu.byu.nlp.util.jargparser.ArgumentValues;
import edu.byu.nlp.util.jargparser.annotations.Description;
import edu.byu.nlp.util.jargparser.annotations.Option;
import edu.byu.nlp.util.jargparser.annotations.Usage;
import edu.byu.nlp.util.jargparser.annotations.Version;

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
		ArgumentParser parser = new ArgumentParser(opts);
		ArgumentValues ov = parser.parseArgs(args);
		System.out.println("Filename: " + opts.filename);
		System.out.println("Verbose: " + opts.verbose);
		System.out.println("Positional Args: " + Arrays.toString(ov.getPositionalArgs()));
	}

}
