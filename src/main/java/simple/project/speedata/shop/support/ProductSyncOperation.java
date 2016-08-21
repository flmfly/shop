package simple.project.speedata.shop.support;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import simple.config.annotation.support.OperationHandler;
import simple.project.speedata.shop.service.AikeSyncService;

@Component
public class ProductSyncOperation implements OperationHandler {

	@Autowired
	private AikeSyncService aikeSyncService;

	@Override
	public boolean disabled(Object domain) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public OperationResult handle(Map<String, Object> parameters, List<Object> domains) {
		this.aikeSyncService.syncProducts();
		OperationResult result = new OperationResult();
		result.setSuccess(true);
		return result;
	}

}
