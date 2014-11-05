/**
 * Copyright 2014 Brigham Young University
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.byu.nlp.util.TrieMap;

/**
 * @author plf1
 *
 */
public class TrieMapTest {

	@Test
	public void testMap(){
		TrieMap<Integer> map = new TrieMap<Integer>();
		map.put("abc", 3);
		map.put("abx", 4);
		map.put("def", 5);

		assertTrue(map.containsKey("abc"));
		assertTrue(map.containsKey("def"));
		assertFalse(map.containsKey("gih"));
		assertTrue(map.containsKeyPrefix("a"));
		assertFalse(map.containsKeyPrefix("b"));
		assertFalse(map.containsUnambiguousKeyPrefix("a"));
		assertTrue(map.containsValue(3));
		assertTrue(map.containsValue(4));
		assertTrue(map.containsValue(5));
		assertFalse(map.containsValue(56));
	}

	@Test
	public void testMap2(){
		TrieMap<Integer> map = new TrieMap<Integer>();
		map.put("xyz", 5);
		map.put("xyz-onyx", 7);

		assertTrue(map.containsKey("xyz"));
		assertFalse(map.containsKey("xyz-"));
		assertTrue(map.containsKey("xyz-onyx"));
		assertEquals(5, (int)map.get("xyz"));
		assertEquals(7, (int)map.get("xyz-onyx"));
	}

}
