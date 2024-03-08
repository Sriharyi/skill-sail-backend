package com.sriharyi.skillsail.service.impl;

import com.sriharyi.skillsail.dto.QuestionDto;
import com.sriharyi.skillsail.dto.SkillDto;
import com.sriharyi.skillsail.exception.SkillNotFoundException;
import com.sriharyi.skillsail.model.Question;
import com.sriharyi.skillsail.model.Skill;
import com.sriharyi.skillsail.repository.QuestionRepository;
import com.sriharyi.skillsail.repository.SkillRepository;
import com.sriharyi.skillsail.service.SkillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    private final QuestionRepository questionRepository;

    @Override
    public SkillDto createSkill(SkillDto skillDto) {
        Skill skill = mapToSkill(skillDto);
        Set<QuestionDto> questions = skillDto.getQuestions();

        if (questions != null) {
            questions.forEach(questionDto -> {
                Question question = mapToQuestion(questionDto);
                question = questionRepository.save(question);
                skill.getQuestions().add(question);
            });
        }

        Skill savedSkill = skillRepository.save(skill);


        return mapToSkillDto(savedSkill);
    }

    @Override
    public List<SkillDto> getAllSkills() {
        List<Skill> skills = skillRepository.findAllByDeletedIsFalse();
        return skills.stream()
                .map(this::mapToSkillDto)
                .toList();
    }

    @Override
    public SkillDto getSkillById(String id) {
        Skill skill = skillRepository.findById(id).orElseThrow(
                () -> new SkillNotFoundException("Skill not found")
        );

        if (skill.isDeleted()) {
            throw new SkillNotFoundException("Skill not found");
        }

        log.info("Skill found: {}", skill);

        return mapToSkillDto(skill);
    }

    @Override
    public SkillDto updateSkill(String id, SkillDto skillDto) {
        Skill skill = mapToSkill(skillDto);
        skill.setId(id);

        Set<QuestionDto> questions = skillDto.getQuestions();

        if (questions != null) {
            questions.forEach(questionDto -> {
                Question question = mapToQuestion(questionDto);
                skill.getQuestions().add(question);
            });
        }

        Skill updatedSkill = skillRepository.save(skill);
        return mapToSkillDto(updatedSkill);
    }

    @Override
    public void deleteSkill(String id) {
        Skill skill = skillRepository.findById(id).orElseThrow(
                () -> new SkillNotFoundException("Skill not found")
        );
        skill.setDeleted(true);
        skillRepository.save(skill);
    }

    @Override
    public QuestionDto createQuestion(String id, QuestionDto questionDto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Page<SkillDto> getSkillsByPage(Pageable pageable) {
        Page<Skill> skills = skillRepository.findAllByDeletedIsFalse(pageable);
        return skills.map(this::mapToSkillDto);
    }

    protected Skill mapToSkill(SkillDto skillDto) {
        return Skill.builder()
                .id(skillDto.getId())
                .name(skillDto.getName())
                .description(skillDto.getDescription())
                .questions(new HashSet<Question>())
                .category(skillDto.getCategory())
                .build();
    }

    protected SkillDto mapToSkillDto(Skill skill) {
        return SkillDto.builder()
                .id(skill.getId())
                .name(skill.getName())
                .description(skill.getDescription())
                .questions(skill.getQuestions().stream()
                        .map(this::mapToQuestionDto)
                        .collect(Collectors.toSet()))
                .category(skill.getCategory())
                .build();
    }

    protected QuestionDto mapToQuestionDto(Question question) {
        return QuestionDto.builder()
                .questionId(question.getId())
                .text(question.getText())
                .options(question.getOptions())
                .correctOption(question.getCorrectOption())
                .type(question.getType())
                .build();
    }

    protected Question mapToQuestion(QuestionDto questionDto) {
        return Question.builder()
                .id(questionDto.getQuestionId())
                .text(questionDto.getText())
                .options(questionDto.getOptions())
                .correctOption(questionDto.getCorrectOption())
                .type(questionDto.getType())
                .build();
    }

    @Override
    public List<SkillDto> getSkillsByCategory(String category) {
        List<Skill> skills = skillRepository.findAllByCategoryAndDeletedIsFalse(category);
        return skills.stream()
                .map(this::mapToSkillDto)
                .toList();
    }
}
