package xyz.ren.file.decode.impl;

import xyz.ren.file.decode.FileDecode;
import xyz.ren.file.encode.impl.FileEncodeFactory;
import xyz.ren.file.utils.encryption.AESUtils;
import xyz.ren.file.utils.encryption.Base64Util;
import xyz.ren.file.utils.encryption.DateEncodeUtils;

import java.io.*;
import java.util.Arrays;
import java.util.Date;

class BigFileDecodeImpl implements FileDecode {

    @Override
    public void decodeFile(File file, String outPath) throws Exception {
        FileEncodeFactory.checkFileExit(outPath);
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bfis = new BufferedInputStream(fis);
        byte[] byteBuf = new byte[3*1024];
        byte[] base64ByteBuf;
        int count;
        FileOutputStream os = new FileOutputStream(outPath);
        BufferedOutputStream bos = new BufferedOutputStream(os);
        while((count = bfis.read(byteBuf)) != -1){
            if(count!=byteBuf.length){//如果有效字节数不为3*1000，则说明文件已经读到尾了，不够填充满byteBuf了
                byte[] copy = Arrays.copyOf(byteBuf, count); //从byteBuf中截取包含有效字节数的字节段
                base64ByteBuf = Base64Util.decode(copy); //对有效字节段进行编码
            }
            else{
                base64ByteBuf =Base64Util.decode(byteBuf);
            }
            bos.write(base64ByteBuf, 0, base64ByteBuf.length);

        }
        bos.close();
        os.close();
        bfis.close();
        fis.close();
    }

    @Override
    public void decodeFileWithDate(File file, String outPath) throws Exception {
        FileEncodeFactory.checkFileExit(outPath);
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bfis = new BufferedInputStream(fis);
        byte[] byteBuf = new byte[3*1024];
        byte[] base64ByteBuf;
        int count;
        FileOutputStream os = new FileOutputStream(outPath);
        BufferedOutputStream bos = new BufferedOutputStream(os);

        byte[] dateEncode =DateEncodeUtils.getByteArr();
        bfis.read(dateEncode);
        Date date = DateEncodeUtils.getDecode(dateEncode);
        if (DateEncodeUtils.isvalid(date)) {

            while ((count = bfis.read(byteBuf)) != -1) {
                if (count != byteBuf.length) {//如果有效字节数不为3*1000，则说明文件已经读到尾了，不够填充满byteBuf了
                    byte[] copy = Arrays.copyOf(byteBuf, count); //从byteBuf中截取包含有效字节数的字节段
                    base64ByteBuf = Base64Util.decode(copy); //对有效字节段进行编码
                } else {
                    base64ByteBuf = Base64Util.decode(byteBuf);
                }
                bos.write(base64ByteBuf, 0, base64ByteBuf.length);

            }
            bos.close();
            os.close();
            bfis.close();
            fis.close();

        }else{
            throw  new RuntimeException("文件超出可使用时间");
        }
    }

    @Override
    public String decodeFileWithCode(File file, String outPath,String code) throws Exception {
        FileEncodeFactory.checkFileExit(outPath);

        AESUtils aesUtils = new AESUtils(code);

        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bfis = new BufferedInputStream(fis);
        byte[] byteBuf = new byte[4*1024];
        byte[] base64ByteBuf;
        int count;

        FileOutputStream os = new FileOutputStream(outPath);
        BufferedOutputStream bos = new BufferedOutputStream(os);

        while((count = bfis.read(byteBuf)) != -1){
            if(count!=byteBuf.length){//如果有效字节数不为3*1000，则说明文件已经读到尾了，不够填充满byteBuf了
                byte[] copy = Arrays.copyOf(byteBuf, count); //从byteBuf中截取包含有效字节数的字节段
                base64ByteBuf = aesUtils.decrypt(copy);
            }
            else{
                base64ByteBuf =aesUtils.decrypt(byteBuf);
            }
            bos.write(base64ByteBuf, 0, base64ByteBuf.length);

        }
        bos.close();
        os.close();
        bfis.close();
        fis.close();

        return "";
    }

    @Override
    public String decodeFileWithDateAndCode(File file, String outPath,String code) throws Exception {
        FileEncodeFactory.checkFileExit(outPath);

        AESUtils aesUtils = new AESUtils(code);

        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bfis = new BufferedInputStream(fis);
        byte[] byteBuf = new byte[4*1024];
        byte[] base64ByteBuf;
        int count;

        FileOutputStream os = new FileOutputStream(outPath);
        BufferedOutputStream bos = new BufferedOutputStream(os);

        byte[] dateEncode =DateEncodeUtils.getByteArr();
        bfis.read(dateEncode);
        byte[] decrypt = aesUtils.decrypt(dateEncode);
        Date date = DateEncodeUtils.getDecode(decrypt);
        if (DateEncodeUtils.isvalid(date)) {

            while ((count = bfis.read(byteBuf)) != -1) {
                if (count != byteBuf.length) {//如果有效字节数不为3*1000，则说明文件已经读到尾了，不够填充满byteBuf了
                    byte[] copy = Arrays.copyOf(byteBuf, count); //从byteBuf中截取包含有效字节数的字节段
                    base64ByteBuf = aesUtils.decrypt(copy);
                } else {
                    base64ByteBuf = aesUtils.decrypt(byteBuf);
                }
                bos.write(base64ByteBuf, 0, base64ByteBuf.length);

            }
            bos.close();
            os.close();
            bfis.close();
            fis.close();

            return "";
        }else{
            throw  new RuntimeException("文件超出可使用时间");
        }
    }
}
