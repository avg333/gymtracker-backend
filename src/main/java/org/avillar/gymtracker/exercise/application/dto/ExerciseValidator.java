package org.avillar.gymtracker.exercise.application.dto;

import org.apache.commons.lang3.StringUtils;
import org.avillar.gymtracker.enums.domain.LoadTypeEnum;
import org.avillar.gymtracker.errors.application.EntityNotFoundException;
import org.avillar.gymtracker.musclegroup.application.dto.MuscleGroupDto;
import org.avillar.gymtracker.musclegroup.application.dto.MuscleSubGroupDto;
import org.avillar.gymtracker.musclegroup.domain.MuscleGroup;
import org.avillar.gymtracker.musclegroup.domain.MuscleGroupDao;
import org.avillar.gymtracker.musclegroup.domain.MuscleSubGroup;
import org.avillar.gymtracker.musclegroup.domain.MuscleSubGroupDao;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ExerciseValidator {

    private static final int LONG_MIN_NAME = 10;
    private static final int LONG_MAX_NAME = 100;
    private static final int LONG_MAX_DESC = 255;
    private static final int LONG_MIN_WEIGHT = 0;
    private static final int LONG_MAX_WEIGHT = 1;

    private final MuscleGroupDao muscleGroupDao;
    private final MuscleSubGroupDao muscleSubGroupDao;

    public ExerciseValidator(MuscleGroupDao muscleGroupDao, MuscleSubGroupDao muscleSubGroupDao) {
        this.muscleGroupDao = muscleGroupDao;
        this.muscleSubGroupDao = muscleSubGroupDao;
    }

    private static void validateLoadType(final ExerciseDto exerciseDto, final Map<String, String> errorMap) {
        final String fieldName = "loadType";
        final LoadTypeEnum loadTypeEnum = exerciseDto.getLoadType();
        if (loadTypeEnum == null || !Arrays.asList(LoadTypeEnum.values()).contains(loadTypeEnum)) {
            errorMap.put(fieldName, "LoadType debe de ser un valor del enumerado");
        }
    }

    private static void validateUnilateral(final ExerciseDto exerciseDto, final Map<String, String> errorMap) {
        final String fieldName = "unilateral";
        final Boolean unilateral = exerciseDto.getUnilateral();
        if (unilateral == null) {
            errorMap.put(fieldName, "Unilateral debe tener un valor");
        }
    }

    private static void validateDescription(final ExerciseDto exerciseDto, final Map<String, String> errorMap) {
        final String fieldName = "description";
        final String description = exerciseDto.getDescription();
        if (StringUtils.isNotEmpty(description) && description.length() > LONG_MAX_DESC) {
            errorMap.put(fieldName, "La longitud de la descripcion del ejercicio es superior a la maxima");
        }
    }

    private static void validateName(final ExerciseDto exerciseDto, final Map<String, String> errorMap) {
        final String fieldName = "name";
        final String name = exerciseDto.getName();
        if (StringUtils.isBlank(name)) {
            errorMap.put(fieldName, "El nombre del ejercicio esta en blanco");
        } else if (name.length() < LONG_MIN_NAME) {
            errorMap.put(fieldName, "La longitud del nombre del ejercicio es inferior a la minima");
        } else if (name.length() > LONG_MAX_NAME) {
            errorMap.put(fieldName, "La longitud del nombre del ejercicio es superior a la maxima");
        }
    }

    public Map<String, String> validate(final ExerciseDto exerciseDto, final Map<String, String> errorMap) {
        validateName(exerciseDto, errorMap);
        validateDescription(exerciseDto, errorMap);
        validateUnilateral(exerciseDto, errorMap);
        validateLoadType(exerciseDto, errorMap);
        validateMuscleGroups(exerciseDto, errorMap);
        validateMuscleSubGroups(exerciseDto, errorMap);
        return errorMap;
    }

    private void validateMuscleGroups(final ExerciseDto exerciseDto, final Map<String, String> errorMap) {
        final String fieldName = "muscleSubGroups";
        final List<MuscleGroupExerciseDto> muscleGroupExerciseDtos = exerciseDto.getMuscleGroupExercises();
        if (CollectionUtils.isEmpty(muscleGroupExerciseDtos)) {
            errorMap.put(fieldName, "La lista de muscleGroupExercises esta vacia");
            return;
        }
        this.validateWeight(muscleGroupExerciseDtos, errorMap);

        final List<MuscleGroupDto> muscleGroupDtos = muscleGroupExerciseDtos.stream()
                .map(MuscleGroupExerciseDto::getMuscleGroup).toList();
        if (CollectionUtils.isEmpty(muscleGroupDtos)) {
            errorMap.put(fieldName, "La lista de muscleGroups esta vacia");
            return;
        }
        final Set<Long> muscleGroupIds = muscleGroupDtos.stream().map(MuscleGroupDto::getId).collect(Collectors.toSet());
        if (muscleGroupIds.size() < muscleGroupDtos.size() ||
                this.muscleGroupDao.findAllById(muscleGroupIds).size() != muscleGroupDtos.size()) {
            errorMap.put(fieldName, "No se han encontrado los muscleSubGroups especificados");
            throw new EntityNotFoundException(MuscleGroup.class, "id", muscleGroupIds.toString());
        }
    }

    private void validateWeight(final List<MuscleGroupExerciseDto> muscleGroupExerciseDtos, final Map<String, String> errorMap) {
        double totalWeight = 0;
        final String fieldName = "weight";
        for (final MuscleGroupExerciseDto muscleGroupExerciseDto : muscleGroupExerciseDtos) {
            final Double weight = muscleGroupExerciseDto.getWeight();
            if (weight == null) {
                errorMap.put(fieldName, "El weight no puede ser nulo");
                return;
            } else if (weight <= LONG_MIN_WEIGHT) {
                errorMap.put(fieldName, "El weight no puede ser <= " + LONG_MIN_WEIGHT);
                return;
            } else if (weight > LONG_MAX_WEIGHT) {
                errorMap.put(fieldName, "El weight no puede ser > " + LONG_MAX_WEIGHT);
                return;
            }
            totalWeight += weight;
        }
        if (totalWeight > LONG_MAX_WEIGHT) {
            errorMap.put(fieldName, "El total weight no puede ser > " + LONG_MAX_WEIGHT);
        } else if (totalWeight < LONG_MAX_WEIGHT) {
            errorMap.put(fieldName, "El total weight no puede ser < " + LONG_MAX_WEIGHT);
        }

    }

    private void validateMuscleSubGroups(final ExerciseDto exerciseDto, final Map<String, String> errorMap) {
        final String fieldName = "muscleSubGroups";
        final List<MuscleSubGroupDto> muscleSubGroupDtos = exerciseDto.getMuscleSubGroups();
        if (CollectionUtils.isEmpty(muscleSubGroupDtos)) {
            return;
        }

        final Set<Long> muscleSubGroupsIds = muscleSubGroupDtos.stream().map(MuscleSubGroupDto::getId).collect(Collectors.toSet());
        if (muscleSubGroupsIds.size() < muscleSubGroupDtos.size() ||
                this.muscleSubGroupDao.findAllById(muscleSubGroupsIds).size() != muscleSubGroupDtos.size()) {
            errorMap.put(fieldName, "No se han encontrado los muscleSubGroups especificados");
            throw new EntityNotFoundException(MuscleSubGroup.class, "id", muscleSubGroupsIds.toString());
        }
    }
}
