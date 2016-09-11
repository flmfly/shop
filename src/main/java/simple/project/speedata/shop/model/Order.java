package simple.project.speedata.shop.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;

import simple.base.model.BaseDictItem;
import simple.config.annotation.AssociateTableColumn;
import simple.config.annotation.AutoFill;
import simple.config.annotation.BooleanValue;
import simple.config.annotation.DataLength;
import simple.config.annotation.DictField;
import simple.config.annotation.Domain;
import simple.config.annotation.ExtentionPoint;
import simple.config.annotation.Reference;
import simple.config.annotation.RepresentationField;
import simple.config.annotation.RepresentationFieldType;
import simple.config.annotation.StandardOperation;
import simple.config.annotation.TableColumn;
import simple.config.annotation.Title;
import simple.config.annotation.support.CurrentDateAutoFillHandler;
import simple.core.jpa.convert.BooleanToStringConverter;
import simple.project.speedata.shop.view.ProductView;

@Domain("订单")
@Entity
@StandardOperation(delete = false, modify = false, add = false, imp = false)
@ExtentionPoint("simple.project.speedata.shop.support.OrderExtentionPoint")
@Table(name = "SPEEDATA_ORDER")
@SequenceGenerator(name = "SEQ_SPEEDATA_ORDER", sequenceName = "SEQ_SPEEDATA_ORDER")
@GenericGenerator(name = "idStrategy", strategy = "native", parameters = {
		@Parameter(name = "sequence", value = "SEQ_SPEEDATA_ORDER") })
public class Order implements Serializable {

	private static final long serialVersionUID = -2222299541763024789L;

	@Id
	@GeneratedValue(generator = "idStrategy")
	@Column(name = "ID")
	@RepresentationField(view = RepresentationFieldType.HIDDEN)
	@TableColumn(title = "id", show = false)
	private Long id;

	@Column(name = "REMOTE_ID")
	@Title("外部系统ID")
	@RepresentationField(sort = 5, disable = true)
	private Long remoteId;

	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID")
	@RepresentationField(sort = 5, title = "产品", view = RepresentationFieldType.REFERENCE, isSearchField = true)
	@Reference(id = "id", label = "name")
	@AssociateTableColumn(titles = "产品", columns = "name")
	private ProductView product;

	@Column(name = "QUANTITY", columnDefinition = "NUMERIC(6,0)")
	@RepresentationField(sort = 50, title = "数量")
	@DecimalMax(value = "9999")
	private Integer quantity;

	@Column(name = "RECEIVER", length = DataLength.CODE_LENGTH)
	@RepresentationField(sort = 10, title = "收货人", isSearchField = true)
	@TableColumn(title = "收货人")
	@Length(max = DataLength.CODE_LENGTH)
	private String receiver;

	@Column(name = "RECEIVER_PHONE", length = DataLength.CODE_LENGTH)
	@RepresentationField(sort = 10, title = "收货人电话", isSearchField = true)
	@TableColumn(title = "收货人电话")
	@Length(max = DataLength.CODE_LENGTH)
	private String receiverPhone;

	@Column(name = "ADDRESS", length = DataLength.REMARK_LENGTH)
	@RepresentationField(sort = 40, title = "收货地址", view = RepresentationFieldType.TEXTAREA)
	@TableColumn(title = "收货地址")
	@Length(max = DataLength.REMARK_LENGTH)
	private String address;

	@Column(name = "REMARK", length = DataLength.REMARK_LENGTH)
	@RepresentationField(sort = 40, title = "备注", view = RepresentationFieldType.TEXTAREA)
	@TableColumn(title = "备注")
	@Length(max = DataLength.REMARK_LENGTH)
	private String remark;

	@ManyToOne
	@JoinColumn(name = "STATE_ID")
	@RepresentationField(sort = 70, title = "状态", view = RepresentationFieldType.SELECT, isSearchField = true)
	@DictField("orderState")
	@Reference(id = "id", label = "name")
	@AssociateTableColumn(titles = "状态", columns = "name", sorts = "50")
	private BaseDictItem state;

	@Column(name = "IS_PROTOTYPE", columnDefinition = "CHAR(1)")
	@RepresentationField(sort = 80, title = "样机订单", view = RepresentationFieldType.BOOLEAN, defaultVal = "false")
	@BooleanValue({ "是", "否" })
	@Convert(converter = BooleanToStringConverter.class)
	private Boolean isPrototype;

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	@RepresentationField(sort = 5, title = "所属用户", view = RepresentationFieldType.REFERENCE, isSearchField = true, disable = true)
	@Reference(id = "id", label = "company")
	@AssociateTableColumn(titles = "所属用户,公司", columns = "account,company")
	private WechartUser user;

	@Column(name = "CREATE_TIME")
	@RepresentationField(title = "创建时间", sort = 99996, view = RepresentationFieldType.DATETIME, disable = true)
	@AutoFill(handler = CurrentDateAutoFillHandler.class)
	private Date createTime;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public WechartUser getUser() {
		return user;
	}

	public void setUser(WechartUser user) {
		this.user = user;
	}

	public Long getRemoteId() {
		return remoteId;
	}

	public void setRemoteId(Long remoteId) {
		this.remoteId = remoteId;
	}

	public ProductView getProduct() {
		return product;
	}

	public void setProduct(ProductView product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BaseDictItem getState() {
		return state;
	}

	public void setState(BaseDictItem state) {
		this.state = state;
	}

	public Boolean getIsPrototype() {
		return isPrototype;
	}

	public void setIsPrototype(Boolean isPrototype) {
		this.isPrototype = isPrototype;
	}

}
