package com.revature.controller;

import static com.revature.util.ClientMessageUtil.FAILED_UPDATE;
import static com.revature.util.ClientMessageUtil.SUCCESSFULLY_TREATED;
import static com.revature.util.ClientMessageUtil.SUCCESSFUL_UPDATE;
import static com.revature.util.ClientMessageUtil.TREATMENT_FAILED;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.model.Employee;
import com.revature.model.Medicine;
import com.revature.model.Patient;
import com.revature.model.StatusCondition;
import com.revature.service.NurseService;
import com.revature.util.ClientMessage;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class NurseController implements EmployeeController {

	@Autowired
	private NurseService nurseService;

	@GetMapping("/nurse/treatment/select")
	public ResponseEntity<List<Medicine>> selectmeds(@RequestBody StatusCondition status) {
		List<Medicine> medicine = nurseService.selectTreatment(status);
		return ResponseEntity.ok(medicine);
	}
	
	@PostMapping("/nurse/treatment/apply")
	public ResponseEntity<ClientMessage> applymeds(@RequestBody Patient p, Medicine med){
		ClientMessage body = (nurseService.applytreatment(p, med)) ? SUCCESSFULLY_TREATED
				: TREATMENT_FAILED;
		return ResponseEntity.ok(body);
	}
	
	@PostMapping("/nurse/patient/healthy")
	public ResponseEntity<ClientMessage> declarehealthy(@RequestBody Patient p){
		ClientMessage body = (nurseService.declarehealthy(p)) ? SUCCESSFULLY_TREATED
				: TREATMENT_FAILED;
		return ResponseEntity.ok(body);
	}

	@PostMapping("/nurse/patients/assigned")
	public ResponseEntity<List<Patient>> getNursePatients(@RequestBody Employee nurse) {
		List<Patient> nursesPatients = nurseService.getNursePatients(nurse);
		return ResponseEntity.ok(nursesPatients);
	}

	@PutMapping("/nurse/update")
	public ResponseEntity<ClientMessage> updateNurse(@RequestBody Employee nurse) {
		ClientMessage body = (nurseService.update(nurse)) ? SUCCESSFUL_UPDATE : FAILED_UPDATE;
		return ResponseEntity.ok(body);
	}

	@Override
	@GetMapping("/nurse/medicine")
	public ResponseEntity<List<Medicine>> getAllMedicines() {
		List<Medicine> medicines = nurseService.getAllMedicines();
		return ResponseEntity.ok(medicines);
	}

	@Override
	@GetMapping("/nurse/patients/all")
	public ResponseEntity<List<Patient>> getAllPatients() {
		List<Patient> patients = nurseService.getAllPatients();
		return ResponseEntity.ok(patients);
	}
}
