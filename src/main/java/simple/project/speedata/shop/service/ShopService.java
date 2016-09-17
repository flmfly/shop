package simple.project.speedata.shop.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import simple.core.service.BaseService;
import simple.project.speedata.shop.model.Product;
import simple.project.speedata.shop.model.ProductPicture;
import simple.project.speedata.shop.model.ProductTag;
import simple.project.speedata.shop.view.ProductView;

@Service
public class ShopService extends BaseService {

	public List<ProductView> select(String tags) {
		Map tagsMap = super.DEFAULT_GSON.fromJson(tags, Map.class);
		Set<Long> tagSet = new HashSet<Long>();
		for (Object obj : tagsMap.values()) {
			tagSet.add(Long.parseLong(String.valueOf(obj)));
		}

		List<ProductView> rtnList = new ArrayList<ProductView>();

		Criteria c = super.hibernateBaseDAO.getCriteria(Product.class);
		c.add(Restrictions.eq("isValid", true));
		c.createCriteria("category").add(Restrictions.eq("isValid", true));

		List<Product> pList = c.list();

		for (Product p : pList) {
			int i = 0;
			Set<ProductTag> ptags = p.getTags();

			Set<Long> ptagsSet = new HashSet<Long>();
			for (ProductTag pt : ptags) {
				ptagsSet.add(pt.getId());
			}

			for (long l : tagSet) {
				if (ptagsSet.contains(l)) {
					i++;
				}
			}

			if (i == tagSet.size()) {
				ProductView pv = new ProductView();
				pv.setId(p.getId());
				pv.setName(p.getName());

				Set<ProductPicture> attachments = new LinkedHashSet<ProductPicture>();
				for (ProductPicture pp : p.getAttachment()) {
					ProductPicture ppa = new ProductPicture();
					ppa.setUrl(pp.getUrl());
					attachments.add(ppa);
				}

				pv.setAttachment(attachments);

				rtnList.add(pv);
			}
		}

		return rtnList;
	}

}
