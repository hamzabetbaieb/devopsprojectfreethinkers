package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;

@Service
@Slf4j
public class EntrepriseServiceImpl implements IEntrepriseService {

	@Autowired
    EntrepriseRepository entrepriseRepoistory;
	@Autowired
	DepartementRepository deptRepoistory;
	
	
	public int ajouterEntreprise(Entreprise entreprise) {
		log.info("ajouterEntreprise lancé");
		try{
			entrepriseRepoistory.save(entreprise);
			log.info("L'entreprise="+entreprise.getId()+" ajoutée");
			
		}
		catch (Exception e){
			log.error("ajouterEnterprise echoué, plus de detail "+e);
		}
	   return entreprise.getId();
		
		
		
	}

	public int ajouterDepartement(Departement dep) {
		log.info("ajouterDepartement lancé");
		try{
		deptRepoistory.save(dep);
		log.info("le Departement ="+dep.getId()+" ajouté");
		}
		catch (Exception e){
			log.error("ajouterDepartement echoué, plus de detail "+e);
		}
		return dep.getId();
	}
	
	public void affecterDepartementAEntreprise(int depId, int entrepriseId) {
		
		
		log.info("affecterDepartementAEntreprise lancé");
				Optional<Departement> depOpt=deptRepoistory.findById(depId);
				Optional<Entreprise> entOpt=entrepriseRepoistory.findById(entrepriseId);
				
				Departement dep = null;
				Entreprise ent = null; 
				if(entOpt.isPresent() && depOpt.isPresent()){
					ent = entOpt.get();
					dep = depOpt.get();
				}
				try{
				if(ent!=null && dep!=null){
					deptRepoistory.save(dep);
		 	log.info("le departement d'id="+depId+" ajouté à l'entreprise "+entrepriseId);
				}}
				catch (Exception e) 
				{log.error("affecterDepartementAEntreprise echoué , plus de detail"+e);}	
		
	}
		
	
	
	public List<String> getAllDepartementsNamesByEntreprise(int entrepriseId) {
		log.info("getAllDepartementsNamesByEntreprise lancé");
	Entreprise	entrepriseManagedEntity=null;
		Optional<Entreprise> entOpt=entrepriseRepoistory.findById(entrepriseId);
		List<String> depNames = new ArrayList<>();
	try{
			if( entOpt.isPresent())
	{
		entrepriseManagedEntity = entOpt.get();
		
	}
	}catch (Exception e){
		log.error("getDepart echoué, "+e);
	}
		
	if(	entrepriseManagedEntity!=null){
		for(Departement dep : entrepriseManagedEntity.getDepartements()){
			depNames.add(dep.getName());
			
		}
		log.info("departments ="+depNames);
	}
		
		return depNames;
		
	}

	@Transactional
	public void deleteEntrepriseById(int entrepriseId) {
		Optional<Entreprise> entOpt=entrepriseRepoistory.findById(entrepriseId);
		Entreprise entreprise=null;
		try{
		if(
		entOpt.isPresent())
		{
		entreprise = entOpt.get();
			
		}
		}catch(Exception e){
			log.error("deleteEntrepriseById echoué, plus de detail "+e);
		}
		
		if(entreprise!=null){
	
         entrepriseRepoistory.delete(entreprise);
         log.info("Entreprise ID ="+entrepriseId+" supprimé");
		
		}
	}
		
	

	@Transactional
	public void deleteDepartementById(int depId) {
		log.info("deleteDepartementById lancé");
		Optional<Departement> depOpt=deptRepoistory.findById(depId);
		Departement dep=null;
		try{
			if(
					depOpt.isPresent())
			{
				dep = depOpt.get();
				
			}
			}catch(Exception e){
				log.error("deleteEntrepriseById echoué,plus de detail "+e);
			}
			
			if(dep!=null){
		
	         deptRepoistory.delete(dep);
	         log.info("Departement ID ="+depId+" supprimé");
			
			}
		}

	public Entreprise getEntrepriseById(int entrepriseId) {
		
		log.info("getEntrepriseById lancé");
		
		Optional<Entreprise> entOpt=entrepriseRepoistory.findById(entrepriseId);
		Entreprise entreprise=null;
		try{
		if(
					entOpt.isPresent())
		{
			entreprise = entOpt.get();
			log.info("L'entreprise d'id ="+entrepriseId);
			
		}
		}catch (Exception e){
			log.error("getEntrepriseById echoué,plus de detail "+e);
		}
		
	   if(entreprise!=null){
	       entrepriseRepoistory.save(entreprise);
        }
	   
		return entrepriseRepoistory.findById(entrepriseId).get();
		
	}

}
