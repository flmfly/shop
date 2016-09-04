package simple.project.speedata.shop.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;

import simple.config.annotation.Attachment;
import simple.config.annotation.DataLength;
import simple.config.annotation.Domain;
import simple.config.annotation.ImageGalleryTableColumn;
import simple.config.annotation.RepresentationField;
import simple.config.annotation.RepresentationFieldType;
import simple.config.annotation.StandardOperation;
import simple.config.annotation.TableColumn;

@Domain(value = "产品")
@Entity
@StandardOperation(imp = false, export = false)
@Table(name = "SPEEDATA_PROMOTION")
@SequenceGenerator(name = "SEQ_SPEEDATA_PROMOTION", sequenceName = "SEQ_SPEEDATA_PROMOTION")
@GenericGenerator(name = "idStrategy", strategy = "native", parameters = {
		@Parameter(name = "sequence", value = "SEQ_SPEEDATA_PROMOTION") })
public class Promotion implements Serializable {

	private static final long serialVersionUID = -2222299541763024789L;

	@Id
	@GeneratedValue(generator = "idStrategy")
	@Column(name = "ID")
	@RepresentationField(view = RepresentationFieldType.HIDDEN)
	@TableColumn(title = "id", show = false)
	private Long id;

	@Column(name = "NAME", length = DataLength.NAME_LENGTH)
	@RepresentationField(sort = 20, title = "名称", isSearchField = true)
	@TableColumn(title = "名称", sort = 20)
	@NotNull(message = "名称不能为空！")
	@Length(max = DataLength.NAME_LENGTH)
	private String name;

	@OneToMany(mappedBy = "promotion", fetch = FetchType.LAZY)
	@RepresentationField(title = "图片", view = RepresentationFieldType.ATTACHMENT, sort = 55)
	@TableColumn(title = "图片", sort = 285)
	@OrderBy("id")
	@ImageGalleryTableColumn(field = "attachment", url = "url", isFileStyle = false, isArray = true, fileNameProperty = "fileName")
	@Attachment(fileName = "fileName", url = "url", size = "size", maxSize = 1034 * 1024
			* 20, type = "image/gif,image/png,image/jpeg,image/jpg", width = 1024 * 2, height = 1024)
	private Set<PromotionPicture> attachment = new HashSet<PromotionPicture>(0);

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

	public Set<PromotionPicture> getAttachment() {
		return attachment;
	}

	public void setAttachment(Set<PromotionPicture> attachment) {
		this.attachment = attachment;
	}

}
