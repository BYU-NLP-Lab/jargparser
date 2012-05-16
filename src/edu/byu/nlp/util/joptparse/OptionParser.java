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
package edu.byu.nlp.util.joptparse;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;

import edu.byu.nlp.util.Strings;
import edu.byu.nlp.util.TrieMap;
import edu.byu.nlp.util.joptparse.annotations.Description;
import edu.byu.nlp.util.joptparse.annotations.Option;
import edu.byu.nlp.util.joptparse.annotations.Options;
import edu.byu.nlp.util.joptparse.annotations.ParserOptions;
import edu.byu.nlp.util.joptparse.annotations.Usage;
import edu.byu.nlp.util.joptparse.annotations.Version;
import edu.byu.nlp.util.joptparse.opthandlers.SingleArgCallback;
import edu.byu.nlp.util.joptparse.opthandlers.ZeroArgCallback;

/**
 * A Java command-line parser inspired by Python's optparse package, but implemented using Annotations.
 * This parser attempts to adhere to the GNU standard for command-line options.
 * Typically, a "driver" class with a main method will use this package to populate instance variables
 * from options on the command-line that will determine runtime behavior. 
 * 
 * <p>A typical use may look like this:</p>
 * 
 * <p><blockquote><pre>
 * 
 * public class SimpleExample {
 * 
 *  @Description("A simple example of command line parsing")
 *  @Usage("%prog [-f] [-q]")
 *  @Version("%prog 1.0")
 * 	private static class SimpleOptions {
 * 		@Option("write report to FILENAME")
 * 		String filename;
 * 		
 * 		@Option("print status messages to stdout")
 * 		boolean verbose = true;
 * 	}
 * 	
 * 	public static void main(String[] args) {
 * 		SimpleOptions opts = new SimpleOptions();
 * 		OptionParser parser = new OptionParser(opts);
 * 		OptionValues ov = parser.parseArgs(args);
 * 		System.out.println("Filename: " + opts.filename);
 * 		System.out.println("Verbose: " + opts.verbose);
 * 		System.out.println("Positional Args: " + Arrays.toString(ov.getPositionalArgs()));
 * 	}
 * 
 * }
 * 
 * </pre></blockquote></p>
 * 
 * This example can be run in the expected ways:
 * <p><code>java SimpleExample --filename=output</code></p>
 * <p><code>java SimpleExample --filename output</code></p>
 * <p><code>java SimpleExample --verbose</code></p>
 * <p><code>java SimpleExample --verbose --filename=output</code></p>
 * <p><code>java SimpleExample --filename output --verbose</code></p>
 * etc. In addition, the program can be run with "--help" or "--h" to diplay
 * the following automatically generated message:
 * <p><blockquote><pre>
 * Usage: joptparse.TypicalExample [-f] [-q]
 * 
 * A simple example of command line parsing
 * 
 * options:
 *   -h, --help            show this help message and exit
 *   --version             show program's version number and exit
 *   --filename=FILENAME   write report to FILENAME
 *   --verbose             print status messages to stdout
 * </pre></blockquote></p>
 * 
 * <p>
 * In simple cases, the <code>@Option</code> annotation is added to
 * each variable that is to become an option. It is recommended
 * that the help message be added to the annotation as shown above,
 * although it is optional. The "option string" (the string
 * used to specify the option on the command line) is automatically
 * inferred from the variable's name. If the variable name is only
 * one character a "short option" is created using the exact case
 * of the variable name. For longer names, by default, the camel
 * case is removed in order to produce a GNU-like option name,
 * e.g. AnOpt becomes --an-opt. Also note that getters and setters
 * can also annotated to be Options.
 * </p>
 * 
 * <p>
 * This class is responsible for converting all arguments on the 
 * command-line to the type specified by the variable carrying
 * the <code>@Option</code> annotation. It currently
 * works with all primitives and their wrappers as well as
 * <code>String</code>s and <code>File</code>s. When more than one
 * option is specified via the <code>nargs</code> attribute,
 * Collections, Sets, Lists, and arrays of the valid types work.
 * It is possible to extend the functionality of the parser
 * to work with virtually any type by implementing a
 * @link{OptionArgumentParser} and calling
 * <code>putOptionArgumentParser</code> on this class. 
 * </p>
 * 
 * <p>
 * Although storing values in variables is the most common case,
 * several other actions are possible, including storing true 
 * or false to booleans, appending values to a set, list, or collection,
 * counting occurrences of an option string, and storing or
 * appending constants. To specify a specific action, set the
 * <code>action</code> attribute on the <code>@Option</code>
 * annotation. Some useful (but not necessary) constants
 * are found in the <code>@Option</code> interface.
 * Additionally, this class provides hooks to
 * add custom actions or override the default behavior via
 * calls to @link{#putOptionHandlerFactory}.
 * </p>
 * 
 * <p>
 * If additional option strings are desired, they can be specified
 * using the <code>optStrings</code> attribute of the <code>@Option</code>
 * annotation. Note, however, that if any options are manually specified
 * in this fashion, no options are inferred from the variable name.
 * Also note that, due to the way that Java annotations work,
 * if one attribute of an annotation is manually specified, then
 * all attributes must be named. This means that we are required
 * to explicitly write out the "help=" when specifying the help string. 
 * For example:
 * <p><pre><blockquote>
 * 		@Option(optStrings={"-f","--file"},
 * 				help="write report to FILE",
 * 				metavar="FILE")
 * 		String filename;
 * 		
 * 		@Option(optStrings={"-q","--quiet"},
 * 				action=Option.STORE_FALSE,
 * 				help="don't print status messages to stdout")
 * 		boolean verbose = true;
 * </blockquote></pre></p>
 * </p>
 * 
 * <p>
 * It is possible for a variable to carry multiple options
 * using the <code>@Options</code> annotation, like so:
 * </p>
 * <p><pre><blockquote>
 * 	@Options( {
 * 			@Option(optStrings = { "-v", "--verbose" },
 * 					action = Option.STORE_TRUE,
 * 					help = "enable verbose message output"),
 * 			@Option(optStrings = { "-q", "--quiet" },
 * 					action = Option.STORE_FALSE,
 * 					help = "only output on errors") })
 * 	private boolean verbose = true;
 * </blockquote></pre></p>
 * 
 * <p>
 * Callbacks are possible by annotating methods with the <code>@Option</code>
 * annotation. If the method takes zero arguments, it is invoked with no
 * parameters. If it has one argument, type conversion is done as for regular
 * arguments and the converted argument is passed as an argument. Arbitrary
 * callbacks should have the method signature:
 * <code>(String optName, Object[] optArgs, OptionParser p, ParserState state)</code>
 * </p>
 * 
 * <p>
 * When compare with Python's optparse, a few things are still missing.
 * First, option groups are not yet supported. In addition, the order
 * that the options are printed in the help message is not guaranteed
 * to be the order they are declared in the class (though this is
 * usually the case). Also, the <code>TitledHelpFormatter</code> is
 * not yet available. Finally, defaults are treated differently, namely,
 * there is no explicit mechanism for setting defaults, other than
 * initializing the value before parsing the objects through the
 * regular means. 
 * </p>
 * 
 * @author rah67
 *
 */
