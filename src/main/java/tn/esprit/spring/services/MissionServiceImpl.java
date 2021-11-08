package tn.esprit.spring.services;




	import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.repository.MissionRepository;


	@Service
	public class MissionServiceImpl  implements IMissionService{
		@Autowired
		MissionRepository missionRepository;
		
			
		
		
		@Override

		public Mission ajouterMission(Mission mission) {
			missionRepository.save(mission);
			
			return mission;
		}




		@Override


		public Optional<Mission> findMissionById(int id) {
        Optional<Mission> mission=missionRepository.findById(id);
			return mission;
		}

		
		

		}