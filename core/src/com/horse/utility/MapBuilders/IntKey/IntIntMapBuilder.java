package com.horse.utility.MapBuilders.IntKey;

import gnu.trove.map.hash.TIntIntHashMap;

public class IntIntMapBuilder {
    private TIntIntHashMap map;
    
    public IntIntMapBuilder(TIntIntHashMap map) {
        this.map = map;
    }
    
    public static IntIntMapBuilder unordered() {
        return new IntIntMapBuilder(new TIntIntHashMap());
    }
    
    public IntIntMapBuilder put(int key, int value) {
        if(map == null) {
            throw new IllegalStateException();
        }
        map.put(key, value);
        return this;
    }
    
    public IntIntMapBuilder put(TIntIntHashMap other) {
        if(map == null) {
            throw new IllegalStateException();
        }
        map.putAll(other);
        return this;
    }
    
    public TIntIntHashMap build() {
        TIntIntHashMap m = map;
        map = null;
        return new TIntIntHashMap(m);
    }
}