/*
 * notes: conflicting options throw an exception, but -v is not the same as --v
 * +v throws OptionError invalid short option string (so do things without -). Detected as long opt BY LENGTH
 * prints help and dies if you use an option that isn't really an option
 */
public class OptionParser {

	public static final String DEFAULT_USAGE = "%prog [options]";
	public static final boolean DEFAULT_ALLOW_INTERSPERSED_ARGS = true;
	public static final boolean DEFAULT_ADD_HELP_OPTION = true;
	public static final boolean DEFAULT_CAMEL_CASE_ALLOWED = false;

	// The reason for maintaining separate lists has to do with the behavior of collision detection
	private Map<Character,OptionHandler> shortOpts;
	private TrieMap<OptionHandler> longOpts;
	
	List<OptionHandler> mainGroup;
	
	private String usage;
	private String version;
	private ConflictHandler conflictHandler;
	// TODO : add option groups (main group may be an instance of OptionGroup)
	private String description;
	private HelpFormatter formatter;
	private String prog;
	private boolean allowInterspersedArgs;
	private int numPos;
	private boolean camelCaseAllowed;
	private Map<String,OptionHandlerFactory> actionMap;
	
	/**
	 * Instantiates a new instance of the option parser with no version and the default help options.
	 */
	public OptionParser() {
		this(null, DEFAULT_ADD_HELP_OPTION);
	}
	
	/**
	 * Instantiates a new instance of the option parser with the specified version string and the default help options.
	 * Adds a <code>--version</code> string to the parser.
	 * 
	 * @param version the version string
	 */
	public OptionParser(String version) {
		this(version, DEFAULT_ADD_HELP_OPTION);
	}
	
	/**
	 * Instantiates a new instance of the option parser with no version string and the specified help option.
	 * 
	 * @param addHelpOption whether or not to add the help option
	 */
	public OptionParser(boolean addHelpOption) {
		this(null, addHelpOption);
	}

	/**
	 * Instantiates a new instance of the option parser using the provided object.
	 * If the object's class is annotated with <code>Description</code>, <code>Usage</code>,
	 * or <code>Version</code>, <code>Program</code>, the appropriate information is
	 * automatically set. If the class is annotated with <code>ParserOptions</code>,
	 * additional options are set. All variables and methods annotated with
	 * <code>Option</code> are automatically added as options.
	 * 
	 * @param optObject the object to use for setting options
	 * 
	 * @see #addOptions(Object)
	 * @see #setDescription(String)
	 * @see #setUsage(String)
	 * @see #setProg(String)
	 * @see #setAllowInterspersedArgs(boolean)
	 * @see #setCamelCaseAllowed(boolean)
	 */
	public OptionParser(Object optObject) {
		init(optObject.getClass(), optObject);
	}

	/**
	 * Instantiates a new instance of the option parser using the static
	 * variables and methods of the provided class.
	 * If the class is annotated with <code>Description</code>, <code>Usage</code>,
	 * or <code>Version</code>, <code>Program</code>, the appropriate information is
	 * automatically set. If the object is annotated with <code>ParserOptions</code>,
	 * additional options are set. All variables and methods annotated with
	 * <code>Option</code> are automatically added as options.
	 * 
	 * @param optObject the object to use for setting options
	 * 
	 * @see #addOptions(Object)
	 * @see #setDescription(String)
	 * @see #setUsage(String)
	 * @see #setProg(String)
	 * @see #setAllowInterspersedArgs(boolean)
	 * @see #setCamelCaseAllowed(boolean)
	 */
	public OptionParser(Class<?> cls) {
		init(cls, null);
	}
	
	private void init(Class<?> cls, Object optObject) {
		Description desc = cls.getAnnotation(Description.class);
		Usage usage = cls.getAnnotation(Usage.class);
		Version version = cls.getAnnotation(Version.class);
		ParserOptions parserOpts = cls.getAnnotation(ParserOptions.class);
		
		init(version == null ? null : version.value(), parserOpts == null ? DEFAULT_ADD_HELP_OPTION : parserOpts.addHelpOption());
		if (parserOpts != null) {
			setAllowInterspersedArgs(parserOpts.allowInterspersedArgs());
			setCamelCaseAllowed(parserOpts.allowCamelCase());
		}
		
		if (desc != null)
			setDescription(desc.value());
		if (usage != null)
			setUsage(usage.value());
		
		// Add the options
		addOptions(cls, optObject);
	}
	
	/**
	 * Instantiates a new instance of the option parser with the specified version string and the specified help options.
	 * Adds a <code>--version</code> string to the parser.
	 * 
	 * @param version the version string
	 * @param addHelpOption if true, a help option is automatically added
	 */
	public OptionParser(String version, boolean addHelpOption) {
		init(version, addHelpOption);
	}

	private void init(String version, boolean addHelpOption) {
		this.shortOpts = new HashMap<Character, OptionHandler>();
		this.longOpts = new TrieMap<OptionHandler>();
		this.mainGroup = new ArrayList<OptionHandler>();
		this.conflictHandler = new ErrorConflictHandler();
		initActions();
		this.numPos = -1;
		this.allowInterspersedArgs = DEFAULT_ALLOW_INTERSPERSED_ARGS;
		this.camelCaseAllowed = DEFAULT_CAMEL_CASE_ALLOWED;
		
		if ( addHelpOption ) {
			// add help option
			addHelpOption("show this help message and exit","-h","--help");
		}
		
		// Because we don't have a remove, we can't actually do this later;
		// Furthermore, it appears that optparse doesn't allow you to set this later, either.
		if ( version != null) {
			// add version option
			version = version.replaceAll("%prog", getProg());
			addVersionOption(version, "show program's version number and exit", "--version");
		}
	}

