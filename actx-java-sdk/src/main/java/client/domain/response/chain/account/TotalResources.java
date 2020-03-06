package client.domain.response.chain.account;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author yujianjian  2019-06-28 14:52
 */
public class TotalResources {
    private String owner;
    private String netWeight;
    private String cpuWeight;
    private String ramBytes;
    private Long ramBytesForFree;

    @JsonProperty("ram_bytes_forfree")
    public Long getRamBytesForFree() {
        return ramBytesForFree;
    }

    @JsonProperty("net_weight")
    public String getNetWeight() {
        return netWeight;
    }


    public String getOwner() {
        return owner;
    }

    @JsonProperty("ram_bytes")
    public String getRamBytes() {
        return ramBytes;
    }


    @JsonProperty("cpu_weight")
    public String getCpuWeight() {
        return cpuWeight;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setNetWeight(String netWeight) {
        this.netWeight = netWeight;
    }

    public void setCpuWeight(String cpuWeight) {
        this.cpuWeight = cpuWeight;
    }

    public void setRamBytes(String ramBytes) {
        this.ramBytes = ramBytes;
    }

    public void setRamBytesForFree(Long ramBytesForFree) {
        this.ramBytesForFree = ramBytesForFree;
    }
}
