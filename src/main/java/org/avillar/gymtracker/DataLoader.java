package org.avillar.gymtracker;

import org.avillar.gymtracker.dao.ExerciseRepository;
import org.avillar.gymtracker.dao.MuscleGroupRepository;
import org.avillar.gymtracker.dao.MuscleSubGroupRepository;
import org.avillar.gymtracker.model.Exercise;
import org.avillar.gymtracker.model.LoadTypeEnum;
import org.avillar.gymtracker.model.MuscleGroup;
import org.avillar.gymtracker.model.MuscleSubGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {


    private final MuscleGroupRepository muscleGroupRepository;
    private final MuscleSubGroupRepository muscleSubGroupRepository;
    private final ExerciseRepository exerciseRepository;

    @Autowired
    public DataLoader(MuscleGroupRepository muscleGroupRepository, MuscleSubGroupRepository muscleSubGroupRepository, ExerciseRepository exerciseRepository) {
        this.muscleGroupRepository = muscleGroupRepository;
        this.muscleSubGroupRepository = muscleSubGroupRepository;
        this.exerciseRepository = exerciseRepository;
    }

    public void run(ApplicationArguments args) {
        final MuscleGroup chest = new MuscleGroup();
        final MuscleGroup lats = new MuscleGroup();
        final MuscleGroup shoulders = new MuscleGroup();
        final MuscleGroup lowerBack = new MuscleGroup();
        final MuscleGroup biceps = new MuscleGroup();
        final MuscleGroup triceps = new MuscleGroup();
        final MuscleGroup abs = new MuscleGroup();
        final MuscleGroup glute = new MuscleGroup();
        final MuscleGroup quadriceps = new MuscleGroup();
        final MuscleGroup hamstrings = new MuscleGroup();
        final MuscleGroup calves = new MuscleGroup();
        chest.setName("chest");
        lats.setName("lats");
        shoulders.setName("shoulders");
        lowerBack.setName("lower back");
        biceps.setName("biceps");
        triceps.setName("triceps");
        abs.setName("abs");
        glute.setName("glute");
        quadriceps.setName("quadriceps");
        hamstrings.setName("hamstrings");
        calves.setName("calves");
        muscleGroupRepository.saveAll(Arrays.asList(chest, lats, shoulders, lowerBack, biceps, triceps, abs, glute,
                quadriceps, hamstrings, calves));

        final MuscleSubGroup chestUpper = new MuscleSubGroup();
        final MuscleSubGroup chestLower = new MuscleSubGroup();
        final MuscleSubGroup chestMiddle = new MuscleSubGroup();
        chestUpper.setName("upper");
        chestLower.setName("lower");
        chestMiddle.setName("middle");
        chestUpper.setMuscleGroup(chest);
        chestLower.setMuscleGroup(chest);
        chestMiddle.setMuscleGroup(chest);
        final MuscleSubGroup tricepsLateral = new MuscleSubGroup();
        final MuscleSubGroup tricepsLong = new MuscleSubGroup();
        final MuscleSubGroup tricepsMedial = new MuscleSubGroup();
        tricepsLateral.setName("lateral");
        tricepsLong.setName("long");
        tricepsMedial.setName("medial");
        tricepsLateral.setMuscleGroup(triceps);
        tricepsLong.setMuscleGroup(triceps);
        tricepsMedial.setMuscleGroup(triceps);
        final MuscleSubGroup shoulderAnterior = new MuscleSubGroup();
        final MuscleSubGroup shoulderLateral = new MuscleSubGroup();
        final MuscleSubGroup shoulderPosterior = new MuscleSubGroup();
        shoulderAnterior.setName("anterior");
        shoulderLateral.setName("lateral");
        shoulderPosterior.setName("posterior");
        shoulderAnterior.setMuscleGroup(shoulders);
        shoulderLateral.setMuscleGroup(shoulders);
        shoulderPosterior.setMuscleGroup(shoulders);
        muscleSubGroupRepository.saveAll(Arrays.asList(chestUpper, chestLower, chestMiddle, tricepsLateral, tricepsLong,
                tricepsMedial, shoulderAnterior, shoulderLateral, shoulderPosterior));

        final List<Exercise> exercises = new ArrayList<>();
        exercises.add(new Exercise("press con mancuernas", null, false, LoadTypeEnum.DUMBBELL, new HashSet<>(List.of(chest)), null));
        exercises.add(new Exercise("press con mancuernas inclinado", null, false, LoadTypeEnum.DUMBBELL, new HashSet<>(Arrays.asList(chest, shoulders)), new HashSet<>(List.of(chestUpper))));
        exercises.add(new Exercise("press banca", null, false, LoadTypeEnum.BAR, new HashSet<>(List.of(chest)), null));
        exercises.add(new Exercise("press banca inclinado", null, false, LoadTypeEnum.BAR, new HashSet<>(Arrays.asList(chest, shoulders)), new HashSet<>(List.of(chestUpper))));
        exercises.add(new Exercise("press en multipower", null, false, LoadTypeEnum.MULTIPOWER, new HashSet<>(List.of(chest)), null));
        exercises.add(new Exercise("press en multipower inclinado", null, false, LoadTypeEnum.MULTIPOWER, new HashSet<>(Arrays.asList(chest, shoulders)), new HashSet<>(List.of(chestUpper))));
        exercises.add(new Exercise("cruces de poleas", null, false, LoadTypeEnum.CABLE, new HashSet<>(List.of(chest)), new HashSet<>(List.of(chestMiddle))));
        exercises.add(new Exercise("pec deck", null, false, LoadTypeEnum.MACHINE, new HashSet<>(List.of(chest)), new HashSet<>(List.of(chestMiddle))));
        exercises.add(new Exercise("flexiones", null, false, LoadTypeEnum.BODYWEIGHT, new HashSet<>(Arrays.asList(chest, shoulders)), new HashSet<>(List.of(chestLower))));
        exercises.add(new Exercise("fondos", null, false, LoadTypeEnum.BODYWEIGHT, new HashSet<>(Arrays.asList(chest, shoulders)), new HashSet<>(List.of(chestLower))));

        exercises.add(new Exercise("press militar", null, false, LoadTypeEnum.BAR, new HashSet<>(List.of(shoulders)), new HashSet<>(List.of(shoulderAnterior))));
        exercises.add(new Exercise("press sentado con mancuernas", null, false, LoadTypeEnum.DUMBBELL, new HashSet<>(List.of(shoulders)), new HashSet<>(List.of(shoulderAnterior))));
        exercises.add(new Exercise("elevaciones laterales con mancuernas", null, false, LoadTypeEnum.DUMBBELL, new HashSet<>(List.of(shoulders)), new HashSet<>(List.of(shoulderLateral))));
        exercises.add(new Exercise("elevaciones laterales con cable", null, false, LoadTypeEnum.CABLE, new HashSet<>(List.of(shoulders)), new HashSet<>(List.of(shoulderLateral))));
        exercises.add(new Exercise("pajaros con mancuernas", null, false, LoadTypeEnum.DUMBBELL, new HashSet<>(List.of(shoulders)), new HashSet<>(List.of(shoulderPosterior))));
        exercises.add(new Exercise("reverse pec deck", null, false, LoadTypeEnum.CABLE, new HashSet<>(List.of(shoulders)), new HashSet<>(List.of(shoulderPosterior))));


        exerciseRepository.saveAll(exercises);
    }
}