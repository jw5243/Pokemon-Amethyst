package com.horse.utility.PackedStrings;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

public class PackedBuilder {
    private static final Charset US_ASCII       = Charset.forName("US-ASCII");
    private static final int     DEFAULT_SIZE   = 16;
    private static final String  DEFAULT_STRING = "";
    private MutablePacked mutablePacked;
    
    public PackedBuilder() {
        this(getDefaultSize());
    }
    
    public PackedBuilder(int size) {
        this(size, getDefaultString());
    }
    
    public PackedBuilder(int size, String value) {
        setMutablePacked(new MutablePacked(convertToEncodedBytes(value)));
    }
    
    public static int getDefaultSize() {
        return DEFAULT_SIZE;
    }
    
    public static String getDefaultString() {
        return DEFAULT_STRING;
    }
    
    public static byte[] convertToEncodedBytes(final String str) {
        if(str == null || str.length() == 0 || str.length() > 52 || str.indexOf('\0') != -1) {
            return null;
        } else {
            final CharsetEncoder enc        = getUsAscii().newEncoder();
            final CharBuffer     charBuffer = CharBuffer.wrap(str);
            try {
                final ByteBuffer byteBuffer = enc.encode(charBuffer);
                return byteBuffer.array();
            } catch(CharacterCodingException e) {
                return null;
            }
        }
    }
    
    public static Charset getUsAscii() {
        return US_ASCII;
    }
    
    public static void main(String[] args) {
        PackedBuilder packedBuilder = new PackedBuilder();
        packedBuilder.append("Test").append("SecondTester");
        System.out.println(packedBuilder.toString());
    }
    
    public MutablePacked getMutablePacked() {
        return mutablePacked;
    }
    
    public void setMutablePacked(MutablePacked mutablePacked) {
        this.mutablePacked = mutablePacked;
    }
    
    public PackedBuilder append(String value) {
        getMutablePacked().setupValues(convertToEncodedBytes(value));
        return this;
    }
    
    public PackedBuilder append(int value) {
        getMutablePacked().setupValues(convertToEncodedBytes(String.valueOf(value)));
        return this;
    }
    
    @Override
    public String toString() {
        return getMutablePacked().toString();
    }
}
