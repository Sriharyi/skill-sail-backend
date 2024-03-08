package com.sriharyi.skillsail.controller;

import com.sriharyi.skillsail.dto.QuestionDto;
import com.sriharyi.skillsail.dto.SkillDto;
import com.sriharyi.skillsail.service.SkillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/skills")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class SkillController {

    private final SkillService skillService;

    @PostMapping
    public ResponseEntity<SkillDto> createSkill(@RequestBody SkillDto skillDto) {
        SkillDto createdSkill = skillService.createSkill(skillDto);
        return new ResponseEntity<>(createdSkill, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SkillDto>> getAllSkills() {
        List<SkillDto> skills = skillService.getAllSkills();
        return new ResponseEntity<>(skills, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillDto> getSkillById(@PathVariable String id) {
        SkillDto skill = skillService.getSkillById(id);
        return new ResponseEntity<>(skill, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SkillDto> updateSkill(@PathVariable String id, @RequestBody SkillDto skillDto) {
        SkillDto updatedSkill = skillService.updateSkill(id, skillDto);
        return new ResponseEntity<>(updatedSkill, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSkill(@PathVariable String id) {
        skillService.deleteSkill(id);
        return new ResponseEntity<>("Successfully Deleted",HttpStatus.OK);
    }

    @PostMapping("/{id}/questions")
    public ResponseEntity<QuestionDto> createQuestion(@PathVariable String id, @RequestBody QuestionDto questionDto) {
        QuestionDto createdQuestion = skillService.createQuestion(id, questionDto);
        return new ResponseEntity<>(createdQuestion, HttpStatus.CREATED);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<SkillDto>> getSkillsByPage(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<SkillDto> skills = skillService.getSkillsByPage(pageable);
        return new ResponseEntity<>(skills, HttpStatus.OK);
    }

    @GetMapping("/category")
    public ResponseEntity<List<SkillDto>> getSkillsByCategory(@RequestParam String category) {
        List<SkillDto> skills = skillService.getSkillsByCategory(category);
        return new ResponseEntity<>(skills, HttpStatus.OK);
    }


}
