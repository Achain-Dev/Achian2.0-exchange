package client.impl;


import client.ActxApiRestFactory;
import client.domain.common.WalletKeyType;
import client.domain.common.transaction.PackedTransaction;
import client.domain.common.transaction.SignedPackedTransaction;
import client.domain.request.chain.AbiJsonToBinRequest;
import client.domain.request.chain.RequiredKeysRequest;
import client.domain.request.chain.transaction.PushTransactionRequest;
import client.domain.request.transaction.SignTransactionRequest;
import client.domain.response.chain.*;
import client.domain.response.chain.abi.Abi;
import client.domain.response.chain.account.ActAccount;
import client.domain.response.chain.code.Code;
import client.domain.response.chain.currencystats.CurrencyStats;
import client.domain.response.chain.transaction.PushedTransaction;
import client.domain.response.chain.transaction.ScheduledTransactionResponse;
import client.domain.response.history.action.Actions;
import client.domain.response.history.controlledaccounts.ControlledAccounts;
import client.domain.response.history.keyaccounts.KeyAccounts;
import client.domain.response.history.transaction.Transaction;

import java.util.*;

public class ActxApiRestRestImpl implements ActxApiRestFactory {
    private final ActxWalletApiService actxWalletApiService;

    private final ActxChainApiService actxChainApiService;

    private final ActxHistoryApiService actxHistoryApiService;

    public ActxApiRestRestImpl(String baseUrl) {
        actxWalletApiService = ActxApiServiceGenerator.createService(ActxWalletApiService.class, baseUrl);
        actxChainApiService = ActxApiServiceGenerator.createService(ActxChainApiService.class, baseUrl);
        actxHistoryApiService = ActxApiServiceGenerator.createService(ActxHistoryApiService.class, baseUrl);
    }

    public ActxApiRestRestImpl(String walletBaseUrl, String chainBaseUrl, String historyBaseUrl) {
        actxWalletApiService = ActxApiServiceGenerator.createService(ActxWalletApiService.class, walletBaseUrl);
        actxChainApiService = ActxApiServiceGenerator.createService(ActxChainApiService.class, chainBaseUrl);
        actxHistoryApiService = ActxApiServiceGenerator.createService(ActxHistoryApiService.class, historyBaseUrl);
    }

    @Override
    public ChainInfo getChainInfo() {
        return ActxApiServiceGenerator.executeSync(actxChainApiService.getChainInfo());
    }

    @Override
    public Block getBlock(String blockNumberOrId) {
        return ActxApiServiceGenerator.executeSync(actxChainApiService.getBlock(Collections.singletonMap("block_num_or_id", blockNumberOrId)));
    }

    @Override
    public ActAccount getActAccount(String accountName) {
        return ActxApiServiceGenerator.executeSync(actxChainApiService.getActAccount(Collections.singletonMap("account_name", accountName)));
    }

    @Override
    public Abi getAbi(String accountName) {
        return ActxApiServiceGenerator.executeSync(actxChainApiService.getAbi(Collections.singletonMap("account_name", accountName)));
    }

    @Override
    public Code getCode(String accountName) {
        return ActxApiServiceGenerator.executeSync(actxChainApiService.getCode(Collections.singletonMap("account_name", accountName)));
    }

    @Override
    public TableRow getTableRows(String scope, String code, String table, String limit) {
        LinkedHashMap<String, String> requestParameters = new LinkedHashMap<>(7);

        requestParameters.put("scope", scope);
        requestParameters.put("code", code);
        requestParameters.put("table", table);
        requestParameters.put("json", "true");
        requestParameters.put("limit", limit);

        return ActxApiServiceGenerator.executeSync(actxChainApiService.getTableRows(requestParameters));
    }

    @Override
    public List<String> getCurrencyBalance(String code, String accountName, String symbol) {
        LinkedHashMap<String, String> requestParameters = new LinkedHashMap<>(3);

        requestParameters.put("code", code);
        requestParameters.put("account", accountName);
        requestParameters.put("symbol", symbol);

        return ActxApiServiceGenerator.executeSync(actxChainApiService.getCurrencyBalance(requestParameters));
    }

    @Override
    public AbiBinToJson abiBinToJson(String code, String action, String binargs) {
        LinkedHashMap<String, String> requestParameters = new LinkedHashMap<>(3);

        requestParameters.put("code", code);
        requestParameters.put("action", action);
        requestParameters.put("binargs", binargs);

        return ActxApiServiceGenerator.executeSync(actxChainApiService.abiBinToJson(requestParameters));
    }

    @Override
    public <T> AbiJsonToBin abiJsonToBin(String code, String action, T args) {
        return ActxApiServiceGenerator.executeSync(actxChainApiService.abiJsonToBin(new AbiJsonToBinRequest(code, action, args)));
    }

    @Override
    public PushedTransaction pushTransaction(String compression, SignedPackedTransaction packedTransaction) {
        return ActxApiServiceGenerator.executeSync(actxChainApiService.pushTransaction(new PushTransactionRequest(compression, packedTransaction, packedTransaction.getSignatures())));
    }

    @Override
    public PushedTransaction pushRawTransaction(String tx) {
        return ActxApiServiceGenerator.executeSync(actxChainApiService.pushRawTransaction(tx));
    }

    @Override
    public List<PushedTransaction> pushTransactions(List<PushTransactionRequest> pushTransactionRequests) {
        return ActxApiServiceGenerator.executeSync(actxChainApiService.pushTransactions(pushTransactionRequests));
    }

