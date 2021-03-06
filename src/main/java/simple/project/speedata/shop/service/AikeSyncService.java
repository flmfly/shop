package simple.project.speedata.shop.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import simple.core.service.BaseService;
import simple.project.speedata.shop.model.Order;
import simple.project.speedata.shop.model.OrderTrack;
import simple.project.speedata.shop.model.Product;
import simple.project.speedata.shop.model.ProductCategory;
import simple.project.speedata.shop.model.WechartUser;

@Service
public class AikeSyncService extends BaseService {

	private static final String TOKEN = "297e3d04ef46a2523fae730c72d5336d";

	private static final String URL_PREFIX = "https://dingtalk.e.ikcrm.com/api/v2";

	private static final String PRODUCT_SYNC_URL = URL_PREFIX + "/products";

	private static final String CUSTOMER_URL = URL_PREFIX + "/customers";

	private static final String ORDER_URL = URL_PREFIX + "/contracts";

	private boolean userExists(long remoteId) {
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpGet get = new HttpGet(CUSTOMER_URL + "/" + remoteId);
			get.addHeader("accept", "application/json");
			get.addHeader("Authorization", "Token token=\"" + TOKEN + "\",version_code=\"3.3.0\",device=\"dingtalk\"");
			HttpResponse response = httpClient.execute(get);
			if (response.getStatusLine().getStatusCode() != 200) {
				return false;
			}

			JSONObject jsonObject = new JSONObject(IOUtils.toString(response.getEntity().getContent()));
			int code = jsonObject.getInt("code");
			if (code == 0) {
				return true;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean saveOrUpdateUser(WechartUser user) {
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();

			if (null != user.getRemoteId() && this.userExists(user.getRemoteId())) {
				HttpPut put = new HttpPut(CUSTOMER_URL + "/" + user.getRemoteId());
				put.addHeader("accept", "application/json");
				put.addHeader("Authorization",
						"Token token=\"" + TOKEN + "\",version_code=\"3.3.0\",device=\"dingtalk\"");
				String postData = "{\"id\":" + user.getRemoteId() + ",\"customer\":{\"name\":\"" + user.getCompany()
						+ "\"}}";
				put.setEntity(new StringEntity(postData, ContentType.APPLICATION_JSON));

				HttpResponse response = httpClient.execute(put);
				if (response.getStatusLine().getStatusCode() != 200) {
					return false;
				}

				JSONObject jsonObject = new JSONObject(IOUtils.toString(response.getEntity().getContent()));
				int code = jsonObject.getInt("code");
				return code == 0;
			} else {
				HttpPost post = new HttpPost(CUSTOMER_URL);
				post.addHeader("accept", "application/json");
				post.addHeader("Authorization",
						"Token token=\"" + TOKEN + "\",version_code=\"3.3.0\",device=\"dingtalk\"");
				String postData = "{\"customer\":{\"name\":\"" + user.getCompany()
						+ "\",\"status\":3101282,\"address_attributes\":{\"wechat\": \"" + user.getAccount() + "\"}}}";
				post.setEntity(new StringEntity(postData, ContentType.APPLICATION_JSON));

				HttpResponse response = httpClient.execute(post);
				if (response.getStatusLine().getStatusCode() != 200) {
					return false;
				}

				JSONObject jsonObject = new JSONObject(IOUtils.toString(response.getEntity().getContent()));
				int code = jsonObject.getInt("code");

				if (code == 0) {
					user.setRemoteId(jsonObject.getJSONObject("data").getLong("id"));
					return true;
				} else {
					return false;
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean saveOrder(Order order) {
		if (!this.userExists(order.getUser().getRemoteId())) {
			if (!this.saveOrUpdateUser(order.getUser())) {
				return false;
			}
		}
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(ORDER_URL);
			post.addHeader("accept", "application/json");
			post.addHeader("Authorization", "Token token=\"" + TOKEN + "\",version_code=\"3.3.0\",device=\"dingtalk\"");
			String date = SDF.format(new Date());
			String postData = "{\"contract\":{\"customer_id\": " + order.getUser().getRemoteId() + ",\"title\": \"微信"
					+ (order.getIsPrototype() ? "样机" : "购货") + "合同-" + order.getUser().getCompany() + "-"
					+ order.getUser().getAccount() + "\",\"start_at\": \"" + date + "\",\"end_at\": \"" + date
					+ "\",\"approve_status\": \"approved\"}}";
			post.setEntity(new StringEntity(postData, ContentType.APPLICATION_JSON));

			HttpResponse response = httpClient.execute(post);
			if (response.getStatusLine().getStatusCode() != 200) {
				return false;
			}

			JSONObject jsonObject = new JSONObject(IOUtils.toString(response.getEntity().getContent()));
			int code = jsonObject.getInt("code");

			if (code == 0) {
				order.setRemoteId(jsonObject.getJSONObject("data").getLong("id"));
				order.setState("待审批");

				OrderTrack ot = new OrderTrack();
				ot.setOrder(order);
				ot.setRecordTime(new Date());
				ot.setState("待审批");
				super.hibernateBaseDAO.save(ot);

				return true;
			} else {
				return false;
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void saveOrderProduct(Order order) {
		long remoteId = order.getRemoteId();
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();

			HttpPost post = new HttpPost(ORDER_URL + "/" + remoteId + "/product_assets");
			post.addHeader("accept", "application/json");
			post.addHeader("Authorization", "Token token=\"" + TOKEN + "\",version_code=\"3.3.0\",device=\"dingtalk\"");
			post.setEntity(
					new StringEntity(
							"{\"id\":" + remoteId + ",\"product_asset\":{\"product_id\":"
									+ order.getProduct().getRemoteId() + ",\"quantity\":" + order.getQuantity() + "}}",
							ContentType.APPLICATION_JSON));

			httpClient.execute(post);

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void syncProducts() {
		Map<Long, ProductCategory> categoryMap = new HashMap<Long, ProductCategory>();
		Map<Long, Product> productMap = new HashMap<Long, Product>();
		try {

			Long total = this.getTotal(PRODUCT_SYNC_URL);

			for (int page = 0; page * PER_PAGE < total; page++) {
				HttpClient httpClient = HttpClientBuilder.create().build();
				HttpGet getRequest = new HttpGet(PRODUCT_SYNC_URL + "?page=" + (page + 1) + "&per_page=" + PER_PAGE);
				getRequest.addHeader("accept", "application/json");
				getRequest.addHeader("Authorization",
						"Token token=\"" + TOKEN + "\",version_code=\"3.3.0\",device=\"dingtalk\"");

				HttpResponse response = httpClient.execute(getRequest);

				if (response.getStatusLine().getStatusCode() != 200) {
					throw new RuntimeException(
							"Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
				}

				JSONObject jsonObject = new JSONObject(IOUtils.toString(response.getEntity().getContent()));
				int code = jsonObject.getInt("code");

				if (code == 0) {
					JSONObject data = jsonObject.getJSONObject("data");

					JSONArray categories = data.getJSONArray("product_categories");
					for (int i = 0; i < categories.length(); i++) {
						JSONObject category = categories.getJSONObject(i);
						Long id = category.getLong("id");
						String name = category.getString("name");
						if (!categoryMap.containsKey(id)) {
							ProductCategory pc = new ProductCategory();
							pc.setRemoteId(id);
							pc.setName(name);
							categoryMap.put(id, pc);
						}
					}

					JSONArray products = data.getJSONArray("products");
					for (int i = 0; i < products.length(); i++) {
						JSONObject product = products.getJSONObject(i);
						Long id = product.getLong("id");
						String name = product.getString("name");
						if (!productMap.containsKey(id)) {
							Product p = new Product();
							p.setRemoteId(id);
							p.setName(name);
							p.setCategory(categoryMap.get(product.getJSONObject("product_category").getLong("id")));
							p.setCode(product.getString("product_no"));
							productMap.put(id, p);
						}
					}
				} else {
					throw new RuntimeException("Failed : API error code : " + code);
				}
			}

			Set<Long> categoryExistSet = new HashSet<Long>();

			Map<Long, ProductCategory> dbCategories = new HashMap<Long, ProductCategory>();

			List<ProductCategory> categories = (List<ProductCategory>) super.hibernateBaseDAO
					.getCriteria(ProductCategory.class).list();
			for (ProductCategory category : categories) {
				Long remoteId = category.getRemoteId();
				ProductCategory temp = categoryMap.get(remoteId);

				if (null != temp) {
					String name = temp.getName();
					if (null != name && !name.equals(category.getName())) {
						category.setName(name);
						super.save(category);
					}
					categoryExistSet.add(remoteId);
					dbCategories.put(remoteId, category);
				} else {
					category.setIsValid(false);
					super.save(category);
				}

			}

			for (Long id : categoryMap.keySet()) {
				if (!categoryExistSet.contains(id)) {
					ProductCategory category = categoryMap.get(id);
					category.setIsValid(true);
					super.save(category);
					dbCategories.put(id, category);
				}
			}

			Set<Long> productExistSet = new HashSet<Long>();

			List<Product> products = (List<Product>) super.hibernateBaseDAO.getCriteria(Product.class).list();
			for (Product product : products) {
				Long remoteId = product.getRemoteId();
				Product temp = productMap.get(remoteId);

				if (null != temp) {
					String name = temp.getName();
					if (null != name && !name.equals(product.getName())) {
						product.setName(name);
						super.save(product);
					}
					long categoryId = temp.getCategory().getRemoteId();
					if (product.getCategory().getRemoteId() != categoryId) {
						product.setCategory(dbCategories.get(categoryId));
					}
					productExistSet.add(remoteId);
				} else {
					product.setIsValid(false);
					super.save(product);
				}
			}

			for (Long id : productMap.keySet()) {
				if (!productExistSet.contains(id)) {
					Product p = productMap.get(id);
					p.setIsValid(true);
					p.setCategory(dbCategories.get(p.getCategory().getRemoteId()));
					super.save(p);
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private Long getTotal(String url) throws ClientProtocolException, IOException {
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet getRequest = new HttpGet(url + "?page=1&per_page=1");
		getRequest.addHeader("accept", "application/json");
		getRequest.addHeader("Authorization",
				"Token token=\"" + TOKEN + "\",version_code=\"3.3.0\",device=\"dingtalk\"");

		HttpResponse response = httpClient.execute(getRequest);

		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
		}

		JSONObject jsonObject = new JSONObject(IOUtils.toString(response.getEntity().getContent()));
		int code = jsonObject.getInt("code");

		if (code == 0) {
			JSONObject data = jsonObject.getJSONObject("data");

			return data.getLong("total_count");
		} else {
			throw new RuntimeException("Failed : API error code : " + code);
		}
	}

	@SuppressWarnings("unchecked")
	public void fetchOrderStates() {
		Criteria c = super.hibernateBaseDAO.getCriteria(Order.class);
		c.add(Restrictions.or(Restrictions.ne("state", "成功结束"), Restrictions.ne("state", "已删除")));

		List<Order> orderList = c.list();

		try {
			for (Order order : orderList) {
				HttpClient httpClient = HttpClientBuilder.create().build();
				HttpGet get = new HttpGet(ORDER_URL + "/" + order.getRemoteId());
				get.addHeader("accept", "application/json");
				get.addHeader("Authorization",
						"Token token=\"" + TOKEN + "\",version_code=\"3.3.0\",device=\"dingtalk\"");

				HttpResponse response = httpClient.execute(get);
				if (response.getStatusLine().getStatusCode() == 200) {

					JSONObject jsonObject = new JSONObject(IOUtils.toString(response.getEntity().getContent()));
					String newStatus = jsonObject.getJSONObject("data").getString("status_mapped");

					if (!"".equals(newStatus) && !order.getState().equals(newStatus)) {
						order.setState(newStatus);

						OrderTrack ot = new OrderTrack();
						ot.setOrder(order);
						ot.setRecordTime(new Date());
						ot.setState(newStatus);
						super.save(ot);
						super.save(order);
					}
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		JSONObject jsonObject = new JSONObject(SAMPLE_JSON_STR);
		System.out.println(jsonObject.getInt("code"));
		JSONObject data = jsonObject.getJSONObject("data");
		System.out.println(data.getLong("total_count"));
		System.out.println(data.getLong("page"));
		System.out.println(data.getLong("per_page"));
		System.out.println(data.getBoolean("is_create"));
		JSONArray categories = data.getJSONArray("product_categories");

		for (int i = 0; i < categories.length(); i++) {
			JSONObject category = categories.getJSONObject(i);
			System.out.println(category.getLong("id"));
			System.out.println(category.getString("name"));
		}
		JSONArray products = data.getJSONArray("products");

		for (int i = 0; i < products.length(); i++) {
			JSONObject product = products.getJSONObject(i);
			System.out.println(product.getLong("id"));
			System.out.println(product.getString("name"));
			System.out.println(product.getJSONObject("product_category").getLong("id"));
		}

	}

	private static final int PER_PAGE = 100;
	private static final String SAMPLE_JSON_STR = "{\"code\":0,\"data\":{\"product_categories\":[{\"id\":39074,\"name\":\"默认分类\",\"self_and_parents_name\":\"默认分类\",\"product_count\":0},{\"id\":48424,\"name\":\"KT40\",\"self_and_parents_name\":\"KT40\",\"product_count\":0},{\"id\":48425,\"name\":\"KT45\",\"self_and_parents_name\":\"KT45\",\"product_count\":0},{\"id\":48431,\"name\":\"KT50\",\"self_and_parents_name\":\"KT50\",\"product_count\":0},{\"id\":48426,\"name\":\"KT55\",\"self_and_parents_name\":\"KT55\",\"product_count\":0},{\"id\":48428,\"name\":\"KT80\",\"self_and_parents_name\":\"KT80\",\"product_count\":0},{\"id\":48429,\"name\":\"TT43R\",\"self_and_parents_name\":\"TT43R\",\"product_count\":0},{\"id\":48430,\"name\":\"TT35\",\"self_and_parents_name\":\"TT35\",\"product_count\":0},{\"id\":48434,\"name\":\"KT45Q\",\"self_and_parents_name\":\"KT45Q\",\"product_count\":0},{\"id\":48435,\"name\":\"KT40Q\",\"self_and_parents_name\":\"KT40Q\",\"product_count\":0},{\"id\":48436,\"name\":\"ODM\",\"self_and_parents_name\":\"ODM\",\"product_count\":0},{\"id\":48437,\"name\":\"配件\",\"self_and_parents_name\":\"配件\",\"product_count\":0},{\"id\":48438,\"name\":\"模块\",\"self_and_parents_name\":\"模块\",\"product_count\":0},{\"id\":48439,\"name\":\"组装物料\",\"self_and_parents_name\":\"组装物料\",\"product_count\":0},{\"id\":48447,\"name\":\"项目合作\",\"self_and_parents_name\":\"项目合作\",\"product_count\":5},{\"id\":48448,\"name\":\"外采产品\",\"self_and_parents_name\":\"外采产品\",\"product_count\":0}],\"products\":[{\"id\":318776,\"name\":\"KT50-B2-C8M-N43-8030\",\"product_no\":\"\",\"category\":0,\"category_mapped\":\"\",\"standard_unit_price\":\"\",\"sale_unit\":\"\",\"introduction\":\"\",\"attachment\":{},\"gross_margin\":\"0.0%\",\"unit_cost\":0.0,\"created_at\":\"2016-08-19 16:51\",\"updated_at\":\"2016-08-19 16:51\",\"is_edit\":true,\"product_category\":{\"id\":48431,\"name\":\"KT50\",\"self_and_parents_name\":\"KT50\",\"product_count\":0}}],\"total_count\":920,\"per_page\":1,\"page\":1,\"is_create\":true}}";
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm");
}
