package tickets.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class CodeUtil {
    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encrypt(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

//    public static void main(String[] args) {
//
//        String str = UUID.randomUUID().toString();
//        str = "1030518209@qq.com";
//        System.out.println(str);
//        try {
//            String result1 = CodeUtil.encrypt(str.getBytes());
//            System.out.println("result1=====加密数据==========" + result1);
//
//            byte result2[] = CodeUtil.decrypt(result1);
//            String str2 = new String(result2);
//            System.out.println("str2========解密数据========" + str2);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