    @Override
    public RequiredKeys getRequiredKeys(PackedTransaction transaction, List<String> keys) {
        return ActxApiServiceGenerator.executeSync(actxChainApiService.getRequiredKeys(new RequiredKeysRequest(transaction, keys)));
    }

    @Override
    public Map<String, CurrencyStats> getCurrencyStats(String code, String symbol) {
        LinkedHashMap<String, String> requestParameters = new LinkedHashMap<>(2);

        requestParameters.put("code", code);
        requestParameters.put("symbol", symbol);

        return ActxApiServiceGenerator.executeSync(actxChainApiService.getCurrencyStats(requestParameters));
    }

    @Override
    public String createWallet(String walletName) {
        return ActxApiServiceGenerator.executeSync(actxWalletApiService.createWallet(walletName));
    }

    @Override
    public void openWallet(String walletName) {
        ActxApiServiceGenerator.executeSync(actxWalletApiService.openWallet(walletName));
    }

    @Override
    public void lockWallet(String walletName) {
        ActxApiServiceGenerator.executeSync(actxWalletApiService.lockWallet(walletName));
    }

    @Override
    public void lockAllWallets() {
        ActxApiServiceGenerator.executeSync(actxWalletApiService.lockAll());
    }

    @Override
    public void unlockWallet(String walletName, String walletPassword) {
        List<String> requestFields = new ArrayList<>(2);

        requestFields.add(walletName);
        requestFields.add(walletPassword);
        ActxApiServiceGenerator.executeSync(actxWalletApiService.unlockWallet(requestFields));
    }

    @Override
    public void importKeyIntoWallet(String walletName, String key) {
        List<String> requestFields = new ArrayList<>(2);

        requestFields.add(walletName);
        requestFields.add(key);
        ActxApiServiceGenerator.executeSync(actxWalletApiService.importKey(requestFields));
    }

    @Override
    public List<String> listWallets() {
        return ActxApiServiceGenerator.executeSync(actxWalletApiService.listWallets());
    }

    @Override
    public List<List<String>> listKeys(String walletName, String password) {
        List<String> requestFields = Arrays.asList(walletName, password);
        return ActxApiServiceGenerator.executeSync(actxWalletApiService.listKeys(requestFields));
    }

    @Override
    public List<String> getPublicKeys() {
        return ActxApiServiceGenerator.executeSync(actxWalletApiService.getPublicKeys());
    }

    @Override
    public SignedPackedTransaction signTransaction(PackedTransaction packedTransaction, List<String> publicKeys, String chainId) {
        return ActxApiServiceGenerator.executeSync(actxWalletApiService.signTransaction(new SignTransactionRequest(packedTransaction, publicKeys, chainId)));
    }

    @Override
    public void setWalletTimeout(Integer timeout) {
        ActxApiServiceGenerator.executeSync(actxWalletApiService.setTimeout(timeout));
    }

    @Override
    public String signDigest(String digest, String publicKey) {
        return ActxApiServiceGenerator.executeSync(actxWalletApiService.signDigest(Arrays.asList(digest, publicKey)));
    }

    @Override
    public String createKey(String walletName, WalletKeyType keyType) {
        return ActxApiServiceGenerator.executeSync(actxWalletApiService.createKey(Arrays.asList(walletName, keyType.name())));
    }

    @Override
    public Actions getActions(String accountName, Long pos, Integer offset) {
        LinkedHashMap<String, Object> requestParameters = new LinkedHashMap<>(3);

        requestParameters.put("account_name", accountName);
        requestParameters.put("pos", pos);
        requestParameters.put("offset", offset);

        return ActxApiServiceGenerator.executeSync(actxHistoryApiService.getActions(requestParameters));
    }

    @Override
    public Transaction getTransaction(String id) {
        LinkedHashMap<String, String> requestParameters = new LinkedHashMap<>(1);

        requestParameters.put("id", id);

        return ActxApiServiceGenerator.executeSync(actxHistoryApiService.getTransaction(requestParameters));
    }

    @Override
    public Transaction getTransaction(String trxId, String blockNumber) {
        LinkedHashMap<String, String> requestParameters = new LinkedHashMap<>(2);

        requestParameters.put("id", trxId);
        requestParameters.put("block_num_hint", blockNumber);

        return ActxApiServiceGenerator.executeSync(actxHistoryApiService.getTransaction(requestParameters));
    }

    @Override
    public KeyAccounts getKeyAccounts(String publicKey) {
        LinkedHashMap<String, String> requestParameters = new LinkedHashMap<>(1);

        requestParameters.put("public_key", publicKey);

        return ActxApiServiceGenerator.executeSync(actxHistoryApiService.getKeyAccounts(requestParameters));
    }

    @Override
    public ControlledAccounts getControlledAccounts(String controllingAccountName) {
        LinkedHashMap<String, String> requestParameters = new LinkedHashMap<>(1);

        requestParameters.put("controlling_account", controllingAccountName);

        return ActxApiServiceGenerator.executeSync(actxHistoryApiService.getControlledAccounts(requestParameters));
    }

    @Override
    public ScheduledTransactionResponse getScheduledtransactions(String lowerBound, String limit) {
        LinkedHashMap<String, String> requestParameters = new LinkedHashMap<>(2);

        requestParameters.put("json", "true");

        if (lowerBound != null) {
            requestParameters.put("lower_bound", lowerBound);
        }

        requestParameters.put("limit", limit);

        return ActxApiServiceGenerator.executeSync(actxChainApiService.getScheduledtransaction(requestParameters));
    }

}
