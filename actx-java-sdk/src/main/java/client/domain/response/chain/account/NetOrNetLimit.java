package client.domain.response.chain.account;

/**
 * @author yujianjian  2019-06-28 14:52
 */
public class NetOrNetLimit {
    private Long used;
    private String available;
    private String max;


    public Long getUsed() {
        return used;
    }

    public String getAvailable() {
        return available;
    }

    public String getMax() {
        return max;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public void setUsed(Long used) {
        this.used = used;
    }
}
