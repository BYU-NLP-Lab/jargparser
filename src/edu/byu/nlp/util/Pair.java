package edu.byu.nlp.util;

import java.io.Serializable;

/**
 * A generic-typed pair of objects.
 * @author Dan Klein
 */
public class Pair<F,S> implements Serializable {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

  F first;
  S second;

  public F getFirst() {
    return first;
  }

  public S getSecond() {
    return second;
  }

  public void setFirst(F pFirst) {
    first = pFirst;
  }

  public void setSecond(S pSecond) {
    second = pSecond;
  }


@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Pair)) return false;

    @SuppressWarnings("rawtypes")
	final Pair pair = (Pair) o;

    if (first != null ? !first.equals(pair.first) : pair.first != null) return false;
    if (second != null ? !second.equals(pair.second) : pair.second != null) return false;

    return true;
  }

  @Override
public int hashCode() {
    int result;
    result = (first != null ? first.hashCode() : 0);
    result = 29 * result + (second != null ? second.hashCode() : 0);
    return result;
  }

  @Override
public String toString() {
    return "(" + getFirst() + ", " + getSecond() + ")";
  }

  public Pair(F first, S second) {
    this.first = first;
    this.second = second;
  }
}
