package simple.project.speedata.shop.view;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;

import simple.config.annotation.AssociateTableColumn;
import simple.config.annotation.Attachment;
import simple.config.annotation.BooleanValue;
import simple.config.annotation.DataLength;
import simple.config.annotation.Domain;
import simple.config.annotation.ImageGalleryTableColumn;
import simple.config.annotation.Reference;
import simple.config.annotation.RepresentationField;
import simple.config.annotation.RepresentationFieldType;
import simple.config.annotation.SearchField;
import simple.config.annotation.TableColumn;
import simple.config.annotation.Title;
import simple.core.jpa.convert.BooleanToStringConverter;
import simple.project.speedata.shop.model.ProductPicture;

@Domain(defaultSort = "name")
@Entity
@Table(name = "SPEEDATA_PRODUCT")
@SequenceGenerator(name = "SEQ_SPEEDATA_PRODUCT", sequenceName = "SEQ_SPEEDATA_PRODUCT")
@GenericGenerator(name = "idStrategy", strategy = "native", parameters = {
		@Parameter(name = "sequence", value = "SEQ_SPEEDATA_PRODUCT") })
public class ProductView implements Serializable {

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

	@ManyToOne
	@JoinColumn(name = "CATEGORY_ID")
	@RepresentationField(sort = 5, title = "产品分类", view = RepresentationFieldType.REFERENCE, isSearchField = true)
	@Reference(id = "id", label = "name")
	@AssociateTableColumn(titles = "分类名称", columns = "name")
	private ProductCategoryView category;

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	@RepresentationField(title = "图片", view = RepresentationFieldType.ATTACHMENT, sort = 55)
	@TableColumn(title = "图片", sort = 285)
	@OrderBy("id")
	@ImageGalleryTableColumn(field = "attachment", url = "url", isFileStyle = false, isArray = true, fileNameProperty = "fileName")
	@Attachment(fileName = "fileName", url = "url", size = "size", maxSize = 92160, type = "image/gif,image/png,image/jpeg,image/jpg", width = 1024, height = 1024)
	private Set<ProductPicture> attachment = new HashSet<ProductPicture>(0);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public ProductCategoryView getCategory() {
		return category;
	}

	public void setCategory(ProductCategoryView category) {
		this.category = category;
	}

	public Set<ProductPicture> getAttachment() {
		return attachment;
	}

	public void setAttachment(Set<ProductPicture> attachment) {
		this.attachment = attachment;
	}

	public Long getRemoteId() {
		return remoteId;
	}

	public void setRemoteId(Long remoteId) {
		this.remoteId = remoteId;
	}

}
