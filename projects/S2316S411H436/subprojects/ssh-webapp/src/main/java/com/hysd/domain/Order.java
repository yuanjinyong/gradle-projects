package com.hysd.domain;

import java.io.Serializable;

/**
 * 订单
 */
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id; // id
	private String orderNumber; // 订单号
	private Customer customer; // 所属客户

	public Order() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", orderNumber=" + orderNumber + "]";
	}

}
