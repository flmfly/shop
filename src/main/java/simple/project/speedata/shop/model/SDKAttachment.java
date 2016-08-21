package simple.project.speedata.shop.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;

import simple.base.BaseModel;
import simple.config.annotation.DataLength;
import simple.config.annotation.Domain;
import simple.config.annotation.RepresentationField;
import simple.config.annotation.TableColumn;
import simple.core.validation.annotation.UniqueKey;

@Domain
@Entity
@Table(name = "SPEEDATA_SDK_ATTACHMENT")
@UniqueKey(columnNames = { "name" }, message = "行业名称已存在！")
@SequenceGenerator(name = "SEQ_SPEEDATA_SDK_ATTACHMENT", sequenceName = "SEQ_SPEEDATA_SDK_ATTACHMENT")
@GenericGenerator(name = "idStrategy", strategy = "native", parameters = {
		@Parameter(name = "sequence", value = "SEQ_SPEEDATA_SDK_ATTACHMENT") })
public class SDKAttachment extends BaseModel implements Serializable {

	private static final long serialVersionUID = 361702473050426893L;

	@Column(name = "PATH", length = DataLength.LONG_TEXT_LENGTH)
	@RepresentationField(sort = 10, title = "路径", isSearchField = true)
	@TableColumn(title = "路径")
	@NotNull(message = "路径不能为空！")
	@Length(max = DataLength.LONG_TEXT_LENGTH)
	private String path;

	@Column(name = "FILE_NAME", length = DataLength.LONG_TEXT_LENGTH)
	@RepresentationField(sort = 10, title = "文件名", isSearchField = true)
	@TableColumn(title = "文件名")
	@NotNull(message = "文件名不能为空！")
	@Length(max = DataLength.LONG_TEXT_LENGTH)
	private String fileName;

	@Column(name = "SIZE")
	@RepresentationField(sort = 10, title = "文件大小", isSearchField = true)
	@TableColumn(title = "文件大小")
	@NotNull(message = "文件大小不能为空！")
	private Long size;

	@ManyToOne
	@JoinColumn(name = "SDK_ID")
	private SDK sdk;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public SDK getSdk() {
		return sdk;
	}

	public void setSdk(SDK sdk) {
		this.sdk = sdk;
	}

}