	/**
	 * Initializes the map of possible actions
	 */
	private void initActions() {
		this.actionMap = new HashMap<String,OptionHandlerFactory>();
		actionMap.put(Option.STORE,new Store());
		actionMap.put(Option.STORE_CONST,new StoreConst());
		actionMap.put(Option.STORE_TRUE,new StoreBoolean.True());
		actionMap.put(Option.STORE_FALSE,new StoreBoolean.False());
		actionMap.put(Option.APPEND_CONST,new AppendConst());
		actionMap.put(Option.APPEND,new Append());
		actionMap.put(Option.COUNT,new Count());
	}

	/**
	 * Adds a version option to the parser. Currently, this is only possible
	 * through the constructor, in compliance to Python's optparse.
	 * 
	 * @param versionString	the string representing the version of the program
	 * @param helpString	the string printed in the help message
	 * @param optStrings	the option strings that are used to cause the version message to print
	 */
	private void addVersionOption(String versionString, String helpString, String... optStrings) {
		addOptionHandler(new VersionOptionHandler(versionString, helpString, new ArrayList<String>(Arrays.asList(optStrings))));
	}
	
	/**
	 * Adds a help option to the parser.
	 * 
	 * @param helpString	the string printed in the help message
	 * @param optStrings	the option strings that when specified cause the help message to print
	 */
	public void addHelpOption(String helpString, String... optStrings) {
		addOptionHandler(new HelpOptionHandler(helpString, new ArrayList<String>(Arrays.asList(optStrings))));
	}

	/**
	 * Parses the specified arguments.
	 * 
	 * @param args the arguments to parse
	 * @return the results of parsing
	 * 
	 * @see OptionValues
	 */
	public OptionValues parseArgs(final String... args) {
		final ParserState s = new ParserState(this, args);
		
		while(!s.remainingArgs.isEmpty()) {
			String curArg = s.remainingArgs.getFirst();
			if (isOpt(curArg)) { // this is an option
				try {
					if (curArg.equals("-")) { // bare -
						// Do not discard the argument
						break;									// Done!
					} else if (curArg.equals("--")) { // bare --
						s.remainingArgs.removeFirst();			// should be discarded
						break;									// Done!
					} else if (isLongOpt(curArg)) { // long option
						parseLongOption(s);
					} else { // short option(s)
						parseShortOptions(s);
					}
				} catch (Exception e) {
					error(": error: option " + curArg + ": " + e.getMessage());
					// not reached, we exit before this
				}
			} else if (allowInterspersedArgs){ // else, a positional arg
				s.posArgs.add(s.remainingArgs.removeFirst());
			} else {							// first non-option since interspersed arguments aren't allowed
				break;
			}
		}

		// Add the remaining arguments (if there are any, e.g. reached "-" or "--" or interspersed args disallowed
		while (!s.remainingArgs.isEmpty()) {
			s.posArgs.add(s.remainingArgs.removeFirst());
		}
		
		// If a specific number of positional arguments are expected, ensure it is correct
		if (numPos >= 0 && s.posArgs.size() != numPos) {
			error("Incorrect number of positional arguments");
		}
		
		return new OptionValues(this, s.posArgs);
	}
	
	/**
	 * Parses a single short options, which may contain more than one option and possible arguments.
	 * 
	 * @param s the state of the parser
	 */
	private void parseShortOptions(ParserState s) {
		// Iterate over each short option
		String curArg = s.remainingArgs.removeFirst();
		for(int charIndex = 1; charIndex < curArg.length(); charIndex++) {
			char optName = curArg.charAt(charIndex);
			OptionHandler opt = shortOpts.get(optName);
			if (opt == null) {
				error("The option " + optName + " does not exist.");
			}
			
			String[] optArgs = new String[opt.getNumArgs()];
			Object[] convertedArgs = null;
			if (opt.getNumArgs() > 0) {
				if ( charIndex + 1 < curArg.length() ) { // the argument is the rest of the string
					// Push the remainder of the string as an option
					s.remainingArgs.addFirst(curArg.substring(charIndex + 1));
					// Ensures the loop over contiguous options terminates
					charIndex = curArg.length();
				}
				getArgs(s, "-" + optName, optArgs);
				convertedArgs = convert(opt.getType(), optArgs);
			}
			
			opt.performAction(Character.toString(optName), convertedArgs, this, s);
		}
	}

	/**
	 * Fills optArgs with correct number of arguments from the command-line
	 * 
	 * @param s			the state of the parser
	 * @param optName	the name of the option, used for errors
	 * @param optArgs	the array to fill
	 */
	private void getArgs(ParserState s, String optName, String[] optArgs) {
		// Store the arguments
		for(int arg = 0; arg < optArgs.length; arg++) {
			if ( s.remainingArgs.isEmpty() )
				error("Expecting argument for option " + optName);
			optArgs[arg] = s.remainingArgs.removeFirst();
		}
	}

	/**
	 * Converts the given array of String to an array of the specified type
	 * using the <code>OptionArgumentParser</code> stored in the parser.
	 * If the parser doesn't exist, a null is returned.
	 * 
	 * @param type	the type the strings are to be converted to
	 * @param args	the strings to convert
	 * @return null if there is no parser for type, an array of converted objects, otherwise
	 */
	public Object[] convert(Class<?> type, String[] args) {
		OptionArgumentParser<?> oap = getOptionArgumentParser(type);
		if (oap == null)
			return null;
		Object[] parsed = (Object[]) Array.newInstance(type, args.length);
		for( int i = 0; i < args.length; i++ ) {
			parsed[i] = oap.parse(args[i]);
		}
		return parsed;
	}

