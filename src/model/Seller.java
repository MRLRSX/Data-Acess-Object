package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Seller {
	private Integer id;
	private String name;
	private String email;
	private LocalDateTime birthDate;
	private Double baseSalary;
    private Integer departmentId;
	
    public Seller() {}
	
	public Seller(Integer id, String name, String email, LocalDateTime birthDate, Double baseSalary, Integer departmentId) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.birthDate = birthDate;
		this.baseSalary = baseSalary;
		this.departmentId = departmentId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDateTime getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDateTime birthDate) {
		this.birthDate = birthDate;
	}

	public Double getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(Double baseSalary) {
		this.baseSalary = baseSalary;
	}
    
	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(baseSalary, birthDate, email, id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			
			return false;
		if (getClass() != obj.getClass())
			return false;
		Seller other = (Seller) obj;
		return Objects.equals(baseSalary, other.baseSalary) && Objects.equals(birthDate, other.birthDate)
				&& Objects.equals(email, other.email) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Seller id: " + id + ", name: " + name + ", email: " + email + ", birthDate: " + birthDate + ", baseSalary: "
				+ baseSalary;
	}
}
