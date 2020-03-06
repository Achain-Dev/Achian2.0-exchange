package demo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.eblock.eos4j.utils.KeyUtil;

/**
 * @author yujian    2020/03/04
 */
public class AddressDemo {

    public static void main(String[] args) {
        generateAddress();
    }

    /**
     * 生成私钥和公钥
     */
    public static void generateAddress() {
        String privateKey = KeyUtil.createPrivateKey();
        String publicKey = KeyUtil.toPublicKey(privateKey);
        System.out.println("私钥: " + privateKey);
        System.out.println("公钥: " + publicKey);

    }

    private final static Pattern PATTERN = Pattern.compile("[a-z1-5.]{1,13}");

    /**
     * 校验账户的合法性
     * @return 合法返回true
     */
    public static boolean checkValidAccount(String account) {
        Matcher matcher = PATTERN.matcher(account);
        return matcher.matches();
    }

    /**
     * 校验公钥的有效性
     */
    public static boolean checkValidPublicKey(String publicKey) {
        return publicKey.length() == 54 && publicKey.startsWith("ACTX");
    }



}
