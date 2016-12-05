/*
 * Copyright (C) 2016.  BoBoMEe(wbwjx115@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.bobomee.android.common.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

/**
 * Multiple value (multiple values for one key) hash map.
 *
 * @author markzhai on 16/3/5
 */
public class MultiHashMap<K, V> implements Serializable {

    private static final long serialVersionUID = 2309170850883325510L;

    private final HashMap<K, HashMap<V, V>> backedMap = new HashMap<K, HashMap<V, V>>();

    /**
     * Returns the mapped value for key-value pair.
     *
     * @return The mapped value for key-value pair.
     */
    public V get(K key, V value) {
        HashMap<V, V> map = backedMap.get(key);
        return map == null ? null : map.get(value);
    }

    /**
     * Returns the value collection with corresponding key.
     *
     * @return the value collection with corresponding key.
     */
    public Collection<V> get(K key) {
        HashMap<V, V> map = backedMap.get(key);
        return map == null ? null : map.values();
    }

    /**
     * Put a key-value pair into this map.
     *
     * @return the value of any previous mapping with the specified key-value pair or
     * {@code null} if there was no such mapping.
     */
    public V put(K key, V value) {
        HashMap<V, V> map = backedMap.get(key);
        if (map == null) {
            map = new HashMap<>();
            backedMap.put(key, map);
        }
        return map.put(value, value);
    }

    /**
     * Put a key-value collection into this map.
     */
    public void put(K key, Collection<V> values) {
        if (values == null) {
            return;
        }
        HashMap<V, V> map = backedMap.get(key);
        if (map == null) {
            map = new HashMap<>();
            backedMap.put(key, map);
        }
        for (V value : values) {
            map.put(value, value);
        }
    }

    /**
     * Remove a key-value pair from this map.
     *
     * @return The removed mapped value.
     */
    public V remove(K key, V value) {
        HashMap<V, V> map = backedMap.get(key);
        return map == null ? null : map.remove(value);
    }

    /**
     * Remove collection with corresponding key.
     *
     * @return removed collection with corresponding key.
     */
    public Collection<V> remove(K key) {
        HashMap<V, V> map = backedMap.remove(key);
        return map == null ? null : map.values();
    }

    /**
     * Check whether corresponding key-value pair is contained in this map.
     *
     * @return Whether corresponding key-value pair is contained in this map.
     */
    public boolean contains(K key, V value) {
        HashMap<V, V> map = backedMap.get(key);
        return map != null && map.containsKey(value);
    }

    /**
     * Check whether corresponding key is contained in this map.
     *
     * @return Whether corresponding key is contained in this map.
     */
    public boolean contains(K key) {
        return backedMap.containsKey(key);
    }

    /**
     * Clear this map.
     */
    public void clear() {
        backedMap.clear();
    }

    /**
     * Returns whether this map is empty.
     *
     * @return Whether this map is empty.
     */
    public boolean isEmpty() {
        return backedMap.isEmpty();
    }

    /**
     * Returns the value number of corresponding key.
     *
     * @return The value number of corresponding key.
     */
    public int sizeOf(K key) {
        HashMap<V, V> map = backedMap.get(key);
        return map == null ? 0 : map.size();
    }

    /**
     * Returns the total size of this map.
     *
     * @return The total size of this map.
     */
    public int size() {
        int size = 0;
        for (HashMap<V, V> map : backedMap.values()) {
            size += map.size();
        }
        return size;
    }

    /**
     * Returns a set of the keys contained in this map. The set is backed by
     * this map so changes to one are reflected by the other. The set does not
     * support adding.
     *
     * @return a set of the keys.
     */
    public Set<K> keySet() {
        return backedMap.keySet();
    }
}
