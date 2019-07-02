import java.util.Arrays;

/**
 * 验证码
 * @author xinYing
 * @date 2019/7/2 8:58
 */
public class VerificationCode {
    public static void main(String[] args) {
        String str =returnRandomString(5);
        System.out.println(str);
    }

    /**
     *
     * @return 指定位数验证码
     */
    private static String returnRandomString(int len){
        final String str ="QWERTYUIPASDFGHJKLZXCVBNMqwertyuipasdfghjkzxcvbnm23456789";
        StringBuilder builder = new StringBuilder();
        for(int i =len;i-->0;){
            builder.append(str.charAt( returnRandom(0,str.length())));
        }
        return builder.toString();
    }
    /**
     * @param start 开端
     * @param end 结束
     * @return 一个指定范围的数
     */
    private static int returnRandom(int start , int end){
        return start+(int)(Math.random()*(end-start+1));
    }
}
