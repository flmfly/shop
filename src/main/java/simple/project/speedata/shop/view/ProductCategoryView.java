package simple.project.speedata.shop.view;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;

import simple.config.annotation.BooleanValue;
import simple.config.annotation.DataLength;
import simple.config.annotation.Domain;
import simple.config.annotation.RepresentationField;
import simple.config.annotation.RepresentationFieldType;
import simple.config.annotation.SearchField;
import simple.config.annotation.TableColumn;
import simple.config.annotation.Title;
import simple.config.annotation.TreeInfo;
import simple.core.jpa.convert.BooleanToStringConverter;
import simple.core.validation.annotation.UniqueKey;

@Domain(defaultSort = "sort", value = "产品分类")
@Entity
@Table(name = "SPEEDATA_PRODUCT_CATEGORY")
@UniqueKey(columnNames = { "name" }, message = "产品分类已存在！")
@TreeInfo(id = "id", label = "name", pid = "parent.id")
@SequenceGenerator(name = "SEQ_SPEEDATA_PRODUCT_CATEGORY", sequenceName = "SEQ_SPEEDATA_PRODUCT_CATEGORY")
@GenericGenerator(name = "idStrategy", strategy = "native", parameters = {
		@Parameter(name = "sequence", value = "SEQ_SPEEDATA_PRODUCT_CATEGORY") })
public class ProductCategoryView implements Serializable {

	private static final long serialVersionUID = -2222299541763024789L;

	@Id
	@GeneratedValue(generator = "idStrategy")
	@Column(name = "ID")
	@RepresentationField(view = RepresentationFieldType.HIDDEN)
	@TableColumn(title = "id", show = false)
	private Long id;

	@Column(name = "IS_VALID", columnDefinition = "CHAR(1)")
	@Title("是否有效")
	@RepresentationField(sort = 99995, view = RepresentationFieldType.BOOLEAN, defaultVal = "true")
	@SearchField
	@TableColumn(title = "是否有效")
	@BooleanValue({ "有效", "无效" })
	@Convert(converter = BooleanToStringConverter.class)
	private Boolean isValid;

	@Column(name = "NAME", length = DataLength.NAME_LENGTH)
	@RepresentationField(sort = 20, title = "名称", isSearchField = true, disable = true)
	@TableColumn(title = "名称")
	@NotNull(message = "名称不能为空！")
	@Length(max = DataLength.NAME_LENGTH)
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
