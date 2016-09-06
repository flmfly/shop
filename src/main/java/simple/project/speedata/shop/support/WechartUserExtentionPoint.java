package simple.project.speedata.shop.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import simple.config.annotation.support.ExtentionPoint;
import simple.project.speedata.shop.model.WechartUser;
import simple.project.speedata.shop.service.AikeSyncService;

@Component
public class WechartUserExtentionPoint implements ExtentionPoint {

	@Autowired
	private AikeSyncService aikeSyncService;

	@Override
	public void beforeSave(Object entity) {
		WechartUser user = (WechartUser) entity;

		if (user.getCompany() != null) {
			// save to ike
			this.aikeSyncService.saveOrUpdateUser(user);
			user.setRemoteId(-1l);
		}
	}

	@Override
	public void afterSave(Object entity) {

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
