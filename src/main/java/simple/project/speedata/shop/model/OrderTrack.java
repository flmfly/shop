package simple.project.speedata.shop.model;

import java.io.Serializable;
import java.util.Date;

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

import simple.config.annotation.AssociateTableColumn;
import simple.config.annotation.AutoFill;
import simple.config.annotation.AutoFillTrigger;
import simple.config.annotation.Domain;
import simple.config.annotation.Operation;
import simple.config.annotation.OperationTarget;
import simple.config.annotation.RepresentationField;
import simple.config.annotation.RepresentationFieldType;
import simple.config.annotation.TableColumn;
import simple.config.annotation.support.CurrentDateTimeAutoFillHandler;
import simple.project.speedata.shop.support.OrderTrackSyncOperation;

@Domain("订单状态追踪")
@Entity
@Table(name = "SPEEDATA_ORDER_TRACK")
@Operation(code = "refresh", iconStyle = "fa fa-refresh", handler = OrderTrackSyncOperation.class, multi = true, name = "同步", target = OperationTarget.ALL)
@SequenceGenerator(name = "SEQ_SPEEDATA_ORDER_TRACK", sequenceName = "SEQ_SPEEDATA_ORDER_TRACK")
@GenericGenerator(name = "idStrategy", strategy = "native", parameters = {
		@Parameter(name = "sequence", value = "SEQ_SPEEDATA_ORDER_TRACK") })
public class OrderTrack implements Serializable {

	private static final long serialVersionUID = -2222299541763024789L;

	@Id
	@GeneratedValue(generator = "idStrategy")
	@Column(name = "ID")
	@RepresentationField(view = RepresentationFieldType.HIDDEN)
	@TableColumn(title = "id", show = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "ORDER_ID")
	@RepresentationField(sort = 5, title = "订单", view = RepresentationFieldType.REFERENCE, isSearchField = true, disable = true)
	@AssociateTableColumn(titles = "订单", columns = "remoteId")
	private Order order;

	@Column(name = "STATE")
	@RepresentationField(sort = 70, title = "状态", isSearchField = true)
	@TableColumn(title = "状态", sort = 50)
	private String state;

	@Column(name = "RECORD_TIME")
	@RepresentationField(title = "记录时间", sort = 99998, view = RepresentationFieldType.DATETIME, disable = true)
	@AutoFill(handler = CurrentDateTimeAutoFillHandler.class, trigger = AutoFillTrigger.ALWAYS)
	private Date recordTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

}
