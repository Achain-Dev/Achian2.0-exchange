package client.domain.response.chain.account;

import java.util.List;

/**
 * @author yujian    2019/11/26
 */
public class VoterInfo {

    private String owner;
    private List<AccountVote> producers;

    public VoterInfo(){}

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<AccountVote> getProducers() {
        return producers;
    }

    public void setProducers(List<AccountVote> producers) {
        this.producers = producers;
    }
}
