package org.avillar.gymtracker.services.impl;

import org.avillar.gymtracker.dao.MuscleGroupRepository;
import org.avillar.gymtracker.dao.MuscleSubGroupRepository;
import org.avillar.gymtracker.model.MuscleGroup;
import org.avillar.gymtracker.model.MuscleSubGroup;
import org.avillar.gymtracker.services.MuscleGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MuscleGroupServiceImpl implements MuscleGroupService {

    private final MuscleGroupRepository muscleGroupRepository;
    private final MuscleSubGroupRepository muscleSubGroupRepository;

    @Autowired
    public MuscleGroupServiceImpl(MuscleGroupRepository muscleGroupRepository, MuscleSubGroupRepository muscleSubGroupRepository) {
        this.muscleGroupRepository = muscleGroupRepository;
        this.muscleSubGroupRepository = muscleSubGroupRepository;
    }

    @Override
    public List<MuscleGroup> getAllMuscleGroups() {
        return this.muscleGroupRepository.findAll();
    }

    @Override
    public List<MuscleSubGroup> getMuscleSubGroups(Long muscleGroupId) {
        return this.muscleSubGroupRepository.findByMuscleGroup(muscleGroupId);
    }

}
