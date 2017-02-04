package com.horse.utility.PackedStrings;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class MutablePacked extends PackedBase {
    private int   maxLength;
    private int[] stringStorage;
    
    public MutablePacked(final byte[] ar) {
        setupValues(ar);
    }
    
    public int getMaxLength() {
        return maxLength;
    }
    
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
    
    public void setupValues(final byte[] ar) {
        if(ar == null) {
            setStringStorage(new int[] {});
            setMaxLength(0);
            return;
        }
        final float quarterArLength     = ar.length / 4f;
        final int arraySizeAllocation = quarterArLength % 1f != 0 ? (int)(quarterArLength) + 1 : (int)(quarterArLength);
        final int   originalSize        = getStringStorage() != null ? getStringStorage().length : 0;
        int[]       stringStorage       = new int[arraySizeAllocation + originalSize];
        for(int index = 0; index < stringStorage.length; index++) {
            if(getStringStorage() != null && index < getStringStorage().length) {
                stringStorage[index] = getStringStorage()[index];
            } else {
                final int quadrupleIndex      = index << 2;
                final int stringStorageLength = getStringStorage() != null ? getStringStorage().length << 2 : 0;
                stringStorage[index] = get(ar, quadrupleIndex + 3 - stringStorageLength) |
                                       get(ar, quadrupleIndex + 2 - stringStorageLength) << 8 |
                                       get(ar, quadrupleIndex + 1 - stringStorageLength) << 16 |
                                       get(ar, quadrupleIndex - stringStorageLength) << 24;
            }
        }
        setStringStorage(stringStorage);
        setMaxLength(getStringStorage().length);
    }
    
    public void appendToStorage(int[] value) {
        int[] stringStorage = new int[value.length + getStringStorage().length];
        for(int index = 0; index < stringStorage.length; index++) {
            stringStorage[index] = index < getStringStorage().length ? getStringStorage()[index] : value[index];
        }
        setStringStorage(stringStorage);
    }
    
    protected ByteBuffer toByteBuffer() {
        final ByteBuffer bbuf = ByteBuffer.allocate(getMaxLength() << 2);
        for(int storageValue : getStringStorage()) {
            bbuf.putInt(storageValue);
        }
        return bbuf;
    }
    
    public int[] getStringStorage() {
        return stringStorage;
    }
    
    public void setStringStorage(int[] stringStorage) {
        this.stringStorage = stringStorage;
    }
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(getStringStorage());
    }
    
    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        MutablePacked mutablePacked = (MutablePacked)(o);
        return getMaxLength() == mutablePacked.getMaxLength() && getStringStorage() == mutablePacked.getStringStorage();
    }
}
