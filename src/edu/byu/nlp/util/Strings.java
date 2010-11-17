/**
 * 
 */
package edu.byu.nlp.util;

import java.io.IOException;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author rah67
 *
 */
public class Strings {
	private Strings() { }
	
    /**
     * @author tarbo 
     * http://forums.java.net/jive/thread.jspa?threadID=34370&tstart=179
     * 
     * @param <T>
     * @param src
     * @param pattern
     * @param dst
     * @return
     * @throws IOException
     */
    public static <T extends Appendable> T join(Iterable<? extends CharSequence> src, CharSequence pattern, T dst) throws IOException {
        Iterator<? extends CharSequence> it = src.iterator();
        if (it.hasNext()) {
            dst.append(it.next());
        }
        while (it.hasNext()) {
            dst.append(pattern).append(it.next());
        }
        return dst;
    }
    
    /**
     * @author tarbo 
     * http://forums.java.net/jive/thread.jspa?threadID=34370&tstart=179
     */
    public static String join(Iterable<? extends CharSequence> src, CharSequence pattern) {
        try {
            return join(src, pattern, new StringBuilder()).toString();
        } catch (IOException excpt) {
            throw new Error("StringBuilder should not throw IOExceptions!");
        }
    }

	public static String repeat(String str, String sep, int n) {
		if (n==0)
			return "";
		StringBuilder sb = new StringBuilder(str);
		for(int i = 1; i < n; i++) {
			sb.append(sep);
			sb.append(str);
		}
		return sb.toString();
	}

	public static String join(String[] names, String pattern) {
		return join(Arrays.asList(names), pattern);
	}

	public static List<Character> asCharacterList(
			final String currentWord) {
		return new CharacterListView(currentWord);
	}
	
	public static class CharacterListView extends AbstractList<Character> {
		
		private final String string;

		public CharacterListView(String string) {
			this.string = string;
		}
		
		@Override
		public Character get(int index) {
			// TODO Auto-generated method stub
			return string.charAt(index);
		}

		@Override
		public int size() {
			// TODO Auto-generated method stub
			return string.length();
		}		
	}
	
}
