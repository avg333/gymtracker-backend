package org.avillar.gymtracker.services.impl;

import org.avillar.gymtracker.model.dao.MuscleGroupDao;
import org.avillar.gymtracker.model.dao.MuscleSubGroupDao;
import org.avillar.gymtracker.model.dao.MuscleSupGroupDao;
import org.avillar.gymtracker.model.dto.MuscleGroupDto;
import org.avillar.gymtracker.model.dto.MuscleSubGroupDto;
import org.avillar.gymtracker.model.dto.MuscleSupGroupDto;
import org.avillar.gymtracker.model.entities.MuscleGroup;
import org.avillar.gymtracker.model.entities.MuscleSubGroup;
import org.avillar.gymtracker.model.entities.MuscleSupGroup;
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
    private final MuscleSupGroupDao muscleSupGroupDao;
    private final MuscleSubGroupDao muscleSubGroupDao;
    private final ModelMapper modelMapper;

    @Autowired
    public MuscleGroupServiceImpl(MuscleGroupDao muscleGroupDao, MuscleSupGroupDao muscleSupGroupDao, MuscleSubGroupDao muscleSubGroupDao, ModelMapper modelMapper) {
        this.muscleGroupDao = muscleGroupDao;
        this.muscleSupGroupDao = muscleSupGroupDao;
        this.muscleSubGroupDao = muscleSubGroupDao;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<MuscleSupGroupDto> getAllMuscleSupGroups() {
        final List<MuscleSupGroup> muscleSupGroups = this.muscleSupGroupDao.findAll();
        return muscleSupGroups.stream().map(muscleGroup -> this.modelMapper.map(muscleGroup, MuscleSupGroupDto.class)).toList();
    }

    @Override
    public MuscleSupGroupDto getMuscleSupGroup(final Long muscleSupGroupId) {
        final MuscleSupGroup muscleSupGroup = this.muscleSupGroupDao.findById(muscleSupGroupId).orElseThrow(() ->
                new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        return this.modelMapper.map(muscleSupGroup, MuscleSupGroupDto.class);
    }

    @Override
    public List<MuscleGroupDto> getAllMuscleGroups(final Long muscleSupGroupId) {
        final MuscleSupGroup muscleSupGroup = this.muscleSupGroupDao.findById(muscleSupGroupId).orElseThrow(() ->
                new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        final List<MuscleGroup> muscleGroups = this.muscleGroupDao.findByMuscleSupGroupOrderByNameAsc(muscleSupGroup);
        return muscleGroups.stream().map(muscleGroup -> this.modelMapper.map(muscleGroup, MuscleGroupDto.class)).toList();
    }

    @Override
    public MuscleGroupDto getMuscleGroup(final Long muscleGroupId) {
        final MuscleGroup muscleGroup = this.muscleGroupDao.findById(muscleGroupId).orElseThrow(() ->
                new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        return this.modelMapper.map(muscleGroup, MuscleGroupDto.class);
    }

    @Override
    public List<MuscleSubGroupDto> getAllMuscleGroupMuscleSubGroups(final Long muscleGroupId) {
        final MuscleGroup muscleGroup = this.muscleGroupDao.findById(muscleGroupId).orElseThrow(() ->
                new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        final List<MuscleSubGroup> muscleSubGroupGroups = this.muscleSubGroupDao.findByMuscleGroupOrderByNameAsc(muscleGroup);
        return muscleSubGroupGroups.stream().map(muscleSubGroup -> this.modelMapper.map(muscleSubGroup, MuscleSubGroupDto.class)).toList();
    }

}
