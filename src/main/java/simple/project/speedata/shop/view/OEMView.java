package simple.project.speedata.shop.view;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;

import simple.base.BaseValiditySupportModel;
import simple.config.annotation.DataLength;
import simple.config.annotation.Domain;
import simple.config.annotation.RepresentationField;
import simple.config.annotation.RepresentationLayout;
import simple.config.annotation.RepresentationLayoutType;
import simple.config.annotation.TableColumn;
import simple.config.annotation.TreeInfo;

@Domain(defaultSort = "sort")
@Entity
@Table(name = "SPEEDATA_SHOP_OEM")
@TreeInfo(id = "id", label = "name", pid = "parent.id")
@RepresentationLayout(view = RepresentationLayoutType.TREE, id = "id", label = "name", pid = "parent.id")
@SequenceGenerator(name = "SEQ_SPEEDATA_SHOP_OEM", sequenceName = "SEQ_SPEEDATA_SHOP_OEM")
@GenericGenerator(name = "idStrategy", strategy = "native", parameters = {
		@Parameter(name = "sequence", value = "SEQ_SPEEDATA_SHOP_OEM") })
public class OEMView extends BaseValiditySupportModel implements Serializable {

	private static final long serialVersionUID = -5417250117391305303L;

	@Column(name = "NAME", length = DataLength.NAME_LENGTH)
	@RepresentationField(sort = 20, title = "名称", isSearchField = true)
	@TableColumn(title = "名称")
	@NotNull(message = "名称不能为空！")
	@Length(max = DataLength.NAME_LENGTH)
	private String name;

	@Column(name = "SORT", columnDefinition = "NUMERIC(4,0)")
	@RepresentationField(sort = 50, title = "排序")
	@DecimalMax(value = "9999")
	private Integer sort;

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
