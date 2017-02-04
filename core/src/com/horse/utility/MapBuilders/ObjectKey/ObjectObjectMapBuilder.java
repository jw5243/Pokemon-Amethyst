package com.horse.utility.MapBuilders.ObjectKey;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class ObjectObjectMapBuilder<K, V> {
    private Map<K, V> map;
    
    public ObjectObjectMapBuilder(Map<K, V> map) {
        this.map = map;
    }
    
    static <K, V> ObjectObjectMapBuilder<K, V> unordered() {
        return new ObjectObjectMapBuilder<>(new HashMap<>());
    }
    
    public static <K, V> ObjectObjectMapBuilder<K, V> ordered() {
        return new ObjectObjectMapBuilder<>(new LinkedHashMap<>());
    }
    
    public static <K extends Comparable<K>, V> ObjectObjectMapBuilder<K, V> sorted() {
        return new ObjectObjectMapBuilder<>(new TreeMap<>());
    }
    
    public static <K, V> ObjectObjectMapBuilder<K, V> sorted(Comparator<K> comparator) {
        return new ObjectObjectMapBuilder<>(new TreeMap<>(comparator));
    }
    
    ObjectObjectMapBuilder<K, V> put(K key, V value) {
        if(map == null) {
            throw new IllegalStateException();
        }
        map.put(key, value);
        return this;
    }
    
    public ObjectObjectMapBuilder<K, V> put(Map<? extends K, ? extends V> other) {
        if(map == null) {
            throw new IllegalStateException();
        }
        map.putAll(other);
        return this;
    }
    
    Map<K, V> build() {
        Map<K, V> m = map;
        map = null;
        return Collections.unmodifiableMap(m);
    }
}