package simple.project.speedata.shop.model;

import java.io.Serializable;

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

import simple.base.model.BaseDictItem;
import simple.config.annotation.AssociateTableColumn;
import simple.config.annotation.DataLength;
import simple.config.annotation.DictField;
import simple.config.annotation.Domain;
import simple.config.annotation.ExtentionPoint;
import simple.config.annotation.Reference;
import simple.config.annotation.RepresentationField;
import simple.config.annotation.RepresentationFieldType;
import simple.config.annotation.TableColumn;
import simple.config.annotation.Title;

@Domain("微信用户")
@Entity
@Table(name = "SPEEDATA_WECHART_USER")
@SequenceGenerator(name = "SEQ_SPEEDATA_WECHART_USER", sequenceName = "SEQ_SPEEDATA_WECHART_USER")
@ExtentionPoint("simple.project.speedata.shop.support.WechartUserExtentionPoint")
@GenericGenerator(name = "idStrategy", strategy = "native", parameters = {
		@Parameter(name = "sequence", value = "SEQ_SPEEDATA_WECHART_USER") })
public class WechartUser implements Serializable {

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

	@Column(name = "WECHART_ID", length = DataLength.CODE_LENGTH)
	@RepresentationField(sort = 10, title = "微信号", isSearchField = true)
	@TableColumn(title = "微信号")
	@Length(max = DataLength.CODE_LENGTH)
	private String account;

	@Column(name = "COMPANY", length = DataLength.CODE_LENGTH)
	@RepresentationField(sort = 10, title = "公司名称", isSearchField = true)
	@TableColumn(title = "公司名称")
	@Length(max = DataLength.CODE_LENGTH)
	private String company;

	@Column(name = "LICENSE", length = DataLength.CODE_LENGTH)
	@RepresentationField(sort = 10, title = "营业执照", isSearchField = true)
	@TableColumn(title = "营业执照")
	@Length(max = DataLength.CODE_LENGTH)
	private String license;

	@Column(name = "PHONE", length = DataLength.CODE_LENGTH)
	@RepresentationField(sort = 10, title = "联系电话", isSearchField = true)
	@TableColumn(title = "联系电话")
	@Length(max = DataLength.CODE_LENGTH)
	private String phone;

	@Column(name = "TITLE", length = DataLength.CODE_LENGTH)
	@RepresentationField(sort = 10, title = "称呼", isSearchField = true)
	@TableColumn(title = "称呼")
	@Length(max = DataLength.CODE_LENGTH)
	private String title;

	@ManyToOne
	@JoinColumn(name = "TRADE_ID")
	@RepresentationField(sort = 70, title = "应用行业", view = RepresentationFieldType.SELECT, isSearchField = true)
	@DictField("trade")
	@Reference(id = "id", label = "name")
	@AssociateTableColumn(titles = "应用行业", columns = "name", sorts = "50")
	@NotNull(message = "状态不能为空！")
	private BaseDictItem trade;

	@Column(name = "ADDRESS", length = DataLength.REMARK_LENGTH)
	@RepresentationField(sort = 40, title = "公司地址", view = RepresentationFieldType.TEXTAREA)
	@TableColumn(title = "公司地址")
	@Length(max = DataLength.REMARK_LENGTH)
	private String address;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRemoteId() {
		return remoteId;
	}

	public void setRemoteId(Long remoteId) {
		this.remoteId = remoteId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BaseDictItem getTrade() {
		return trade;
	}

	public void setTrade(BaseDictItem trade) {
		this.trade = trade;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
