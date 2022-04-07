package tn.esprit.spring.services;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;
import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest()
@Slf4j
public class EmployeTest {
	
	@Autowired
	EmployeServiceImpl service=new EmployeServiceImpl();
	@Autowired
	EmployeRepository employeRep;
	
	@Autowired
	ContratRepository contratRep;
	@Autowired
	EntrepriseServiceImpl serviceEntreprise=new EntrepriseServiceImpl();
	@Autowired
	DepartementRepository departementRep;
	
	@Test
	public void getEmployePrenomByIdtest() {
		Employe emp=new Employe();
		emp.setNom("Habassi");
		emp.setPrenom("Seif");
		service.ajouterEmploye(emp);
		String prenom=service.getEmployePrenomById(emp.getId());
		log.info("Le prenom est: "+prenom);
		assertEquals("Seif",prenom);
	}
	 
	  @Test
	public void deleteEmployeByIdTest(){
		Employe emp=new Employe();
		emp.setNom("Habassi");
		emp.setPrenom("Aymen");
		service.ajouterEmploye(emp);
		Long NbrOfEmployeesBeforeDelete = employeRep.count();
		service.deleteEmployeById(emp.getId());
		Long NbrOfEmployeesAfterDelete = employeRep.count();
		assertNotEquals(NbrOfEmployeesBeforeDelete,NbrOfEmployeesAfterDelete);
	}
	@Test
	public void deleteContratByIdTest() throws ParseException{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
 		Date d = dateFormat.parse("2020-10-30");
 		Contrat contrat = new Contrat(d,"civp",20);
		contratRep.save(contrat);
		Long NbrOfContratsBeforeDelete = contratRep.count();
		contratRep.deleteById(contrat.getReference());
		Long NbrOfContratsAfterDelete = contratRep.count();
		log.info("Nombre de contrats avant la suppression: "+NbrOfContratsBeforeDelete+", Nombre de contrat après suppression "+NbrOfContratsAfterDelete);
		assertNotEquals(NbrOfContratsBeforeDelete,NbrOfContratsAfterDelete);
	}
	@Test
	public void getNombreEmployeJPQLTest(){
		Employe emp=new Employe();
		emp.setNom("Habassi");
		emp.setPrenom("Mehdi");
		service.ajouterEmploye(emp);
		int nbr=service.getNombreEmployeJPQL();
		log.info(""+nbr);
		assertNotEquals(0,nbr);
	}
	@Test
	public void getAllEmployeNamesJPQL(){
		List<String> list=service.getAllEmployeNamesJPQL();
		for(int i=0;i<list.size();i++){
			log.info("Le nom de l employe num "+i+" est: "+list.get(i));
			assertNotEquals("",list.get(i));
		}
	}
	@Test
	public void getAllEmployeByEntrepriseTest(){
		Employe emp=new Employe();
		emp.setNom("HabassiHabassi");
		emp.setPrenom("AymenAymen");
	    Departement dep=new Departement();
	    dep.setName("APOLO");
	    List<Employe> listEmp=new ArrayList<Employe>();
	    List<Departement> listDep=new ArrayList<Departement>();
	    listEmp.add(emp);
	    listDep.add(dep);
	    emp.setDepartements(listDep);
	    dep.setEmployes(listEmp);
	    Entreprise entreprise=new Entreprise();
	    entreprise.setDepartements(listDep);
	    entreprise.setName("HABASSI CROP.");
	    dep.setEntreprise(entreprise);
	    service.ajouterEmploye(emp);
	    serviceEntreprise.ajouterEntreprise(entreprise);
	    departementRep.save(dep);
		List<Employe> listEmpParEntreprise=new ArrayList<Employe>();
		listEmpParEntreprise=service.getAllEmployeByEntreprise(entreprise);
		log.info("taille de la premiere list: "+listEmp.size()+", taille de la deuxieme list: "+listEmpParEntreprise.size());
		for(int i=0;i<listEmpParEntreprise.size();i++){
			log.info("Les prenoms: "+listEmpParEntreprise.get(i).getPrenom()+" Taille de tableau: "+listEmpParEntreprise.size());
			log.info("Les prenoms: "+listEmp.get(i).getPrenom()+" Taille de tableau: "+listEmp.size());
		}
		assertEquals(listEmp.size(),listEmpParEntreprise.size());
	}
}
