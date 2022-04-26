package org.avillar.gymtracker;

import org.avillar.gymtracker.model.dao.*;
import org.avillar.gymtracker.model.entities.*;
import org.avillar.gymtracker.model.enums.LoadTypeEnum;
import org.avillar.gymtracker.model.enums.ProgramLevelEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final ProgramRepository programRepository;
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    @Autowired
    public DataLoader(MuscleGroupRepository muscleGroupRepository, MuscleSubGroupRepository muscleSubGroupRepository, ExerciseRepository exerciseRepository,
                      ProgramRepository programRepository, SessionRepository sessionRepository, UserRepository userRepository) {
        this.muscleGroupRepository = muscleGroupRepository;
        this.muscleSubGroupRepository = muscleSubGroupRepository;
        this.exerciseRepository = exerciseRepository;
        this.programRepository = programRepository;
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

    public void run(ApplicationArguments args) {
        this.createExercises();
        final UserApp user = new UserApp();
        final String pass = "chema69";
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setUsername("chema");
        user.setName("Chema");
        user.setLastNameFirst("Garcia");
        user.setPassword(passwordEncoder.encode(pass));
        userRepository.save(user);
        this.createPrograms(user);
    }

    private void createExercises() {
        final MuscleGroup chest = new MuscleGroup(null, "chest", null, null, null);
        final MuscleGroup lats = new MuscleGroup(null, "lats", null, null, null);
        final MuscleGroup shoulders = new MuscleGroup(null, "shoulders", null, null, null);
        final MuscleGroup lowerBack = new MuscleGroup(null, "lower back", null, null, null);
        final MuscleGroup biceps = new MuscleGroup(null, "biceps", null, null, null);
        final MuscleGroup triceps = new MuscleGroup(null, "triceps", null, null, null);
        final MuscleGroup abs = new MuscleGroup(null, "abs", null, null, null);
        final MuscleGroup glute = new MuscleGroup(null, "glute", null, null, null);
        final MuscleGroup quadriceps = new MuscleGroup(null, "quadriceps", null, null, null);
        final MuscleGroup hamstrings = new MuscleGroup(null, "hamstrings", null, null, null);
        final MuscleGroup calves = new MuscleGroup(null, "calves", null, null, null);
        muscleGroupRepository.saveAll(Arrays.asList(chest, lats, shoulders, lowerBack, biceps, triceps, abs, glute,
                quadriceps, hamstrings, calves));

        final MuscleSubGroup chestUpper = new MuscleSubGroup(null, "upper", null, chest, null);
        final MuscleSubGroup chestMiddle = new MuscleSubGroup(null, "middle", null, chest, null);
        final MuscleSubGroup chestLower = new MuscleSubGroup(null, "lower", null, chest, null);
        final MuscleSubGroup tricepsLateral = new MuscleSubGroup(null, "lateral", null, triceps, null);
        final MuscleSubGroup tricepsLong = new MuscleSubGroup(null, "long", null, triceps, null);
        final MuscleSubGroup tricepsMedial = new MuscleSubGroup(null, "medial", null, triceps, null);
        final MuscleSubGroup shoulderAnterior = new MuscleSubGroup(null, "anterior", null, shoulders, null);
        final MuscleSubGroup shoulderLateral = new MuscleSubGroup(null, "lateral", null, shoulders, null);
        final MuscleSubGroup shoulderPosterior = new MuscleSubGroup(null, "posterior", null, shoulders, null);
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

    public void createPrograms(UserApp user) {
        final Program pushPullLegs = new Program(null, "Push-Pull-Legs", "Push pull legs frec2", null, ProgramLevelEnum.MEDIUM, null, user);
        final Program fullBody = new Program(null, "Full body", "Full body frec1", null, ProgramLevelEnum.EASY, null, user);
        final Program weider = new Program(null, "Weider", "Weider frec1", null, ProgramLevelEnum.HARD, null, user);
        programRepository.saveAll(Arrays.asList(pushPullLegs, fullBody, weider));

        final Session push = new Session(null, "Push", null, 1, pushPullLegs, null);
        final Session pull = new Session(null, "Pull", null, 2, pushPullLegs, null);
        final Session legs = new Session(null, "Legs", null, 3, pushPullLegs, null);
        final Session reversePush = new Session(null, "Reverse push", null, 4, pushPullLegs, null);
        final Session reversePull = new Session(null, "Reverse pull", null, 5, pushPullLegs, null);
        final List<Session> sessions = new ArrayList<>(Arrays.asList(push, pull, legs, reversePush, reversePull));

        final Session upperFirst = new Session(null, "Upper first", null, 1, fullBody, null);
        final Session lowerFirst = new Session(null, "Lower first", null, 2, fullBody, null);
        final Session upperSecond = new Session(null, "Upper second", null, 3, fullBody, null);
        final Session lowerSecond = new Session(null, "Lower second", null, 4, fullBody, null);
        sessions.addAll(Arrays.asList(upperFirst, lowerFirst, upperSecond, lowerSecond));

        final Session chest = new Session(null, "Chest", null, 1, weider, null);
        final Session back = new Session(null, "Back", null, 2, weider, null);
        final Session shoulder = new Session(null, "Shoulder", null, 3, weider, null);
        final Session legs2 = new Session(null, "Legs", null, 4, weider, null);
        final Session arms = new Session(null, "Arms", null, 5, weider, null);
        sessions.addAll(Arrays.asList(chest, back, shoulder, legs2, arms));

        sessionRepository.saveAll(sessions);
    }
}