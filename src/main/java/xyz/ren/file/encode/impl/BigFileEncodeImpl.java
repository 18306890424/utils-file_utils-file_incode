package xyz.ren.file.encode.impl;

import xyz.ren.file.encode.FileEncode;
import xyz.ren.file.utils.common.FileBytesUtils;
import xyz.ren.file.utils.encryption.AESUtils;
import xyz.ren.file.utils.encryption.Base64Util;
import xyz.ren.file.utils.encryption.DateEncodeUtils;

import java.io.*;
import java.util.Arrays;
import java.util.Date;

class BigFileEncodeImpl implements FileEncode {

    @Override
    public void encodeFile(File file, String outPath) throws Exception {
        FileEncodeFactory.checkFileExit(outPath);

        FileInputStream fis = null;
        BufferedInputStream bfis = null;
        FileOutputStream os = null;
        BufferedOutputStream bos = null;
        try {
            fis = new FileInputStream(file);
            bfis = new BufferedInputStream(fis);
            os = new FileOutputStream(outPath);
            bos = new BufferedOutputStream(os);

            fileBase64InputAndOutput(bfis,bos);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            FileBytesUtils.close(fis,bfis,os,bos);
        }

    }


    @Override
    public void encodeFile(File file, String outPath, Date date) throws Exception {
        FileEncodeFactory.checkFileExit(outPath);

        FileInputStream fis = null;
        BufferedInputStream bfis = null;
        FileOutputStream os = null;
        BufferedOutputStream bos = null;
        try {
            fis = new FileInputStream(file);
            bfis = new BufferedInputStream(fis);
            os = new FileOutputStream(outPath);
            bos = new BufferedOutputStream(os);

            byte[] dateEncode = DateEncodeUtils.getEncode(date);
            bos.write(dateEncode);
            bos.flush();

            fileBase64InputAndOutput(bfis,bos);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            FileBytesUtils.close(fis,bfis,os,bos);
        }

    }

    @Override
    public String encodeFileWithCode(File file, String outPath) throws Exception {
        FileEncodeFactory.checkFileExit(outPath);

        FileInputStream fis = null;
        BufferedInputStream bfis = null;
        FileOutputStream os = null;
        BufferedOutputStream bos = null;

        try {
            fis = new FileInputStream(file);
            bfis = new BufferedInputStream(fis);
            os = new FileOutputStream(outPath);
            bos = new BufferedOutputStream(os);

            byte[] key = AESUtils.randomKey();
            AESUtils aesUtils = new AESUtils(key);

            fileInputAndOutput(bfis,bos,aesUtils);

            return Base64Util.encodeToString(key);

        }catch (Exception e){
            e.printStackTrace();
            throw  e;
        }finally {
            FileBytesUtils.close(fis,bfis,os,bos);
        }
    }

    @Override
    public String encodeFileWithCode(File file, String outPath, Date date) throws Exception {

        FileEncodeFactory.checkFileExit(outPath);

        FileInputStream fis = null;
        BufferedInputStream bfis = null;
        FileOutputStream os = null;
        BufferedOutputStream bos = null;

        try {
            fis = new FileInputStream(file);
            bfis = new BufferedInputStream(fis);
            os = new FileOutputStream(outPath);
            bos = new BufferedOutputStream(os);


            byte[] key = AESUtils.randomKey();
            AESUtils aesUtils = new AESUtils(key);

            byte[] dateEncode = DateEncodeUtils.getEncode(date);
            bos.write(aesUtils.encrypt(dateEncode));
            bos.flush();

            fileInputAndOutput(bfis,bos,aesUtils);
            return  Base64Util.encodeToString(key);

        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            FileBytesUtils.close(fis,bfis,os,bos);
        }


    }


    @Override
    public String encodeFileWithCode(File file, String outPath,String keyCode) throws Exception {

        FileEncodeFactory.checkFileExit(outPath);

        FileInputStream fis = null;
        BufferedInputStream bfis = null;
        FileOutputStream os = null;
        BufferedOutputStream bos = null;

        try {
            fis = new FileInputStream(file);
            bfis = new BufferedInputStream(fis);
            os = new FileOutputStream(outPath);
            bos = new BufferedOutputStream(os);


            AESUtils aesUtils = new AESUtils(keyCode);

            fileInputAndOutput(bfis,bos,aesUtils);

            return keyCode;

        }catch (Exception e){
            e.printStackTrace();
            throw  e;
        }finally {
            FileBytesUtils.close(fis,bfis,os,bos);
        }
    }

    @Override
    public String encodeFileWithCode(File file, String outPath,String keyCode, Date date) throws Exception {
        FileEncodeFactory.checkFileExit(outPath);

        FileInputStream fis = null;
        BufferedInputStream bfis = null;
        FileOutputStream os = null;
        BufferedOutputStream bos = null;

        try {
            fis = new FileInputStream(file);
            bfis = new BufferedInputStream(fis);
            os = new FileOutputStream(outPath);
            bos = new BufferedOutputStream(os);



            AESUtils aesUtils = new AESUtils(keyCode);

            byte[] dateEncode = DateEncodeUtils.getEncode(date);
            bos.write(aesUtils.encrypt(dateEncode));
            bos.flush();


            return keyCode;

        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            FileBytesUtils.close(fis,bfis,os,bos);
        }

    }


    private void fileBase64InputAndOutput(BufferedInputStream bfis,BufferedOutputStream bos) throws IOException {
        byte[] byteBuf = new byte[3 * 1024];
        byte[] base64ByteBuf;
        int count;
        while ((count = bfis.read(byteBuf)) != -1) {
            if (count != byteBuf.length) {//如果有效字节数不为3*1000，则说明文件已经读到尾了，不够填充满byteBuf了
                byte[] copy = Arrays.copyOf(byteBuf, count); //从byteBuf中截取包含有效字节数的字节段
                base64ByteBuf = Base64Util.encode(copy); //对有效字节段进行编码
            } else {
                base64ByteBuf = Base64Util.encode(byteBuf);
            }
            bos.write(base64ByteBuf, 0, base64ByteBuf.length);

        }
    }

    private void fileInputAndOutput(BufferedInputStream bfis,BufferedOutputStream bos,AESUtils aesUtils) throws IOException {

        byte[] byteBuf = new byte[4 * 1024];
        byte[] base64ByteBuf;
        int count;

        while ((count = bfis.read(byteBuf)) != -1) {
            if (count != byteBuf.length) {//如果有效字节数不为3*1000，则说明文件已经读到尾了，不够填充满byteBuf了
                byte[] copy = Arrays.copyOf(byteBuf, count); //从byteBuf中截取包含有效字节数的字节段
                base64ByteBuf = aesUtils.encrypt(copy);
            } else {
                base64ByteBuf = aesUtils.encrypt(byteBuf);
            }
            bos.write(base64ByteBuf, 0, base64ByteBuf.length);
        }
    }
}
