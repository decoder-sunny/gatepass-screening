package com.gatepass.main.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="templateset",uniqueConstraints=
@UniqueConstraint(columnNames={"TemplateId", "QuestionId"}))
public class TemplateSet {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int id;
	
	@NotNull
	@OneToOne
    @JoinColumn(name="TemplateId")
	private Templates template;
	
	@NotNull
	@OneToOne
	@JoinColumn(name="QuestionId")
	private Questions question;


	public TemplateSet(int id, int template,int question) {
		super();
		this.id = id;
		this.template = new Templates(template);
		this.question = new Questions(question);
	}
	
	

	
	
}
