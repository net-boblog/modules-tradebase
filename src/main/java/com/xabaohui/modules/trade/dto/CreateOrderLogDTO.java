package com.xabaohui.modules.trade.dto;


public class CreateOrderLogDTO {
	private Integer orderId;
	private Integer operatorId; // ����Ա XXX operator�����ʶ�ƴ���ˡ�����
	private String operateDetail;
	private String operateType;

	public CreateOrderLogDTO() {
		super();
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperateDetail() {
		return operateDetail;
	}

	public void setOperateDetail(String operateDetail) {
		this.operateDetail = operateDetail;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	

}
