package tn.esprit.spring.services;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Employe;

import tn.esprit.spring.repository.EmployeRepository;


@Service
public class EmployeServiceImpl implements IEmployeService {

@Autowired
EmployeRepository employeRepository;

public int ajouterEmploye(Employe employe) {
	employeRepository.save(employe);
	return employe.getId();
}

	
	

}
