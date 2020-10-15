package xyz.ren.file.utils.encryption;

import java.util.Base64;

public class Base64Util {
    //Base64转码加密
    public static  String encodeToString(byte[] bytes){
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(bytes);
    }
    public static  String encodeToString(String s){
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(s.getBytes());
    }
    public static  byte[] encode(byte[] bytes){
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encode(bytes);
    }
    public static  byte[] encode(String s){
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encode(s.getBytes());
    }



    //Base64解码
    public static   byte[] decode(String base64Key) {
        Base64.Decoder decoder =Base64.getDecoder();
        return decoder.decode(base64Key);
    }
    public static   byte[] decode(byte[] base64Key) {
        Base64.Decoder decoder =Base64.getDecoder();
        return decoder.decode(base64Key);
    }
    public static   String decodeToString(String base64Key) {
        Base64.Decoder decoder =Base64.getDecoder();
        return new String(decoder.decode(base64Key));
    }
    public static   String decodeToString(byte[] base64Key) {
        Base64.Decoder decoder =Base64.getDecoder();
        return new String(decoder.decode(base64Key));
    }



}