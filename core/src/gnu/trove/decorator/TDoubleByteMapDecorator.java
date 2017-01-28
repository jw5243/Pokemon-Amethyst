///////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2002, Eric D. Friedman All Rights Reserved.
// Copyright (c) 2009, Robert D. Eden All Rights Reserved.
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

package gnu.trove.decorator;

import gnu.trove.iterator.TDoubleByteIterator;
import gnu.trove.map.TDoubleByteMap;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


//////////////////////////////////////////////////
// THIS IS A GENERATED CLASS. DO NOT HAND EDIT! //
//////////////////////////////////////////////////


/**
 * Wrapper class to make a TDoubleByteMap conform to the <tt>java.util.Map</tt> API.
 * This class simply decorates an underlying TDoubleByteMap and translates the Object-based
 * APIs into their Trove primitive analogs.
 * <p/>
 * Note that wrapping and unwrapping primitive values is extremely inefficient.  If
 * possible, users of this class should override the appropriate methods in this class
 * and use a table of canonical values.
 * <p/>
 * Created: Mon Sep 23 22:07:40 PDT 2002
 *
 * @author Eric D. Friedman
 * @author Robert D. Eden
 * @author Jeff Randall
 */
public class TDoubleByteMapDecorator extends AbstractMap<Double, Byte> implements Map<Double, Byte>, Externalizable, Cloneable {
    
    static final long serialVersionUID = 1L;
    
    /**
     * the wrapped primitive map
     */
    protected TDoubleByteMap _map;
    
    
    /**
     * FOR EXTERNALIZATION ONLY!!
     */
    public TDoubleByteMapDecorator() {
    }
    
    
    /**
     * Creates a wrapper that decorates the specified primitive map.
     *
     * @param map the <tt>TDoubleByteMap</tt> to wrap.
     */
    public TDoubleByteMapDecorator(TDoubleByteMap map) {
        super();
        this._map = map;
    }
    
    
    /**
     * Returns a reference to the map wrapped by this decorator.
     *
     * @return the wrapped <tt>TDoubleByteMap</tt> instance.
     */
    public TDoubleByteMap getMap() {
        return _map;
    }
    
    /**
     * Returns the number of entries in the map.
     *
     * @return the map's size.
     */
    public int size() {
        return this._map.size();
    }
    
    /**
     * Indicates whether map has any entries.
     *
     * @return true if the map is empty
     */
    public boolean isEmpty() {
        return size() == 0;
    }
    
    /**
     * Checks for the presence of <tt>val</tt> in the values of the map.
     *
     * @param val an <code>Object</code> value
     *
     * @return a <code>boolean</code> value
     */
    public boolean containsValue(Object val) {
        return val instanceof Byte && _map.containsValue(unwrapValue(val));
    }
    
    /**
     * Checks for the present of <tt>key</tt> in the keys of the map.
     *
     * @param key an <code>Object</code> value
     *
     * @return a <code>boolean</code> value
     */
    public boolean containsKey(Object key) {
        if(key == null) {
            return _map.containsKey(_map.getNoEntryKey());
        }
        return key instanceof Double && _map.containsKey(unwrapKey(key));
    }
    
    /**
     * Retrieves the value for <tt>key</tt>
     *
     * @param key an <code>Object</code> value
     *
     * @return the value of <tt>key</tt> or null if no such mapping exists.
     */
    public Byte get(Object key) {
        double k;
        if(key != null) {
            if(key instanceof Double) {
                k = unwrapKey(key);
            } else {
                return null;
            }
        } else {
            k = _map.getNoEntryKey();
        }
        byte v = _map.get(k);
        // There may be a false positive since primitive maps
        // cannot return null, so we have to do an extra
        // check here.
        if(v == _map.getNoEntryValue()) {
            return null;
        } else {
            return wrapValue(v);
        }
    }
    
    /**
     * Inserts a key/value pair into the map.
     *
     * @param key   an <code>Object</code> value
     * @param value an <code>Object</code> value
     *
     * @return the previous value associated with <tt>key</tt>,
     * or Byte(0) if none was found.
     */
    public Byte put(Double key, Byte value) {
        double k;
        byte   v;
        if(key == null) {
            k = _map.getNoEntryKey();
        } else {
            k = unwrapKey(key);
        }
        if(value == null) {
            v = _map.getNoEntryValue();
        } else {
            v = unwrapValue(value);
        }
        byte retval = _map.put(k, v);
        if(retval == _map.getNoEntryValue()) {
            return null;
        }
        return wrapValue(retval);
    }
    
    /**
     * Deletes a key/value pair from the map.
     *
     * @param key an <code>Object</code> value
     *
     * @return the removed value, or null if it was not found in the map
     */
    public Byte remove(Object key) {
        double k;
        if(key != null) {
            if(key instanceof Double) {
                k = unwrapKey(key);
            } else {
                return null;
            }
        } else {
            k = _map.getNoEntryKey();
        }
        byte v = _map.remove(k);
        // There may be a false positive since primitive maps
        // cannot return null, so we have to do an extra
        // check here.
        if(v == _map.getNoEntryValue()) {
            return null;
        } else {
            return wrapValue(v);
        }
    }
    
