package simple.project.speedata.shop.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import simple.core.Constants;
import simple.core.model.DomainData;
import simple.core.model.Status;
import simple.project.speedata.shop.service.ShopService;

/**
 * @author Jeffrey
 */
@RestController
@RequestMapping("/shop")
public class ShopController implements Constants {

	/** The base service. */
	@Autowired
	protected ShopService shopService;

	/** The Constant logger. */
	static final Logger logger = Logger.getLogger(ShopController.class);

	@RequestMapping(value = "/select", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json;charset=UTF-8")
	public String select(@RequestBody String json) {
		DomainData domainData = new DomainData();
		try {
			domainData.setData(this.shopService.select(json));
		} catch (Exception e) {
			e.printStackTrace();
			domainData.setStatus(new Status(500, e.getMessage()));
		}
		return this.shopService.toJsonStrWithSubs(domainData);
	}

}