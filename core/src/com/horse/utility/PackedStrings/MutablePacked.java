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
            return;
        }
        final float quarterArLength     = ar.length / 4f;
        final int   arraySizeAllocation = quarterArLength % 1f != 0 ? (int)(quarterArLength) + 1 : (int)(quarterArLength);
        final int   originalSize        = (getStringStorage() != null ? getStringStorage().length : 0);
        int[]       stringStorage       = new int[arraySizeAllocation + originalSize];
        for(int index = 0; index < stringStorage.length; index++) {
            if(getStringStorage() != null && index < getStringStorage().length) {
                stringStorage[index] = getStringStorage()[index];
            } else {
                int quadrupleIndex = index << 2;
                if(getStringStorage() == null) {
                    stringStorage[index] = get(ar, quadrupleIndex + 3) | get(ar, quadrupleIndex + 2) << 8 | get(ar, quadrupleIndex + 1) << 16 | get(ar, quadrupleIndex) << 24;
                } else {
                    stringStorage[index] = get(ar, quadrupleIndex + 3 - getStringStorage().length) | get(ar, quadrupleIndex + 2 - getStringStorage().length) << 8 |
                                           get(ar, quadrupleIndex + 1 - getStringStorage().length) << 16 | get(ar, quadrupleIndex - getStringStorage().length) << 24;
                }
            }
        }
        setStringStorage(stringStorage);
        setMaxLength(getStringStorage().length);
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
