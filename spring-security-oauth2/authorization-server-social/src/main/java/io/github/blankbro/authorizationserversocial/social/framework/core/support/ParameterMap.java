/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.blankbro.authorizationserversocial.social.framework.core.support;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;

/**
 * Generally useful base class for creating MultiValueMaps that store HTTP query parameters.
 * May be subclassed to add specific getter/setter methods for known parameters used in a specific context.
 * Also makes it easy to adapt a Map&lt;String, List&lt;String&gt;&gt; to an MultiValueMap&lt;String, String&gt;.
 * @author Craig Walls
 */
public class ParameterMap implements MultiValueMap<String, String> {

	private final Map<String, List<String>> parameters;
	
	/**
	 * Creates a new MultiValueMap&lt;String, String&gt; that is initially empty.
	 */
	protected ParameterMap() {
		this(null);
	}

	/**
	 * Wraps the provided Map&lt;String, List&lt;String&gt;&gt; as a MultiValueMap&lt;String, String&gt;.
	 * The map passed in is stored internally.
	 * No copy is created
	 * @param parameters the parameters as a Map.
	 */
	protected ParameterMap(Map<String, List<String>> parameters) {
		if (parameters != null) {
			this.parameters = parameters;
		} else {
			this.parameters = new LinkedMultiValueMap<String, String>();
		}
	}
	
	// MultiValueMap method implementations
	
	public void clear() {
		parameters.clear();
	}

	public boolean containsKey(Object key) {
		return parameters.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return parameters.containsKey(value);
	}

	public Set<Entry<String, List<String>>> entrySet() {
		return parameters.entrySet();
	}

	public List<String> get(Object key) {
		return parameters.get(key);
	}

	public boolean isEmpty() {
		return parameters.isEmpty();
	}

	public Set<String> keySet() {
		return parameters.keySet();
	}

	public List<String> put(String key, List<String> values) {
		return parameters.put(key, values);
	}

	public void putAll(Map<? extends String, ? extends List<String>> map) {
		parameters.putAll(map);
	}

	public List<String> remove(Object key) {
		return parameters.remove(key);
	}

	public int size() {
		return parameters.size();
	}

	public Collection<List<String>> values() {
		return parameters.values();
	}

	public String getFirst(String key) {
		List<String> values = parameters.get(key);
		return values != null ? values.get(0) : null;
	}

	public void add(String key, String value) {
		List<String> values = parameters.get(key);
		if (values == null) {
			values = new LinkedList<String>();
			this.parameters.put(key, values);
		}
		values.add(value);
	}

	@Override
	public void addAll(String key, List<? extends String> newValues) {
		List<String> values = this.parameters.get(key);
		if (values == null) {
			values = new LinkedList<>();
			values.addAll(newValues);
		}
		this.parameters.put(key, values);
	}

	@Override
	public void addAll(MultiValueMap<String, String> values) {
		Set<Entry<String, List<String>>> entries = values.entrySet();
		for (Entry<String, List<String>> entry : entries) {
			this.addAll(entry.getKey(), entry.getValue());
		}
	}

	public void set(String key, String value) {
		List<String> values = new LinkedList<String>();
		values.add(value);
		parameters.put(key, values);
	}

	public void setAll(Map<String, String> values) {
		for (Entry<String, String> entry : values.entrySet()) {
			set(entry.getKey(), entry.getValue());
		}
	}

	public Map<String, String> toSingleValueMap() {
		Map<String, String> map = new LinkedHashMap<String, String>(this.parameters.size());
		for (Entry<String, List<String>> entry : parameters.entrySet()) {
			map.put(entry.getKey(), entry.getValue().get(0));
		}
		return map;
	}

}
