package client.domain.response.chain.account;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author yujianjian  2019-06-28 14:53
 */
public class RefundRequest {
    private String owner;
    private String requestTime;
    /**
     * 正在赎回的net act
     */
    private String netAmount;
    /**
     * 正在赎回的cpu act
     */
    private String cpuAmount;

    public String getOwner() {
        return owner;
    }

    @JsonProperty("cpu_amount")
    public String getCpuAmount() {
        return cpuAmount;
    }

    @JsonProperty("net_amount")
    public String getNetAmount() {
        return netAmount;
    }

    @JsonProperty("request_time")
    public String getRequestTime() {
        return requestTime;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public void setNetAmount(String netAmount) {
        this.netAmount = netAmount;
    }

    public void setCpuAmount(String cpuAmount) {
        this.cpuAmount = cpuAmount;
    }
}
