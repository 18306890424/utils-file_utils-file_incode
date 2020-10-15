package xyz.ren.file.encode;

import xyz.ren.file.decode.FileDecode;
import xyz.ren.file.decode.impl.FileDecodeFactory;
import xyz.ren.file.encode.impl.FileEncodeFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

    @org.junit.Test
    public  void testEncode() throws Exception {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("2021-01-01");


        File f = new File("D:\\fileTest\\test.txt");
        String out1 = "D:\\fileTest\\out1.cnic";
        String out2 = "D:\\fileTest\\out2.cnic";
        String out3 = "D:\\fileTest\\out3.cnic";
        String out4 = "D:\\fileTest\\out4.cnic";


        FileEncode fileEncode = FileEncodeFactory.getFileEncode(f);
        try {
            fileEncode.encodeFile(f,out1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            fileEncode.encodeFile(f,out2,date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println(fileEncode.encodeFileWithCode(f, out3));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println(fileEncode.encodeFileWithCode(f,out4, date));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @org.junit.Test
    public void  testDecode(){

        File f1 = new File("D:\\fileTest\\out1.cnic");
        File f2 = new File("D:\\fileTest\\out2.cnic");
        File f3 = new File("D:\\fileTest\\out3.cnic");
        File f4 = new File("D:\\fileTest\\out4.cnic");


        String out11 = "D:\\fileTest\\test1.txt";
        String out21 = "D:\\fileTest\\test2.txt";
        String out31 = "D:\\fileTest\\test3.txt";
        String out41 = "D:\\fileTest\\test4.txt";


        String code1 = "YEWbJubox0TSjKo6wVbHvg==";
        String code2 = "q6HSUYXWasgPEiORfr2n+A==";
        FileDecode fileEncode = FileDecodeFactory.getFileEncode(f1);
        try {
            fileEncode.decodeFile(f1, out11);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            fileEncode.decodeFileWithDate(f2, out21);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            fileEncode.decodeFileWithCode(f3, out31, code1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            fileEncode.decodeFileWithDateAndCode(f4, out41, code2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
