package GTD.DL.DLDAO.util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Drugnanov on 4.1.2015.
 */
public final class HashConverter {

    /**
     * @param txt, text in plain format
     * @param hashType MD5 OR SHA1
     * @return hash in hashType
     */
    public static String getHash(String txt, String hashType)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        java.security.MessageDigest md = java.security.MessageDigest.getInstance(hashType);
        byte[] array = md.digest(txt.getBytes("UTF-8"));
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }

    public static String md5(String txt) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return HashConverter.getHash(txt, "MD5");
    }

    public static String sha1(String txt) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return HashConverter.getHash(txt, "SHA1");
    }

    private HashConverter() {
    }


}
