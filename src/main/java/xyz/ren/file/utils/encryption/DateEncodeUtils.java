package xyz.ren.file.utils.encryption;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateEncodeUtils {

    public static final int SIZE ;
    public static final String DATE_FORMATE;

    private static  SimpleDateFormat dateFormat;

    static {
        DATE_FORMATE = "yyyy-MM-dd hh:mm:ss";
        dateFormat = new SimpleDateFormat(DATE_FORMATE);
        String formatDate = dateFormat.format(new Date());
        byte[] encode = Base64Util.encode(formatDate);
        SIZE = encode.length;
    }

    public static byte[] getEncode(Date date){
        if (date==null){
            throw  new RuntimeException("error date");
        }else{
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMATE);
            String formatDate = dateFormat.format(date);

            byte[] encode = Base64Util.encode(formatDate);
            return  encode;
        }
    }

    public static Date  getDecode(byte[] bite) throws ParseException {

        if (bite==null||bite.length!= SIZE){
            throw  new RuntimeException("error date");
        }else{
            String dateString = Base64Util.decodeToString(bite);
            Date date = dateFormat.parse(dateString);
            return  date;
        }
    }


    public static byte[] getByteArr(){
        return  new byte[SIZE];
    }

    public static boolean isvalid(Date date){
        if (date==null){
            throw  new RuntimeException("error date");
        }else{
            Date currentDate = new Date();
            return  currentDate.before(date);
        }
    }

    @Test
    public void test(){
        Date date = new Date();
        byte[] encode = this.getEncode(date);
        System.out.println(encode.length);
    }
}
