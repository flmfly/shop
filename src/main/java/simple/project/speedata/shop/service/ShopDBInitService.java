package simple.project.speedata.shop.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import simple.base.model.BaseDict;
import simple.base.model.BaseDictItem;
import simple.base.model.BaseMenu;
import simple.base.model.BaseResource;
import simple.core.service.BaseService;

@Service
public class ShopDBInitService implements InitializingBean {

	@Autowired
	private BaseService baseService;

	@Autowired
	@Qualifier("transactionManager")
	private PlatformTransactionManager txManager;

	@Override
	public void afterPropertiesSet() throws Exception {
		TransactionTemplate tmpl = new TransactionTemplate(txManager);
		tmpl.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				updateDB();
			}
		});
	}

	private void updateDB() {

		BaseMenu bm = new BaseMenu();
		bm.setCode("010");
		bm.setName("基础数据");
		bm.setIconCss("fa fa-university");
		bm.setSort(10);
		bm = this.insertIfNotExist(bm, "code", bm.getCode());

		this.saveMenu("shop_product_category", "产品分类", "/list/productcategory", "010001", "fa fa-th-large", 10, bm);
		this.saveMenu("shop_product", "产品", "/list/product", "010002", "fa fa-product-hunt", 20, bm);
		this.saveMenu("shop_product_tag_category", "产品特征分类", "/list/producttagcategory", "010004", "fa fa-product-hunt",
				24, bm);
		this.saveMenu("shop_product_tag", "产品特征", "/tree/producttag/producttagcategory/category", "010005",
				"fa fa-product-hunt", 26, bm);
		this.saveMenu("shop_sdk", "SDK维护", "/list/sdk", "010003", "fa fa-suitcase", 30, bm);

		bm = new BaseMenu();
		bm.setCode("020");
		bm.setName("展示数据");
		bm.setIconCss("fa fa-tv");
		bm.setSort(20);
		bm = this.insertIfNotExist(bm, "code", bm.getCode());

		this.saveMenu("shop_trade", "行业应用", "/tree/trade", "020001", "fa fa-industry", 10, bm);
		this.saveMenu("shop_company", "公司风采", "/tree/company", "020002", "fa fa-building", 20, bm);
		this.saveMenu("shop_promotion", "促销活动", "/tree/promotion", "020003", "fa fa-flag", 30, bm);
		this.saveMenu("shop_oem", "OEM", "/tree/oem", "020004", "fa fa-cog", 40, bm);

		bm = new BaseMenu();
		bm.setCode("030");
		bm.setName("应用数据");
		bm.setIconCss("fa fa-life-ring");
		bm.setSort(20);
		bm = this.insertIfNotExist(bm, "code", bm.getCode());

		this.saveMenu("shop_enquiry", "报价列表", "/list/enquiry", "030001", "fa fa-money", 10, bm);
		this.saveMenu("shop_sdk_request", "SDK下载请求", "/list/sdkrequest", "030002", "fa fa-download", 20, bm);
		this.saveMenu("shop_order", "订单", "/list/order", "030003", "fa fa-file-text-o", 30, bm);
		this.saveMenu("shop_order_track", "订单追踪", "/list/ordertrack", "030004", "fa fa-file-text-o", 40, bm);

		// this.saveMenu("base_resource", "资源维护", "/list/baseresource",
		// "001001",
		// "fa fa-clipboard", 10, bm);
		// this.saveMenu("base_menu", "菜单维护", "/tree/basemenu", "001002",
		// "fa fa-list-ul", 20, bm);
		// this.saveMenu("base_dict", "字典维护", "/list/basedict", "001003",
		// "fa fa-sort-alpha-asc", 30, bm);
		// this.saveMenu("base_dict_item", "字典项维护",
		// "/tree/basedictitem/basedict/dict", "001004", "fa fa-bars", 40,
		// bm);
		// this.saveMenu("base_employee", "人员维护", "/list/baseemployee",
		// "001005",
		// "fa fa-male", 50, bm);
		// this.saveMenu("base_org", "组织维护", "/tree/baseorg", "001006",
		// "fa fa-globe", 60, bm);
		// this.saveMenu("base_role", "角色维护", "/list/baserole", "001007",
		// "fa fa-users", 70, bm);
		// this.saveMenu("base_user", "用户维护", "/list/baseuser", "001008",
		// "fa fa-user", 80, bm);
		// BaseDict bd = new BaseDict();
		// bd.setCode("userState");
		// bd.setName("用户状态");
		//
		// bd = this.insertIfNotExist(bd, "code", bd.getCode());
		//
		// BaseDictItem bdi2 = this.saveBaseDictItem("userStateNormal", "激活",
		// null, bd);
		// this.saveBaseDictItem("userStateLocked", "锁定", null, bd);
		//
		BaseDict bd1 = new BaseDict();
		bd1.setCode("userType");
		bd1.setName("用户类型");

		bd1 = this.insertIfNotExist(bd1, "code", bd1.getCode());

		// BaseDictItem bdi1 = this.saveBaseDictItem("userTypeWechart", "微信用户",
		// null,
		// bd1, 9999l);
		this.saveBaseDictItem("userTypeWechart", "微信用户", null, bd1);
		//
		// BaseDict smsOperateState = new BaseDict();
		// smsOperateState.setCode("smsOperateState");
		// smsOperateState.setName("短信状态");
		// smsOperateState = this.insertIfNotExist(smsOperateState, "code",
		// smsOperateState.getCode());
		// this.saveBaseDictItem("valid", "有效", null,smsOperateState);
		// this.saveBaseDictItem("invalid", "无效", null, smsOperateState);
		//
		// BaseUser admin = new BaseUser();
		// admin.setAccount("admin");
		// admin.setPassword("cnplhd1@3$");
		// admin.setPassword1("cnplhd1@3$");
		// admin.setName("系统管理员");
		// admin.setType(bdi1);
		// admin.setState(bdi2);
		//
		// this.insertIfNotExist(admin, "account", admin.getAccount());
	}

	private BaseDictItem saveBaseDictItem(String code, String name, BaseDictItem parent, BaseDict dict) {
		BaseDictItem bdi = new BaseDictItem();
		bdi.setCode(code);
		bdi.setName(name);
		bdi.setParent(parent);
		bdi.setDict(dict);
		bdi = this.insertIfNotExist(bdi, "code", bdi.getCode());
		return bdi;
	}

	private void saveMenu(String code, String name, String uri, String mcode, String css, int sort, BaseMenu parent) {
		BaseResource br = new BaseResource();
		br.setCode(code);
		br.setName(name);
		br.setUri(uri);
		br = this.insertIfNotExist(br, "code", br.getCode());

		BaseMenu bm = new BaseMenu();
		bm.setCode(mcode);
		bm.setName(name);
		bm.setIconCss(css);
		bm.setParent(parent);
		bm.setResource(br);
		bm.setSort(sort);
		bm = this.insertIfNotExist(bm, "code", bm.getCode());
	}

	@SuppressWarnings("unchecked")
	private <T> T insertIfNotExist(T t, String param, String val) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq(param, val));
		List<T> result = (List<T>) baseService.find(t.getClass(), criterions);
		if (result.isEmpty()) {
			this.baseService.save(t);
		} else {
			return result.get(0);
		}

		return t;

	}

}
