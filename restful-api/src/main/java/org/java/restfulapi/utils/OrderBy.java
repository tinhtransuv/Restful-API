package org.java.restfulapi.utils;

public enum OrderBy {

	ID("id");

	private String OrderByCode;

	private OrderBy(String orderBy) {
		this.OrderByCode = orderBy;
	}

	public String getOrderByCode() {
		return this.OrderByCode;
	}
}
