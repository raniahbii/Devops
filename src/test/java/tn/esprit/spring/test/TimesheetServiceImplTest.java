package tn.esprit.spring.test;

import static org.assertj.core.api.Assertions.assertThat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.TimesheetPK;
import tn.esprit.spring.services.EmployeServiceImpl;
import tn.esprit.spring.services.IMissionService;
import tn.esprit.spring.services.ITimesheetService;
@RunWith(SpringRunner.class)
@SpringBootTest
public class TimesheetServiceImplTest {
	
	@Autowired
	ITimesheetService timesheetser ; 
	@Autowired
	IMissionService missSer;
	@Autowired
	EmployeServiceImpl iEmployeService;
	private static final Logger LOGGER = LogManager.getLogger(TimesheetServiceImplTest.class);
	
	@Test
	public  void testajouterMission()  {
		Mission m = new Mission();
		m.setId(10);
		m.setName("SagemCom");
		m.setDescription("embarqué");
		missSer.ajouterMission(m);
		assertThat(m.getName()).isEqualTo("SagemCom");

	}
	

	@Test
	public void testajouterTimesheet() throws ParseException{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
		Date date = dateFormat.parse("2015-03-23");
		Date date1 = dateFormat.parse("2016-06-12");
	
		timesheetser.ajouterTimesheet(2,3,date,date1);
		
	}
	
	@Test
	public void testValiderTimeSheet() {
		try {
			LOGGER.info("In ValiderTimeSheet() :");
			LOGGER.debug("lancer methode");
			timesheetser.validerTimesheet(1, 1, new Date(), new Date(), 1);
		} catch (Exception e) {
			LOGGER.error("Erreur dans ValiderTimeSheet() :" + e);
		}
	
	}
	
	@Test
	public void testfindTimesheet()
	{
		int idemp;
		int idmiss ;
		Employe employe = new Employe( "rania", "habibi", "rania.habibi@esprit.tn", true, Role.INGENIEUR);

		idemp = iEmployeService.ajouterEmploye(employe);
		Mission mission = new Mission("Miss","RR");
		idmiss = timesheetser.ajouterMission(mission);
		timesheetser.ajouterTimesheet(idmiss, idemp, new Date(),new Date());
		Assert.assertNotNull(timesheetser.findTimesheet(new TimesheetPK(idmiss, idemp, new Date(), new Date()))); 

	}
	
}