package tn.esprit.spring.entities;

import javax.persistence.Entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class MissionExterne extends Mission {
	
	private static final long serialVersionUID = -3046278688391172322L;
	
	
	private String emailFacturation;
	
	
	private float tauxJournalierMoyen;

	
	


	public MissionExterne(String name, String description, String emailFacturation, float tauxJournalierMoyen) {
		super(name, description);
		this.emailFacturation = emailFacturation;
		this.tauxJournalierMoyen = tauxJournalierMoyen;
	}


	public String getEmailFacturation() {
		return emailFacturation;
	}


	public void setEmailFacturation(String emailFacturation) {
		this.emailFacturation = emailFacturation;
	}


	public float getTauxJournalierMoyen() {
		return tauxJournalierMoyen;
	}


	public void setTauxJournalierMoyen(float tauxJournalierMoyen) {
		this.tauxJournalierMoyen = tauxJournalierMoyen;
	}
	
	
	

}
