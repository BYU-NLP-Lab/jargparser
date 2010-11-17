/**
 * 
 */
package edu.byu.nlp.util.joptparse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * @author rah67
 *
 */
public class CollectionFactories {
	public static class ArrayListFactory<E> implements CollectionFactory<E> {
		@Override
		public Collection<E> newInstance() {
			return new ArrayList<E>();
		}
	}

	public static class LinkedListFactory<E> implements CollectionFactory<E> {
		@Override
		public Collection<E> newInstance() {
			return new LinkedList<E>();
		}
	}

	public static class HashSetFactory<E> implements CollectionFactory<E> {
		@Override
		public Collection<E> newInstance() {
			return new HashSet<E>();
		}
	}
}
