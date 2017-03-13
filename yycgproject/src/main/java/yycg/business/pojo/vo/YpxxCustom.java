package yycg.business.pojo.vo;

import yycg.business.pojo.po.Ypxx;

public class YpxxCustom extends Ypxx {
	//交易状态名称
	private String jyztmc;
	
	private String price_start;
	
	private String price_end;

	public String getJyztmc() {
		return jyztmc;
	}

	public void setJyztmc(String jyztmc) {
		this.jyztmc = jyztmc;
	}

	public String getPrice_start() {
		return price_start;
	}

	public void setPrice_start(String price_start) {
		this.price_start = price_start;
	}

	public String getPrice_end() {
		return price_end;
	}

	public void setPrice_end(String price_end) {
		this.price_end = price_end;
	}
}
