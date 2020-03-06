package client;

import client.impl.ActxApiRestRestImpl;

public class ActxApiClientFactory {

    private String baseUrl;

    private String walletBaseUrl;

    private String chainBaseUrl;

    private String historyBaseUrl;

    private ActxApiClientFactory(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    private ActxApiClientFactory(String walletBaseUrl, String chainBaseUrl, String historyBaseUrl) {
        this.walletBaseUrl = walletBaseUrl;
        this.chainBaseUrl = chainBaseUrl;
        this.historyBaseUrl = historyBaseUrl;
    }

    public static ActxApiClientFactory newInstance(String baseUrl) {
        return new ActxApiClientFactory(baseUrl);
    }

    public static ActxApiClientFactory newInstance(String walletBaseUrl, String chainBaseUrl, String historyBaseUrl) {
        return new ActxApiClientFactory(walletBaseUrl, chainBaseUrl, historyBaseUrl);
    }

    public ActxApiRestFactory newRestClient() {
        return baseUrl != null
                ? new ActxApiRestRestImpl(baseUrl)
                : new ActxApiRestRestImpl(walletBaseUrl, chainBaseUrl, historyBaseUrl);
    }

}
