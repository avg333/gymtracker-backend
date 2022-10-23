package org.avillar.gymtracker.services.impl;

import org.avillar.gymtracker.model.dao.MuscleGroupDao;
import org.avillar.gymtracker.model.dao.MuscleSubGroupDao;
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

    private final MuscleGroupDao muscleGroupDao;
    private final MuscleSubGroupDao muscleSubGroupDao;
    private final ModelMapper modelMapper;

    @Autowired
    public MuscleGroupServiceImpl(MuscleGroupDao muscleGroupDao, MuscleSubGroupDao muscleSubGroupDao, ModelMapper modelMapper) {
        this.muscleGroupDao = muscleGroupDao;
        this.muscleSubGroupDao = muscleSubGroupDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<MuscleGroupDto> getAllMuscleGroups() {
        final List<MuscleGroup> muscleGroups = this.muscleGroupDao.findAll();
        return muscleGroups.stream().map(muscleGroup -> this.modelMapper.map(muscleGroup, MuscleGroupDto.class)).toList();
    }

    @Override
    public List<MuscleSubGroupDto> getAllMuscleGroupMuscleSubGroups(Long muscleGroupId) {
        final MuscleGroup muscleGroup = this.muscleGroupDao.findById(muscleGroupId).orElseThrow(() ->
                new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        final List<MuscleSubGroup> muscleSubGroupGroups = this.muscleSubGroupDao.findByMuscleGroupOrderByNameAsc(muscleGroup);
        return muscleSubGroupGroups.stream().map(muscleSubGroup -> this.modelMapper.map(muscleSubGroup, MuscleSubGroupDto.class)).toList();
    }

}
