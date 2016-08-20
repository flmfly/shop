package simple.project.speedata.shop.model;

import java.io.Serializable;

import javax.persistence.Column;
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

import simple.base.BaseValiditySupportModel;
import simple.config.annotation.AssociateTableColumn;
import simple.config.annotation.DataLength;
import simple.config.annotation.Domain;
import simple.config.annotation.Reference;
import simple.config.annotation.RepresentationField;
import simple.config.annotation.RepresentationFieldType;
import simple.config.annotation.TableColumn;
import simple.config.annotation.TreeInfo;
import simple.core.validation.annotation.UniqueKey;

@Domain(defaultSort = "sort", value = "产品")
@Entity
@Table(name = "SPEEDATA_PRODUCT")
@UniqueKey(columnNames = { "name" }, message = "行业名称已存在！")
@TreeInfo(id = "id", label = "name", pid = "parent.id")
@SequenceGenerator(name = "SEQ_SPEEDATA_PRODUCT", sequenceName = "SEQ_SPEEDATA_PRODUCT")
@GenericGenerator(name = "idStrategy", strategy = "native", parameters = {
		@Parameter(name = "sequence", value = "SEQ_SPEEDATA_PRODUCT") })
public class Product extends BaseValiditySupportModel implements Serializable {

	private static final long serialVersionUID = -2222299541763024789L;

	@Column(name = "CODE", length = DataLength.CODE_LENGTH)
	@RepresentationField(sort = 10, title = "编码", isSearchField = true)
	@TableColumn(title = "编码")
	@NotNull(message = "编码不能为空！")
	@Length(max = DataLength.CODE_LENGTH)
	private String code;

	@Column(name = "NAME", length = DataLength.NAME_LENGTH)
	@RepresentationField(sort = 20, title = "名称", isSearchField = true)
	@TableColumn(title = "名称")
	@NotNull(message = "名称不能为空！")
	@Length(max = DataLength.NAME_LENGTH)
	private String name;

	@ManyToOne
	@JoinColumn(name = "CATEGORY_ID")
	@RepresentationField(sort = 5, title = "产品分类", view = RepresentationFieldType.REFERENCE, isSearchField = true)
	@Reference(id = "id", label = "name")
	@AssociateTableColumn(titles = "分类编码,分类名称", columns = "code,name")
	private ProductCategory category;

	@Column(name = "SORT", columnDefinition = "NUMERIC(4,0)")
	@RepresentationField(sort = 50, title = "排序")
	@DecimalMax(value = "9999")
	private Integer sort;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
