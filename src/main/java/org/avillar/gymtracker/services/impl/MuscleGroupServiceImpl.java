package org.avillar.gymtracker.services.impl;

import org.avillar.gymtracker.model.dao.MuscleGroupRepository;
import org.avillar.gymtracker.model.dao.MuscleSubGroupRepository;
import org.avillar.gymtracker.model.dto.MuscleGroupDto;
import org.avillar.gymtracker.model.dto.MuscleSubGroupDto;
import org.avillar.gymtracker.model.entities.MuscleGroup;
import org.avillar.gymtracker.model.entities.MuscleSubGroup;
import org.avillar.gymtracker.services.MuscleGroupService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class MuscleGroupServiceImpl implements MuscleGroupService {
    private static final String NOT_FOUND_ERROR_MSG = "El SetGroup no existe";

    private final MuscleGroupRepository muscleGroupRepository;
    private final MuscleSubGroupRepository muscleSubGroupRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MuscleGroupServiceImpl(MuscleGroupRepository muscleGroupRepository, MuscleSubGroupRepository muscleSubGroupRepository,ModelMapper modelMapper) {
        this.muscleGroupRepository = muscleGroupRepository;
        this.muscleSubGroupRepository = muscleSubGroupRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<MuscleGroupDto> getAllMuscleGroups() {
        final List<MuscleGroup> muscleGroups = this.muscleGroupRepository.findAll();
        return muscleGroups.stream().map(muscleGroup -> this.modelMapper.map(muscleGroup, MuscleGroupDto.class)).toList();
    }

    @Override
    public List<MuscleSubGroupDto> getAllMuscleGroupMuscleSubGroups(Long muscleGroupId) {
        final MuscleGroup muscleGroup = this.muscleGroupRepository.findById(muscleGroupId).orElseThrow(() ->
                new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        final List<MuscleSubGroup> muscleSubGroupGroups = this.muscleSubGroupRepository.findByMuscleGroupOrderByNameAsc(muscleGroup);
        return muscleSubGroupGroups.stream().map(muscleSubGroup -> this.modelMapper.map(muscleSubGroup, MuscleSubGroupDto.class)).toList();
    }

}
