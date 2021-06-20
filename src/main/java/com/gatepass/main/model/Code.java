package com.gatepass.main.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name="code",uniqueConstraints=@UniqueConstraint(columnNames={"CodeValueType","Value"}))
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Code {
	
	@javax.persistence.Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int id;
	
	@OneToOne
	@JoinColumn(name="CodeValueType",nullable=false)
	private CodeValueType codeValueType;
	
	@NotNull
	@Column(name="Value")
	private String value;
	
	@Transient
	private String info;	

	public Code(int id) {
		super();
		this.id = id;
	}

	public Code(int id, @NotNull String value) {
		super();
		this.id = id;
		this.value = value;
	}

	
	
	
}