	/**
	 * Parse a long option, including any arguments.
	 * 
	 * @param s the parser's state
	 */
	private void parseLongOption(ParserState s) {
		
		// Remove the prefix
		String optString = s.remainingArgs.removeFirst();
		String optName = OptionParser.stripPrefix(optString);

		// Search for an equals sign
		int pos = optName.indexOf('=');
		if (pos > -1) {			// there was an equals, so split:
			// Otherwise, push the argument (the last half of the string excluding the =)back on the deque 
			s.remainingArgs.addFirst(optName.substring(pos+1));
			
			// The option string is the first half
			optName = optName.substring(0,pos);
		}
		
		Entry<CharSequence, OptionHandler> optPair = longOpts.getEntryFromUnambiguousPrefix(optName);
		if (optPair == null)
			error("The option " + optName + " doesn't exist.");

		// TODO : consider forcing TrieMap to be String
		String fullName = optPair.getKey().toString();
		OptionHandler opt = optPair.getValue();
		
		String[] optArgs = new String[opt.getNumArgs()];
		Object[] convertedArgs = null;
		if (opt.getNumArgs() > 0) { // requires an argument
			getArgs(s, optString, optArgs);
			convertedArgs = convert(opt.getType(), optArgs);
		} else if ( pos > -1 ) { // no argument, yet one is provided via --opt=arg syntax
			error("--" + fullName + " option does not take a value");
		}
		opt.performAction(fullName, convertedArgs, this, s);
	}
	
	private void error(String msg) {
		System.err.println(msg);
		System.out.println(helpString());
		System.exit(-1);
	}
	
	public String helpString() {
		return getHelpFormatter().format(this);
	}

	/**
	 * Overwrites current usage string and if you set the usage string after calling this method,
	 * this default usage string set by this method is overwritten.
	 * This method will typically get called instead of setUsage. For more flexibility call setUsage,
	 * although you may still want to call this first to enable checking for the correct number of
	 * positional arguments.
	 * 
	 * <p>Experimental</p>
	 * 
	 * @param desc
	 * @param oneToOne
	 * @param names
	 */
	public void setPositionalArgs(String desc, boolean oneToOne, String... names) {
		this.numPos = (oneToOne) ? names.length : -1;
		setUsage(DEFAULT_USAGE + " " + Strings.join(names, " ") + "\n" + desc);
	}
	
	// TODO : more fully document
	/**
	 * Inspects the provided object for <code>Option</code>s and adds them.
	 * 
	 * @param optObject the object to inspect for options
	 */
	public void addOptions(Object optObject) {
		addOptions(optObject.getClass(), optObject);
	}

	// TODO : more fully document
	/**
	 * Inspects the class for <code>Option</code>s and adds them.
	 * 
	 * @param optObject the object to inspect for options
	 */
	public void addOptions(Class<?> cls) {
		addOptions(cls, null);
	}
	
	private void addOptions(Class<?> cls, Object optObject) {

		// Search fields for options
		for ( Field f : cls.getDeclaredFields()) {
			Options opts = f.getAnnotation(Options.class);
			if ( opts != null && ( optObject != null || Modifier.isStatic(f.getModifiers()) ) ) { 
				for( Option opt : opts.value() ) {
					addOpt(f, opt, optObject);
				}
			}
			
			Option opt = f.getAnnotation(Option.class);
			if ( opt != null && ( optObject != null || Modifier.isStatic(f.getModifiers()) ) ) { 
				addOpt(f, opt, optObject);
			}
		}

		// Search methods for options
		for ( Method m : cls.getDeclaredMethods()) {
			Options opts = m.getAnnotation(Options.class);
			if ( opts != null && ( optObject != null || Modifier.isStatic(m.getModifiers()) ) ) { 
				for( Option opt : opts.value() ) {
					addOpt(m, opt, optObject);
				}
			}
			
			Option opt = m.getAnnotation(Option.class);
			if ( opt != null && ( optObject != null || Modifier.isStatic(m.getModifiers()) ) ) { 
				addOpt(m, opt, optObject);
			}
		}
	}

	private static Class<?>[] actionSignature = new Class<?>[]{String.class, Object[].class, OptionParser.class, ParserState.class};
	
	/**
	 * Determines what type of option this method should be and adds the appropriate action
	 * 
	 * @param m			the method under consideration
	 * @param opt		the actual annotation
	 * @param optObject	the object in which things will be stored
	 */
	private void addOpt(Method m, Option opt, Object optObject) {
		
		String name = m.getName();
		String baseName = basename(name);
		if ( m.getParameterTypes().length == 0 ) {				// getter or zero-argument callback
			if ( isGetter(m) ) {								// is it a getter?
				// Make sure there is a setter
				Method setter;
				try {
					setter = getSetter(optObject, m, baseName);
					if ( setter == null )
						throw new IllegalArgumentException("Getter doesn't have corresponding setter");
				} catch (SecurityException e) {
					throw new RuntimeException(e);
				}
				
				if (opt.nargs() != -1 && opt.nargs() != 1)
					throw new IllegalArgumentException("Property requires exactly one argument");
				
				addOpt(new Property(m, setter), opt, optObject);
				return;											// Not necessary; adds clarity, avoids future bugs
			} else {											// It must be a simple (zero-argument) callback
				if ( opt.nargs() > 0)
					throw new IllegalArgumentException("Zero argument callback requires no arguments");
				addOptionHandler(new ZeroArgCallback(opt, optObject, getOptStrings(opt, m.getName()), m));
				return;											// Not necessary; adds clarity, avoids future bugs
			}
				
		} else if (  m.getParameterTypes().length == 1 ) {		// Setter-style method (Simple callback,
		
			if ( isSetter(m) ) {
				Method getter;
				try {
					getter = getGetter(optObject, m, baseName);
				} catch (Exception e) {
					throw new RuntimeException(e);
				} 
				
				if ( getter != null) {
					if (opt.nargs() != -1 && opt.nargs() != 1)
						throw new IllegalArgumentException("Property requires exactly one argument");
					addOpt(new Property(getter, m), opt, optObject);
					return;											// Not necessary; adds clarity, avoids future bugs
				}
			}
			
			// We are here either because this wasn't a setter or because there wasn't a getter	
			if ( opt.nargs() > 1) {
				throw new UnsupportedOperationException("Currently, single argument callbacks can only take one option argument");
			} else {
				addOptionHandler(new SingleArgCallback(opt, optObject, getOptStrings(opt, m.getName()), m, opt.nargs() == -1 ? 1 : opt.nargs()));				
				return;											// Not necessary; adds clarity, avoids future bugs
			}
		} else if ( Arrays.equals(actionSignature,m.getParameterTypes()) ) { // Callback functions

			if (opt.nargs() > 0)
				throw new IllegalArgumentException("Callback methods cannot directly take arguments");
			addOptionHandler(new CallbackOptionHandler(m,opt,optObject,getOptStrings(opt, m.getName())));
			return;											// Not necessary; adds clarity, avoids future bugs
		} else
			throw new IllegalArgumentException("Method must be a getter, one-argument method, or callback to be used as an option");
	}

