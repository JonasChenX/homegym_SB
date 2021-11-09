package edu.ntut.project_01.homegym.service.impl;

import edu.ntut.project_01.homegym.model.FQA;
import edu.ntut.project_01.homegym.repository.FQARepository;
import edu.ntut.project_01.homegym.service.FQAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FQAServiceImpl implements FQAService {

    @Autowired
    private FQARepository fQARepository;

    @Override
    public void save(FQA fqa) {
        fQARepository.save(fqa);
    }

    public List<FQA> showFQA(int courseId){

        return fQARepository.findFQAByCourseCourseId(courseId);
    }

    public Optional<FQA> findById(int courseId){

        return fQARepository.findById(courseId);
    }

}