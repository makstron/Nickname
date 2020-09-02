package com.klim.architecture.utils;

import java.security.SecureRandom;

public class UID implements java.io.Serializable, Comparable<UID> {
//    old code for test
//    var r = Db.getInstance().getBlob("select X'dd3fd980ea2e453bb4ae39fe39d9d428';")
//    var s = "dd3fd980ea2e453bb4ae39fe39d9d428"
//    var ba = ByteArray(16)
//    var ii = 0
//        for(i in 0 .. s.length - 2 step 2) {
//        var oneS = s.substring(i, i + 2)
//        var oneI = Integer.parseInt(oneS,16)
//        ba[ii] = oneI.toByte()
//        ii++
//    }
//
//    var uid = UID.randomUID()
//    var uid_ = UID.fromStringUnFormatted("d44c2e3392804af78db2e510305b9c46")
//    var ba_ = uid.toByteArray()

    private byte[] data;
    private static final long serialVersionUID = -4856846361193249489L;

    private final long mostSigBits;
    private final long leastSigBits;

    private static class Holder {
        static final SecureRandom numberGenerator = new SecureRandom();
    }

    public static UID randomUID() {
        return new UID(randomData());
    }

    public static UID timeRandomUID() {
        byte[] data = randomData();
        long currentTime = System.currentTimeMillis();
        data[0] = (byte) (currentTime >> 40 & 255);
        data[1] = (byte) (currentTime >> 32 & 255);
        data[2] = (byte) (currentTime >> 24 & 255);
        data[3] = (byte) (currentTime >> 16 & 255);
        data[4] = (byte) (currentTime >> 8 & 255);
        data[5] = (byte) (currentTime & 255);
        return new UID(data);
    }

    public UID(byte[] data) {
        long msb = 0;
        long lsb = 0;
        assert data.length == 16 : "data must be 16 bytes in length";
        for (int i = 0; i < 8; i++)
            msb = (msb << 8) | (data[i] & 0xff);
        for (int i = 8; i < 16; i++)
            lsb = (lsb << 8) | (data[i] & 0xff);
        this.mostSigBits = msb;
        this.leastSigBits = lsb;
    }

    public UID(long mostSigBits, long leastSigBits) {
        this.mostSigBits = mostSigBits;
        this.leastSigBits = leastSigBits;
    }

    private static byte[] randomData() {
        byte[] randomBytes = new byte[16];
        Holder.numberGenerator.nextBytes(randomBytes);
        randomBytes[6] &= 0x0f;  /* clear version        */
        randomBytes[6] |= 0x40;  /* set to version 4     */
        randomBytes[8] &= 0x3f;  /* clear variant        */
        randomBytes[8] |= 0x80;  /* set to IETF variant  */
        return randomBytes;
    }

    public static UID fromString(String name) {
        if (name.contains("-")) {
            return fromStringFormatted(name);
        } else {
            return fromStringUnFormatted(name);
        }
    }

    public static UID fromStringFormatted(String name) {
        String[] components = name.split("-");
        if (components.length != 5)
            throw new IllegalArgumentException("Invalid UID string: " + name);
        for (int i = 0; i < 5; i++)
            components[i] = "0x" + components[i];

        return fromStringComponents(components);
    }

    public static UID fromStringUnFormatted(String name) {
        String[] components = new String[5];
        components[0] = "0x" + name.substring(0, 8);
        components[1] = "0x" + name.substring(8, 12);
        components[2] = "0x" + name.substring(12, 16);
        components[3] = "0x" + name.substring(16, 20);
        components[4] = "0x" + name.substring(20);
        return fromStringComponents(components);
    }

    public static UID fromStringComponents(String[] components) {
        long mostSigBits = Long.decode(components[0]).longValue();
        mostSigBits <<= 16;
        mostSigBits |= Long.decode(components[1]).longValue();
        mostSigBits <<= 16;
        mostSigBits |= Long.decode(components[2]).longValue();

        long leastSigBits = Long.decode(components[3]).longValue();
        leastSigBits <<= 48;
        leastSigBits |= Long.decode(components[4]).longValue();

        return new UID(mostSigBits, leastSigBits);
    }

