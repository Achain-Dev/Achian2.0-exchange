package client.domain.response.chain.account;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author yujianjian  2019-06-28 14:53
 */
public class SelfDelegatedBandwidth {
    private String from;
    private String to;
    private String netWeight;
    private String cpuWeight;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @JsonProperty("net_weight")
    public String getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(String netWeight) {
        this.netWeight = netWeight;
    }

    @JsonProperty("cpu_weight")
    public String getCpuWeight() {
        return cpuWeight;
    }

    public void setCpuWeight(String cpuWeight) {
        this.cpuWeight = cpuWeight;
    }
}
