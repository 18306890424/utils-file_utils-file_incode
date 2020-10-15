package xyz.ren.file.utils.common;


import xyz.ren.file.utils.encryption.AESUtils;
import xyz.ren.file.utils.encryption.Base64Util;

import java.io.*;

public class FileBytesUtils {


    /**
     * 读取文件bite
     * @param file
     * @return
     * @throws Exception
     */
    public static byte[] getFileBite(File file) throws Exception {
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return buffer;

    }
    /**
     * 将字符保存文本文件
     * @param buffer
     * @param targetPath
     * @throws Exception
     */

    public static void toFile(byte[] buffer, String targetPath)
            throws Exception {
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }

    public static void main(String[] args) {
        try {

            checkFileExit("D:\\fileTest\\out1.txt");
            checkFileExit("D:\\fileTest\\out2.txt");
            checkFileExit("D:\\fileTest\\out3.txt");
            checkFileExit("D:\\fileTest\\out4.txt");
            checkFileExit("D:\\fileTest\\out5.txt");
            checkFileExit("D:\\fileTest\\out6.txt");

            byte[] fileBite = getFileBite( new File("D:\\fileTest\\test.txt"));
            System.out.println("读取文件bite");
            System.out.println(new String(fileBite));
            toFile(fileBite, "D:\\fileTest\\out1.txt");//源文件base64

            byte[] encode = Base64Util.encode(fileBite);
            System.out.println("文件base64加密");
            System.out.println(new String(encode));
            toFile(encode, "D:\\fileTest\\out2.txt");//源文件base64

            byte[] decode = Base64Util.decode(encode);
            System.out.println("文件base64解密");
            System.out.println(new String(decode));
            toFile(decode, "D:\\fileTest\\out3.txt");//源文件base64

            byte[] randomKey = AESUtils.randomKey();
            System.out.println("生成秘钥");
            System.out.println(Base64Util.encodeToString(randomKey));
           // toFile(fileBite, "D:\\fileTest\\out4.txt");//源文件base64

            AESUtils aesUtils = new AESUtils(randomKey);
            System.out.println("文件加密");
            byte[] encrypt = aesUtils.encrypt(fileBite);//文件加密
            System.out.println(new String(encrypt));
            toFile(encrypt, "D:\\fileTest\\out5.txt");//源文件base64


            byte[] decrypt = aesUtils.decrypt(encrypt);
            System.out.println("文件解密");
            System.out.println(new String(decrypt));
            toFile(decrypt, "D:\\fileTest\\out6.txt");//源文件base64

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    private static void checkFileExit(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()){
            File parentFile = file.getParentFile();
            parentFile.mkdirs();
            file.createNewFile();
        }else{
            file.delete();
            file.createNewFile();
        }
    }


    public static void close(FileInputStream fis,BufferedInputStream bfis,

                            FileOutputStream os,BufferedOutputStream bos){

            if (bos != null) {

                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bfis != null) {
                try {
                    bfis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


    }
}
