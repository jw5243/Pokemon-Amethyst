package com.horse.utility.OptimizedMaps.MapBuilders.DoubleKeys;

import com.horse.utility.OptimizedMaps.DoubleKeys.JIntIntIntMap;

public class IntIntIntMapBuilder {
    private JIntIntIntMap map;
    
    public IntIntIntMapBuilder(JIntIntIntMap map) {
        this.map = map;
    }
    
    public static IntIntIntMapBuilder unordered() {
        return new IntIntIntMapBuilder(new JIntIntIntMap());
    }
    
    public IntIntIntMapBuilder put(int firstKey, int secondKey, int value) {
        if(map == null) {
            throw new IllegalStateException();
        }
        map.put(firstKey, secondKey, value);
        return this;
    }
    
    public IntIntIntMapBuilder put(JIntIntIntMap other) {
        if(map == null) {
            throw new IllegalStateException();
        }
        map.putAll(other);
        return this;
    }
    
    public JIntIntIntMap build() {
        JIntIntIntMap m = map;
        map = null;
        return m;
    }
}
