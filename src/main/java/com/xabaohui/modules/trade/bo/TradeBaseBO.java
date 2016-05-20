package com.xabaohui.modules.trade.bo;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.xabaohui.modules.trade.bean.Order;
import com.xabaohui.modules.trade.bean.OrderDetail;
import com.xabaohui.modules.trade.bean.OrderLog;
import com.xabaohui.modules.trade.bean.OrderLogOperateType;
import com.xabaohui.modules.trade.bean.OrderStatus;
import com.xabaohui.modules.trade.dao.OrderDao;
import com.xabaohui.modules.trade.dao.OrderDetailDao;
import com.xabaohui.modules.trade.dao.OrderLogDao;
import com.xabaohui.modules.trade.dto.CreateOrderLogDTO;

/**
 * ���շ���״̬�Ľӿ�
 * 
 * @author zhang Hongwei
 * 
 */
public class TradeBaseBO {

	private OrderDao orderDao;
	private OrderDetailDao orderDetailDao;
	private OrderLogDao orderLogDao;
	private Logger log = LoggerFactory.getLogger(TradeBaseBO.class);

	/**
	 * ֧���ɹ���
	 */
	public void payForOrderSuccess(Integer orderId, Integer operatorId) {

		checkOrderIsEmpty(orderId, operatorId);
		flashOrderMessage(orderId, OrderStatus.ORDER_STATUS_PAID);

		// ��־
		String logText = "֧���ɹ�";
		String operateType = OrderLogOperateType.ORDER_LOG_OPERATE_TYPE_PAY;
		createOrderLog(logText, operateType, orderId, operatorId);
	}

	/**
	 * ���䵽�䷢ϵͳ��
	 */
	public void assignForOrderSuccess(Integer orderId, Integer operatorId) {
		checkOrderIsEmpty(orderId, operatorId);
		flashOrderMessage(orderId, OrderStatus.ORDER_STATUS_ASSIGNED);

		// ��־
		String logText = "�ѷ���ɹ�";
		String operateType = OrderLogOperateType.ORDER_LOG_OPERATE_TYPE_ASSIGN;
		createOrderLog(logText, operateType, orderId, operatorId);

	}

	/**
	 * �����ɹ�
	 */
	public void sendedSuccess(Integer orderId, Integer operatorId) {

		checkOrderIsEmpty(orderId, operatorId);
		flashOrderMessage(orderId, OrderStatus.ORDER_STATUS_SENDED);
		// ��־
		String logText = "�����ɹ�";
		String operateType = OrderLogOperateType.ORDER_LOG_OPERATE_TYPE_SEND;
		createOrderLog(logText, operateType, orderId, operatorId);
	}
	/**
	 * �˿�ɹ�
	 */
	public void refundForOrderSuccess(Integer orderId, Integer operatorId) {
		checkOrderIsEmpty(orderId, operatorId);
		flashOrderMessage(orderId, OrderStatus.ORDER_STATUS_REFUNDSOME);

		// ��־
		String logText = "�˿�ɹ�";
		String operateType = OrderLogOperateType.ORDER_LOG_OPERATE_TYPE_REFUND;
		createOrderLog(logText, operateType, orderId, operatorId);
	}

	/**
	 * ��ȫ�˿�֮��
	 */
	public void refundForOrderOver(Integer orderId, Integer operatorId) {
		checkOrderIsEmpty(orderId, operatorId);
		flashOrderMessage(orderId, OrderStatus.ORDER_STATUS_CANCEL);
		// ��־
		String logText = "ȫ���˿�ɹ�";
		String operateType = OrderLogOperateType.ORDER_LOG_OPERATE_TYPE_REFUND;
		createOrderLog(logText, operateType, orderId, operatorId);
	}

	/**
	 * ���¶�����ϸ�汾��
	 * 
	 * @param upcDto
	 * @return
	 */
	private int flashOrderDetailVersion(Integer id) {
		// ��֤
		if (id == null) {
			throw new RuntimeException("��ȡIDΪ��");
		}
		OrderDetail od = orderDetailDao.findById(id);
		return od.getVersion() + 1;
	}

	/**
	 * ������־
	 * 
	 * @param logText
	 * @param operateType
	 * @param orderId
	 * @param operatorId
	 */
	private void createOrderLog(String logText, String operateType,
			Integer orderId, Integer operatorId) {
		// ��־
		CreateOrderLogDTO col = new CreateOrderLogDTO();
		col.setOperateDetail(logText);
		col.setOperateType(operateType);
		col.setOperatorId(operatorId);
		col.setOrderId(orderId);

		OrderLog orderlog = new OrderLog();
		BeanUtils.copyProperties(col, orderlog);
		Date date = new Date();
		orderlog.setGmtCreate(date);
		orderlog.setGmtModify(date);
		orderlog.setVersion(1);
		orderLogDao.save(orderlog);
		log.info("��־��ӳɹ�");
	}

	/**
	 * ���¶�����Ϣ
	 * 
	 * @param order
	 * @param orderStatus
	 */
	private void flashOrderMessage(Integer orderId, String orderStatus) {

		Order order = orderDao.findById(orderId);
		if (order == null) {
			throw new RuntimeException("TradeBase��δ��ȡ��������");
		}
		order.setOrderStatus(orderStatus);
		Date date = new Date();
		order.setGmtModify(date);
		order.setVersion(flashOrderDetailVersion(order.getOrderId()));
	}

	/**
	 * ���Order�Ƿ�Ϊ��
	 * 
	 * @param order
	 */
	private void checkOrderIsEmpty(Integer orderId, Integer operatorId) {
		if (orderId == null) {
			throw new RuntimeException("TradeBase��δ��ȡ����ID");
		}
		if (operatorId == null) {
			throw new RuntimeException("TradeBase��δ��ȡ����ԱID");
		}
		log.info("��֤ͨ��");
	}

	public OrderDao getOrderDao() {
		return orderDao;
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public OrderDetailDao getOrderDetailDao() {
		return orderDetailDao;
	}

	public void setOrderDetailDao(OrderDetailDao orderDetailDao) {
		this.orderDetailDao = orderDetailDao;
	}

	public OrderLogDao getOrderLogDao() {
		return orderLogDao;
	}

	public void setOrderLogDao(OrderLogDao orderLogDao) {
		this.orderLogDao = orderLogDao;
	}
}
