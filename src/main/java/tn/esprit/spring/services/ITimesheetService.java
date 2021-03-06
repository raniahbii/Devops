package tn.esprit.spring.services;

import java.util.Date;

import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.entities.TimesheetPK;



public interface ITimesheetService {
	
	public int ajouterMission(Mission mission);
	public void affecterMissionADepartement(int missionId, int depId);
	public void ajouterTimesheet(int missionId, int employeId, Date dateDebut, Date dateFin);
	public void validerTimesheet(int missionId, int employeId, Date dateDebut, Date dateFin, int validateurId);
	public Timesheet findTimesheet(TimesheetPK pk);
}
