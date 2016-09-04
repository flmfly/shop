package simple.project.speedata.shop.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
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

import simple.base.BaseValiditySupportModel;
import simple.config.annotation.AssociateTableColumn;
import simple.config.annotation.DataLength;
import simple.config.annotation.Domain;
import simple.config.annotation.Reference;
import simple.config.annotation.RepresentationField;
import simple.config.annotation.RepresentationFieldType;
import simple.config.annotation.RepresentationLayout;
import simple.config.annotation.RepresentationLayoutType;
import simple.config.annotation.TableColumn;
import simple.config.annotation.Title;
import simple.config.annotation.TreeInfo;

@Domain(defaultSort = "sort")
@Entity
@Table(name = "SPEEDATA_SHOP_OEM")
@TreeInfo(id = "id", label = "name", pid = "parent.id")
@RepresentationLayout(view = RepresentationLayoutType.TREE, id = "id", label = "name", pid = "parent.id")
@SequenceGenerator(name = "SEQ_SPEEDATA_SHOP_OEM", sequenceName = "SEQ_SPEEDATA_SHOP_OEM")
@GenericGenerator(name = "idStrategy", strategy = "native", parameters = {
		@Parameter(name = "sequence", value = "SEQ_SPEEDATA_SHOP_OEM") })
public class OEM extends BaseValiditySupportModel implements Serializable {

	private static final long serialVersionUID = -5417250117391305303L;

	@Column(name = "NAME", length = DataLength.NAME_LENGTH)
	@RepresentationField(sort = 20, title = "名称", isSearchField = true)
	@TableColumn(title = "名称")
	@NotNull(message = "名称不能为空！")
	@Length(max = DataLength.NAME_LENGTH)
	private String name;

	@ManyToOne
	@JoinColumn(name = "PARENT_ID")
	@Title("所属上级")
	@RepresentationField(sort = 40, view = RepresentationFieldType.REFERENCE, isSearchField = true)
	@Reference(id = "id", label = "name", pid = "parent.id")
	@AssociateTableColumn(sorts = "40", titles = "所属上级", columns = "name")
	private OEM parent;

	@Column(name = "SORT", columnDefinition = "NUMERIC(4,0)")
	@RepresentationField(sort = 50, title = "排序")
	@DecimalMax(value = "9999")
	private Integer sort;

	@Column(name = "CONTENT")
	@Title("展示内容")
	@Lob
	@RepresentationField(view = RepresentationFieldType.HTML_EDITOR, sort = 60)
	private String content;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public OEM getParent() {
		return parent;
	}

	public void setParent(OEM parent) {
		this.parent = parent;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
