package xyz.ren.file.utils.common;

import xyz.ren.file.utils.encryption.AESUtils;
import xyz.ren.file.utils.encryption.Base64Util;

import java.io.*;
import java.util.Arrays;

public class BigFileInput {

    public  void BigFileAesEncode(File file, File output, AESUtils aesUtils) throws IOException {

        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bfis = new BufferedInputStream(fis);
        byte[] byteBuf = new byte[3*1024];
        byte[] base64ByteBuf;
        int count;

        FileOutputStream os = new FileOutputStream(output);
        BufferedOutputStream bos = new BufferedOutputStream(os);

        while((count = bfis.read(byteBuf)) != -1){
            if(count!=byteBuf.length){//如果有效字节数不为3*1000，则说明文件已经读到尾了，不够填充满byteBuf了
                byte[] copy = Arrays.copyOf(byteBuf, count); //从byteBuf中截取包含有效字节数的字节段
                base64ByteBuf = aesUtils.encrypt(copy);
            }
            else{
                base64ByteBuf =aesUtils.encrypt(byteBuf);
            }
            bos.write(base64ByteBuf, 0, base64ByteBuf.length);

        }
        bos.close();
        os.close();
        bfis.close();
        fis.close();
    }

    public  void BigFileAesDecode(File file,File output,AESUtils aesUtils) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bfis = new BufferedInputStream(fis);
        byte[] byteBuf = new byte[3*1024];
        byte[] base64ByteBuf;
        int count;
        FileOutputStream os = new FileOutputStream(output);
        BufferedOutputStream bos = new BufferedOutputStream(os);

        while((count = bfis.read(byteBuf)) != -1){
            if(count!=byteBuf.length){//如果有效字节数不为3*1000，则说明文件已经读到尾了，不够填充满byteBuf了
                byte[] copy = Arrays.copyOf(byteBuf, count); //从byteBuf中截取包含有效字节数的字节段
//                base64ByteBuf = Base64Util.decode(copy); //对有效字节段进行编码
                base64ByteBuf = aesUtils.decrypt(copy); //对有效字节段进行编码
            }
            else{
//                base64ByteBuf =Base64Util.decode(byteBuf);
                base64ByteBuf =aesUtils.decrypt(byteBuf);
            }
            bos.write(base64ByteBuf, 0, base64ByteBuf.length);

        }
        bos.close();
        os.close();
        bfis.close();
        fis.close();
    }

    public  void BigFileBase64Encode(File file, File output) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bfis = new BufferedInputStream(fis);
        byte[] byteBuf = new byte[3*1024];
        byte[] base64ByteBuf;
        int count;
        FileOutputStream os = new FileOutputStream(output);
        BufferedOutputStream bos = new BufferedOutputStream(os);

        while((count = bfis.read(byteBuf)) != -1){
            if(count!=byteBuf.length){//如果有效字节数不为3*1000，则说明文件已经读到尾了，不够填充满byteBuf了
                byte[] copy = Arrays.copyOf(byteBuf, count); //从byteBuf中截取包含有效字节数的字节段
                base64ByteBuf = Base64Util.encode(copy); //对有效字节段进行编码
            }
            else{
                base64ByteBuf =Base64Util.encode(byteBuf);
            }
            bos.write(base64ByteBuf, 0, base64ByteBuf.length);

        }
        bos.close();
        os.close();
        bfis.close();
        fis.close();

    }

    public  void BigFileBase64Decode(File file,File output) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bfis = new BufferedInputStream(fis);
        byte[] byteBuf = new byte[3*1024];
        byte[] base64ByteBuf;
        int count;
        FileOutputStream os = new FileOutputStream(output);
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




}
