package simple.project.speedata.shop.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
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

import simple.config.annotation.AssociateTableColumn;
import simple.config.annotation.DataLength;
import simple.config.annotation.DefaultValue;
import simple.config.annotation.Domain;
import simple.config.annotation.Reference;
import simple.config.annotation.RepresentationField;
import simple.config.annotation.RepresentationFieldType;
import simple.config.annotation.SearchField;
import simple.config.annotation.TableColumn;
import simple.config.annotation.Title;
import simple.config.annotation.support.DateTimeDefaultValueHandler;
import simple.core.validation.annotation.UniqueKey;

@Domain
@Entity
@Table(name = "SPEEDATA_SDK")
@UniqueKey(columnNames = { "product" }, message = "该产品的sdk已存在！")
@SequenceGenerator(name = "SEQ_SPEEDATA_SDK", sequenceName = "SEQ_SPEEDATA_SDK")
@GenericGenerator(name = "idStrategy", strategy = "native", parameters = {
		@Parameter(name = "sequence", value = "SEQ_SPEEDATA_SDK") })
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
	@AssociateTableColumn(titles = "产品编码,产品名称", columns = "code,name")
	private Product product;

	@Column(name = "EMAIL", length = DataLength.LONG_TEXT_LENGTH)
	@Title("邮箱")
	@RepresentationField(sort = 10, isSearchField = true)
	@TableColumn(title = "邮箱")
	@NotNull(message = "邮箱不能为空！")
	@Length(max = DataLength.LONG_TEXT_LENGTH)
	private String email;

	@Column(name = "CREATE_TIME")
	@Title("请求下载时间")
	@RepresentationField(sort = 99996, view = RepresentationFieldType.DATE, disable = true)
	@TableColumn(title = "请求下载时间")
	@SearchField(isRange = true)
	@DefaultValue(handler = DateTimeDefaultValueHandler.class)
	private Date createTime;

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

}
