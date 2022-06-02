package br.com.venda.vo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class ExceptionVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String title;
	private List<String> detail;
	private String instance;
	private LocalDate timestamp;

	public LocalDate getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDate timestamp) {
		this.timestamp = timestamp;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getDetail() {
		return detail;
	}

	public void setDetail(List<String> detail) {
		this.detail = detail;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

}
