package xyz.ren.file.decode.impl;

import xyz.ren.file.decode.FileDecode;
import xyz.ren.file.encode.FileEncode;

import java.io.File;
import java.io.IOException;


public class FileDecodeFactory  {

    public static FileDecode getFileEncode(File file){
        if (file==null||!file.exists()){
            throw new RuntimeException("no file");
        }
        if (file.length()<1000){
            return new BaseFileDecodeImpl();
        }else{
            return  new BigFileDecodeImpl();
        }
    }

    public  static void checkFileExit(String path) throws IOException {
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
}
