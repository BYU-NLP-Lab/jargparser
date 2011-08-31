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
