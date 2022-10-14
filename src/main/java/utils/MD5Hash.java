package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Hash  extends Object {

    /**
     * Private function to turn md5 result to 32 hex-digit string.
     * @param hash
     * @return
     */
    private static String asHex(byte[] hash) {
        StringBuffer buf = new StringBuffer(hash.length * 2);
        int i;

        for (i = 0; i < hash.length; i++) {
            if (((int) hash[i] & 0xff) < 0x10) {
                buf.append("0");
            }

            buf.append(Long.toString((int) hash[i] & 0xff, 16));
        }

        return buf.toString().toLowerCase();
    }

    /**
     * Take a string and return its md5 hash as a hex digit string.
     * @param arg
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String hash(String arg) throws NoSuchAlgorithmException {
        return hash(arg.getBytes());
    }

    /**
     * Non static version for VB.
     * @param arg
     * @return
     * @throws NoSuchAlgorithmException
     */
    public String doHash(String arg) throws NoSuchAlgorithmException {
        return hash(arg.getBytes());
    }

    /**
     * Take a byte array and return its md5 hash as a hex digit string.
     * @param barray
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String hash(byte[] barray) throws NoSuchAlgorithmException {
        String restring = "";
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(barray);
        byte[] result = md.digest();
        restring = asHex(result);
        return restring;
    }
}
