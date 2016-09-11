package simple.project.speedata.shop.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import simple.config.annotation.support.ExtentionPoint;
import simple.project.speedata.shop.model.Order;
import simple.project.speedata.shop.service.AikeSyncService;

@Component
public class OrderExtentionPoint implements ExtentionPoint {

	@Autowired
	private AikeSyncService aikeSyncService;

	@Override
	public void beforeSave(Object entity) {
		Order order = (Order) entity;

		if (order.getRemoteId() == null) {
			// save to ike
			this.aikeSyncService.saveOrder(order);
		}
	}

	@Override
	public void afterSave(Object entity) {
		this.aikeSyncService.saveOrderProduct((Order) entity);
	}

	@Override
	public void beforeFetch(Object entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterFetch(Object entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeDelete(Object entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterDelete(Object entity) {
		// TODO Auto-generated method stub

	}

}
