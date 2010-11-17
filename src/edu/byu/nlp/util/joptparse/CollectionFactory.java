/**
 * 
 */
package edu.byu.nlp.util.joptparse;

import java.util.Collection;

/**
 * @author rah67
 *
 */
public interface CollectionFactory<E> {
	Collection<E> newInstance();
}
