package xyz.ren.file.encode.impl;

import xyz.ren.file.encode.FileEncode;
import xyz.ren.file.utils.encryption.AESUtils;
import xyz.ren.file.utils.common.ArraysUtils;
import xyz.ren.file.utils.encryption.Base64Util;
import xyz.ren.file.utils.encryption.DateEncodeUtils;
import xyz.ren.file.utils.common.FileBytesUtils;

import java.io.File;
import java.util.Date;

class BaseFileEncodeImpl implements FileEncode {

    @Override
    public void encodeFile(File file, String outPath) throws Exception {
        FileEncodeFactory.checkFileExit(outPath);
        byte[] fileBite = FileBytesUtils.getFileBite(file);
        byte[] encode = Base64Util.encode(fileBite);
        FileBytesUtils.toFile(encode,outPath);
    }

    @Override
    public void encodeFile(File file, String outPath, Date date) throws Exception {
        FileEncodeFactory.checkFileExit(outPath);
        byte[] fileBite = FileBytesUtils.getFileBite(file);
        byte[] dateEncode = DateEncodeUtils.getEncode(date);
        byte[] concatAll = ArraysUtils.concatAll(dateEncode, fileBite);
        byte[] encode = Base64Util.encode(concatAll);
        FileBytesUtils.toFile(encode,outPath);

    }

    @Override
    public String encodeFileWithCode(File file, String outPath) throws Exception {
        FileEncodeFactory.checkFileExit(outPath);
        byte[] key = AESUtils.randomKey();
        AESUtils aesUtils = new AESUtils(key);
        byte[] fileBite = FileBytesUtils.getFileBite(file);
        byte[] encrypt = aesUtils.encrypt(fileBite);
        FileBytesUtils.toFile(encrypt,outPath);
        String keyString = Base64Util.encodeToString(key);
        return keyString;
    }

    @Override
    public String encodeFileWithCode(File file, String outPath, Date date) throws Exception {
        FileEncodeFactory.checkFileExit(outPath);
        byte[] key = AESUtils.randomKey();
        AESUtils aesUtils = new AESUtils(key);
        byte[] fileBite = FileBytesUtils.getFileBite(file);
        byte[] dateEncode = DateEncodeUtils.getEncode(date);
        byte[] concatAll = ArraysUtils.concatAll(dateEncode, fileBite);
        byte[] encode = aesUtils.encrypt(concatAll);
        FileBytesUtils.toFile(encode,outPath);
        String keyString = Base64Util.encodeToString(key);
        return keyString;
    }

    @Override
    public String encodeFileWithCode(File file, String outPath,String keyCode) throws Exception {
        FileEncodeFactory.checkFileExit(outPath);

        AESUtils aesUtils = new AESUtils(keyCode);
        byte[] fileBite = FileBytesUtils.getFileBite(file);
        byte[] encrypt = aesUtils.encrypt(fileBite);
        FileBytesUtils.toFile(encrypt,outPath);
        return keyCode;
    }

    @Override
    public String encodeFileWithCode(File file, String outPath,String keyCode, Date date) throws Exception {
        FileEncodeFactory.checkFileExit(outPath);
        AESUtils aesUtils = new AESUtils(keyCode);

        byte[] fileBite = FileBytesUtils.getFileBite(file);
        byte[] dateEncode = DateEncodeUtils.getEncode(date);
        byte[] concatAll = ArraysUtils.concatAll(dateEncode, fileBite);
        byte[] encode = aesUtils.encrypt(concatAll);
        FileBytesUtils.toFile(encode,outPath);

        return keyCode;
    }


}
