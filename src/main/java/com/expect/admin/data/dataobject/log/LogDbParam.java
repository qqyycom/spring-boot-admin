package com.expect.admin.data.dataobject.log;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/**
 * 数据库日志参数
 */
@Entity
@Table(name = "c_log_db_param")
public class LogDbParam {

	private String id;
	private String param;// 参数
	private LogDb logDb;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "id", nullable = false, unique = true, length = 32)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Type(type = "text")
	@Column(name = "param")
	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	@ManyToOne
	@JoinColumn(name = "log_db_id")
	public LogDb getLogDb() {
		return logDb;
	}

	public void setLogDb(LogDb logDb) {
		this.logDb = logDb;
	}

}
