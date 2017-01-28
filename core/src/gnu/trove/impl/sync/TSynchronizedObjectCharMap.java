///////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2008, Robert D. Eden All Rights Reserved.
// Copyright (c) 2009, Jeff Randall All Rights Reserved.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
///////////////////////////////////////////////////////////////////////////////

package gnu.trove.impl.sync;


//////////////////////////////////////////////////
// THIS IS A GENERATED CLASS. DO NOT HAND EDIT! //
//////////////////////////////////////////////////

////////////////////////////////////////////////////////////
// THIS IS AN IMPLEMENTATION CLASS. DO NOT USE DIRECTLY!  //
// Access to these methods should be through TCollections //
////////////////////////////////////////////////////////////


import gnu.trove.TCharCollection;
import gnu.trove.function.TCharFunction;
import gnu.trove.iterator.TObjectCharIterator;
import gnu.trove.map.TObjectCharMap;
import gnu.trove.procedure.TCharProcedure;
import gnu.trove.procedure.TObjectCharProcedure;
import gnu.trove.procedure.TObjectProcedure;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;


public class TSynchronizedObjectCharMap<K> implements TObjectCharMap<K>, Serializable {
    private static final long serialVersionUID = 1978198479659022715L;
    final         Object            mutex;    // Object on which to synchronize
    private final TObjectCharMap<K> m;     // Backing Map
    private transient Set<K>          keySet = null;
    private transient TCharCollection values = null;
    
    public TSynchronizedObjectCharMap(TObjectCharMap<K> m) {
        if(m == null) {
            throw new NullPointerException();
        }
        this.m = m;
        mutex = this;
    }
    
    public TSynchronizedObjectCharMap(TObjectCharMap<K> m, Object mutex) {
        this.m = m;
        this.mutex = mutex;
    }
    
    // unchanging over the life of the map, no need to lock
    public char getNoEntryValue() {
        return m.getNoEntryValue();
    }
    
    public int size() {
        synchronized(mutex) {
            return m.size();
        }
    }
    
    public boolean isEmpty() {
        synchronized(mutex) {
            return m.isEmpty();
        }
    }
    
    public boolean containsKey(Object key) {
        synchronized(mutex) {
            return m.containsKey(key);
        }
    }
    
    public boolean containsValue(char value) {
        synchronized(mutex) {
            return m.containsValue(value);
        }
    }
    
    public char get(Object key) {
        synchronized(mutex) {
            return m.get(key);
        }
    }
    
    public char put(K key, char value) {
        synchronized(mutex) {
            return m.put(key, value);
        }
    }
    
    public char putIfAbsent(K key, char value) {
        synchronized(mutex) {
            return m.putIfAbsent(key, value);
        }
    }
    
    public char remove(Object key) {
        synchronized(mutex) {
            return m.remove(key);
        }
    }
    
    public void putAll(Map<? extends K, ? extends Character> map) {
        synchronized(mutex) {
            m.putAll(map);
        }
    }
    
    public void putAll(TObjectCharMap<? extends K> map) {
        synchronized(mutex) {
            m.putAll(map);
        }
    }
    
    public void clear() {
        synchronized(mutex) {
            m.clear();
        }
    }
    
    public Set<K> keySet() {
        synchronized(mutex) {
            if(keySet == null) {
                keySet = new SynchronizedSet<K>(m.keySet(), mutex);
            }
            return keySet;
        }
    }
    
    public Object[] keys() {
        synchronized(mutex) {
            return m.keys();
        }
    }
    
    public K[] keys(K[] array) {
        synchronized(mutex) {
            return m.keys(array);
        }
    }
    
    public TCharCollection valueCollection() {
        synchronized(mutex) {
            if(values == null) {
                values = new TSynchronizedCharCollection(m.valueCollection(), mutex);
            }
            return values;
        }
    }
    
    public char[] values() {
        synchronized(mutex) {
            return m.values();
        }
    }
    
    public char[] values(char[] array) {
        synchronized(mutex) {
            return m.values(array);
        }
    }
    
    public TObjectCharIterator<K> iterator() {
        return m.iterator(); // Must be manually synched by user!
    }
    
    public boolean increment(K key) {
        synchronized(mutex) {
            return m.increment(key);
        }
    }
    
    public boolean adjustValue(K key, char amount) {
        synchronized(mutex) {
            return m.adjustValue(key, amount);
        }
    }
    
    public char adjustOrPutValue(K key, char adjust_amount, char put_amount) {
        synchronized(mutex) {
            return m.adjustOrPutValue(key, adjust_amount, put_amount);
        }
    }
    
    public boolean forEachKey(TObjectProcedure<? super K> procedure) {
        synchronized(mutex) {
            return m.forEachKey(procedure);
        }
    }
    
    public boolean forEachValue(TCharProcedure procedure) {
        synchronized(mutex) {
            return m.forEachValue(procedure);
        }
    }
    
    public boolean forEachEntry(TObjectCharProcedure<? super K> procedure) {
        synchronized(mutex) {
            return m.forEachEntry(procedure);
        }
    }
    
    public void transformValues(TCharFunction function) {
        synchronized(mutex) {
            m.transformValues(function);
        }
    }
    
    public boolean retainEntries(TObjectCharProcedure<? super K> procedure) {
        synchronized(mutex) {
            return m.retainEntries(procedure);
        }
    }
    
    public int hashCode() {
        synchronized(mutex) {
            return m.hashCode();
        }
    }
    
    public boolean equals(Object o) {
        synchronized(mutex) {
            return m.equals(o);
        }
    }
    
    public String toString() {
        synchronized(mutex) {
            return m.toString();
        }
    }
    
    private void writeObject(ObjectOutputStream s) throws IOException {
        synchronized(mutex) {
            s.defaultWriteObject();
        }
    }
}