	// TODO : move the following few methods to a utility class
	
	/**
	 * Determines if the provided method is a setter
	 */
	private Method getSetter(Object optObject, Method getter, String baseName) throws SecurityException {
		Method setter = getMethod(optObject.getClass(), "set"+baseName, new Class<?>[]{getter.getReturnType()});
		if (setter == null || setter.getReturnType() != Void.TYPE)
			return null;
		return setter;
	}

	/**
	 * Convenience method for getting a method from a class the returns null instead of throwing an exception
	 */
	private Method getMethod(Class<?> cls, String name, Class<?>[] parameterTypes) {
		try {
			return cls.getMethod(name, parameterTypes);
		} catch (NoSuchMethodException e) {
			return null;
		}
	}
	
	/**
	 * Searches the class of the provided object for the getter that corresponds to the specified setter.
	 */
	private Method getGetter(Object optObject, Method setter, String baseName) throws SecurityException {
		if (setter.getParameterTypes().length != 1)
			return null;
		Class<?> type = setter.getParameterTypes()[0];
		Method getter = getMethod(optObject.getClass(), "get"+baseName, null);
		if (getter == null && (type == Boolean.TYPE || type == Boolean.class) ) {
			getter = getMethod(optObject.getClass(), "is"+baseName, null);
			if (getter == null)
				getter = getMethod(optObject.getClass(), "has"+baseName, null);
		}
		if (getter == null)
			return null;
		
		if (getter.getReturnType() != setter.getParameterTypes()[0])
			return null;
		return getter;
	}

	/**
	 * Grabs the basename of a property (getter/setter)
	 */
	private String basename(String name) {
		if (name.startsWith("get") || name.startsWith("set") || name.startsWith("has"))
			return name.substring(3);
		if (name.startsWith("is"))
			return name.substring(2);
		return name;
	}

	/**
	 * Determines if a method is a getter
	 */
	private boolean isGetter(Method m) {
		if (m.getReturnType() == Void.TYPE)
			return false;
		if (m.getParameterTypes().length > 0)
			return false;
		String name = m.getName();
		if (name.startsWith("get") &&
				name.length() > 3 && 
				Character.isUpperCase(name.charAt(3))) {
			return true;
		}
		if (name.startsWith("is") &&
				name.length() > 2 && 
				Character.isUpperCase(name.charAt(2))) {
			return true;
		}
		if (name.startsWith("has") &&
				name.length() > 3 && 
				Character.isUpperCase(name.charAt(3))) {
			return true;
		}
		return false;
	}

	/**
	 * Determines if the specified method is a setter
	 */
	private boolean isSetter(Method m) {
		if (m.getReturnType() != Void.TYPE)
			return false;
		if (m.getParameterTypes().length != 1)
			return false;
		String name = m.getName();
		if (name.startsWith("set") &&
				name.length() > 3 && 
				Character.isUpperCase(name.charAt(3))) {
			return true;
		}
		return false;
	}

	/**
	 * Adds an instance variable as an option
	 */
	private void addOpt(Field f, Option opt, Object optObject) {
		InstanceVariable var = new InstanceVariable(f);
		
		addOpt(var, opt, optObject);
	}

	/**
	 * Adds an instance variable or property as an option.
	 */
	private void addOpt(ReflectiveVariable var, Option opt, Object optObject) {

		List<String> optStrings = getOptStrings(opt, var.getName());

		String action = opt.action();
		
		// type-dependent defaults
		if (action.equals("")) {
			if (var.getType() == Boolean.TYPE || var.getType() == Boolean.class ) {
				action = Option.STORE_TRUE;
			} else {
				action = Option.STORE;
			}
		}
		
		OptionHandlerFactory handlerFactory = actionMap.get(action);
		if (handlerFactory == null)
			throw new IllegalArgumentException("The action " + action + ", specified for option strings " + optStrings + ", is not registered");

		OptionHandler handler = handlerFactory.newHandler(this, var, opt, optObject, optStrings);
		addOptionHandler(handler);
	}

	/**
	 * Grabs the option strings from the annotation, if specified,
	 * or creates on based on the name of the field/method.
	 */
	private List<String> getOptStrings(Option opt, String name) {
		ArrayList<String> optStrings = new ArrayList<String>();
		for( String optString : opt.optStrings() ) {
			if (optString.equals("")) {
				optString = toOptionString(name);
			}
			optStrings.add(optString);			
		}
		return optStrings;
	}

	/**
	 * Convert given field/method name to option string.
	 * Single character names are treated as short options.
	 * Converted to camel case if camel case is allowed.
	 * 
	 * @see #isCamelCaseAllowed()
	 */
	String toOptionString(String name) {
		String optString;
		if (name.length() == 1) {
			optString = "-" + name;
		} else {
			if (!camelCaseAllowed) {
				name = removeCamelCase(name);
			}
			optString = "--" + name;
		}
		return optString;
	}
	
