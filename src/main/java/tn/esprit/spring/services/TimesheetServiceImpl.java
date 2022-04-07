package tn.esprit.spring.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.entities.TimesheetPK;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;

@Slf4j
@Service
public class TimesheetServiceImpl implements ITimesheetService {
	

	@Autowired
	MissionRepository missionRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;
	@Autowired
	EmployeRepository employeRepository;
	
	public int ajouterMission(Mission mission) {
		log.info("[INFO] :L'ajout d'une mission commence");
		try {
			missionRepository.save(mission);
			log.info("[INFO] :Mission ajouté avec succées  ");
			} catch (Exception e) 
		{log.error("[Error] : Mission ne peut pas etre ajouter merci de verifier vos données d'entrés , plus de detail ",e );}
		return mission.getId();
	}

	public void affecterMissionADepartement(int missionId, int depId) {
		log.info("[INFO] :Affecter Mission à un  Departement commence");
		Optional<Mission> missionOpt=missionRepository.findById(missionId);
		Optional<Departement> departOpt=deptRepoistory.findById(depId);
		Mission mission = null;
		Departement dep = null; 
		if(departOpt.isPresent()){
			dep = departOpt.get();
			}
		if(missionOpt.isPresent()){
			mission = missionOpt.get();
			mission.setDepartement(dep);
			}
		try {	
		missionRepository.save(mission);
		log.info("[INFO] :Affectation mission departement reussite ");}
		catch (Exception e) 
		{log.error("[Error] : Mission ne peut pas etre affecté au departement merci de verifier vos données d'entrés , plus de detail ",e );}		
	}
	public void ajouterTimesheet(int missionId, int employeId, Date dateDebut, Date dateFin) {
		log.info("[INFO] :La fonction Ajouter TimeSheet commence");
		try {
		TimesheetPK timesheetPK = new TimesheetPK();
		timesheetPK.setDateDebut(dateDebut);
		timesheetPK.setDateFin(dateFin);
		timesheetPK.setIdEmploye(employeId);
		timesheetPK.setIdMission(missionId);
		
		Timesheet timesheet = new Timesheet();
		timesheet.setTimesheetPK(timesheetPK);
		timesheet.setValide(false);
		timesheetRepository.save(timesheet);
		log.info("[INFO] : timesheet ajouté avec succées");
		}catch (Exception e)
		{log.error("[Error] : Mission ne peut pas etre affecté au departement merci de verifier vos données d'entrés , plus de detail ",e );}		
		
	}
	public void validerTimesheet(int missionId, int employeId, Date dateDebut, Date dateFin, int validateurId) {
		log.info("[INFO] La fonction Valider TimeSheet Commence ");
		Optional<Employe> validateuropt = employeRepository.findById(validateurId);
		Optional<Mission> missionopt = missionRepository.findById(missionId);
		Mission mission=null ;
		Employe validateur = null ;
		if(missionopt.isPresent()){
			mission = missionopt.get();
			}
		if(validateuropt.isPresent()){
			validateur = validateuropt.get();
			
		}
		try{
			if( (mission != null )&&( validateur != null))
		{if(!validateur.getRole().equals(Role.CHEF_DEPARTEMENT)){
			log.info(" [INFO] :l'employe doit etre chef de departement pour valider une feuille de temps !");
			return;
		}
		
		boolean chefDeLaMission = false;
		for(Departement dep : validateur.getDepartements()){
			if(dep.getId() == mission.getDepartement().getId()){
				chefDeLaMission = true;
				break;
			}
		}
		if(!chefDeLaMission){
			log.info("[INFO] : l'employe doit etre chef de departement de la mission en question");
			return;
		}

		TimesheetPK timesheetPK = new TimesheetPK(missionId, employeId, dateDebut, dateFin);
		Timesheet timesheet =timesheetRepository.findBytimesheetPK(timesheetPK);
		timesheet.setValide(true);
		
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		log.info(" [INFO] : dateDebut : " + dateFormat.format(timesheet.getTimesheetPK().getDateDebut()));}}
		catch (Exception e )
		{log.error("[Error] : Validation impossible ",e );}		
		
		
	}

	public List<Mission> findAllMissionByEmployeJPQL(int employeId) {
		log.info("[INFO]:  Find All Mission BY Employee Commence");
		List<Mission> l = new ArrayList <>();
		try {
			l = timesheetRepository.findAllMissionByEmployeJPQL(employeId);
		}catch (Exception e)
		{log.error("[Error] : Get All Employee By Mission Failed , plus de detail ",e );}
		return l ;
		
	}

	
	public List<Employe> getAllEmployeByMission(int missionId) {
		log.info("[INFO] Find All Employee By Mission Commence");
		List<Employe> l = new ArrayList <>();
		try {
		l = timesheetRepository.getAllEmployeByMission(missionId);
		}catch (Exception e)
		{log.error("[Error] : Get All Employee By Mission Failed , plus de detail ",e );}
		return l ;
	}

}
    