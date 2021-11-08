package tn.esprit.spring.services;

import java.util.Optional;

import tn.esprit.spring.entities.Mission;

public interface IMissionService {

	
	Mission ajouterMission(Mission mission);
	Optional<Mission> findMissionById(int id);


}