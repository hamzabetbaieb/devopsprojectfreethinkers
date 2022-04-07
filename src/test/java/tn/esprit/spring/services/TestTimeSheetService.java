package tn.esprit.spring.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;



import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.TimesheetPK;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;
@RunWith(SpringRunner.class)
@SpringBootTest()
@Slf4j
public class TestTimeSheetService {
	@Autowired
	MissionRepository missionRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;
	@Autowired
	EmployeRepository employeRepository;
	@Autowired
	TimesheetServiceImpl service ;
	@Autowired
	EntrepriseServiceImpl serviceDepartement ;
	@Autowired
	EmployeServiceImpl serviceEmp ;

	@Test
	@Order(1)
	public void testajouterMission() {
		Mission mission1 = new Mission ("missionEtrange","mission difficile");
			Mission MissionAjoute = missionRepository.save(mission1);
			//int retour = service.ajouterMission(mission1);
			assertThat(MissionAjoute.getId()).isEqualTo(mission1.getId());	
	}
	@Test
	@Order(2)
	public void TestaffecterMissionADepartement() {
		Mission missionX = new Mission ("missionEtrange","mission difficile");
		Departement departementX = new Departement("Departement X") ;
		int idMission = service.ajouterMission(missionX);
		int idDepartement = serviceDepartement.ajouterDepartement(departementX);
		missionX.setDepartement(departementX);
		service.affecterMissionADepartement(idMission,idDepartement);
		log.info("[INFO]L'ID DE DEPARTEMENT EN QUESTION"+ idDepartement );
		assertEquals(missionX.getDepartement().getId(),idDepartement);
		
	}
	
	@Test
	@Order(3)
	public void TestajouterTimesheet() throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
 		Date datedebut = dateFormat.parse("2020-10-30");
 		Date datefin = dateFormat.parse("2022-10-06");
		Employe em1 = new Employe();
		em1.setNom("bbbb");
		em1.setPrenom("ttt");
		int idemp =serviceEmp.ajouterEmploye(em1);
		Mission mission1 = new Mission ("missionEtrange","mission difficile");
		int idmission = service.ajouterMission(mission1);
		TimesheetPK timesheetPK = new TimesheetPK(idmission, idemp, datedebut, datefin);
		service.ajouterTimesheet(timesheetPK.getIdMission(),timesheetPK.getIdEmploye(), timesheetPK.getDateDebut(),timesheetPK.getDateFin());
		assertThat(timesheetPK.getIdMission()).isEqualTo(idmission);
	}
	@Test
	@Order(4)
	public void TestvaliderTimesheet() throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Departement departementX = new Departement("Departement X") ;
 		Date datedebut = dateFormat.parse("2020-10-30");
 		Date datefin = dateFormat.parse("2022-10-06");
		Employe emvalidateur  = new Employe();
		emvalidateur.setNom("toto");
		emvalidateur.setPrenom("titi");
		emvalidateur.setRole(Role.CHEF_DEPARTEMENT);
		int idvalidateur =serviceEmp.ajouterEmploye(emvalidateur);
		Employe em  = new Employe();
		em.setNom("tototp");
		em.setPrenom("tititp");
		int idem =serviceEmp.ajouterEmploye(em);
		Mission mission1 = new Mission ("missionEtrange","mission difficile");
		int idmission = service.ajouterMission(mission1);
		int idDepartement = serviceDepartement.ajouterDepartement(departementX);
		TimesheetPK timesheetPK = new TimesheetPK(idmission,idvalidateur,datedebut,datefin);
		timesheetPK.setIdEmploye(idem);
		timesheetPK.setIdMission(idmission);
		service.ajouterTimesheet(idmission, idDepartement, new Date(20141212), new Date(20201212) );
		service.validerTimesheet(idmission, idDepartement, datedebut, datefin,idvalidateur );
		assertNotEquals("Les deux objets ne sont pas identiques",timesheetPK.getIdEmploye(),idvalidateur);
	}

	@Test
	@Order(5)
	public void TestfindAllMissionByEmployeJPQL() throws ParseException  {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
 		Date datedebut = dateFormat.parse("2020-10-30");
 		Date datefin = dateFormat.parse("2022-10-06");
		Employe em1 = new Employe();
		em1.setNom("aa");
		em1.setPrenom("kkkk");
		int idemp =serviceEmp.ajouterEmploye(em1);
		Mission mission1 = new Mission ("missionEtrange","mission difficile");
		int idmission = service.ajouterMission(mission1);
		TimesheetPK timesheetPK = new TimesheetPK(idmission, em1.getId(), datedebut, datefin);
		timesheetPK.setIdEmploye(idemp);
		timesheetPK.setIdMission(idmission);
		service.ajouterTimesheet(idmission, idemp, new Date(20141212), new Date(20201212) );
		List<Mission> M = new ArrayList<> () ;
		M = service.findAllMissionByEmployeJPQL(idemp) ;
		log.info("le nombre des mission est "+ M.size());
		int length = M.size();
		 assertNotEquals(0, length); 
	}

}
