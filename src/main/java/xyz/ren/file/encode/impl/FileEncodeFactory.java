package xyz.ren.file.encode.impl;

import xyz.ren.file.encode.FileEncode;

import java.io.File;
import java.io.IOException;


public class FileEncodeFactory  {

    public static FileEncode getFileEncode(File file){
        if (file==null||!file.exists()){
            throw new RuntimeException("no file");
        }
        if (file.length()<1000){
            return new BaseFileEncodeImpl();
        }else{
            return  new BigFileEncodeImpl();
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
