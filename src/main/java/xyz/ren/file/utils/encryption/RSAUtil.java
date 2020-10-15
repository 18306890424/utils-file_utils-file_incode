package xyz.ren.file.utils.encryption;



import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class RSAUtil {

    public final static int PUBLIC = 0;
    public final static int PRIVATE = 1;

    private PrivateKey privateKey;
    private PublicKey publicKey;
    private KeyPair keyPair;

    public RSAUtil() throws Exception {

        this.keyPair = createKeyPair();
        this.publicKey = this.keyPair.getPublic();
        this.privateKey =  this.keyPair.getPrivate();
    }



    public RSAUtil(KeyPair keyPair) throws Exception {
        this(keyPair.getPublic(),keyPair.getPrivate());
        this.keyPair = keyPair;
    }

    private RSAUtil(PublicKey publicKey, PrivateKey privateKey) throws Exception {
         this.publicKey = publicKey;
         this.privateKey = privateKey;
    }

    public RSAUtil(PublicKey publicKey ) throws Exception {
        this(publicKey,null);
    }
    public RSAUtil(PrivateKey privateKey) throws Exception {
        this(null,privateKey);

    }

    public RSAUtil(String key,int keytype) throws Exception {
        if (keytype==PUBLIC){
            PublicKey publicKey = string2PublicKey(key);
            this.publicKey = publicKey;
        }else if (keytype==PRIVATE){
            PrivateKey privateKey = string2PrivateKey(key);
            this.privateKey = privateKey;
        }else{
            throw  new RuntimeException("key类型错误");
        }
    }




    //生成秘钥对
    private  KeyPair createKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return  keyPairGenerator.generateKeyPair();

    }

    //获取公钥(Base64编码)
    private   String getPublicKeyString(KeyPair keyPair){
        PublicKey publicKey = keyPair.getPublic();
        byte[] bytes = publicKey.getEncoded();
        return Base64Util.encodeToString(bytes);
    }

    //获取私钥(Base64编码)
    private  String getPrivateKeyString(KeyPair keyPair){
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] bytes = privateKey.getEncoded();
        return Base64Util.encodeToString(bytes);
    }

    //将Base64编码后的公钥转换成PublicKey对象
    private  PublicKey string2PublicKey(String pubStr) throws Exception{
        byte[] keyBytes =Base64Util.decode(pubStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return   keyFactory.generatePublic(keySpec);

    }

    //将Base64编码后的私钥转换成PrivateKey对象
    private  PrivateKey string2PrivateKey(String priStr) throws Exception{
        byte[] keyBytes = Base64Util.decode(priStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return  keyFactory.generatePrivate(keySpec);

    }


    /**
     * 公钥加密
     * @param content
     * @return   生成base64编码格式
     * @throws Exception
     */
    public  byte[] publicEncrypt(byte[] content ) throws Exception{
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bytes = cipher.doFinal(content);
        return bytes;
    }

    /**
     * 私钥解密（生成base64编码格式）
     * @param content
     * @return  base64编码格式
     * @throws Exception
     */
    public  byte[] privateDecrypt(byte[] content ) throws Exception{
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bytes = cipher.doFinal(content);
        return bytes;
    }


    /**
     * 签名
     * @param content
     * @return
     */
    public byte[] sign(byte[] content) {
        if (!hasPrivateKey()) {
            throw new RuntimeException("private key is null.");
        }
        if (content == null) {
            return null;
        }
        try {
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initSign(privateKey);
            signature.update(content);
            return signature.sign();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 认证
     * @param sign  签名
     * @param content
     * @return
     */
    public boolean verify(byte[] sign, byte[] content) {
        if (!hasPublicKey()) {
            throw new RuntimeException("public key is null.");
        }
        if (sign == null || content == null) {
            return false;
        }
        try {
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initVerify(publicKey);
            signature.update(content);
            return signature.verify(sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取公钥(Base64编码)
     * @return
     */
    public String getPublicKeyString(){
        if (hasPublicKey()){
            byte[] bytes = publicKey.getEncoded();
            return Base64Util.encodeToString(bytes);
        }else{
            throw new RuntimeException("获取私钥失败,暂无相关私钥信息");
        }
    }

    /**
     * 获取私钥(Base64编码)
     * @return
     */
    private  String getPrivateKeyString(){
        if (hasPrivateKey()){
            byte[] bytes = privateKey.getEncoded();
            return Base64Util.encodeToString(bytes);
        }else{
            throw new RuntimeException("获取私钥失败,暂无相关私钥信息");
        }
    }


    /**
     * 查询是否有公钥
     * @return
     */
    private boolean hasPublicKey(){
        if (this.keyPair==null&&this.publicKey==null) {
            return  false;
        }else{
            return  true;
        }
    }

    /**
     * 查询是否有私钥
     * @return
     */
    private boolean hasPrivateKey(){
        if (this.keyPair==null&&this.privateKey==null) {
            return  false;
        }else{
            return  true;
        }
    }
}