package io.github.edetec.medical.model.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Medic {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String phone;
	private String cpf;
	private String crm;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "medic_has_specialty", joinColumns = { @JoinColumn(name = "medic_id") }, inverseJoinColumns = {
			@JoinColumn(name = "specialty_id") })
	private List<MedicalSpecialty> specialties;

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCrm() {
		return crm;
	}

	public void setCrm(String crm) {
		this.crm = crm;
	}
	
	public List<MedicalSpecialty> getSpecialties() {
		return specialties;
	}
	
	public void setSpecialties(List<MedicalSpecialty> specialties) {
		this.specialties = specialties;
	}

}
