package io.eblock.eos4j.ese;


/**
 * @author mc_q776355102
 * 
 * since：2018年10月11日 下午2:29:53
 */
public enum Action {

	transfer("${precision},${quantity}@act.token"), account("account"), ram("ram"), delegate("${precision},${quantity}@act.token"), voteproducer("voteproducer"),
	close("${precision},${quantity}@act.token"),ramAct("${precision},${quantity}@act.token");

	private String code;

	private Action(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}