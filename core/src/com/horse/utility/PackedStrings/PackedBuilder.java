package com.horse.utility.PackedStrings;

import com.horse.utility.PackedStrings.Immutable.Packed28;
import org.openjdk.jol.info.GraphLayout;

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
        System.out.println(GraphLayout.parseInstance(packedBuilder.getMutablePacked()).toFootprint());
        //System.out.println(MemoryCalculator.sizeOf(packedBuilder.getMutablePacked()));
        
        String stringValue = "TestSecondTestTestSecondTest";
        System.out.println(GraphLayout.parseInstance(stringValue).toFootprint());
        //System.out.println(MemoryCalculator.sizeOf(stringValue));
        
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Test").append("SecondTest");
        System.out.println(GraphLayout.parseInstance(stringBuilder).toFootprint());
        //System.out.println(MemoryCalculator.sizeOf(stringBuilder));
        
        PackedBase packedBase = new Packed28(convertToEncodedBytes("TestSecondTesterTestSecondTest"));
        System.out.println(GraphLayout.parseInstance(packedBase).toFootprint());
        //System.out.println(MemoryCalculator.sizeOf(packedBase));
        
        PackedBuilder packedBuilder1 = new PackedBuilder();
        packedBuilder1.append(new MutablePacked(convertToEncodedBytes("Test"))).append("Test")
                      .append(new char[] {'a', 'b', 'c'}).append(1).append(1f);
        System.out.println(packedBuilder1.toString());
        System.out.println(GraphLayout.parseInstance(packedBuilder1).toFootprint());
        //System.out.println(MemoryCalculator.sizeOf(packedBuilder1));
    }
    
    public MutablePacked getMutablePacked() {
        return mutablePacked;
    }
    
    public void setMutablePacked(MutablePacked mutablePacked) {
        this.mutablePacked = mutablePacked;
    }
    
    public PackedBuilder append(MutablePacked value) {
        getMutablePacked().appendToStorage(value.getStringStorage());
        return this;
    }
    
    public PackedBuilder append(String value) {
        getMutablePacked().setupValues(convertToEncodedBytes(value));
        return this;
    }
    
    public PackedBuilder append(char[] value) {
        return append(new String(value));
    }
    
    public PackedBuilder append(byte value) {
        return append(String.valueOf(value));
    }
    
    public PackedBuilder append(short value) {
        return append(String.valueOf(value));
    }
    
    public PackedBuilder append(int value) {
        /*if (value == Integer.MIN_VALUE)
            return "-2147483648";
        int size = (value < 0) ? Integer.stringSize(-value) + 1 : stringSize(value);
        char[] buf = new char[size];
        getChars(value, size, buf);
        return new String(buf, true);*/
        return append(String.valueOf(value));
    }
    
    public PackedBuilder append(float value) {
        return append(String.valueOf(value));
    }
    
    public PackedBuilder append(long value) {
        return append(String.valueOf(value));
    }
    
    public PackedBuilder append(double value) {
        return append(String.valueOf(value));
    }
    
    @Override
    public String toString() {
        return getMutablePacked().toString();
    }
}
