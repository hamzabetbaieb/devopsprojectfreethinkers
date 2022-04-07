package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.TimesheetRepository;

@Slf4j
@Service
public class EmployeServiceImpl implements IEmployeService {

	@Autowired
	EmployeRepository employeRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	ContratRepository contratRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;

	public int ajouterEmploye(Employe employe) {
		log.info("ajouterEmploye a été lancé ");
		try{
		employeRepository.save(employe);
		}
		catch (Exception e){
			log.error("L'ajout de l'employé a echoué "+e);
		}
		log.info("L'employé dont L'ID = "+employe.getId()+" est ajouté");
		return employe.getId();
	}

	public void mettreAjourEmailByEmployeId(String email, int employeId) {
		log.info("mettreAjourEmailByEmployeId a été lancé ");
		Optional<Employe> empOpt=employeRepository.findById(employeId);
		Employe employe=null;
		if(empOpt.isPresent()){
		employe = empOpt.get();
		}
		if(employe!=null){
		employe.setEmail(email);
		employeRepository.save(employe);
		log.info("L'employé dont L'ID = "+employe.getId()+" est mis a jour ");

		}
		
	}

	@Transactional	
	public void affecterEmployeADepartement(int employeId, int depId) {
		log.info("affecterEmploye a été lancé ");
		
		
		Optional<Employe> empOpt=employeRepository.findById(employeId);
		Optional<Departement> depOpt=deptRepoistory.findById(depId); 
		
		Departement depManagedEntity=null;
		Employe employeManagedEntity=null;
		try{
			if(empOpt.isPresent() && depOpt.isPresent()){
				employeManagedEntity = empOpt.get();
				depManagedEntity = depOpt.get();
			}
			
		}
		catch (Exception e){
			log.error("L'affectation de l'employé a echoué "+e.toString());
		}
		if(employeManagedEntity!=null && depManagedEntity!=null){
			
		
		
		if(depManagedEntity.getEmployes() == null){

			List<Employe> employes = new ArrayList<>();
			employes.add(employeManagedEntity);
			depManagedEntity.setEmployes(employes);
		}else{

			depManagedEntity.getEmployes().add(employeManagedEntity);
		}
		}	
	}
	@Transactional
	public void desaffecterEmployeDuDepartement(int employeId, int depId)
	{
		Departement dep = deptRepoistory.findById(depId).get();

		int employeNb = dep.getEmployes().size();
		for(int index = 0; index < employeNb; index++){
			if(dep.getEmployes().get(index).getId() == employeId){
				dep.getEmployes().remove(index);
				break;//a revoir
			}
		}
	}

	public int ajouterContrat(Contrat contrat) {
		contratRepoistory.save(contrat);
		return contrat.getReference();
	}

	public void affecterContratAEmploye(int contratId, int employeId) {
		Contrat contratManagedEntity = contratRepoistory.findById(contratId).get();
		Employe employeManagedEntity = employeRepository.findById(employeId).get();

		contratManagedEntity.setEmploye(employeManagedEntity);
		contratRepoistory.save(contratManagedEntity);
		
	}

	public String getEmployePrenomById(int employeId) {
		Employe employeManagedEntity = employeRepository.findById(employeId).get();
		return employeManagedEntity.getPrenom();
	}
	public void deleteEmployeById(int employeId)
	{
		Employe employe = employeRepository.findById(employeId).get();

		for(Departement dep : employe.getDepartements()){
			dep.getEmployes().remove(employe);
		}

		employeRepository.delete(employe);
	}

	public void deleteContratById(int contratId) {
		Contrat contratManagedEntity = contratRepoistory.findById(contratId).get();
		contratRepoistory.delete(contratManagedEntity);

	}

	public int getNombreEmployeJPQL() {
		return employeRepository.countemp();
	}
	
	public List<String> getAllEmployeNamesJPQL() {
		return employeRepository.employeNames();

	}
	
	public List<Employe> getAllEmployeByEntreprise(Entreprise entreprise) {
		return employeRepository.getAllEmployeByEntreprisec(entreprise);
	}

	public void mettreAjourEmailByEmployeIdJPQL(String email, int employeId) {
		employeRepository.mettreAjourEmailByEmployeIdJPQL(email, employeId);

	}
	public void deleteAllContratJPQL() {
         employeRepository.deleteAllContratJPQL();
	}
	
	public float getSalaireByEmployeIdJPQL(int employeId) {
		return employeRepository.getSalaireByEmployeIdJPQL(employeId);
	}

	public Double getSalaireMoyenByDepartementId(int departementId) {
		return employeRepository.getSalaireMoyenByDepartementId(departementId);
	}
	
	public List<Timesheet> getTimesheetsByMissionAndDate(Employe employe, Mission mission, Date dateDebut,
			Date dateFin) {
		return timesheetRepository.getTimesheetsByMissionAndDate(employe, mission, dateDebut, dateFin);
	}

	public List<Employe> getAllEmployes() {
				return (List<Employe>) employeRepository.findAll();
	}

}