    // Field Accessor Methods

    public long getLeastSignificantBits() {
        return leastSigBits;
    }

    public long getMostSignificantBits() {
        return mostSigBits;
    }

    public int version() {
        // Version is bits masked by 0x000000000000F000 in MS long
        return (int) ((mostSigBits >> 12) & 0x0f);
    }

    public int variant() {
        // This field is composed of a varying number of bits.
        // 0    -    -    Reserved for NCS backward compatibility
        // 1    0    -    The IETF aka Leach-Salz variant (used by this class)
        // 1    1    0    Reserved, Microsoft backward compatibility
        // 1    1    1    Reserved for future definition.
        return (int) ((leastSigBits >>> (64 - (leastSigBits >>> 62)))
                & (leastSigBits >> 63));
    }

    public long timestamp() {
        if (version() != 1) {
            throw new UnsupportedOperationException("Not a time-based UID");
        }

        return (mostSigBits & 0x0FFFL) << 48
                | ((mostSigBits >> 16) & 0x0FFFFL) << 32
                | mostSigBits >>> 32;
    }

    public int clockSequence() {
        if (version() != 1) {
            throw new UnsupportedOperationException("Not a time-based UID");
        }

        return (int) ((leastSigBits & 0x3FFF000000000000L) >>> 48);
    }

    public long node() {
        if (version() != 1) {
            throw new UnsupportedOperationException("Not a time-based UID");
        }

        return leastSigBits & 0x0000FFFFFFFFFFFFL;
    }

    // Object Inherited Methods

    public byte[] toByteArray() {
        long msb = mostSigBits;
        long lsb = leastSigBits;
        byte[] data = new byte[16];
        for (int i = 0; i < 8; i++) {
            data[7 - i] = (byte) (msb & 0xFF);
            msb = msb >> 8;
        }
        for (int i = 8; i < 16; i++) {
            data[15 - (i - 8)] = (byte) (lsb & 0xFF);
            lsb = lsb >> 8;
        }

        return data;
    }

    public String toString() {
        return (digits(mostSigBits >> 32, 8) + "-" +
                digits(mostSigBits >> 16, 4) + "-" +
                digits(mostSigBits, 4) + "-" +
                digits(leastSigBits >> 48, 4) + "-" +
                digits(leastSigBits, 12));
    }

    public String toStringClr() {
        return (digits(mostSigBits >> 32, 8) +
                digits(mostSigBits >> 16, 4) +
                digits(mostSigBits, 4) +
                digits(leastSigBits >> 48, 4) +
                digits(leastSigBits, 12));
    }

    private static String digits(long val, int digits) {
        long hi = 1L << (digits * 4);
        return Long.toHexString(hi | (val & (hi - 1))).substring(1);
    }

    public int hashCode() {
        long hilo = mostSigBits ^ leastSigBits;
        return ((int) (hilo >> 32)) ^ (int) hilo;
    }

    public boolean equals(Object obj) {
        if ((null == obj) || (obj.getClass() != UID.class))
            return false;
        UID id = (UID) obj;
        return (mostSigBits == id.mostSigBits &&
                leastSigBits == id.leastSigBits);
    }

    // Comparison Operations

    public int compareTo(UID val) {
        // The ordering is intentionally set up so that the UIDs
        // can simply be numerically compared as two numbers
        return (this.mostSigBits < val.mostSigBits ? -1 :
                (this.mostSigBits > val.mostSigBits ? 1 :
                        (this.leastSigBits < val.leastSigBits ? -1 :
                                (this.leastSigBits > val.leastSigBits ? 1 :
                                        0))));
    }

    @Override
    public UID clone(){
        return new UID(mostSigBits, leastSigBits);
    }
}
