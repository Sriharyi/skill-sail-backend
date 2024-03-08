package com.sriharyi.skillsail.service;

import com.sriharyi.skillsail.dto.QuestionDto;
import com.sriharyi.skillsail.dto.SkillDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SkillService   {
    SkillDto createSkill(SkillDto skillDto);

    List<SkillDto> getAllSkills();

    SkillDto getSkillById(String id);

    SkillDto updateSkill(String id, SkillDto skillDto);

    void deleteSkill(String id);

    QuestionDto createQuestion(String id, QuestionDto questionDto);


    Page<SkillDto> getSkillsByPage(Pageable pageable);

    List<SkillDto> getSkillsByCategory(String category);
}
