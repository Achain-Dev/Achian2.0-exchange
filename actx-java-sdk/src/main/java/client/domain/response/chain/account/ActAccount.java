package client.domain.response.chain.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Data;

/**
 * @author yujianjian  2019-06-28 10:07
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ActAccount {


    private String accountName;
    /**
     * 可用act余额
     */
    private String availableBalance;
    /**
     * 质押的net,精度4位
     */
    private Long netWeight;

    /**
     * 账号创建时间
     */
    private String created;
    /**
     * 质押的cpu,精度4位
     */
    private Long cpuWeight;
    private NetOrNetLimit netLimit;
    private NetOrNetLimit cpuLimit;
    /**
     * 内存使用字节数
     */
    private Long ramUsage;

    /**
     * 总资源
     */
    private TotalResources totalResources;
    /**
     * 正在赎回的
     */
    private RefundRequest refundRequest;

    /**
     * 自己的
     */
    private SelfDelegatedBandwidth selfDelegatedBandwidth;

    private VoterInfo voterInfo;

    private List<Permission> permissions;

    public ActAccount() {

    }

    @JsonProperty("voter_info")
    public VoterInfo getVoterInfo() {
        return voterInfo;
    }

    public void setVoterInfo(VoterInfo voterInfo) {
        this.voterInfo = voterInfo;
    }

    @JsonProperty("account_name")
    public String getAccountName() {
        return accountName;
    }

    @JsonProperty("core_liquid_balance")
    public String getAvailableBalance() {
        return availableBalance;
    }

    @JsonProperty("cpu_weight")
    public Long getCpuWeight() {
        return cpuWeight;
    }

    @JsonProperty("net_weight")
    public Long getNetWeight() {
        return netWeight;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    @JsonProperty("cpu_limit")
    public NetOrNetLimit getCpuLimit() {
        return cpuLimit;
    }

    @JsonProperty("net_limit")
    public NetOrNetLimit getNetLimit() {
        return netLimit;
    }

    @JsonProperty("ram_usage")
    public Long getRamUsage() {
        return ramUsage;
    }

    @JsonProperty("total_resources")
    public TotalResources getTotalResources() {
        return totalResources;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
    }

    public void setNetWeight(Long netWeight) {
        this.netWeight = netWeight;
    }

    public void setCpuWeight(Long cpuWeight) {
        this.cpuWeight = cpuWeight;
    }

    public void setNetLimit(NetOrNetLimit netLimit) {
        this.netLimit = netLimit;
    }

    public void setCpuLimit(NetOrNetLimit cpuLimit) {
        this.cpuLimit = cpuLimit;
    }

    public void setRamUsage(Long ramUsage) {
        this.ramUsage = ramUsage;
    }

    public void setTotalResources(TotalResources totalResources) {
        this.totalResources = totalResources;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }


    @JsonProperty("refund_request")
    public RefundRequest getRefundRequest() {
        return refundRequest;
    }

    public void setRefundRequest(RefundRequest refundRequest) {
        this.refundRequest = refundRequest;
    }

    @JsonProperty("self_delegated_bandwidth")
    public SelfDelegatedBandwidth getSelfDelegatedBandwidth() {
        return selfDelegatedBandwidth;
    }

    public void setSelfDelegatedBandwidth(SelfDelegatedBandwidth selfDelegatedBandwidth) {
        this.selfDelegatedBandwidth = selfDelegatedBandwidth;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}