	/**
	 * Utility that removes the camel case of an identifier
	 */
	private String removeCamelCase(String name) {
		StringBuilder sb = new StringBuilder(name.length());
		sb.append(Character.toLowerCase(name.charAt(0)));
		for(int i = 1; i < name.length(); i++) {
			char c = name.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append('-');
				sb.append(Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * Adds an option handler directly to the parser.
	 * 
	 * @param opt the option handler to add
	 */
	public void addOptionHandler(OptionHandler opt) {
		for( String optString : opt.getOptionStrings() ) {
			OptionHandler prev = null;
			if (OptionParser.isLongOpt(optString)) {
				prev = longOpts.put(stripPrefix(optString),opt);
			} else {
				prev = shortOpts.put(stripPrefix(optString).charAt(0),opt);
			}
			if (prev != null) { // duplicate option?
				conflictHandler.handleConflict(this, optString, opt, prev);
			}
		}
		mainGroup.add(opt);
	}

	/**
	 * Checks if the option is already present. optString should contain the prefixes (i.e. dashes). 
	 * 
	 * @param optString
	 * @return true if the option is present
	 */
	public boolean hasOption(String optString) {
		if (!isOpt(optString)) return false;
		optString = stripPrefix(optString);
		if (isLongOpt(optString) && longOpts.containsKey(optString)) return true;
		return shortOpts.containsKey(optString);
	}
	
	/**
	 * Gets the option handler for the specified option string
	 * (should include "--" or "-").
	 * If the option string hasn't been added to the parser,
	 * a null value is returned.
	 * 
	 * @param optString the string to search for
	 * @return null, if the option string isn't found; the handler for the option otherwise
	 */
	public OptionHandler getOption(String optString) {
		if (!isOpt(optString)) return null;
		if (isLongOpt(optString)) {
			Entry<CharSequence, OptionHandler> entry = longOpts.getEntryFromUnambiguousPrefix(stripPrefix(optString));
			return entry == null ? null : entry.getValue();
		}
		return shortOpts.get(optString.charAt(1));
	}
	
	/**
	 * Returns the usage string, no replacements made.
	 * The usage string is the first line of the help message
	 * which generally includes the program name, a reference to
	 * options and the list of positional args.
	 *  
	 * @return the usage string
	 * 
	 * @see #setUsage(String)
	 * @see #getUsageString()
	 */
	public String getUsage() {
		if (usage == null) {
			usage = DEFAULT_USAGE;
		}
		return usage;
	}

	/**
	 * Sets the usage string which is the first line of the help message.
	 * May contain the special string "%prog" which is replaced with
	 * the program name.
	 * <p>
	 * For example:
	 * </p>
	 * <p><blockquote>%prog [-f] config-file</blockquote></p>
	 * 
	 * @param usage	the usage string
	 * 
	 * @see #getUsage()
	 * @see #getUsageString()
	 */
	public void setUsage(String usage) {
		this.usage = usage;
	}

	/**
	 * Returns the usage string, with "%prog" replaced
	 * with the program name. Equivalent to
	 * <code>getUsage().replaceAll("%prog",getProg())</code>
	 *  
	 * @return the usage string
	 * 
	 * @see #setUsage(String)
	 * @see #getUsageString()
	 */
	public String getUsageString() {
		return getUsage().replaceAll("%prog", getProg());
	}

	/**
	 * Prints the usage string to stdout, replacing "%prog" 
	 * with the program name. Equivalent to
	 * <code>System.out.println(getUsageString())</code>
	 *  
	 * @see #getUsageString()
	 * @see #getUsage()
	 * @see #setUsage(String)
	 */
	public void printUsage() {
		System.out.println(getUsageString());
	}

	/**
	 * Prints the help message to standard out.
	 */
	public void printHelp() {
		System.out.println(	helpString() );
	}
	
	/**
	 * Gets a copy of the list of all the option handlers
	 * @return
	 */
	public OptionHandler[] getOptions() {
		return mainGroup.toArray(new OptionHandler[mainGroup.size()]);
	}

	/**
	 * Set intersection between this instance and the requested (this - optionStrings).
	 * 
	 * @param optionStrings
	 * @return
	 */
	// TODO : push this into the conflict handling stuff
	public Set<String> existingOptions(List<String> optionStrings) {
		Set<String> existing = new HashSet<String>();
		for( String optString : optionStrings ) {
			if (isLongOpt(optString)) {
				if (longOpts.containsKey(stripPrefix(optString)))
					existing.add(optString);
			} else {
				if (shortOpts.containsKey(stripPrefix(optString)))
					existing.add(optString);
			}
		}
		return existing;
	}
	
	/**
	 * Returns the version string, settable only upon instantiate
	 * to maintain compliance with Python's optparse.
	 * "%prog" is <i>not</i> replaced with the program name.
	 * 
	 * @return the version string; null if none exists
	 * 
	 * @see #getVersionString();
	 * @see OptionParser#OptionParser(String, boolean)
	 * @see OptionParser#OptionParser(String)
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Returns the version string, settable only upon instantiate
	 * to maintain compliance with Python's optparse.
	 * "%prog" <i>is</i> replaced with the program name.
	 * 
	 * @return the version string; null if none exists
	 *
	 * @see #getVersion();
	 * @see OptionParser#OptionParser(String, boolean)
	 * @see OptionParser#OptionParser(String)
	 */
	public String getVersionString() {
		return version == null ? null : version.replaceAll("%prog", getProg());
	}

	/**
	 * Gets the description of the program.
	 * The description is printed in the help message.
	 * 
	 * @return the description of the program
	 * 
	 * @see #setDescription(String)
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the program.
	 * Gets a description of the program.
	 * 
	 * @param description the description of the program
	 * 
	 * @see #getDescription()
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the formatter used to format the help message.
	 * 
	 * @return the formatter used to format the help message
	 * 
	 * @see #setHelpFormatter(HelpFormatter)
	 */
	public HelpFormatter getHelpFormatter() {
		if (formatter == null)
			this.formatter = new IndentedHelpFormatter();
		return formatter;
	}

	/**
	 * Sets the formatter used to format the help message.
	 * 
	 * @param the formatter to be used to format the help message
	 * 
	 * @see #getHelpFormatter()
	 */
	public void setHelpFormatter(HelpFormatter formatter) {
		this.formatter = formatter;
	}
	
	/**
	 * Gets the handler used to resolve conflicts.
	 * 
	 * @return the handler used to resolve conflicts
	 * 
	 * @see #setConflictHandler(ConflictHandler)
	 */
	public ConflictHandler getConflictHandler() {
		if (conflictHandler == null) {
			conflictHandler = new ErrorConflictHandler();
		}
		return conflictHandler;
	}

	/**
	 * Sets the handler used to resolve conflicts.
	 * 
	 * @param conflictHandler the handler to be used to resolve conflicts
	 * 
	 * @see #getConflictHandler()
	 */
	public void setConflictHandler(ConflictHandler conflictHandler) {
		this.conflictHandler = conflictHandler;
	}

	/**
	 * Gets the program name. If it hasn't been explicitly set,
	 * uses the name of the main class used to run the current code.
	 * The "%prog" variable gets replaced with this value and
	 * typically shows up in version and usage information.
	 * 
	 * @return the program name
	 * 
	 * @see #setProg(String)
	 */
	public String getProg() {
		if (prog == null) {
			Map<Thread, StackTraceElement[]> traces = Thread.getAllStackTraces();
			for ( Entry<Thread, StackTraceElement[]> e : traces.entrySet() ) {
//					if (e.getKey().getName().equals("main")) {
				// TODO : determine if this is reliable (if not, use above, also perhaps not always reliable
				if (e.getKey().getId() == 1) {
					StackTraceElement[] stack = e.getValue();
					StackTraceElement main = stack[stack.length - 1];
					String mainClass = main.getClassName();
					this.prog = mainClass;
					break;
				}
			}
			if (this.prog == null) // Did we find it?
				throw new RuntimeException("Could not infer the main class name");
		}
		return prog;
	}

	/**
	 * Sets the program name. The "%prog" variable gets replaced
	 * with this value and typically shows up in version and usage
	 * information.
	 * 
	 * @param prog the name of the program
	 * 
	 * @see #getProg()
	 */
	public void setProg(String prog) {
		this.prog = prog;
	}

	/**
	 * Queries whether or not this parser allows interspersed
	 * positional arguments. For instance, if "--verbose"
	 * was a boolean flag, the command-line "--verbose arg1 -t"
	 * would normally set arg1 as a positional argument,
	 * unless this flag is disabled.
	 * 
	 * @return true if interspersed arguments are allowed
	 */
	public boolean getAllowInterspersedArgs() {
		return allowInterspersedArgs;
	}

	/**
	 * Queries whether or not this parser allows interspersed
	 * positional arguments. For instance, if "--verbose"
	 * was a boolean flag, the command-line "--verbose arg1 -t"
	 * would normally set arg1 as a positional argument,
	 * unless this flag is disabled.
	 * 
	 * @param allowInterspersedArgs whether or not to allow interspersed arguments
	 */
	public void setAllowInterspersedArgs(boolean allowInterspersedArgs) {
		this.allowInterspersedArgs = allowInterspersedArgs;
	}

	/**
	 * Maps classes to parsers
	 * 
	 * @see #putOptionArgumentParser(Class, OptionArgumentParser)
	 * @see #getOptionArgumentParser(Class)
	 * @see #removeOptionArgumentParser(Class)
	 */
	private final Map<Class<?>, OptionArgumentParser<?>> parserMap = new HashMap<Class<?>, OptionArgumentParser<?>>();
	{
		parserMap.put(String.class, new OptionArgumentParser<String>() {
			@Override
			public String parse(String arg) {
				return arg;
			}
		});
		
		final class DoubleParser implements OptionArgumentParser<Double> {
			@Override
			public Double parse(String arg) {
				return Double.valueOf(arg);
			}
		}

		final class FloatParser implements OptionArgumentParser<Float> {
			@Override
			public Float parse(String arg) {
				return Float.valueOf(arg);
			}
		}

		final class LongParser implements OptionArgumentParser<Long> {
			@Override
			public Long parse(String arg) {
				if (arg.startsWith("0x"))
					return Long.valueOf(arg.substring(2), 16);
				if (arg.startsWith("0b"))
					return Long.valueOf(arg.substring(2), 2);
				if (arg.startsWith("0"))
					return Long.valueOf(arg.substring(1), 8);
				return Long.valueOf(arg);
			}
		}

		final class IntParser implements OptionArgumentParser<Integer> {
			@Override
			public Integer parse(String arg) {
				if (arg.equals("0"))
					return 0;
				if (arg.startsWith("0x"))
					return Integer.valueOf(arg.substring(2), 16);
				if (arg.startsWith("0b"))
					return Integer.valueOf(arg.substring(2), 2);
				if (arg.startsWith("0"))
					return Integer.valueOf(arg.substring(1), 8);
				return Integer.valueOf(arg);
			}
		}

		final class ByteParser implements OptionArgumentParser<Byte> {
			@Override
			public Byte parse(String arg) {
				return Byte.valueOf(arg);
			}
		}

		parserMap.put(Byte.class, new ByteParser());
		parserMap.put(Byte.TYPE, new ByteParser());
		parserMap.put(Integer.class, new IntParser());
		parserMap.put(Integer.TYPE, new IntParser());
		parserMap.put(Long.class, new LongParser());
		parserMap.put(Long.TYPE, new LongParser());
		parserMap.put(Float.class, new FloatParser());
		parserMap.put(Float.TYPE, new FloatParser());
		parserMap.put(Double.class, new DoubleParser());
		parserMap.put(Double.TYPE, new DoubleParser());
		
		parserMap.put(File.class, new OptionArgumentParser<File>() {
			@Override
			public File parse(String arg) {
				return new File(arg);
			}
		});
		
		parserMap.put(Level.class, new OptionArgumentParser<Level>(){
			@Override
			public Level parse(String arg) {
				return Level.parse(arg);
			}
		});
	}

	/**
	 * Adds an option handler factory for the specified action name.
	 * If one exists, it is replaced. Anytime an option
	 * with the <code>action</code> attribute equal to <code>actionName</code>
	 * will use <code>factory</code> to create a new option handler.
	 * 
	 * @param actionName the action to use the factory for
	 * @param factory	 the factory to use
	 * @return the previous factory for this action, if it existed; null otherwise
	 * 
	 * @see #getOptionHandlerFactory(String)
	 * @see #removeOptionHandlerFactory(String)
	 */
	public OptionHandlerFactory putOptionHandlerFactory(String actionName, OptionHandlerFactory factory) {
		return actionMap.put(actionName, factory);
	}
	
	/**
	 * Gets the option handler factory for the specified action name.
	 * Anytime an option with the <code>action</code> attribute equal
	 * to <code>actionName</code> will use the 
	 * corresponding <code>OptionHandlerFactory</code> to create a new option
	 * handler.
	 *  
	 * @param actionName the name of the action for which to get the handler 
	 * @return the handler corresponding to actionName, if there is one; null otherwise
	 * 
	 * @see #putOptionHandlerFactory(String, OptionHandlerFactory)
	 * @see #removeOptionHandlerFactory(String)
	 */
	public OptionHandlerFactory getOptionHandlerFactory(String actionName) {
		return actionMap.get(actionName);
	}
	
	/**
	 * Remove the option handler factory for the specified action name,
	 * if it exists.
	 * 
	 * @param actionName the name of the action to remove
	 * @return the removed handler, if it existed; null, otherwise
	 * 
	 * @see #putOptionHandlerFactory(String, OptionHandlerFactory)
	 * @see #getOptionHandlerFactory(String)
	 */
	public OptionHandlerFactory removeOptionHandlerFactory(String actionName) {
		return actionMap.remove(actionName);
	}

	/**
	 * Adds a parser for the specified type.
	 * This method allows the option parser to be extended to
	 * be able to handle options of arbitrary types,
	 * provided that a parser is added via this method for those types.
	 * The option parser initially contains parsers for
	 * all primitive types, their wrappers, and <code>File</code>.
	 * These can be replaced, if necessary, via this method.
	 * 
	 * @param <T>	the type that the specified parser can parse
	 * @param type	the class object of the type
	 * @param parser the parser for the object
	 * 
	 * @return the previous parser for type, if one existed; null otherwise
	 * 
	 * @see #getOptionArgumentParser(Class)
	 * @see #removeOptionArgumentParser(Class)
	 */
	@SuppressWarnings("unchecked")
	public <T> OptionArgumentParser<T> putOptionArgumentParser(Class<T> type, OptionArgumentParser<T> parser) {
		return (OptionArgumentParser<T>) parserMap.put(type, parser);
	}
	
	/**
	 * Removes a parser for the specified type.
	 * 
	 * @param <T>	the type that the specified parser can parse
	 * @param type	the class object of the type
	 * 
	 * @return the parser that was removed, if it existed; null otherwise
	 * 
	 * @see #putOptionArgumentParser(Class, OptionArgumentParser)
	 */
	@SuppressWarnings("unchecked")
	public <T> OptionArgumentParser<T> removeOptionArgumentParser(Class<T> type) {
		return ((Map<Class<T>, OptionArgumentParser<T>>)(Object)parserMap).remove(type);
	}
	
	/**
	 * Gets the option parser for the specified type.
	 * 
	 * @param <T>	the type that the specified parser can parse
	 * @param type	the class object of the type
	 * @return the parser for the specified type, if it exists; null otherwise
	 * 
	 * @see #putOptionArgumentParser(Class, OptionArgumentParser)
	 * @see #removeOptionArgumentParser(Class)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> OptionArgumentParser<T> getOptionArgumentParser(final Class<T> type) {
		OptionArgumentParser<T> ret = ((Map<Class<T>, OptionArgumentParser<T>>)(Object)parserMap).get(type);
		if (ret == null && type.isEnum()) {
			return new EnumParser(type);
		}
		return ret;
	}
	
	private static class EnumParser<E extends Enum<E>> implements OptionArgumentParser<E> {

		private final Class<E> type;
		
		public EnumParser(Class<E> type) {
			this.type = type;
		}

		@Override
		public E parse(String arg) {
			return Enum.valueOf(type, arg);
		}
		
	}

	/**
	 * An argument is an option if it starts with -, +, or /
	 * 
	 * @param arg
	 * @return
	 */
	public final static boolean isOpt(String arg) {
		// Short option
		return isLongOpt(arg) || arg.startsWith("-") || arg.startsWith("+");
	}
	
	/**
	 * Decides whether or not an option string is a long option.
	 * Currently, this is accomplished simply by checking if the length is > 2
	 * 
	 * @param optString
	 * @return true if optString is a long option
	 */
	public static boolean isLongOpt(String optString) {
		return optString.startsWith("-") && optString.charAt(1) == '-';
	}
	
	/**
	 * Strips the prefix (-,+,/,etc. from an option string)
	 * Assumes first character is always stripped; only supports -- for multi-char prefixes.
	 * Also assumes isOpt has already been called.
	 * 
	 * @param optString the option string to strip
	 * @return stripped string
	 */
	public final static String stripPrefix(String optString) {
		if (optString.charAt(1) == '-') return optString.substring(2);
		return optString.substring(1);
	}

	/**
	 * Removes the specified option handler.
	 * 
	 * @param opt the handler to remove
	 * @return true if succesfully removed 
	 */
	public boolean remove(OptionHandler opt) {
		for( String optString : opt.getOptionStrings() ) {
			if (isLongOpt(optString)) {
				longOpts.remove(stripPrefix(optString));
			} else {
				shortOpts.remove(stripPrefix(optString).charAt(0));
			}
		}
		return mainGroup.remove(opt);
	}
	
	/**
	 * Query whether camel case should be allowed when adding variables
	 * that don't explicitly set the <code>optStrings</code> attribute.
	 * If camel case is not allowed, then variables and methods that are added
	 * are converted to GNU-like syntax, e.g. aVarName is added as
	 * "--a-var-name". Otherwise, the options are added as is
	 * 
	 * @return whether camel case should be allowed when adding variables
	 * 
	 * @see #setCamelCaseAllowed(boolean)
	 */
	public boolean isCamelCaseAllowed() {
		return camelCaseAllowed;
	}

	/**
	 * Set whether camel case should be allowed when adding variables
	 * that don't explicitly set the <code>optStrings</code> attribute.
	 * If camel case is not allowed, then variables and methods that are added
	 * are converted to GNU-like syntax, e.g. aVarName is added as
	 * "--a-var-name". Otherwise, the options are added as is
	 * 
	 * @param camelCaseAllowed
	 * 
	 * @see #isCamelCaseAllowed()
	 */
	public void setCamelCaseAllowed(boolean camelCaseAllowed) {
		this.camelCaseAllowed = camelCaseAllowed;
	}

	/**
	 * Maps primitive types to their wrappers
	 */
	private static Map<Class<?>,Class<?>> primitiveTypes = new HashMap<Class<?>, Class<?>>();
	{
		primitiveTypes.put(Boolean.TYPE, Boolean.class);
		primitiveTypes.put(Byte.TYPE, Byte.class);
		primitiveTypes.put(Integer.TYPE, Integer.class);
		primitiveTypes.put(Long.TYPE, Long.class);
		primitiveTypes.put(Float.TYPE, Float.class);
		primitiveTypes.put(Double.TYPE, Double.class);
	}

	/**
	 * If the specified class is a primitive,
	 * returns the class of its wrapper.
	 * Otherwise, returns cls.
	 * @param cls the class to obtain a wrapper for
	 * @return the potentially wrapped class
	 */
	public static Class<?> toWrapper(Class<?> cls) {
		if (cls!= null && cls.isPrimitive())
			return primitiveTypes.get(cls);
		return cls;
	}
	
	public static String[] parse(Object optObj, String[] args) {
		OptionParser parser = new OptionParser(optObj);
		return parser.parseArgs(args).getPositionalArgs();
	}
	
}
