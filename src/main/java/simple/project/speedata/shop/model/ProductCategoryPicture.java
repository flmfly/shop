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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import simple.base.BaseValiditySupportModel;
import simple.config.annotation.DataLength;
import simple.config.annotation.Domain;
import simple.config.annotation.RepresentationField;
import simple.config.annotation.RepresentationFieldType;
import simple.config.annotation.TableColumn;

@Domain(defaultSort = "sort")
@Entity
@Table(name = "SPEEDATA_PRODUCT_CATEGORY_PICTURE")
@SequenceGenerator(name = "SEQ_SPEEDATA_PRODUCT_CATEGORY_PICTURE", sequenceName = "SEQ_SPEEDATA_PRODUCT_CATEGORY_PICTURE")
@GenericGenerator(name = "idStrategy", strategy = "native", parameters = {
		@Parameter(name = "sequence", value = "SEQ_SPEEDATA_PRODUCT_CATEGORY_PICTURE") })
public class ProductCategoryPicture extends BaseValiditySupportModel implements Serializable {

	private static final long serialVersionUID = -2222299541763024789L;

	@Id
	@GeneratedValue(generator = "idStrategy")
	@Column(name = "ID")
	@RepresentationField(view = RepresentationFieldType.HIDDEN)
	@TableColumn(title = "id", show = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID")
	private ProductCategory category;

	@Column(name = "FILE_NAME", length = DataLength.NAME_LENGTH)
	@RepresentationField(title = "附件名称", sort = 50)
	@TableColumn(title = "附件名称", sort = 50)
	private String fileName;

	@Column(name = "URL", length = DataLength.NAME_LENGTH)
	@RepresentationField(title = "URL", sort = 30)
	@TableColumn(title = "URL", sort = 30)
	private String url;

	@Column(name = "ATTACHEMENT_SIZE", columnDefinition = "NUMERIC(10,2)")
	@TableColumn(title = "尺寸", sort = 40)
	@RepresentationField(title = "尺寸", sort = 40)
	private Long size;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProductCategory getCategory() {
		return category;
	}

	public void setCategory(ProductCategory category) {
		this.category = category;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

}
