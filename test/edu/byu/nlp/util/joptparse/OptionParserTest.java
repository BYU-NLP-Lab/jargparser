/**
 * 
 */
package edu.byu.nlp.util.joptparse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import edu.byu.nlp.util.joptparse.annotations.Description;
import edu.byu.nlp.util.joptparse.annotations.Option;
import edu.byu.nlp.util.joptparse.annotations.Options;
import edu.byu.nlp.util.joptparse.annotations.ParserOptions;
import edu.byu.nlp.util.joptparse.annotations.Usage;
import edu.byu.nlp.util.joptparse.annotations.Version;

/**
 * @author rah67
 *
 */
public class OptionParserTest {

	private final class PropTest {
		private int a;
		private int b;
		private List<Integer> list = new ArrayList<Integer>();
		
		@Option
		public int getA() { return a; }

		public void setA(int a) { this.a=a; }

		public int getB() { return b; }

		@Option
		public void setB(int b) { this.b=b; }

		@Option
		public void zero() { this.a = -1; }

		@Option
		public void single(double c) { this.a = (int)c; }

		@Option
		public void full(String optName, Object[] optArgs, OptionParser p, ParserState state) {
			while(!state.remainingArgs.isEmpty() && !state.remainingArgs.getFirst().startsWith("-")) {
				int next = p.getOptionArgumentParser(Integer.class).parse(state.remainingArgs.removeFirst());
				list.add(next);
			}
		}
	}


	private class OptionsTest {
		@Option("Number of clusters")
		private int n = 1;
		
		private String name;
		
		@Option
		private String thisIsATest;
		
		@Option
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		public String getThisIsATest() {
			return thisIsATest;
		}
	}

	/**
	 * Test method for {@link edu.byu.nlp.util.joptparse.OptionParser#parseArgs(java.lang.String[])}.
	 */
	@Test
	public void testParseArgs() {
		OptionsTest options = new OptionsTest();
		OptionParser parser = new OptionParser();
		parser.addOptions(options);
		parser.parseArgs("-n","5","--name","Test","--this-is-a-test","Worked!");
		assertEquals(options.n,5);
		assertEquals(options.getName(), "Test");
		assertEquals(options.getThisIsATest(), "Worked!");
		
		parser = new OptionParser();
		parser.addOptions(new Object() {
			@Option("number of clusters") int n;
			@Option String name;
			@Option String thisIsATest;
			@Options( { @Option,
				@Option(optStrings = "--quiet", action = Option.STORE_FALSE) })
			boolean verbose;
		});
		OptionValues opts = parser.parseArgs("-n","5","--name","Test","--this-is-a-test","Worked!","--verbose","--quiet");
		int n = opts.getValue("n");
		assertEquals(n,5);
		assertEquals(opts.getValue("name"),"Test");
		assertEquals(opts.getValue("thisIsATest"),"Worked!");
		assertFalse((Boolean)opts.getValue("verbose"));
		assertFalse((Boolean)opts.getValue("quiet"));
//		System.out.println(opts.optionsMap());
//		opts.properties().list(System.out);
		
		parser = new OptionParser();
		parser.addOptions(new Object(){@Option(help="testing choices",choices={"a","b","c"}) String choice;});
		OptionValues opt = parser.parseArgs("--choice=c");
		assertEquals(opt.getValue("choice"),"c");
//		parser.parseArgs("--choice=d");
		
		parser = new OptionParser();
		parser.addOptions(new Object(){@Option(help="testing choices",choices={"1","2","3"}) int choice;});
		opt = parser.parseArgs("--choice=2");
		int c = opt.getValue("choice");
		assertEquals(c,2);
//		parser.parseArgs("--choice=5");

		parser = new OptionParser();
		parser.addOptions(new Object(){@Option(action=Option.APPEND) List<Integer> t;});
		opt = parser.parseArgs("-t3 -t1 -t5".split("\\s"));
		assertEquals(opt.getValue("t"),Arrays.asList(3,1,5));

		parser = new OptionParser();
		parser.addOptions(new Object(){@Option(action=Option.APPEND,nargs=3) List<Integer[]> t;});
		opt = parser.parseArgs("-t3 4 5 -t1 0 -1 -t5 4 6".split("\\s+"));
		assertEquals(((List<Integer[]>)opt.getValue("t")).get(0),new Integer[]{3,4,5});
		assertEquals(((List<Integer[]>)opt.getValue("t")).get(1),new Integer[]{1,0,-1});
		assertEquals(((List<Integer[]>)opt.getValue("t")).get(2),new Integer[]{5,4,6});

		parser = new OptionParser();
		parser.addOptions(new Object(){@Option(action=Option.STORE,nargs=3) List<Integer> t;});
		opt = parser.parseArgs("-t3 4 5 -t1 0 -1 -t5 4 6".split("\\s+"));
		assertEquals(opt.getValue("t"),Arrays.asList(5,4,6));

		parser = new OptionParser();
		parser.addOptions(new Object(){@Option(action=Option.STORE,nargs=3) int[] t;});
		opt = parser.parseArgs("-t3 4 5 -t1 0 -1 -t5 4 6".split("\\s+"));
		assertTrue(Arrays.equals((int[])opt.getValue("t"),new int[]{5,4,6}));

		parser = new OptionParser();
		parser.addOptions(new Object(){@Option(action=Option.COUNT) int t;});
		opt = parser.parseArgs("".split("\\s+"));
		assertEquals(opt.getValue("t"),0);
		opt = parser.parseArgs("-t -t -t".split("\\s+"));
		assertEquals(opt.getValue("t"),3);
		parser = new OptionParser();
		parser.addOptions(new Object(){@Option(action=Option.COUNT) Integer t;});
		opt = parser.parseArgs("-t -t -t".split("\\s+"));
		assertEquals(opt.getValue("t"),3);
		opt = parser.parseArgs("-t -t".split("\\s+"));
		assertEquals(opt.getValue("t"),5);

		PropTest propTest = new PropTest();
		
		parser = new OptionParser();
		parser.addOptions(propTest);
		opt = parser.parseArgs("-a5 -b-2".split("\\s+"));
		assertEquals(propTest.a,5);
		assertEquals(propTest.b,-2);
		opt = parser.parseArgs("--zero".split("\\s+"));
		assertEquals(propTest.a,-1);
		opt = parser.parseArgs("--single=7.7".split("\\s+"));
		assertEquals(propTest.a,7);
		opt = parser.parseArgs("--full 1 2 3 4 5 -a6 -b2".split("\\s+"));
		assertEquals(propTest.a,6);
		assertEquals(propTest.b,2);
		assertEquals(propTest.list,Arrays.asList(1,2,3,4,5));
		
		TestClass test = new TestClass();
		parser = new OptionParser(test);
		parser.parseArgs("--AStrangeThing=5");
		assertEquals(test.AStrangeThing,5);
//		parser.printHelp();
		
//		parser = new OptionParser("%prog 1.0");
//		parser.parseArgs("--help");
//		parser.parseArgs("--version");
	}

	
	@Description("This is a test class")
	@Usage("%prog [options] file1 file2 file3")
	@Version("0.1alpha")
	@ParserOptions(addHelpOption=true,allowCamelCase=true,allowInterspersedArgs=true)
	private static class TestClass {
		@Option("a strange thing")
		private int AStrangeThing = -1;
	}

}
