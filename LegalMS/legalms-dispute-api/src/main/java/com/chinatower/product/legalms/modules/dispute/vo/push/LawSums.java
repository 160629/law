package com.chinatower.product.legalms.modules.dispute.vo.push;

import java.util.List;

public class LawSums{
	private List<LawSum> list;

	public List<LawSum> getList() {
		return list;
	}

	public void setList(List<LawSum> list) {
		this.list = list;
	}

	public LawSums(List<LawSum> list) {
		super();
		this.list = list;
	}
	
}