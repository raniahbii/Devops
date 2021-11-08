package tn.esprit.spring.services;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
	private static final Logger l =LogManager.getLogger(TimesheetServiceImpl.class);
	
	public int ajouterMission(Mission mission) {
		missionRepository.save(mission);
		return mission.getId();
	}
    
	public void affecterMissionADepartement(int missionId, int depId) {
	
        Optional<Departement> depOptional = deptRepoistory.findById(depId);
        Optional<Mission> missionOptional = missionRepository.findById(missionId);
        if (depOptional.isPresent() && missionOptional.isPresent()) {
            Departement entrepriseManagedEntity = depOptional.get();
            Mission misManagedEntity = missionOptional.get();
            misManagedEntity.setDepartement(entrepriseManagedEntity);
            missionRepository.save(misManagedEntity);
        }
		
	}

	public void ajouterTimesheet(int missionId, int employeId, Date dateDebut, Date dateFin) {
		TimesheetPK timesheetPK = new TimesheetPK();
		timesheetPK.setDateDebut(dateDebut);
		timesheetPK.setDateFin(dateFin);
		timesheetPK.setIdEmploye(employeId);
		timesheetPK.setIdMission(missionId);
		
		Timesheet timesheet = new Timesheet();
		timesheet.setTimesheetPK(timesheetPK);
		timesheet.setValide(false); //par defaut non valide
		timesheetRepository.save(timesheet);
		
	}

	

	public void validerTimesheet(int missionId, int employeId, Date dateDebut, Date dateFin, int validateurId) {
		l.info("In valider Timesheet");

		
		Employe validateur = employeRepository.findById(validateurId).orElse(null);
		Mission mission = missionRepository.findById(missionId).orElse(null);
		//verifier s'il est un chef de departement (interet des enum)
		if(validateur!=null && !validateur.getRole().equals(Role.CHEF_DEPARTEMENT)){
		
			return;
		}
		boolean chefDeLaMission = false;
		if(validateur!=null && validateur.getDepartements()!=null )
		for(Departement dep : validateur.getDepartements()){
			if(mission !=null && mission.getDepartement() !=null && dep.getId() == mission.getDepartement().getId()){
				chefDeLaMission = true;
				break;
			}
		}
		if(!chefDeLaMission){
			l.info("l'employe doit etre chef de departement de la mission en question");

			return;
		}
//
		TimesheetPK timesheetPK = new TimesheetPK(missionId, employeId, dateDebut, dateFin);
		Timesheet timesheet =timesheetRepository.findBytimesheetPK(timesheetPK);
			timesheet.setValide(true);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		l.info("dateDebut : " , dateFormat.format(timesheet.getTimesheetPK().getDateDebut()));

		
	}
	@Override
	public Timesheet findTimesheet(TimesheetPK pk) {
		
		return timesheetRepository.findBytimesheetPK(pk);
	}
}
