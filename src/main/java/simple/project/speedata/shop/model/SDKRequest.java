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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;

import simple.base.model.BaseUser;
import simple.config.annotation.AssociateTableColumn;
import simple.config.annotation.AutoFill;
import simple.config.annotation.BooleanValue;
import simple.config.annotation.DataLength;
import simple.config.annotation.DefaultValue;
import simple.config.annotation.Domain;
import simple.config.annotation.Reference;
import simple.config.annotation.RepresentationField;
import simple.config.annotation.RepresentationFieldType;
import simple.config.annotation.SearchField;
import simple.config.annotation.StandardOperation;
import simple.config.annotation.TableColumn;
import simple.config.annotation.Title;
import simple.config.annotation.support.CurrentDateTimeAutoFillHandler;
import simple.config.annotation.support.DateTimeDefaultValueHandler;
import simple.core.jpa.convert.BooleanToStringConverter;

@Domain
@StandardOperation(add = false, modify = false, imp = false, check = false, delete = false)
@Entity
@Table(name = "SPEEDATA_SDK_REQUEST")
@SequenceGenerator(name = "SEQ_SPEEDATA_SDKK_REQUEST", sequenceName = "SEQ_SPEEDATA_SDKK_REQUEST")
@GenericGenerator(name = "idStrategy", strategy = "native", parameters = {
		@Parameter(name = "sequence", value = "SEQ_SPEEDATA_SDKK_REQUEST") })
public class SDKRequest implements Serializable {

	private static final long serialVersionUID = -2222299541763024789L;

	@Id
	@GeneratedValue(generator = "idStrategy")
	@Column(name = "ID")
	@RepresentationField(view = RepresentationFieldType.HIDDEN)
	@TableColumn(title = "id", show = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID")
	@RepresentationField(sort = 5, title = "产品", view = RepresentationFieldType.REFERENCE, isSearchField = true)
	@Reference(id = "id", label = "name")
	@AssociateTableColumn(titles = "产品分类,产品名称", columns = "category.name,name")
	private Product product;

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	@RepresentationField(sort = 5, title = "请求用户", view = RepresentationFieldType.REFERENCE, isSearchField = true)
	@Reference(id = "id", label = "name")
	@AssociateTableColumn(titles = "请求用户", columns = "name")
	private BaseUser user;

	@Column(name = "EMAIL", length = DataLength.LONG_TEXT_LENGTH)
	@Title("邮箱")
	@RepresentationField(sort = 10)
	@SearchField(canFuzzy = true)
	@TableColumn(title = "邮箱")
	@NotNull(message = "邮箱不能为空！")
	@Length(max = DataLength.LONG_TEXT_LENGTH)
	private String email;

	@Column(name = "CREATE_TIME")
	@Title("请求下载时间")
	@RepresentationField(sort = 99996, view = RepresentationFieldType.DATETIME, disable = true)
	@TableColumn(title = "请求下载时间")
	@SearchField(isRange = true)
	@AutoFill(handler = CurrentDateTimeAutoFillHandler.class)
	@DefaultValue(handler = DateTimeDefaultValueHandler.class)
	private Date createTime;

	@Column(name = "IS_SENT", columnDefinition = "CHAR(1)")
	@Title("处理状态")
	@RepresentationField(sort = 99995, view = RepresentationFieldType.BOOLEAN, defaultVal = "false")
	@SearchField
	@TableColumn(title = "处理状态")
	@BooleanValue({ "已发送", "未发送" })
	@Convert(converter = BooleanToStringConverter.class)
	private Boolean isSent;

	@Column(name = "SENT_STATUS", columnDefinition = "CHAR(1)")
	@Title("发送状态")
	@RepresentationField(sort = 99995, view = RepresentationFieldType.BOOLEAN, defaultVal = "false")
	@TableColumn(title = "发送状态")
	@BooleanValue({ "成功", "" })
	@Convert(converter = BooleanToStringConverter.class)
	private Boolean sentStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BaseUser getUser() {
		return user;
	}

	public void setUser(BaseUser user) {
		this.user = user;
	}

	public Boolean getIsSent() {
		return isSent;
	}

	public void setIsSent(Boolean isSent) {
		this.isSent = isSent;
	}

	public Boolean getSentStatus() {
		return sentStatus;
	}

	public void setSentStatus(Boolean sentStatus) {
		this.sentStatus = sentStatus;
	}

}
