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

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import edu.byu.nlp.util.jargparser.annotations.Option;
import edu.byu.nlp.util.jargparser.arghandlers.AppendCollectionOption;

class Append implements OptionHandlerFactory {

	@Override
	public OptionHandler newHandler(ArgumentParser p, ReflectiveVariable f, Option opt, Object optObject, List<String> optStrings) {
		
		if ( !opt.choices()[0].equals("") ) 
			throw new UnsupportedOperationException("Choices currently not implemented with append");
		
		if (f.getType().isArray()) {
			throw new IllegalArgumentException("Cannot append to arrays");
		} else if (f.getType() == Collection.class ||
				f.getType() == Iterable.class ||
				f.getType() == List.class ||
				f.getType() == ArrayList.class ||
				f.getType() == LinkedList.class ||
				f.getType() == Set.class) {

			int numArgs = opt.nargs();
			if ( numArgs == -1 )
				numArgs = 1;
			if ( numArgs < 1 )
				throw new IllegalArgumentException("Append option requires at least one argument!");
			
			Class<?> innerType;
			
			if ( numArgs > 1 ) {
				// appending a collection
				Type type = ((ParameterizedType) f.getGenericType()).getActualTypeArguments()[0]; // a collection only has one type (hence the [0])
				// note(pfelt): this code appears to be intended to get the class value from something  
				// like List<Integer[]>, but does not succeed. Even if the correct type is returned, 
				// subsequent code is broken. Looks like a feature that wasn't fully implemented or 
				// the reflection API has since broken it. For now I'm passing back type information 
				// assuming something like List<Integer> for appending lists. That's maybe less 
				// elegant, but it is currently working (see OptionParserTest.testParseArgs6())
//				if ( !(type instanceof GenericArrayType) )
//					throw new IllegalArgumentException("Can only append one-dimensional arrays when using multiple arguments");
//				Type gct = ((GenericArrayType)type).getGenericComponentType();
//				
//				if ( !(gct instanceof Class<?>) || ((Class<?>)gct).isArray() )
//					throw new IllegalArgumentException("Can only append one-dimensional arrays when using multiple arguments");
//					
//				innerType = (Class<?>) gct;
				innerType = (Class<?>) type;
			} else {
				// appending a single arg
				try {
					innerType = (Class<?>) ((ParameterizedType) f.getGenericType()).getActualTypeArguments()[0];
				} catch (Exception e) {
					throw new IllegalArgumentException("For a single argument, only \"simple\" collections are allowed");
				}
			}

			// get a factory
			CollectionFactory<Object> factory;
			if (f.getType().isInstance(Set.class)) {
				factory = new CollectionFactories.HashSetFactory<Object>();
			} else if (f.getType().isInstance(LinkedList.class)){
				factory = new CollectionFactories.LinkedListFactory<Object>();
			} else {
				factory = new CollectionFactories.ArrayListFactory<Object>();
			}

			return new AppendCollectionOption(f, opt, optObject, optStrings, factory, innerType, numArgs);
		} else {
			throw new IllegalArgumentException("Append requires a supported collection");
		}
	}
}