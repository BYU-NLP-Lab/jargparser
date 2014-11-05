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
package edu.byu.nlp.util.jargparser;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import edu.byu.nlp.util.jargparser.ArgumentParser;
import edu.byu.nlp.util.jargparser.ArgumentValues;
import edu.byu.nlp.util.jargparser.ParserState;
import edu.byu.nlp.util.jargparser.annotations.Description;
import edu.byu.nlp.util.jargparser.annotations.Option;
import edu.byu.nlp.util.jargparser.annotations.Options;
import edu.byu.nlp.util.jargparser.annotations.ParserOptions;
import edu.byu.nlp.util.jargparser.annotations.Usage;
import edu.byu.nlp.util.jargparser.annotations.Version;

/**
 * @author rah67
 *
 */
public class OptionParserTest {

	
	
	public class PropTest {
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
		public void full(String optName, Object[] optArgs, ArgumentParser p, ParserState state) {
			while(!state.remainingArgs.isEmpty() && !state.remainingArgs.getFirst().startsWith("-")) {
				int next = p.getOptionArgumentParser(Integer.class).parse(state.remainingArgs.removeFirst());
				list.add(next);
			}
		}
	}

	public class OptionsTest {
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

	

//	/**
//	 * Test method for {@link edu.byu.nlp.util.jargparser.ArgumentParser#parseArgs(java.lang.String[])}.
//	 */
//	@Test
//	public void testParseArgs1() {
//		OptionsTest options = new OptionsTest();
//		final ArgumentParser parser = new ArgumentParser();
//		parser.setExitOnError(false); // throw exception we can catch so we can test errors
//		parser.addArguments(options);
//		parser.parseArgs("-n","5","--name","Test","--this-is-a-test","Worked!");
//		assertEquals(5, options.n);
//		assertEquals("Test", options.getName());
//		assertEquals("Worked!", options.getThisIsATest());
//	}
//
//	@Test
//	public void testParseArgs2() {
//		final ArgumentParser parser = new ArgumentParser();
//		parser.setExitOnError(false); // throw exception we can catch so we can test errors
//		parser.addArguments(new Object() {
//			@Option("number of clusters") int n;
//			@Option String name;
//			@Option String thisIsATest;
//			@Options( { @Option,
//				@Option(optStrings = "--quiet", action = Option.STORE_FALSE) })
//			boolean verbose;
//		});
//		ArgumentValues args = parser.parseArgs("-n","5","--name","Test","--this-is-a-test","Worked!","--verbose","--quiet");
//		int n = args.getValue("n");
//		assertEquals(5,n);
//		assertEquals("Test",args.getValue("name"));
//		assertEquals("Worked!",args.getValue("thisIsATest"));
//		assertFalse((Boolean)args.getValue("verbose"));
//		assertFalse((Boolean)args.getValue("quiet"));
////		System.out.println(opts.optionsMap());
////		opts.properties().list(System.out);
//	}
//
//	@Test
//	public void testParseArgs3() {
//		final ArgumentParser parser = new ArgumentParser();
//		parser.setExitOnError(false); // throw exception we can catch so we can test errors
//		parser.addArguments(new Object(){@Option(help="testing choices",choices={"a","b","c"}) String choice;});
//		ArgumentValues args = parser.parseArgs("--choice=c");
//		assertEquals("c",args.getValue("choice"));
//		assertFail(new Runnable() {
//			@Override
//			public void run() {
//				parser.parseArgs("--choice=d");
//			}
//		});
//	}
//
//	@Test
//	public void testParseArgs4() {
//		final ArgumentParser parser = new ArgumentParser();
//		parser.setExitOnError(false); // throw exception we can catch so we can test errors
//		parser.addArguments(new Object(){@Option(help="testing choices",choices={"1","2","3"}) int choice;});
//		ArgumentValues args = parser.parseArgs("--choice=2");
//		int c = args.getValue("choice");
//		assertEquals(2,c);
//		assertFail(new Runnable() {
//			@Override
//			public void run() {
//				parser.parseArgs("--choice=5");
//			}
//		});
//	}
//
//	@Test
//	public void testParseArgs5() {
//		final ArgumentParser parser = new ArgumentParser();
//		parser.setExitOnError(false); // throw exception we can catch so we can test errors
//		parser.addArguments(new Object(){@Option(action=Option.APPEND) List<Integer> t;});
//		ArgumentValues args = parser.parseArgs("-t3 -t1 -t5".split("\\s"));
//		assertEquals(Arrays.asList(3,1,5),args.getValue("t"));
//	}

