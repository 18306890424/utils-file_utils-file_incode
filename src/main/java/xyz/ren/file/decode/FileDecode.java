package xyz.ren.file.decode;

import java.io.File;
import java.util.Date;

public interface FileDecode {

     void decodeFile(File file, String outPath) throws Exception;
     void decodeFileWithDate(File file, String outPath ) throws Exception;
     String decodeFileWithCode(File file, String outPath,String code) throws Exception;
     String decodeFileWithDateAndCode(File file, String outPath,String code) throws Exception;

}
