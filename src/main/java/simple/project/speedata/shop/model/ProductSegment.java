package simple.project.speedata.shop.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;

import simple.config.annotation.AssociateTableColumn;
import simple.config.annotation.DataLength;
import simple.config.annotation.Domain;
import simple.config.annotation.Reference;
import simple.config.annotation.RepresentationField;
import simple.config.annotation.RepresentationFieldType;
import simple.config.annotation.TableColumn;
import simple.config.annotation.Title;

@Domain(defaultSort = "sort")
@Entity
@Table(name = "SPEEDATA_PRODUCT_SEGMENT")
@SequenceGenerator(name = "SEQ_SPEEDATA_PRODUCT_SEGMENT", sequenceName = "SEQ_SPEEDATA_PRODUCT_SEGMENT")
@GenericGenerator(name = "idStrategy", strategy = "native", parameters = {
		@Parameter(name = "sequence", value = "SEQ_SPEEDATA_PRODUCT_SEGMENT") })
public class ProductSegment implements Serializable {

	private static final long serialVersionUID = -2222299541763024789L;

	@Id
	@GeneratedValue(generator = "idStrategy")
	@Column(name = "ID")
	@RepresentationField(view = RepresentationFieldType.HIDDEN)
	@TableColumn(title = "id", show = false)
	private Long id;

	@Column(name = "NAME", length = DataLength.NAME_LENGTH)
	@RepresentationField(sort = 20, title = "名称", isSearchField = true)
	@TableColumn(title = "名称")
	@NotNull(message = "名称不能为空！")
	@Length(max = DataLength.NAME_LENGTH)
	private String name;

	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID")
	@RepresentationField(sort = 5, title = "所属产品", view = RepresentationFieldType.REFERENCE, isSearchField = true)
	@Reference(id = "id", label = "name")
	@AssociateTableColumn(titles = "所属产品", columns = "name")
	private Product product;

	@Column(name = "SORT", columnDefinition = "NUMERIC(4,0)")
	@RepresentationField(sort = 50, title = "排序")
	@DecimalMax(value = "9999")
	private Integer sort;

	@Column(name = "CONTENT")
	@Title("展示内容")
	@Lob
	@RepresentationField(view = RepresentationFieldType.HTML_EDITOR, sort = 60)
	private String content;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
