package xyz.ren.file.utils.encryption;



import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

public class AESUtils {

    public final static int AES_SIEZ_1=128;
    public final static int AES_SIEZ_2=192;
    public final static int AES_SIEZ_3=256;


    private SecretKeySpec keySpec;
    private IvParameterSpec iv;

    public AESUtils(byte[] aesKey, byte[] iv) {
        if (aesKey == null || aesKey.length < 16 || (iv != null && iv.length < 16)) {
            throw new RuntimeException("错误的初始密钥");
        }
        if (iv == null) {
            iv = Md5Util.computeMD5(aesKey);
        }
        keySpec = new SecretKeySpec(aesKey, "AES");
        this.iv = new IvParameterSpec(iv);
    }

    public AESUtils(byte[] aesKey) {
        if (aesKey == null || aesKey.length < 16) {
            throw new RuntimeException("错误的初始密钥");
        }
        keySpec = new SecretKeySpec(aesKey, "AES");
        this.iv = new IvParameterSpec(Md5Util.computeMD5(aesKey));
    }

    public AESUtils(String aesKey) {
        this(Base64Util.decode(aesKey));
    }
    /**
     * 加密
     * @param data
     * @return
     */
    public byte[] encrypt(byte[] data) {
        byte[] result = null;
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/CFB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
            result = cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 解密
     * @param secret
     * @return
     */
    public byte[] decrypt(byte[] secret) {
        byte[] result = null;
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/CFB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
            result = cipher.doFinal(secret);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    //随机生成指定长度key size 必须为 128  192  265
    public static byte[] randomKey(int size) {
        byte[] result = null;
        try {
            KeyGenerator gen = KeyGenerator.getInstance("AES");
            gen.init(size, new SecureRandom());
            SecretKey key = gen.generateKey();
            byte[] encoded = key.getEncoded();
            result = gen.generateKey().getEncoded();
        } catch (Exception e) {
            throw new RuntimeException("生成秘钥失败");
        }
        return result;
    }
    //随机生成指定长度key size 必须为 128  192  265
    public static byte[] randomKey() {
        return randomKey(AES_SIEZ_1);
    }

}
