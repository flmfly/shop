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

import simple.config.annotation.DataLength;
import simple.config.annotation.Domain;
import simple.config.annotation.RepresentationField;
import simple.config.annotation.RepresentationFieldType;
import simple.config.annotation.TableColumn;

@Domain
@Entity
@Table(name = "SPEEDATA_PROMOTION_PICTURE")
@SequenceGenerator(name = "SEQ_SPEEDATA_PRODUCT_PICTURE", sequenceName = "SEQ_SPEEDATA_PROMOTION_PICTURE")
@GenericGenerator(name = "idStrategy", strategy = "native", parameters = {
		@Parameter(name = "sequence", value = "SEQ_SPEEDATA_PROMOTION_PICTURE") })
public class PromotionPicture implements Serializable {

	private static final long serialVersionUID = -2222299541763024789L;

	@Id
	@GeneratedValue(generator = "idStrategy")
	@Column(name = "ID")
	@RepresentationField(view = RepresentationFieldType.HIDDEN)
	@TableColumn(title = "id", show = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "PROMOTION_ID")
	private Promotion promotion;

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

	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
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
