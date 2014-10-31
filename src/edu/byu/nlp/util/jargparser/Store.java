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

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import edu.byu.nlp.util.jargparser.annotations.Option;
import edu.byu.nlp.util.jargparser.arghandlers.StoreBooleanArrayFieldOption;
import edu.byu.nlp.util.jargparser.arghandlers.StoreByteArrayFieldOption;
import edu.byu.nlp.util.jargparser.arghandlers.StoreChoicesOption;
import edu.byu.nlp.util.jargparser.arghandlers.StoreCollectionOption;
import edu.byu.nlp.util.jargparser.arghandlers.StoreDoubleArrayFieldOption;
import edu.byu.nlp.util.jargparser.arghandlers.StoreFieldOption;
import edu.byu.nlp.util.jargparser.arghandlers.StoreFloatArrayFieldOption;
import edu.byu.nlp.util.jargparser.arghandlers.StoreIntArrayFieldOption;
import edu.byu.nlp.util.jargparser.arghandlers.StoreLongArrayFieldOption;

class Store implements OptionHandlerFactory {

	@Override
	public OptionHandler newHandler(ArgumentParser p, ReflectiveVariable var, Option opt, Object optObject, List<String> optStrings) {
		
		int numArgs = opt.nargs();
		if ( numArgs == -1 )
			numArgs = 1;
		if ( numArgs < 1 )
			throw new IllegalArgumentException("Store option requires at least one argument!");
		
		if ( !opt.choices()[0].equals("") && numArgs != 1 ) 
			throw new IllegalArgumentException("Choices only work with exactly one argument");
		
		if (var.getType().isArray()) {
			if (var.getType().getComponentType().isPrimitive()) {
				if (var.getType().getComponentType() == Boolean.TYPE) {
					return new StoreBooleanArrayFieldOption(var, opt, optObject, optStrings, Boolean.class, numArgs);
				} else if (var.getType().getComponentType() == Byte.TYPE) {
					return new StoreByteArrayFieldOption(var, opt, optObject, optStrings, Byte.class, numArgs);
				} else if (var.getType().getComponentType() == Integer.TYPE) {
					return new StoreIntArrayFieldOption(var, opt, optObject, optStrings, Integer.class, numArgs);
				} else if (var.getType().getComponentType() == Long.TYPE) {
					return new StoreLongArrayFieldOption(var, opt, optObject, optStrings, Long.class, numArgs);
				} else if (var.getType().getComponentType() == Float.TYPE) {
					return new StoreFloatArrayFieldOption(var, opt, optObject, optStrings, Float.class, numArgs);
				} else if (var.getType().getComponentType() == Double.TYPE) {
					return new StoreDoubleArrayFieldOption(var, opt, optObject, optStrings, Double.class, numArgs);
				} else {
					throw new IllegalStateException("Reported primitive array, but component isn't primitive");
				}
			} else {
				return new StoreFieldOption(var, opt, optObject, optStrings, var.getType(), numArgs);
			}
		} else if (var.getType() == Collection.class ||
				var.getType() == Iterable.class ||
				var.getType() == List.class ||
				var.getType() == ArrayList.class ||
				var.getType() == LinkedList.class ||
				var.getType() == Set.class) {

			// get a factory
			CollectionFactory<Object> factory;
			if (var.getType().isInstance(Set.class)) {
				factory = new CollectionFactories.HashSetFactory<Object>();
			} else if (var.getType().isInstance(LinkedList.class)){
				factory = new CollectionFactories.LinkedListFactory<Object>();
			} else {
				factory = new CollectionFactories.ArrayListFactory<Object>();
			}

			// Get the inner type
			Class<?> innerType = (Class<?>) ((ParameterizedType) var.getGenericType()).getActualTypeArguments()[0];
			
			return new StoreCollectionOption(var, opt, optObject, optStrings, factory, innerType, numArgs);
		} else {
			Class<?> type = ArgumentParser.toWrapper(var.getType());

			if (!opt.choices()[0].equals("")) 
				return new StoreChoicesOption(p, var, opt, optObject, optStrings, type, numArgs);
				
			return new StoreFieldOption(var, opt, optObject, optStrings, type, numArgs);
		}
	}
}