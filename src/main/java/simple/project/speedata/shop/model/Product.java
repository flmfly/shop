package simple.project.speedata.shop.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;

import simple.base.BaseValiditySupportModel;
import simple.base.model.BaseDictItem;
import simple.config.annotation.AssociateTableColumn;
import simple.config.annotation.Attachment;
import simple.config.annotation.BooleanValue;
import simple.config.annotation.DataLength;
import simple.config.annotation.DictField;
import simple.config.annotation.Domain;
import simple.config.annotation.ImageGalleryTableColumn;
import simple.config.annotation.Operation;
import simple.config.annotation.OperationTarget;
import simple.config.annotation.Reference;
import simple.config.annotation.RepresentationField;
import simple.config.annotation.RepresentationFieldType;
import simple.config.annotation.TableColumn;
import simple.config.annotation.Title;
import simple.config.annotation.TreeInfo;
import simple.core.jpa.convert.BooleanToStringConverter;
import simple.core.validation.annotation.UniqueKey;
import simple.project.speedata.shop.support.ProductSyncOperation;

@Domain(defaultSort = "sort", value = "产品")
@Entity
@Table(name = "SPEEDATA_PRODUCT")
@UniqueKey(columnNames = { "name" }, message = "产品已存在！")
@TreeInfo(id = "id", label = "name", pid = "parent.id")
@Operation(code = "refresh", iconStyle = "fa fa-refresh", handler = ProductSyncOperation.class, multi = true, name = "同步", target = OperationTarget.ALL)
@SequenceGenerator(name = "SEQ_SPEEDATA_PRODUCT", sequenceName = "SEQ_SPEEDATA_PRODUCT")
@GenericGenerator(name = "idStrategy", strategy = "native", parameters = {
		@Parameter(name = "sequence", value = "SEQ_SPEEDATA_PRODUCT") })
public class Product extends BaseValiditySupportModel implements Serializable {

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

	@ManyToOne
	@JoinColumn(name = "CATEGORY_ID")
	@RepresentationField(sort = 5, title = "产品分类", view = RepresentationFieldType.REFERENCE, isSearchField = true)
	@Reference(id = "id", label = "name")
	@AssociateTableColumn(titles = "分类名称", columns = "name")
	private ProductCategory category;

	@Column(name = "SORT", columnDefinition = "NUMERIC(4,0)")
	@RepresentationField(sort = 50, title = "排序")
	@DecimalMax(value = "9999")
	private Integer sort;
	//
	// @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	// @Cascade({ CascadeType.ALL })
	// @RepresentationField(title = "展示内容列表", view =
	// RepresentationFieldType.TAB)
	// @TabView
	// private Set<ProductSegment> segmentList;

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	@RepresentationField(title = "图片", view = RepresentationFieldType.ATTACHMENT, sort = 55)
	@TableColumn(title = "图片", sort = 285)
	@OrderBy("id")
	@ImageGalleryTableColumn(field = "attachment", url = "url", isFileStyle = false, isArray = true, fileNameProperty = "fileName")
	@Attachment(fileName = "fileName", url = "url", size = "size", maxSize = 1034 * 1024
			* 20, type = "image/gif,image/png,image/jpeg,image/jpg", width = 1024, height = 1024)
	private Set<ProductPicture> attachment = new HashSet<ProductPicture>(0);

	@Column(name = "INTRO")
	@Title("商品介绍")
	@Lob
	@RepresentationField(view = RepresentationFieldType.HTML_EDITOR, sort = 60)
	private String intro;

	@Column(name = "SPEC")
	@Title("规格参数")
	@Lob
	@RepresentationField(view = RepresentationFieldType.HTML_EDITOR, sort = 60)
	private String spec;

	@ManyToOne
	@JoinColumn(name = "OS")
	@RepresentationField(sort = 70, title = "操作系统", view = RepresentationFieldType.SELECT, isSearchField = true)
	@DictField("os")
	@Reference(id = "id", label = "name")
	@AssociateTableColumn(titles = "操作系统", columns = "name", sorts = "50")
	private BaseDictItem os;

