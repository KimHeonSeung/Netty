package com.devheon.util.singleton;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <pre>
 * Description :
 *     SHA256 암호화 관련 클래스 - Singleton
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2020-04-01
 * </pre>
 */
public class Sha256Helper {
    /* Sigleton */
    private static Sha256Helper instance;
    public static Sha256Helper getInstance() {
        if(instance == null) instance = new Sha256Helper();
        return instance;
    }
    /* Sigleton */

    private final String HASH = "SHA-256";

    /**
     * <pre>
     * Description
     *     해시코드 반환
     * ===============================================
     * Parameters
     *     Obejct -> String or File
     * Returns
     *     String hashCode
     * Throws
     *     Exception
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2020-04-01
     * </pre>
     */
    public String getHashCode(Object obj) throws Exception {
        String result = null;

        if(obj instanceof String) {
            result = generateHashCode((String) obj);
        } else if(obj instanceof File) {
            result = generateHashCode((File) obj);
        } else {
            throw new Exception("Unknown Hash target.");
        }


        return result;
    }

    /**
     * <pre>
     * Description
     *     salt 값과 함께 해시코드 반환
     * ===============================================
     * Parameters
     *     String str  : 해시 대상
     *     String salt : 솔트
     * Returns
     *     String hashCode
     * Throws
     *     none
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2020-04-01
     * </pre>
     */
    public String getHashCodeWithSalt(String str, String salt) {
        final byte[] saltByte = salt.getBytes();

        String result = null;

        try {

            final StringBuffer sb = new StringBuffer();

            for (byte b : saltByte)
                sb.append(String.format("%02x", b));	            	/* byte -> Hex */

            byte[] a = str.getBytes();									/* original value */
            byte[] bytes = new byte[a.length + saltByte.length];		/* salt */

            System.arraycopy(a, 0, bytes, 0, a.length);
            System.arraycopy(saltByte, 0, bytes, a.length, saltByte.length);

            final MessageDigest sh = MessageDigest.getInstance(HASH);
            sh.update(bytes);

            result = convertHashBytesToString(sh.digest());

        } catch (NoSuchAlgorithmException | NullPointerException e ) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * <pre>
     * Description
     *     문자열 대상의 해시값을 생성
     * ===============================================
     * Parameters
     *     String str : 해시 대상 문자열
     * Returns
     *     String hashCode
     * Throws
     *     NoSuchAlgorithmException
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2020-04-01
     * </pre>
     */
    private String generateHashCode(String str) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(HASH);
        md.update(str.getBytes());
        return convertHashBytesToString(md.digest());
    }

    /**
     * <pre>
     * Description
     *     파일 대상의 해시값을 생성
     * ===============================================
     * Parameters
     *     File file : 대상 파일
     * Returns
     *     String hashCode
     * Throws
     *     NoSuchAlgorithmException
     *     IOException
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2020-04-01
     * </pre>
     */
    private String generateHashCode(File file) throws NoSuchAlgorithmException, IOException {
        int bufferSize = 16 * 1024;

        MessageDigest md = MessageDigest.getInstance(HASH);

        InputStream is = new FileInputStream(file);
        int read = 0;
        byte[] buffer = new byte[bufferSize];

        while(read != -1) {
            read = is.read(buffer);
            if(read > 0) md.update(buffer, 0, read);
        }

        is.close();

        return convertHashBytesToString(md.digest());
    }

    /**
     * <pre>
     * Description
     *     해시 바이트를 문자열로 변환 (digest)
     * ===============================================
     * Parameters
     *     byte[] hashByte : 문자열로 변환할 해시 바이트 대상
     * Returns
     *     String hashCode
     * Throws
     *     none
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2020-04-01
     * </pre>
     */
    private String convertHashBytesToString(byte[] hashBytes) {

        StringBuffer sbResult = new StringBuffer();

        for(int i = 0 ; i < hashBytes.length ; ++i) {
            sbResult.append(
                    Integer.toString( (hashBytes[i]&0xff) + 0x100, 16 ).substring(1)
            );
        }

        return sbResult.toString();
    }

}
