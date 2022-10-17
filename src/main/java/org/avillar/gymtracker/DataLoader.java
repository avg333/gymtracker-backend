package org.avillar.gymtracker;

import org.avillar.gymtracker.model.dao.*;
import org.avillar.gymtracker.model.entities.*;
import org.avillar.gymtracker.model.enums.ActivityLevelEnum;
import org.avillar.gymtracker.model.enums.GenderEnum;
import org.avillar.gymtracker.model.enums.LoadTypeEnum;
import org.avillar.gymtracker.model.enums.ProgramLevelEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DataLoader implements ApplicationRunner {

    private final MuscleGroupRepository muscleGroupRepository;
    private final MuscleSubGroupRepository muscleSubGroupRepository;
    private final ExerciseRepository exerciseRepository;
    private final ProgramRepository programRepository;
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final SetGroupRepository setGroupRepository;

    @Autowired
    public DataLoader(MuscleGroupRepository muscleGroupRepository, MuscleSubGroupRepository muscleSubGroupRepository, ExerciseRepository exerciseRepository,
                      ProgramRepository programRepository, SessionRepository sessionRepository, UserRepository userRepository, SetGroupRepository setGroupRepository) {
        this.muscleGroupRepository = muscleGroupRepository;
        this.muscleSubGroupRepository = muscleSubGroupRepository;
        this.exerciseRepository = exerciseRepository;
        this.programRepository = programRepository;
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
        this.setGroupRepository = setGroupRepository;
    }

    public void run(ApplicationArguments args) {
        this.createExercises();
        final UserApp user = new UserApp("chema", new BCryptPasswordEncoder().encode("chema69"),
                null, "Chema", "Garcia", "Romero", null,
                GenderEnum.MALE, ActivityLevelEnum.EXTREME, null, null);
        userRepository.save(user);
        this.createPrograms(user);
        this.crearSets();
    }

    private void createExercises() {
        final MuscleGroup chest = new MuscleGroup("chest", null, null, null);
        final MuscleGroup lats = new MuscleGroup("lats", null, null, null);
        final MuscleGroup shoulders = new MuscleGroup("shoulders", null, null, null);
        final MuscleGroup lowerBack = new MuscleGroup("lower back", null, null, null);
        final MuscleGroup biceps = new MuscleGroup("biceps", null, null, null);
        final MuscleGroup triceps = new MuscleGroup("triceps", null, null, null);
        final MuscleGroup abs = new MuscleGroup("abs", null, null, null);
        final MuscleGroup glute = new MuscleGroup("glute", null, null, null);
        final MuscleGroup quadriceps = new MuscleGroup("quadriceps", null, null, null);
        final MuscleGroup hamstrings = new MuscleGroup("hamstrings", null, null, null);
        final MuscleGroup calves = new MuscleGroup("calves", null, null, null);
        muscleGroupRepository.saveAll(Arrays.asList(chest, lats, shoulders, lowerBack, biceps, triceps, abs, glute,
                quadriceps, hamstrings, calves));

        final MuscleSubGroup chestUpper = new MuscleSubGroup("upper", null, chest, null);
        final MuscleSubGroup chestMiddle = new MuscleSubGroup("middle", null, chest, null);
        final MuscleSubGroup chestLower = new MuscleSubGroup("lower", null, chest, null);
        final MuscleSubGroup tricepsLateral = new MuscleSubGroup("lateral", null, triceps, null);
        final MuscleSubGroup tricepsLong = new MuscleSubGroup("long", null, triceps, null);
        final MuscleSubGroup tricepsMedial = new MuscleSubGroup("medial", null, triceps, null);
        final MuscleSubGroup shoulderAnterior = new MuscleSubGroup("anterior", null, shoulders, null);
        final MuscleSubGroup shoulderLateral = new MuscleSubGroup("lateral", null, shoulders, null);
        final MuscleSubGroup shoulderPosterior = new MuscleSubGroup("posterior", null, shoulders, null);
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

    private void createPrograms(UserApp user) {
        final Program pushPullLegs = new Program("Push-Pull-Legs", "Push pull legs frec2", null, ProgramLevelEnum.MEDIUM, user, null);
        final Program fullBody = new Program("Full body", "Full body frec1", null, ProgramLevelEnum.EASY, user, null);
        final Program weider = new Program("Weider", "Weider frec1", null, ProgramLevelEnum.HARD, user, null);
        programRepository.saveAll(Arrays.asList(pushPullLegs, fullBody, weider));

        final Session push = new Session("Push", null, 0, pushPullLegs, null);
        final Session pull = new Session("Pull", null, 1, pushPullLegs, null);
        final Session legs = new Session("Legs", null, 2, pushPullLegs, null);
        final Session reversePush = new Session("Reverse push", null, 3, pushPullLegs, null);
        final Session reversePull = new Session("Reverse pull", null, 4, pushPullLegs, null);
        final List<Session> sessions = new ArrayList<>(Arrays.asList(push, pull, legs, reversePush, reversePull));

        final Session upperFirst = new Session("Upper first", null, 0, fullBody, null);
        final Session lowerFirst = new Session("Lower first", null, 1, fullBody, null);
        final Session upperSecond = new Session("Upper second", null, 2, fullBody, null);
        final Session lowerSecond = new Session("Lower second", null, 3, fullBody, null);
        sessions.addAll(Arrays.asList(upperFirst, lowerFirst, upperSecond, lowerSecond));

        final Session chest = new Session("Chest", null, 0, weider, null);
        final Session back = new Session("Back", null, 1, weider, null);
        final Session shoulder = new Session("Shoulder", null, 2, weider, null);
        final Session legs2 = new Session("Legs", null, 3, weider, null);
        final Session arms = new Session("Arms", null, 4, weider, null);
        sessions.addAll(Arrays.asList(chest, back, shoulder, legs2, arms));

        sessionRepository.saveAll(sessions);
    }

    private void crearSets(){
        final List<Exercise> exercises = this.exerciseRepository.findAll();
        final List<Session> sessions = this.sessionRepository.findAll();
        final List<SetGroup> setGroups = new ArrayList<>();

        for(final Session session: sessions){
            for (int i = 0; i < 5; i++) {
                int rnd = new Random().nextInt(exercises.size() - 1);
                final SetGroup setGroup = new SetGroup("Descripcion" + rnd, i, exercises.get(rnd), session, null);
                setGroups.add(setGroup);
            }
        }
        this.setGroupRepository.saveAll(setGroups);
    }
}