package org.avillar.gymtracker;

import org.avillar.gymtracker.dao.ExerciseRepository;
import org.avillar.gymtracker.dao.LoadTypeRepository;
import org.avillar.gymtracker.dao.MuscleGroupRepository;
import org.avillar.gymtracker.dao.MuscleSubGroupRepository;
import org.avillar.gymtracker.model.Exercise;
import org.avillar.gymtracker.model.LoadType;
import org.avillar.gymtracker.model.MuscleGroup;
import org.avillar.gymtracker.model.MuscleSubGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Component
public class DataLoader implements ApplicationRunner {

    private final LoadTypeRepository loadTypeRepository;
    private final MuscleGroupRepository muscleGroupRepository;
    private final MuscleSubGroupRepository muscleSubGroupRepository;
    private final ExerciseRepository exerciseRepository;

    @Autowired
    public DataLoader(LoadTypeRepository loadTypeRepository, MuscleGroupRepository muscleGroupRepository,
                      MuscleSubGroupRepository muscleSubGroupRepository, ExerciseRepository exerciseRepository) {
        this.loadTypeRepository = loadTypeRepository;
        this.muscleGroupRepository = muscleGroupRepository;
        this.muscleSubGroupRepository = muscleSubGroupRepository;
        this.exerciseRepository = exerciseRepository;
    }

    public void run(ApplicationArguments args) {
        final LoadType loadType1 = new LoadType();
        final LoadType loadType2 = new LoadType();
        final LoadType loadType3 = new LoadType();
        final LoadType loadType4 = new LoadType();
        loadType1.setName("bar");
        loadType2.setName("dumbbell");
        loadType3.setName("cable");
        loadType4.setName("bodyweight");
        loadTypeRepository.saveAll(Arrays.asList(loadType1, loadType2, loadType3, loadType4));

        final MuscleGroup muscleGroup1 = new MuscleGroup();
        final MuscleGroup muscleGroup2 = new MuscleGroup();
        final MuscleGroup muscleGroup3 = new MuscleGroup();
        final MuscleGroup muscleGroup4 = new MuscleGroup();
        final MuscleGroup muscleGroup5 = new MuscleGroup();
        muscleGroup1.setName("chest");
        muscleGroup2.setName("back");
        muscleGroup3.setName("shoulders");
        muscleGroup4.setName("biceps");
        muscleGroup5.setName("triceps");
        muscleGroupRepository.saveAll(Arrays.asList(muscleGroup1, muscleGroup2, muscleGroup3, muscleGroup4, muscleGroup5));

        final MuscleSubGroup muscleSubGroup1 = new MuscleSubGroup();
        final MuscleSubGroup muscleSubGroup2 = new MuscleSubGroup();
        final MuscleSubGroup muscleSubGroup3 = new MuscleSubGroup();
        muscleSubGroup1.setName("upper");
        muscleSubGroup2.setName("lower");
        muscleSubGroup3.setName("middle");
        muscleSubGroup1.setMuscleGroup(muscleGroup1);
        muscleSubGroup2.setMuscleGroup(muscleGroup1);
        muscleSubGroup3.setMuscleGroup(muscleGroup1);
        final MuscleSubGroup muscleSubGroup4 = new MuscleSubGroup();
        final MuscleSubGroup muscleSubGroup5 = new MuscleSubGroup();
        muscleSubGroup4.setName("upper");
        muscleSubGroup5.setName("lower");
        muscleSubGroup4.setMuscleGroup(muscleGroup2);
        muscleSubGroup5.setMuscleGroup(muscleGroup2);
        final MuscleSubGroup muscleSubGroup6 = new MuscleSubGroup();
        final MuscleSubGroup muscleSubGroup7 = new MuscleSubGroup();
        final MuscleSubGroup muscleSubGroup8 = new MuscleSubGroup();
        muscleSubGroup6.setName("anterior");
        muscleSubGroup7.setName("lateral");
        muscleSubGroup8.setName("posterior");
        muscleSubGroup6.setMuscleGroup(muscleGroup3);
        muscleSubGroup7.setMuscleGroup(muscleGroup3);
        muscleSubGroup8.setMuscleGroup(muscleGroup3);
        muscleSubGroupRepository.saveAll(Arrays.asList(muscleSubGroup1, muscleSubGroup2, muscleSubGroup3,
                muscleSubGroup4, muscleSubGroup5, muscleSubGroup6, muscleSubGroup7, muscleSubGroup8));

        final Exercise exercise1 = new Exercise();
        final Exercise exercise2 = new Exercise();
        final Exercise exercise3 = new Exercise();
        final Exercise exercise4 = new Exercise();
        final Exercise exercise5 = new Exercise();
        exercise1.setName("dumbbell press");
        exercise2.setName("cru");
        exercise1.setUnilateral(false);
        exercise2.setUnilateral(false);
        exercise1.setMuscleGroups(Set.of(muscleGroup1));
        exercise2.setMuscleGroups(Set.of(muscleGroup1));
        exercise1.setLoadType(loadType2);
        exercise2.setLoadType(loadType1);
        exercise3.setName("lateral raise");
        exercise4.setName("seated press");
        exercise3.setUnilateral(true);
        exercise4.setUnilateral(false);
        exercise3.setLoadType(loadType2);
        exercise4.setLoadType(loadType2);
        exercise3.setMuscleGroups(Set.of(muscleGroup3));
        exercise4.setMuscleGroups(Set.of(muscleGroup3));
        exercise5.setName("seated cable row");
        exercise5.setUnilateral(false);
        exercise5.setMuscleGroups(Set.of(muscleGroup2));
        exercise5.setLoadType(loadType2);
        exerciseRepository.saveAll(Arrays.asList(exercise1, exercise2, exercise3,
                exercise4, exercise5));
    }
}