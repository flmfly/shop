package simple.project.speedata.shop.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;

import simple.base.BaseValiditySupportModel;
import simple.config.annotation.Attachment;
import simple.config.annotation.DataLength;
import simple.config.annotation.Domain;
import simple.config.annotation.ImageGalleryTableColumn;
import simple.config.annotation.RepresentationField;
import simple.config.annotation.RepresentationFieldType;
import simple.config.annotation.TableColumn;
import simple.config.annotation.Title;
import simple.config.annotation.TreeInfo;
import simple.core.validation.annotation.UniqueKey;

@Domain(defaultSort = "sort", value = "产品分类")
@Entity
@Table(name = "SPEEDATA_PRODUCT_CATEGORY")
@UniqueKey(columnNames = { "name" }, message = "产品分类已存在！")
@TreeInfo(id = "id", label = "name", pid = "parent.id")
@SequenceGenerator(name = "SEQ_SPEEDATA_PRODUCT_CATEGORY", sequenceName = "SEQ_SPEEDATA_PRODUCT_CATEGORY")
@GenericGenerator(name = "idStrategy", strategy = "native", parameters = {
		@Parameter(name = "sequence", value = "SEQ_SPEEDATA_PRODUCT_CATEGORY") })
public class ProductCategory extends BaseValiditySupportModel implements Serializable {

	private static final long serialVersionUID = -2222299541763024789L;

	@Column(name = "REMOTE_ID")
	@Title("外部系统ID")
	@RepresentationField(sort = 5, disable = true)
	private Long remoteId;

	@Column(name = "CODE", length = DataLength.CODE_LENGTH)
	@RepresentationField(sort = 10, title = "编码", isSearchField = true, disable = true)
	@TableColumn(title = "编码")
	@Length(max = DataLength.CODE_LENGTH)
	private String code;

	@Column(name = "NAME", length = DataLength.NAME_LENGTH)
	@RepresentationField(sort = 20, title = "名称", isSearchField = true, disable = true)
	@TableColumn(title = "名称")
	@NotNull(message = "名称不能为空！")
	@Length(max = DataLength.NAME_LENGTH)
	private String name;

	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
	@RepresentationField(title = "图片", view = RepresentationFieldType.ATTACHMENT, sort = 55)
	@TableColumn(title = "图片", sort = 285)
	@ImageGalleryTableColumn(field = "attachment", url = "url", isFileStyle = false, isArray = true, fileNameProperty = "fileName")
	@Attachment(fileName = "fileName", url = "url", size = "size", maxSize = 1024
			* 1204, type = "image/gif,image/png,image/jpeg,image/jpg", width = 1024, height = 1024)
	private Set<ProductCategoryPicture> attachment = new HashSet<ProductCategoryPicture>(0);

	@Column(name = "INTRO")
	@Title("商品分类介绍")
	@Lob
	@RepresentationField(view = RepresentationFieldType.HTML_EDITOR, sort = 60)
	private String intro;

	@Column(name = "SORT", columnDefinition = "NUMERIC(4,0)")
	@RepresentationField(sort = 50, title = "排序")
	@DecimalMax(value = "9999")
	private Integer sort;

	public Long getRemoteId() {
		return remoteId;
	}

	public void setRemoteId(Long remoteId) {
		this.remoteId = remoteId;
	}

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

	public Set<ProductCategoryPicture> getAttachment() {
		return attachment;
	}

	public void setAttachment(Set<ProductCategoryPicture> attachment) {
		this.attachment = attachment;
	}

}
