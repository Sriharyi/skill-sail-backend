package com.sriharyi.skillsail.controller;

import com.sriharyi.skillsail.dto.EmployerDto;
import com.sriharyi.skillsail.service.EmployerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/employers")
public class EmployerController {

    private final EmployerService employerService;

    @PostMapping
    public ResponseEntity<EmployerDto> createEmployer(@RequestBody EmployerDto employerDto) {
        EmployerDto createdEmployer = employerService.createEmployer(employerDto);
        return new ResponseEntity<>(createdEmployer, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EmployerDto>> getAllEmployers() {
        List<EmployerDto> employers = employerService.getAllEmployers();
        return new ResponseEntity<>(employers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployerDto> getEmployerById(@PathVariable String id) {
        EmployerDto employer = employerService.getEmployerById(id);
        return new ResponseEntity<>(employer, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployerDto> updateEmployer(@PathVariable String id, @RequestBody EmployerDto employerDto) {
        EmployerDto updatedEmployer = employerService.updateEmployer(id, employerDto);
        return new ResponseEntity<>(updatedEmployer, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployer(@PathVariable String id) {
        employerService.deleteEmployer(id);
        return new ResponseEntity<>("Successfully Deleted",HttpStatus.OK);
    }



}
