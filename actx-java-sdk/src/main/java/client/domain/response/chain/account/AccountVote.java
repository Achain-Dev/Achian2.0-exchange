package client.domain.response.chain.account;

/**
 * @author yujian    2019/11/26
 */
public class AccountVote {
    private String key;
    private Long value;

    public AccountVote(){}

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
