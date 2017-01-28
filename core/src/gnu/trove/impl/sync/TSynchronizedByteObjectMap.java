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


import gnu.trove.function.TObjectFunction;
import gnu.trove.iterator.TByteObjectIterator;
import gnu.trove.map.TByteObjectMap;
import gnu.trove.procedure.TByteObjectProcedure;
import gnu.trove.procedure.TByteProcedure;
import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.set.TByteSet;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;


public class TSynchronizedByteObjectMap<V> implements TByteObjectMap<V>, Serializable {
    // use serialVersionUID from JDK 1.2.2 for interoperability
    private static final long serialVersionUID = 1978198479659022715L;
    final         Object            mutex;    // Object on which to synchronize
    private final TByteObjectMap<V> m;     // Backing Map
    private transient TByteSet      keySet = null;
    private transient Collection<V> values = null;
    
    public TSynchronizedByteObjectMap(TByteObjectMap<V> m) {
        if(m == null) {
            throw new NullPointerException();
        }
        this.m = m;
        mutex = this;
    }
    
    public TSynchronizedByteObjectMap(TByteObjectMap<V> m, Object mutex) {
        this.m = m;
        this.mutex = mutex;
    }
    
    // unchanging over the life of the map, no need to lock
    public byte getNoEntryKey() {
        return m.getNoEntryKey();
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
    
    public boolean containsKey(byte key) {
        synchronized(mutex) {
            return m.containsKey(key);
        }
    }
    
    public boolean containsValue(Object value) {
        synchronized(mutex) {
            return m.containsValue(value);
        }
    }
    
    public V get(byte key) {
        synchronized(mutex) {
            return m.get(key);
        }
    }
    
    public V put(byte key, V value) {
        synchronized(mutex) {
            return m.put(key, value);
        }
    }
    
    public V putIfAbsent(byte key, V value) {
        synchronized(mutex) {
            return m.putIfAbsent(key, value);
        }
    }
    
    public V remove(byte key) {
        synchronized(mutex) {
            return m.remove(key);
        }
    }
    
    public void putAll(Map<? extends Byte, ? extends V> map) {
        synchronized(mutex) {
            m.putAll(map);
        }
    }
    
    public void putAll(TByteObjectMap<? extends V> map) {
        synchronized(mutex) {
            m.putAll(map);
        }
    }
    
    public void clear() {
        synchronized(mutex) {
            m.clear();
        }
    }
    
    public TByteSet keySet() {
        synchronized(mutex) {
            if(keySet == null) {
                keySet = new TSynchronizedByteSet(m.keySet(), mutex);
            }
            return keySet;
        }
    }
    
    public byte[] keys() {
        synchronized(mutex) {
            return m.keys();
        }
    }
    
    public byte[] keys(byte[] array) {
        synchronized(mutex) {
            return m.keys(array);
        }
    }
    
    public Collection<V> valueCollection() {
        synchronized(mutex) {
            if(values == null) {
                values = new SynchronizedCollection<V>(m.valueCollection(), mutex);
            }
            return values;
        }
    }
    
    public Object[] values() {
        synchronized(mutex) {
            return m.values();
        }
    }
    
    public V[] values(V[] array) {
        synchronized(mutex) {
            return m.values(array);
        }
    }
    
    public TByteObjectIterator<V> iterator() {
        return m.iterator(); // Must be manually synched by user!
    }
    
    public boolean forEachKey(TByteProcedure procedure) {
        synchronized(mutex) {
            return m.forEachKey(procedure);
        }
    }
    
    public boolean forEachValue(TObjectProcedure<? super V> procedure) {
        synchronized(mutex) {
            return m.forEachValue(procedure);
        }
    }
    
    public boolean forEachEntry(TByteObjectProcedure<? super V> procedure) {
        synchronized(mutex) {
            return m.forEachEntry(procedure);
        }
    }
    
    public void transformValues(TObjectFunction<V, V> function) {
        synchronized(mutex) {
            m.transformValues(function);
        }
    }
    
    public boolean retainEntries(TByteObjectProcedure<? super V> procedure) {
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
