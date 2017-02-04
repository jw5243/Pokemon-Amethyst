package com.horse.utility.MapBuilders.ObjectKey;

import gnu.trove.map.hash.TObjectIntHashMap;

public class ObjectIntMapBuilder<K> {
    private TObjectIntHashMap<K> map;
    
    public ObjectIntMapBuilder(TObjectIntHashMap<K> map) {
        this.map = map;
    }
    
    public static <K> ObjectIntMapBuilder<K> unordered() {
        return new ObjectIntMapBuilder<>(new TObjectIntHashMap<>());
    }
    
    public ObjectIntMapBuilder<K> put(K key, int value) {
        if(map == null) {
            throw new IllegalStateException();
        }
        map.put(key, value);
        return this;
    }
    
    public ObjectIntMapBuilder<K> put(TObjectIntHashMap<K> other) {
        if(map == null) {
            throw new IllegalStateException();
        }
        map.putAll(other);
        return this;
    }
    
    public TObjectIntHashMap<K> build() {
        TObjectIntHashMap<K> m = map;
        map = null;
        return new TObjectIntHashMap<>(m);
    }
}
