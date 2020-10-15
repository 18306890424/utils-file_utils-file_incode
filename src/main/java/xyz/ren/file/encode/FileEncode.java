package xyz.ren.file.encode;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public interface FileEncode {

     void encodeFile(File file, String outPath) throws Exception;
     void encodeFile(File file, String outPath, Date date) throws Exception;
     String encodeFileWithCode(File file, String outPath) throws Exception;
     String encodeFileWithCode(File file, String outPath, Date date) throws Exception;
     String encodeFileWithCode(File file, String outPath,String keyCode) throws Exception;
     String encodeFileWithCode(File file, String outPath,String keyCode, Date date) throws Exception;

}
