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
		ArgumentParser parser = new ArgumentParser(StaticExample.class);
		ArgumentValues ov = parser.parseArgs(args);
		System.out.println("Filename: " + filename);
		System.out.println("Verbose: " + verbose);
		System.out.println("Positional Args: " + Arrays.toString(ov.getPositionalArgs()));
	}

}
