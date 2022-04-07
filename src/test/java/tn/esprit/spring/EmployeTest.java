package tn.esprit.spring;




import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;

import tn.esprit.spring.entities.Role;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;
import tn.esprit.spring.services.EmployeServiceImpl;
import tn.esprit.spring.services.EntrepriseServiceImpl;
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest()
public class EmployeTest {
	
	@Autowired
	EmployeServiceImpl employeService;
	
	
	
	@Autowired
	EntrepriseServiceImpl entrepriseService;
	
	@Autowired
	DepartementRepository departementRepository;
	
	@Autowired 
	ContratRepository ContratRepo;
	
	@Autowired
	EntrepriseRepository  enterpriseRepositor;
	
	@Test
	@Order(1)
	public void testAjoutEmploye() {
		Employe employe = new Employe("bet","hamza","hamza.betbaieb@esprit.tn",true,Role.INGENIEUR);
		int id = employeService.ajouterEmploye(employe);
		Assert.assertNotNull(employeService.getEmployePrenomById(id));
	}
	@Test
	@Order(2)
	
 	public void ajouterContrat() throws ParseException {
 		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
 		Date d = dateFormat.parse("2020-10-30");
 		Contrat c = new Contrat(d,"cdd",3000);
 	int ContratRef = employeService.ajouterContrat(c); 
 		Assert.assertEquals("check ajout contrat ",c.getReference(),ContratRef);
 		
 	}
	

	@Test
	@Order(3)
	public void TestaffecterContratAEmploye()  throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
 		Date date = dateFormat.parse("2020-11-30");
		Contrat contratX = new Contrat (date,"cddddd",3);
		Employe employe = new Employe("hhhhh","zzzzz","hhzz@gmail.com",true,Role.INGENIEUR);
		int idContrat = employeService.ajouterContrat(contratX);   
		int idEmploye = employeService.ajouterEmploye(employe); 
		contratX.setEmploye(employe);
		//log.info("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		employeService.affecterContratAEmploye(idContrat, idEmploye); 
		//log.info("id employe zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz= "+ idEmploye );
		Assert.assertEquals(contratX.getEmploye().getId(),idEmploye );
	}
	
	@Test
	@Order(4)
	public void testMettreAjourEmailByEmployeId()
	{
		String newMail = "miseajour@gmail.com";
        employeService.mettreAjourEmailByEmployeId(newMail, employeService.getAllEmployes().get(1).getId());
        String updatedMail = employeService.getAllEmployes().get(1).getEmail();
       // log.info("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+newMail+updatedMail);
        Assert.assertEquals("check updated mail ", newMail, updatedMail);}
	
	
	
	
	
	
}
