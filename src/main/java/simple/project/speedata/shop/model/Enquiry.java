package simple.project.speedata.shop.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;

import simple.base.BaseModel;
import simple.base.model.BaseUser;
import simple.config.annotation.BooleanValue;
import simple.config.annotation.DataLength;
import simple.config.annotation.Domain;
import simple.config.annotation.Operation;
import simple.config.annotation.RepresentationField;
import simple.config.annotation.RepresentationFieldType;
import simple.config.annotation.TableColumn;
import simple.core.jpa.convert.BooleanToStringConverter;
import simple.core.validation.annotation.UniqueKey;
import simple.project.speedata.shop.support.EnquiryApproveOperation;

@Domain
@Entity
@Table(name = "SPEEDATA_ENQUIRY")
@UniqueKey(columnNames = { "name" }, message = "行业名称已存在！")
@Operation(handler = EnquiryApproveOperation.class, code = "approve", name = "审核通过", multi = true, iconStyle = "fa fa-check")
@SequenceGenerator(name = "SEQ_SPEEDATA_ENQUIRY", sequenceName = "SEQ_SPEEDATA_ENQUIRY")
@GenericGenerator(name = "idStrategy", strategy = "native", parameters = {
		@Parameter(name = "sequence", value = "SEQ_SPEEDATA_ENQUIRY") })
public class Enquiry extends BaseModel implements Serializable {

	private static final long serialVersionUID = 361702473050426893L;

	@Column(name = "PROJECT", length = DataLength.LONG_TEXT_LENGTH)
	@RepresentationField(sort = 10, title = "项目名称", isSearchField = true, disable = true)
	@TableColumn(title = "项目名称")
	@NotNull(message = "项目名称不能为空！")
	@Length(max = DataLength.LONG_TEXT_LENGTH)
	private String project;

	@Column(name = "FUNC", length = DataLength.MAX_STRING_LENGTH)
	@RepresentationField(sort = 10, title = "功能描述", view = RepresentationFieldType.TEXTAREA, disable = true)
	@TableColumn(title = "功能描述")
	@NotNull(message = "功能描述不能为空！")
	@Length(max = DataLength.MAX_STRING_LENGTH)
	private String func;

	@Column(name = "QUANTITY", columnDefinition = "NUMERIC(4,0)")
	@RepresentationField(sort = 50, title = "数量", disable = true)
	@DecimalMax(value = "9999")
	private Integer quantity;

	@Column(name = "IS_CUSTOM", columnDefinition = "CHAR(1)")
	@RepresentationField(title = "是否定制", sort = 99995, view = RepresentationFieldType.BOOLEAN, disable = true)
	@BooleanValue({ "是", "否" })
	@Convert(converter = BooleanToStringConverter.class)
	private Boolean isCustom;

	@Column(name = "EMAIL", length = DataLength.LONG_TEXT_LENGTH)
	@RepresentationField(sort = 10, title = "邮箱", disable = true)
	@TableColumn(title = "邮箱")
	@NotNull(message = "邮箱不能为空！")
	@Length(max = DataLength.LONG_TEXT_LENGTH)
	private String email;

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private BaseUser user;

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getFunc() {
		return func;
	}

	public void setFunc(String func) {
		this.func = func;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Boolean getIsCustom() {
		return isCustom;
	}

	public void setIsCustom(Boolean isCustom) {
		this.isCustom = isCustom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BaseUser getUser() {
		return user;
	}

	public void setUser(BaseUser user) {
		this.user = user;
	}

}
