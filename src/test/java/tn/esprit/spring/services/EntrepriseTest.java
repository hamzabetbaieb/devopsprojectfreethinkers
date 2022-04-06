package tn.esprit.spring.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;
import tn.esprit.spring.services.EntrepriseServiceImpl;
@RunWith(SpringRunner.class)
@SpringBootTest()

public class EntrepriseTest {
	@Autowired
	EntrepriseServiceImpl entrepriseService ;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
    EntrepriseRepository entrepriseRepoistory;
	
	
     
@Test
@Order(1)
	public void testAjouterEntreprise() {
    Entreprise entreprise =new Entreprise("SSII Consulting","Cite El Ghazela");
			int id = entrepriseService.ajouterEntreprise(entreprise);
		    assertThat(entreprise.getId()).isEqualTo(id);
			
	}

@Test
@Order(2)
public void testAjouterDepartement() {
	
	Departement dep =new Departement("Telecom");
	int id = entrepriseService.ajouterDepartement(dep);
    assertThat(dep.getId()).isEqualTo(id);
	
}
@Test
@Order(3)
public void testaffecterDepartementAEntreprise() {
	
	  Departement dep=new Departement("Telecom");
	  Entreprise entreprise =new Entreprise("SSII Consulting","Cite El Ghazela");
	  int idemp = entrepriseService.ajouterEntreprise(entreprise);
	  int iddep = entrepriseService.ajouterDepartement(dep);
      dep.setEntreprise(entreprise);
	  entrepriseService.affecterDepartementAEntreprise(iddep,idemp);
	  assertThat(dep.getEntreprise().getId()).isEqualTo(idemp);
	  
}

@Test
@Order(6)
public void testDeleteDepartementById() {
	
	Departement dep=new Departement();
	dep.setName("Telecom");
	entrepriseService.ajouterDepartement(dep);
	Long NbrOfDepartsBeforeDelete = deptRepoistory.count();
	entrepriseService.deleteDepartementById(dep.getId());
	Long NbrOfDepartsAfterDelete = deptRepoistory.count();
	assertNotEquals(NbrOfDepartsBeforeDelete,NbrOfDepartsAfterDelete);
	}
@Test
@Order(7)
public void testDeleteEnterprisetById() {
	
	Entreprise ent=new Entreprise();
	ent.setName("entreprise");
	entrepriseService.ajouterEntreprise(ent);
	Long NbrOfEmployeesBeforeDelete = entrepriseRepoistory.count();
	entrepriseService.deleteEntrepriseById(ent.getId());
	Long NbrOfEmployeesAfterDelete = entrepriseRepoistory.count();
	assertNotEquals(NbrOfEmployeesBeforeDelete,NbrOfEmployeesAfterDelete);
	}

@Test
@Order(4)
public void testgetEntrepriseById() {
Entreprise ent = new Entreprise("SSII Consulting","");
entrepriseService.ajouterEntreprise(ent);
int id =ent.getId();
String entreprise=entrepriseService.getEntrepriseById(id).getName();
assertEquals("SSII Consulting",entreprise);

}

@Test
@Order(5)
public void testgetAllDepartements() {
    List<Departement> departement = (List<Departement>)deptRepoistory.findAll();
    assertThat(departement).size().isPositive();
}

}

