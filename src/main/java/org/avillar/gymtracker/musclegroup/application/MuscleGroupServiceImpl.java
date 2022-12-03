package org.avillar.gymtracker.musclegroup.application;

import jakarta.persistence.EntityNotFoundException;
import org.avillar.gymtracker.musclegroup.application.dto.MuscleGroupDto;
import org.avillar.gymtracker.musclegroup.application.dto.MuscleGroupMapper;
import org.avillar.gymtracker.musclegroup.application.dto.MuscleSubGroupDto;
import org.avillar.gymtracker.musclegroup.application.dto.MuscleSupGroupDto;
import org.avillar.gymtracker.musclegroup.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MuscleGroupServiceImpl implements MuscleGroupService {
    private static final String MSUPG_NOT_FOUND_ERROR_MSG = "The MuscleSupGroup does not exist";
    private static final String MG_NOT_FOUND_ERROR_MSG = "The MuscleGroup does not exist";

    private final MuscleGroupDao muscleGroupDao;
    private final MuscleSupGroupDao muscleSupGroupDao;
    private final MuscleSubGroupDao muscleSubGroupDao;
    private final MuscleGroupMapper muscleGroupMapper;

    @Autowired
    public MuscleGroupServiceImpl(MuscleGroupDao muscleGroupDao, MuscleSupGroupDao muscleSupGroupDao,
                                  MuscleSubGroupDao muscleSubGroupDao, MuscleGroupMapper muscleGroupMapper) {
        this.muscleGroupDao = muscleGroupDao;
        this.muscleSupGroupDao = muscleSupGroupDao;
        this.muscleSubGroupDao = muscleSubGroupDao;
        this.muscleGroupMapper = muscleGroupMapper;
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<MuscleSupGroupDto> getAllMuscleSupGroups() {
        return this.muscleGroupMapper.toDtos(this.muscleSupGroupDao.findAll(), true);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public MuscleSupGroupDto getMuscleSupGroup(final Long muscleSupGroupId) throws EntityNotFoundException {
        final MuscleSupGroup muscleSupGroup = this.muscleSupGroupDao.findById(muscleSupGroupId)
                .orElseThrow(() -> new EntityNotFoundException(MSUPG_NOT_FOUND_ERROR_MSG));
        return this.muscleGroupMapper.toDto(muscleSupGroup, true);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<MuscleGroupDto> getAllMuscleSupGroupMuscleGroups(final Long muscleSupGroupId) throws EntityNotFoundException {
        final MuscleSupGroup muscleSupGroup = this.muscleSupGroupDao.findById(muscleSupGroupId)
                .orElseThrow(() -> new EntityNotFoundException(MSUPG_NOT_FOUND_ERROR_MSG));
        return this.muscleGroupMapper.toDtos(
                this.muscleGroupDao.findByMuscleSupGroupsOrderByNameAsc(muscleSupGroup), true);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public MuscleGroupDto getMuscleGroup(final Long muscleGroupId) throws EntityNotFoundException {
        final MuscleGroup muscleGroup = this.muscleGroupDao.findById(muscleGroupId)
                .orElseThrow(() -> new EntityNotFoundException(MG_NOT_FOUND_ERROR_MSG));
        return this.muscleGroupMapper.toDto(muscleGroup, true);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<MuscleSubGroupDto> getAllMuscleGroupMuscleSubGroups(final Long muscleGroupId) throws EntityNotFoundException {
        final MuscleGroup muscleGroup = this.muscleGroupDao.findById(muscleGroupId)
                .orElseThrow(() -> new EntityNotFoundException(MG_NOT_FOUND_ERROR_MSG));
        return this.muscleGroupMapper.toDtos(
                this.muscleSubGroupDao.findByMuscleGroupOrderByNameAsc(muscleGroup), true);
    }

}
