package xyz.ren.file.decode.impl;

import xyz.ren.file.decode.FileDecode;
import xyz.ren.file.encode.impl.FileEncodeFactory;
import xyz.ren.file.utils.encryption.AESUtils;
import xyz.ren.file.utils.encryption.Base64Util;
import xyz.ren.file.utils.encryption.DateEncodeUtils;
import xyz.ren.file.utils.common.FileBytesUtils;

import java.io.File;
import java.util.Arrays;
import java.util.Date;

class BaseFileDecodeImpl implements FileDecode {

    @Override
    public void decodeFile(File file, String outPath) throws Exception {
        FileEncodeFactory.checkFileExit(outPath);
        byte[] fileBite = FileBytesUtils.getFileBite(file);
        byte[] decode = Base64Util.decode(fileBite);
        FileBytesUtils.toFile(decode,outPath);
    }

    @Override
    public void decodeFileWithDate(File file, String outPath ) throws Exception {
        FileEncodeFactory.checkFileExit(outPath);
        byte[] fileBite = FileBytesUtils.getFileBite(file);
        byte[] decode = Base64Util.decode(fileBite);

        byte[] dateEncode =DateEncodeUtils.getByteArr();
        System.arraycopy(decode,0,dateEncode,0,dateEncode.length);
        Date date = DateEncodeUtils.getDecode(dateEncode);
        if (DateEncodeUtils.isvalid(date)){
            FileBytesUtils.toFile(Arrays.copyOfRange(decode,dateEncode.length,dateEncode.length),outPath);
        }else{
            throw  new RuntimeException("超出允许查看时间");
        }




    }

    @Override
    public String decodeFileWithCode(File file, String outPath,String code) throws Exception {
        FileEncodeFactory.checkFileExit(outPath);
        AESUtils aesUtils = new AESUtils(code);
        byte[] fileBite = FileBytesUtils.getFileBite(file);
        byte[] encrypt = aesUtils.decrypt(fileBite);
        FileBytesUtils.toFile(encrypt,outPath);
        return "";
    }

    @Override
    public String decodeFileWithDateAndCode(File file, String outPath,String code ) throws Exception {
        FileEncodeFactory.checkFileExit(outPath);
        AESUtils aesUtils = new AESUtils(code);

        byte[] fileBite = FileBytesUtils.getFileBite(file);
        byte[] decrypt = aesUtils.decrypt(fileBite);

        byte[] dateEncode =DateEncodeUtils.getByteArr();
        System.arraycopy(decrypt,0,dateEncode,0,dateEncode.length);
        Date date = DateEncodeUtils.getDecode(dateEncode);

        if (DateEncodeUtils.isvalid(date)){
            FileBytesUtils.toFile(Arrays.copyOfRange(decrypt,dateEncode.length,dateEncode.length),outPath);
        }else{
            throw  new RuntimeException("超出允许查看时间");
        }

        return "";
    }
}