    /**
     * Copies the key/value mappings in <tt>map</tt> into this map.
     * Note that this will be a <b>deep</b> copy, as storage is by
     * primitive value.
     *
     * @param map a <code>Map</code> value
     */
    public void putAll(Map<? extends Double, ? extends Byte> map) {
        Iterator<? extends Entry<? extends Double, ? extends Byte>> it = map.entrySet().iterator();
        for(int i = map.size(); i-- > 0; ) {
            Entry<? extends Double, ? extends Byte> e = it.next();
            this.put(e.getKey(), e.getValue());
        }
    }
    
    /**
     * Empties the map.
     */
    public void clear() {
        this._map.clear();
    }
    
    /**
     * Returns a Set view on the entries of the map.
     *
     * @return a <code>Set</code> value
     */
    public Set<Entry<Double, Byte>> entrySet() {
        return new AbstractSet<Entry<Double, Byte>>() {
            public Iterator<Entry<Double, Byte>> iterator() {
                return new Iterator<Entry<Double, Byte>>() {
                    private final TDoubleByteIterator it = _map.iterator();
                    
                    public boolean hasNext() {
                        return it.hasNext();
                    }
                    
                    public Entry<Double, Byte> next() {
                        it.advance();
                        double       ik  = it.key();
                        final Double key = (ik == _map.getNoEntryKey()) ? null : wrapKey(ik);
                        byte         iv  = it.value();
                        final Byte   v   = (iv == _map.getNoEntryValue()) ? null : wrapValue(iv);
                        return new Entry<Double, Byte>() {
                            private Byte val = v;
                            
                            public Double getKey() {
                                return key;
                            }
                            
                            public Byte getValue() {
                                return val;
                            }
                            
                            public Byte setValue(Byte value) {
                                val = value;
                                return put(key, value);
                            }
                            
                            public int hashCode() {
                                return key.hashCode() + val.hashCode();
                            }
                            
                            public boolean equals(Object o) {
                                return o instanceof Map.Entry && ((Entry)o).getKey().equals(key) && ((Entry)o).getValue().equals(val);
                            }
                        };
                    }
                    
                    public void remove() {
                        it.remove();
                    }
                };
            }
            
            public int size() {
                return _map.size();
            }
            
            public boolean isEmpty() {
                return TDoubleByteMapDecorator.this.isEmpty();
            }
            
            public boolean contains(Object o) {
                if(o instanceof Map.Entry) {
                    Object k = ((Entry)o).getKey();
                    Object v = ((Entry)o).getValue();
                    return TDoubleByteMapDecorator.this.containsKey(k) && TDoubleByteMapDecorator.this.get(k).equals(v);
                } else {
                    return false;
                }
            }
            
            public boolean add(Entry<Double, Byte> o) {
                throw new UnsupportedOperationException();
            }
            
            public boolean remove(Object o) {
                boolean modified = false;
                if(contains(o)) {
                    //noinspection unchecked
                    Double key = ((Entry<Double, Byte>)o).getKey();
                    _map.remove(unwrapKey(key));
                    modified = true;
                }
                return modified;
            }
            
            public boolean addAll(Collection<? extends Entry<Double, Byte>> c) {
                throw new UnsupportedOperationException();
            }
            
            public void clear() {
                TDoubleByteMapDecorator.this.clear();
            }
        };
    }
    
    /**
     * Wraps a key
     *
     * @param k key in the underlying map
     *
     * @return an Object representation of the key
     */
    protected Double wrapKey(double k) {
        return Double.valueOf(k);
    }
    
    
    /**
     * Unwraps a key
     *
     * @param key wrapped key
     *
     * @return an unwrapped representation of the key
     */
    protected double unwrapKey(Object key) {
        return ((Double)key).doubleValue();
    }
    
    
    /**
     * Wraps a value
     *
     * @param k value in the underlying map
     *
     * @return an Object representation of the value
     */
    protected Byte wrapValue(byte k) {
        return Byte.valueOf(k);
    }
    
    
    /**
     * Unwraps a value
     *
     * @param value wrapped value
     *
     * @return an unwrapped representation of the value
     */
    protected byte unwrapValue(Object value) {
        return ((Byte)value).byteValue();
    }
    
    // Implements Externalizable
    public void writeExternal(ObjectOutput out) throws IOException {
        // VERSION
        out.writeByte(0);
        
        // MAP
        out.writeObject(_map);
    }
    
    // Implements Externalizable
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        
        // VERSION
        in.readByte();
        
        // MAP
        _map = (TDoubleByteMap)in.readObject();
    }
    
} // TDoubleByteHashMapDecorator
