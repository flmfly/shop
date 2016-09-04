package simple.project.speedata.shop.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import simple.base.BaseModel;
import simple.config.annotation.AssociateTableColumn;
import simple.config.annotation.Attachment;
import simple.config.annotation.Domain;
import simple.config.annotation.ImageGalleryTableColumn;
import simple.config.annotation.Reference;
import simple.config.annotation.RepresentationField;
import simple.config.annotation.RepresentationFieldType;
import simple.config.annotation.TableColumn;
import simple.config.annotation.Title;
import simple.core.validation.annotation.UniqueKey;

@Domain
@Entity
@Table(name = "SPEEDATA_SDK")
@UniqueKey(columnNames = { "product" }, message = "该产品的sdk已存在！")
@SequenceGenerator(name = "SEQ_SPEEDATA_SDK", sequenceName = "SEQ_SPEEDATA_SDK")
@GenericGenerator(name = "idStrategy", strategy = "native", parameters = {
		@Parameter(name = "sequence", value = "SEQ_SPEEDATA_SDK") })
public class SDK extends BaseModel implements Serializable {

	private static final long serialVersionUID = -2222299541763024789L;

	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID")
	@RepresentationField(sort = 5, title = "产品", view = RepresentationFieldType.REFERENCE, isSearchField = true)
	@Reference(id = "id", label = "name")
	@AssociateTableColumn(titles = "产品编码,产品名称", columns = "code,name")
	private Product product;

	@OneToMany(mappedBy = "sdk", fetch = FetchType.LAZY)
	@Cascade({ CascadeType.ALL })
	@Title("文件")
	@RepresentationField(view = RepresentationFieldType.ATTACHMENT)
	@Attachment(fileName = "fileName", url = "url", size = "size", maxSize = 2097152 * 100)
	@ImageGalleryTableColumn(field = "attachment", url = "url", isFileStyle = true, isArray = true, fileNameProperty = "fileName")
	@TableColumn(title = "文件", sort = 285)
	private Set<SDKAttachment> sdkAttachments = new HashSet<SDKAttachment>(0);

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Set<SDKAttachment> getSdkAttachments() {
		return sdkAttachments;
	}

	public void setSdkAttachments(Set<SDKAttachment> sdkAttachments) {
		this.sdkAttachments = sdkAttachments;
	}

}