	@ManyToOne
	@JoinColumn(name = "COMMUNICATE")
	@RepresentationField(sort = 70, title = "通讯模块", view = RepresentationFieldType.SELECT, isSearchField = true)
	@DictField("communicate")
	@Reference(id = "id", label = "name")
	@AssociateTableColumn(titles = "通讯模块", columns = "name", sorts = "50")
	private BaseDictItem communicate;

	@ManyToOne
	@JoinColumn(name = "WIFI")
	@RepresentationField(sort = 70, title = "WIFI", view = RepresentationFieldType.SELECT, isSearchField = true)
	@DictField("wifi")
	@Reference(id = "id", label = "name")
	@AssociateTableColumn(titles = "WIFI", columns = "name", sorts = "50")
	private BaseDictItem wifi;

	@ManyToOne
	@JoinColumn(name = "SIZE")
	@RepresentationField(sort = 70, title = "屏幕尺寸", view = RepresentationFieldType.SELECT, isSearchField = true)
	@DictField("screenSize")
	@Reference(id = "id", label = "name")
	@AssociateTableColumn(titles = "屏幕尺寸", columns = "name", sorts = "50")
	private BaseDictItem size;

	@ManyToOne
	@JoinColumn(name = "SCAN")
	@RepresentationField(sort = 70, title = "条码扫描", view = RepresentationFieldType.SELECT, isSearchField = true)
	@DictField("barcodeScan")
	@Reference(id = "id", label = "name")
	@AssociateTableColumn(titles = "条码扫描", columns = "name", sorts = "50")
	private BaseDictItem scan;

	@ManyToOne
	@JoinColumn(name = "RFID")
	@RepresentationField(sort = 70, title = "RFID", view = RepresentationFieldType.SELECT, isSearchField = true)
	@DictField("rfid")
	@Reference(id = "id", label = "name")
	@AssociateTableColumn(titles = "RFID", columns = "name", sorts = "50")
	private BaseDictItem rfid;

	@ManyToOne
	@JoinColumn(name = "FINGERPRINT")
	@RepresentationField(sort = 70, title = "指纹识别", view = RepresentationFieldType.SELECT, isSearchField = true)
	@DictField("fingerprint")
	@Reference(id = "id", label = "name")
	@AssociateTableColumn(titles = "指纹识别", columns = "name", sorts = "50")
	private BaseDictItem fingerprint;

	@ManyToOne
	@JoinColumn(name = "PRINTER")
	@RepresentationField(sort = 70, title = "打印机", view = RepresentationFieldType.SELECT, isSearchField = true)
	@DictField("printer")
	@Reference(id = "id", label = "name")
	@AssociateTableColumn(titles = "打印机", columns = "name", sorts = "50")
	private BaseDictItem printer;

	@ManyToOne
	@JoinColumn(name = "TEMPERATURE")
	@RepresentationField(sort = 70, title = "温度模块", view = RepresentationFieldType.SELECT, isSearchField = true)
	@DictField("temperature")
	@Reference(id = "id", label = "name")
	@AssociateTableColumn(titles = "温度模块", columns = "name", sorts = "50")
	private BaseDictItem temperature;

	@ManyToOne
	@JoinColumn(name = "INFRARED")
	@RepresentationField(sort = 70, title = "红外模块", view = RepresentationFieldType.SELECT, isSearchField = true)
	@DictField("infrared")
	@Reference(id = "id", label = "name")
	@AssociateTableColumn(titles = "红外模块", columns = "name", sorts = "50")
	private BaseDictItem infrared;

	@Column(name = "IS_ANTI", columnDefinition = "CHAR(1)")
	@RepresentationField(title = "防爆功能", sort = 99995, view = RepresentationFieldType.BOOLEAN)
	@BooleanValue({ "需要", "不需要" })
	@Convert(converter = BooleanToStringConverter.class)
	private Boolean isAnti;

	public Long getRemoteId() {
		return remoteId;
	}

	public void setRemoteId(Long remoteId) {
		this.remoteId = remoteId;
	}

	public ProductCategory getCategory() {
		return category;
	}

	public void setCategory(ProductCategory category) {
		this.category = category;
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

	public Set<ProductPicture> getAttachment() {
		return attachment;
	}

	public void setAttachment(Set<ProductPicture> attachment) {
		this.attachment = attachment;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

}
