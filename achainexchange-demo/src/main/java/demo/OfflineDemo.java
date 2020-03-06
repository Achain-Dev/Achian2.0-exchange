package demo;

import io.eblock.eos4j.EosRpcService;
import io.eblock.eos4j.OfflineSign;
import io.eblock.eos4j.api.vo.SignParam;
import io.eblock.eos4j.api.vo.transaction.Transaction;

/**
 * @author yujian    2020/03/04
 */
public class OfflineDemo {

    // 节点rpc地址
    private static String baseUrl = "http://127.0.0.1:9500";
    private final static EosRpcService rpc = new EosRpcService(baseUrl);


    /**
     * 链上创建账户，转账的转入转出必须是账户，类似eos
     * @param depositPrivateKey 托管创建账户人的私钥
     * @param fromAccount 托管创建账户人的账户
     * @param accountName 需要创建的账户名
     * @param publicKey 需要创建的账户名，绑定的公钥
     * @throws Exception
     */
    public static void createAccountDemo(String depositPrivateKey,String fromAccount,String accountName, String publicKey) throws Exception{
        // 账户名不合法
        if(!AddressDemo.checkValidAccount(accountName)) {
            return;
        }
        SignParam params = rpc.getOfflineSignParams(600L);
        OfflineSign sign = new OfflineSign();
        String result = sign.createAccount(params, depositPrivateKey, fromAccount, accountName, publicKey, publicKey, 0L);
        Transaction t2 = rpc.pushTransaction(result);
        System.out.println("创建账户成功,交易id = " + t2.getTransactionId() + " \n ");
    }

    /**
     * 主链币转账
     * @param privateKey 转出人私钥
     * @param fromAccount 转出人的账户名
     * @param toAccount 转入人的账户名
     * @param quantity 转账数量，注必须4位精度小数(如1.0000)，跟eos一致
     * @param memo 转账备注，可以为空
     * @throws Exception
     */
    public static void transferDemo(String privateKey,String fromAccount,String toAccount,String quantity,String memo) throws Exception{
        // 1. 去链上获取签名参数
        SignParam params = rpc.getOfflineSignParams(600L);
        quantity = quantity.trim() + " ACT";

        OfflineSign sign = new OfflineSign();
        // 2. 用私钥签名交易数据
        String result = sign.transfer(params, privateKey, "act.token", fromAccount, toAccount, quantity, memo);
        System.out.println("交易离线签名的序列号数据: " + result);
        // 3. 发送交易数据到链上,返回transactionId
        Transaction t1 = rpc.pushTransaction(result);

        System.out.println("转账成功,交易id = " + t1.getTransactionId() + " \n ");
    }
}
