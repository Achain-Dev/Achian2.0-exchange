package io.eblock.eos4j;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import io.eblock.eos4j.api.vo.SignParam;
import io.eblock.eos4j.api.vo.transaction.push.Tx;
import io.eblock.eos4j.api.vo.transaction.push.TxAction;
import io.eblock.eos4j.api.vo.transaction.push.TxRequest;
import io.eblock.eos4j.api.vo.transaction.push.TxSign;
import io.eblock.eos4j.ese.Action;
import io.eblock.eos4j.ese.DataParam;
import io.eblock.eos4j.ese.DataType;
import io.eblock.eos4j.ese.Ese;


/**
 * @author mc_q776355102
 * <p>
 * since：2018年10月11日 下午2:31:49
 */
public class OfflineSign {

    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public OfflineSign() {
        dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    /**
     * @param compression
     * @param pushTransaction
     * @param signatures
     * @return
     * @throws Exception
     */
    public String pushTransaction(String compression, Tx pushTransaction, String[] signatures) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String mapJakcson = mapper.writeValueAsString(new TxRequest(compression, pushTransaction, signatures));
        return mapJakcson;
    }

    /**
     * 离线签名转账
     *
     * @param signParam
     * @param pk
     * @param contractAccount
     * @param from
     * @param to
     * @param quantity
     * @param memo
     * @return
     * @throws Exception
     */
    public String transfer(SignParam signParam, String pk, String contractAccount, String from, String to,
                           String quantity, String memo) throws Exception {
        Tx tx = new Tx();
        tx.setExpiration(signParam.getHeadBlockTime().getTime() / 1000 + signParam.getExp());
        tx.setRef_block_num(signParam.getLastIrreversibleBlockNum());
        tx.setRef_block_prefix(signParam.getRefBlockPrefix());
        tx.setNet_usage_words(0L);
        tx.setMax_cpu_usage_ms(0L);
        tx.setDelay_sec(0L);
        // actions
        List<TxAction> actions = new ArrayList<TxAction>();
        // data
        Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
        dataMap.put("from", from);
        dataMap.put("to", to);
        dataMap.put("quantity", new DataParam(quantity, DataType.asset, Action.transfer).getValue());
        dataMap.put("memo", memo);
        // action
        TxAction action = new TxAction(from, contractAccount, "transfer", dataMap);
        actions.add(action);
        tx.setActions(actions);
        // sgin
        String sign = Ecc.signTransaction(pk, new TxSign(signParam.getChainId(), tx));
        // data parse
        String data = Ecc.parseTransferData(from, to, quantity, memo);
        // reset data
        action.setData(data);
        // reset expiration
        tx.setExpiration(dateFormatter.format(new Date(1000 * Long.parseLong(tx.getExpiration().toString()))));
        return pushTransaction("none", tx, new String[]{sign});
    }

    /**
     * 购买cpu或net,不购买的传空字符串
     *
     * @return
     */
    public String buyNetOrCpu(SignParam signParam, String pk,String from, String to,
                              String cpuQuantity, String netQuantity) throws Exception {
        Tx tx = new Tx();
        tx.setExpiration(signParam.getHeadBlockTime().getTime() / 1000 + signParam.getExp());
        tx.setRef_block_num(signParam.getLastIrreversibleBlockNum());
        tx.setRef_block_prefix(signParam.getRefBlockPrefix());
        tx.setNet_usage_words(0L);
        tx.setMax_cpu_usage_ms(0L);
        tx.setDelay_sec(0L);

        // actions
        List<TxAction> actions = new ArrayList<TxAction>();

        if (StringUtils.isBlank(cpuQuantity)) {
            cpuQuantity = "0.0000 ACT";
        }

        if (StringUtils.isBlank(netQuantity)) {
            netQuantity = "0.0000 ACT";
        }

        // data
        Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
        dataMap.put("stake_net_quantity", new DataParam(netQuantity, DataType.asset, Action.delegate).getValue());
        dataMap.put("receiver", to);
        dataMap.put("transfer", 0);
        dataMap.put("from", from);
        dataMap.put("stake_cpu_quantity", new DataParam(cpuQuantity, DataType.asset, Action.delegate).getValue());


        // action
        TxAction action = new TxAction(from, "act", "delegatebw", dataMap);
        actions.add(action);
        tx.setActions(actions);
        // sgin
        String sign = Ecc.signTransaction(pk, new TxSign(signParam.getChainId(), tx));
        // data parse
        // String data = Ecc.parseBuyNetOrCpuData(from, to, cpuQuantity, netQuantity);
        String data = Ecc.parseDelegateData(from,to,netQuantity,cpuQuantity,0);
        // reset data
        action.setData(data);
        // reset expiration
        tx.setExpiration(dateFormatter.format(new Date(1000 * Long.parseLong(tx.getExpiration().toString()))));
        return pushTransaction("none", tx, new String[]{sign});

    }

    /**
     * 购买ram bytes
     */
    public String buyRam(SignParam signParam, String pk,String payer,String receiver,Long buyRam) throws Exception {
        Tx tx = new Tx();
        tx.setExpiration(signParam.getHeadBlockTime().getTime() / 1000 + signParam.getExp());
        tx.setRef_block_num(signParam.getLastIrreversibleBlockNum());
        tx.setRef_block_prefix(signParam.getRefBlockPrefix());
        tx.setNet_usage_words(0L);
        tx.setMax_cpu_usage_ms(0L);
        tx.setDelay_sec(0L);

        // actions
        List<TxAction> actions = new ArrayList<TxAction>();
        Map<String, Object> buyMap = new LinkedHashMap<String, Object>();
        buyMap.put("payer", payer);
        buyMap.put("receiver", receiver);
        buyMap.put("bytes", buyRam);
        TxAction buyAction = new TxAction(payer, "act", "buyrambytes", buyMap);
        actions.add(buyAction);
        tx.setActions(actions);

        String sign = Ecc.signTransaction(pk, new TxSign(signParam.getChainId(), tx));
        // data parse
        String ramData = Ese.parseBuyRamData(payer,receiver,buyRam);
        buyAction.setData(ramData);
        tx.setExpiration(dateFormatter.format(new Date(1000 * Long.parseLong(tx.getExpiration().toString()))));
        return pushTransaction("none", tx, new String[]{sign});
    }