	@Test
	public void testParseArgs6() {
		final ArgumentParser parser = new ArgumentParser();
		parser.setExitOnError(false); // throw exception we can catch so we can test errors
		parser.addArguments(new Object(){@Option(action=Option.APPEND,nargs=3) List<Integer[]> t;});
		ArgumentValues args = parser.parseArgs("-t3 4 5 -t1 0 -1 -t5 4 6".split("\\s+"));
		assertEquals(new Integer[]{3,4,5}, ((List<Integer[]>)args.getValue("t")).get(0));
		assertEquals(new Integer[]{1,0,-1}, ((List<Integer[]>)args.getValue("t")).get(1));
		assertEquals(new Integer[]{5,4,6}, ((List<Integer[]>)args.getValue("t")).get(2));
	}

//	@Test
//	public void testParseArgs7() {
//		final ArgumentParser parser = new ArgumentParser();
//		parser.setExitOnError(false); // throw exception we can catch so we can test errors
//		parser.addArguments(new Object(){@Option(action=Option.STORE,nargs=3) List<Integer> t;});
//		ArgumentValues args = parser.parseArgs("-t3 4 5 -t1 0 -1 -t5 4 6".split("\\s+"));
//		assertEquals(Arrays.asList(3,4,5,1,0,-1,5,4,6),args.getValue("t"));
//	}
//
//	@Test
//	public void testParseArgs8() {
//		final ArgumentParser parser = new ArgumentParser();
//		parser.setExitOnError(false); // throw exception we can catch so we can test errors
//		parser.addArguments(new Object(){@Option(action=Option.STORE,nargs=3) int[] t;});
//		ArgumentValues args = parser.parseArgs("-t3 4 5 -t1 0 -1 -t5 4 6".split("\\s+"));
//		assertTrue(Arrays.equals(new int[]{5,4,6},(int[])args.getValue("t")));
//	}
//
//	@Test
//	public void testParseArgs9() {
//		final ArgumentParser parser = new ArgumentParser();
//		parser.addArguments(new Object(){@Option(action=Option.COUNT) int t;});
//		ArgumentValues args = parser.parseArgs("".split("\\s+"));
//		assertEquals(0,args.getValue("t"));
//		args = parser.parseArgs("-t -t -t".split("\\s+"));
//		assertEquals(3,args.getValue("t"));
//	}
//	
//	@Test
//	public void testParseArgs10() {
//		final ArgumentParser parser = new ArgumentParser();
//		parser.setExitOnError(false); // throw exception we can catch so we can test errors
//		parser.addArguments(new Object(){@Option(action=Option.COUNT) Integer t;});
//		ArgumentValues args = parser.parseArgs("-t -t -t".split("\\s+"));
//		assertEquals(3,args.getValue("t"));
//		args = parser.parseArgs("-t -t".split("\\s+"));
//		assertEquals(5,args.getValue("t"));
//	}
//
//	@Test
//	public void testParseArgs11() {
//		final ArgumentParser parser = new ArgumentParser();
//		parser.setExitOnError(false); // throw exception we can catch so we can test errors
//		PropTest propTest = new PropTest();
//		
//		parser.addArguments(propTest);
//		ArgumentValues args = parser.parseArgs("-a5 -b-2".split("\\s+"));
//		assertEquals(5,propTest.a);
//		assertEquals(-2,propTest.b);
//		args = parser.parseArgs("--zero".split("\\s+"));
//		assertEquals(-1,propTest.a);
//		args = parser.parseArgs("--single=7.7".split("\\s+"));
//		assertEquals(7,propTest.a);
//		args = parser.parseArgs("--full 1 2 3 4 5 -a6 -b2".split("\\s+"));
//		assertEquals(6,propTest.a);
//		assertEquals(2,propTest.b);
//		assertEquals(Arrays.asList(1,2,3,4,5),propTest.list);
//	}
//	
//	private static enum Color {RED,GREEN,BLUE};
//	private static class EnumOpts{
//		@Option
//		private Color color;
//	}
//
//	@Test
//	public void testParseArgs12() {
//		final ArgumentParser parser = new ArgumentParser();
//		parser.setExitOnError(false); // throw exception we can catch so we can test errors
//		EnumOpts propTest = new EnumOpts();
//		
//		parser.addArguments(propTest);
//		parser.parseArgs("--color=RED");
//		
//		assertEquals(propTest.color, Color.RED);
//		
//		assertFail(new Runnable() {
//			@Override
//			public void run() {
//				parser.parseArgs("--color=MAUVE");
//			}
//		});
//	}


	private static void assertFail(Runnable r){
		try{
			r.run();
			fail("program was expected to fail");
		}
		catch(Exception e){
			// good
		}
	}
	
	public static void main(String[] args){
		
	}

}