    public String buyRamByAct(SignParam signParam, String pk,String payer,String receiver,String quantity) throws Exception {
        Tx tx = new Tx();
        tx.setExpiration(signParam.getHeadBlockTime().getTime() / 1000 + signParam.getExp());
        tx.setRef_block_num(signParam.getLastIrreversibleBlockNum());
        tx.setRef_block_prefix(signParam.getRefBlockPrefix());
        tx.setNet_usage_words(0L);
        tx.setMax_cpu_usage_ms(0L);
        tx.setDelay_sec(0L);

        // actions
        List<TxAction> actions = new ArrayList<TxAction>();
        Map<String, Object> buyMap = new LinkedHashMap<String, Object>();
        buyMap.put("payer", payer);
        buyMap.put("receiver", receiver);
        buyMap.put("quant", new DataParam(quantity, DataType.asset, Action.transfer).getValue());
        TxAction buyAction = new TxAction(payer, "act", "buyram", buyMap);
        actions.add(buyAction);
        tx.setActions(actions);

        String sign = Ecc.signTransaction(pk, new TxSign(signParam.getChainId(), tx));
        // data parse
        String ramData = Ese.parseBuyRamActData(payer,receiver,quantity);
        buyAction.setData(ramData);
        tx.setExpiration(dateFormatter.format(new Date(1000 * Long.parseLong(tx.getExpiration().toString()))));
        return pushTransaction("none", tx, new String[]{sign});
    }

    public String sellRam(SignParam signParam,String pk,String account,Long sellRam) throws Exception {
        Tx tx = new Tx();
        tx.setExpiration(signParam.getHeadBlockTime().getTime() / 1000 + signParam.getExp());
        tx.setRef_block_num(signParam.getLastIrreversibleBlockNum());
        tx.setRef_block_prefix(signParam.getRefBlockPrefix());
        tx.setNet_usage_words(0L);
        tx.setMax_cpu_usage_ms(0L);
        tx.setDelay_sec(0L);

        // actions
        List<TxAction> actions = new ArrayList<TxAction>();
        Map<String, Object> sellMap = new LinkedHashMap<String, Object>();
        sellMap.put("account", account);
        sellMap.put("bytes", sellRam);
        TxAction sellAction = new TxAction(account, "act", "buyrambytes", sellMap);
        actions.add(sellAction);
        tx.setActions(actions);

        String sign = Ecc.signTransaction(pk, new TxSign(signParam.getChainId(), tx));
        // data parse
        String ramData = Ese.parseSellRamData(account,sellRam);
        sellAction.setData(ramData);
        tx.setExpiration(dateFormatter.format(new Date(1000 * Long.parseLong(tx.getExpiration().toString()))));
        return pushTransaction("none", tx, new String[]{sign});
    }



    /**
     * 离线签名创建账户
     *
     * @param signParam
     * @param pk
     * @param creator
     * @param newAccount
     * @param owner
     * @param active
     * @param buyRam
     * @return
     * @throws Exception
     */
    public String createAccount(SignParam signParam, String pk, String creator, String newAccount, String owner,
                                String active, Long buyRam) throws Exception {
        Tx tx = new Tx();
        tx.setExpiration(signParam.getHeadBlockTime().getTime() / 1000 + signParam.getExp());
        tx.setRef_block_num(signParam.getLastIrreversibleBlockNum());
        tx.setRef_block_prefix(signParam.getRefBlockPrefix());
        tx.setNet_usage_words(0L);
        tx.setMax_cpu_usage_ms(0L);
        tx.setDelay_sec(0L);
        // actions
        List<TxAction> actions = new ArrayList<>();
        tx.setActions(actions);
        // create
        Map<String, Object> createMap = new LinkedHashMap<>();
        createMap.put("creator", creator);
        createMap.put("name", newAccount);
        createMap.put("owner", owner);
        createMap.put("active", active);
        TxAction createAction = new TxAction(creator, "act", "newaccount", createMap);
        actions.add(createAction);
//        // buyrap
//        Map<String, Object> buyMap = new LinkedHashMap<>();
//        buyMap.put("payer", creator);
//        buyMap.put("receiver", newAccount);
//        buyMap.put("bytes", buyRam);
//        TxAction buyAction = new TxAction(creator, "actx", "buyrambytes", buyMap);
//        actions.add(buyAction);
        // sgin
        String sign = Ecc.signTransaction(pk, new TxSign(signParam.getChainId(), tx));
        // data parse
        String accountData = Ese.parseAccountData(creator, newAccount, owner, active);
        System.out.println(accountData);
        createAction.setData(accountData);
        // data parse
//        String ramData = Ese.parseBuyRamData(creator, newAccount, buyRam);
//        buyAction.setData(ramData);
        // reset expiration
        tx.setExpiration(dateFormatter.format(new Date(1000 * Long.parseLong(tx.getExpiration().toString()))));
        return pushTransaction("none", tx, new String[]{sign});
    }


}
