package org.avillar.gymtracker.utils.domain;

import org.avillar.gymtracker.enums.domain.ActivityLevelEnum;
import org.avillar.gymtracker.enums.domain.GenderEnum;
import org.avillar.gymtracker.enums.domain.LoadTypeEnum;
import org.avillar.gymtracker.enums.domain.ProgramLevelEnum;
import org.avillar.gymtracker.exercise.domain.Exercise;
import org.avillar.gymtracker.exercise.domain.ExerciseDao;
import org.avillar.gymtracker.measure.domain.Measure;
import org.avillar.gymtracker.measure.domain.MeasureDao;
import org.avillar.gymtracker.musclegroup.domain.*;
import org.avillar.gymtracker.program.domain.Program;
import org.avillar.gymtracker.program.domain.ProgramDao;
import org.avillar.gymtracker.session.domain.Session;
import org.avillar.gymtracker.session.domain.SessionDao;
import org.avillar.gymtracker.set.domain.Set;
import org.avillar.gymtracker.set.domain.SetDao;
import org.avillar.gymtracker.setgroup.domain.SetGroup;
import org.avillar.gymtracker.setgroup.domain.SetGroupDao;
import org.avillar.gymtracker.user.domain.UserApp;
import org.avillar.gymtracker.user.domain.UserDao;
import org.avillar.gymtracker.workout.domain.Workout;
import org.avillar.gymtracker.workout.domain.WorkoutDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.*;

@Component
public class DataLoader implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataLoader.class);


    private final Random random = new Random();

    private final MuscleGroupExerciseDao muscleGroupExerciseDao;
    private final MuscleGroupDao muscleGroupDao;
    private final MuscleSupGroupDao muscleSupGroupDao;
    private final MuscleSubGroupDao muscleSubGroupDao;
    private final ExerciseDao exerciseDao;
    private final ProgramDao programDao;
    private final SessionDao sessionDao;
    private final UserDao userDao;
    private final SetGroupDao setGroupDao;
    private final SetDao setDao;
    private final MeasureDao measureDao;
    private final WorkoutDao workoutDao;

    @Autowired
    public DataLoader(MeasureDao measureDao, SetDao setDao, MuscleGroupDao muscleGroupDao, MuscleSupGroupDao muscleSupGroupDao,
                      SetGroupDao setGroupDao, MuscleGroupExerciseDao muscleGroupExerciseDao,
                      ExerciseDao exerciseDao, UserDao userDao, ProgramDao programDao, SessionDao sessionDao,
                      MuscleSubGroupDao muscleSubGroupDao, WorkoutDao workoutDao) {
        this.muscleGroupExerciseDao = muscleGroupExerciseDao;
        this.muscleGroupDao = muscleGroupDao;
        this.muscleSupGroupDao = muscleSupGroupDao;
        this.muscleSubGroupDao = muscleSubGroupDao;
        this.exerciseDao = exerciseDao;
        this.programDao = programDao;
        this.sessionDao = sessionDao;
        this.userDao = userDao;
        this.setGroupDao = setGroupDao;
        this.setDao = setDao;
        this.measureDao = measureDao;
        this.workoutDao = workoutDao;
    }

    public void run(ApplicationArguments args) {
        if (!userDao.findAll().isEmpty()) {
            LOGGER.info("La base de datos ya tiene datos. No se insertaran mas");
            return;
        }
        final UserApp user = this.userDao.save(new UserApp(
                "chema", new BCryptPasswordEncoder().encode("chema69"), null, "Chema",
                "Garcia", "Romero", null, GenderEnum.MALE, ActivityLevelEnum.EXTREME,
                null, null, null, null,null));
        this.userDao.save(new UserApp(
                "alex", new BCryptPasswordEncoder().encode("alex69"), null, "Alex",
                "Garcia", "Fernandez", null, GenderEnum.FEMALE, ActivityLevelEnum.MODERATE,
                null, null, null, null,null));
        LOGGER.info("Creados dos usuarios");

        this.createMeasures(user);
        this.createExercisesWithMuscleGroups();

        this.createVTapper2(user);
        this.createPrograms(user);
        this.createWorkouts(user);
        this.createSets();
    }

    private void createMeasures(final UserApp userApp) {
        final List<Measure> measures = new ArrayList<>();
        final String dt = "2022-10-10";  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }
        for (int i = 0; 100 > i; i++) {
            c.add(Calendar.DATE, 1);
            final Measure measure = new Measure(
                    c.getTime(), null, 185.0, random.nextDouble(80, 90),
                    random.nextDouble(10, 15), userApp, null);
            measures.add(measure);
        }
        this.measureDao.saveAll(measures);

    }

    private void createExercisesWithMuscleGroups() {
        final MuscleSupGroup chestSupGroup = new MuscleSupGroup("chest", null, null);
        final MuscleSupGroup backSupGroup = new MuscleSupGroup("back", null, null);
        final MuscleSupGroup shouldersSupGroup = new MuscleSupGroup("shoulders", null, null);
        final MuscleSupGroup armsSupGroup = new MuscleSupGroup("arms", null, null);
        final MuscleSupGroup coreSupGroup = new MuscleSupGroup("core", null, null);
        final MuscleSupGroup legsSupGroup = new MuscleSupGroup("legs", null, null);
        this.muscleSupGroupDao.saveAll(Arrays.asList(chestSupGroup, backSupGroup, shouldersSupGroup, armsSupGroup,
                coreSupGroup, legsSupGroup));

        // Chest
        final MuscleGroup chest = new MuscleGroup("chest", null, new HashSet<>(List.of(chestSupGroup)), null, null);
        // Back
        final MuscleGroup lats = new MuscleGroup("lats", null, new HashSet<>(List.of(backSupGroup)), null, null);
        final MuscleGroup trapezius = new MuscleGroup("trapezius", null, new HashSet<>(List.of(backSupGroup)), null, null);
        // Shoulder
        final MuscleGroup shoulderAnterior = new MuscleGroup("shoulder anterior", null, new HashSet<>(Arrays.asList(shouldersSupGroup, chestSupGroup)), null, null);
        final MuscleGroup shoulderLateral = new MuscleGroup("shoulders lateral", null, new HashSet<>(List.of(shouldersSupGroup)), null, null);
        final MuscleGroup shoulderPosterior = new MuscleGroup("shoulders posterior", null, new HashSet<>(Arrays.asList(shouldersSupGroup, backSupGroup)), null, null);
        // Arms
        final MuscleGroup forearms = new MuscleGroup("forearms", null, new HashSet<>(List.of(armsSupGroup)), null, null);
        final MuscleGroup biceps = new MuscleGroup("biceps", null, new HashSet<>(List.of(armsSupGroup)), null, null);
        final MuscleGroup triceps = new MuscleGroup("triceps", null, new HashSet<>(List.of(armsSupGroup)), null, null);
        // Core
        final MuscleGroup abs = new MuscleGroup("abs", null, new HashSet<>(List.of(coreSupGroup)), null, null);
        final MuscleGroup lowerBack = new MuscleGroup("lower back", null, new HashSet<>(List.of(coreSupGroup)), null, null);
        // Legs
        final MuscleGroup quadriceps = new MuscleGroup("quadriceps", null, new HashSet<>(List.of(legsSupGroup)), null, null);
        final MuscleGroup hamstrings = new MuscleGroup("hamstrings", null, new HashSet<>(List.of(legsSupGroup)), null, null);
        final MuscleGroup glute = new MuscleGroup("glute", null, new HashSet<>(List.of(legsSupGroup)), null, null);
        final MuscleGroup calves = new MuscleGroup("calves", null, new HashSet<>(List.of(legsSupGroup)), null, null);
        muscleGroupDao.saveAll(Arrays.asList(chest, lats, trapezius,
                shoulderAnterior, shoulderLateral, shoulderPosterior,
                forearms, biceps, triceps,
                abs, lowerBack,
                quadriceps, hamstrings, glute, calves));

        // Chest
        final MuscleSubGroup chestUpper = new MuscleSubGroup("upper", null, chest, null);
        final MuscleSubGroup chestMiddle = new MuscleSubGroup("middle", null, chest, null);
        final MuscleSubGroup chestLower = new MuscleSubGroup("lower", null, chest, null);
        // Back
        // Traps
        final MuscleSubGroup trapsUpper = new MuscleSubGroup("upper", null, trapezius, null);
        final MuscleSubGroup trapsMiddle = new MuscleSubGroup("middle", null, trapezius, null);
        final MuscleSubGroup trapsLower = new MuscleSubGroup("lower", null, trapezius, null);

        // Forearms
        // Biceps
        // Triceps
        final MuscleSubGroup tricepsLateral = new MuscleSubGroup("lateral", null, triceps, null);
        final MuscleSubGroup tricepsLong = new MuscleSubGroup("long", null, triceps, null);
        final MuscleSubGroup tricepsMedial = new MuscleSubGroup("medial", null, triceps, null);

        // Abs
        // Lowerback

        // Cuads
        // Hams
        // Glutes
        final MuscleSubGroup gluteMaximus = new MuscleSubGroup("maximus", null, glute, null);
        final MuscleSubGroup gluteMedius = new MuscleSubGroup("medius", null, glute, null);
        final MuscleSubGroup gluteMinimus = new MuscleSubGroup("minimus", null, glute, null);
        // Calves
        final MuscleSubGroup calvesSoleus = new MuscleSubGroup("soleus", null, calves, null);
        final MuscleSubGroup calvesGastrocnemius = new MuscleSubGroup("gastrocnemius", null, calves, null);


        muscleSubGroupDao.saveAll(Arrays.asList(
                chestUpper, chestLower, chestMiddle,
                trapsUpper, trapsMiddle, trapsLower,
                tricepsLateral, tricepsLong, tricepsMedial,
                gluteMaximus, gluteMedius, gluteMinimus,
                calvesSoleus, calvesGastrocnemius));

        // Chest
        final Exercise exCh1 = new Exercise("hammer strength press", null, false, LoadTypeEnum.MACHINE, new HashSet<>(List.of(chestUpper)));
        final Exercise exCh2 = new Exercise("cable crossover", null, false, LoadTypeEnum.CABLE, new HashSet<>(List.of(chestMiddle)));
        final Exercise exCh3 = new Exercise("incline smith machine press", null, false, LoadTypeEnum.MULTIPOWER, new HashSet<>(List.of(chestUpper)));
        final Exercise exCh4 = new Exercise("incline dumbbell press", null, false, LoadTypeEnum.DUMBBELL, new HashSet<>(List.of(chestUpper)));
        final Exercise exCh5 = new Exercise("pec deck", null, false, LoadTypeEnum.MACHINE, new HashSet<>(List.of(chestMiddle)));
        final Exercise exCh6 = new Exercise("bench press", null, false, LoadTypeEnum.BAR, null);
        final Exercise exCh7 = new Exercise("push up", null, false, LoadTypeEnum.BODYWEIGHT, new HashSet<>(List.of(chestLower)));

        // Back
        final Exercise exBa1 = new Exercise("pulldown machine", null, false, LoadTypeEnum.MACHINE, null);
        final Exercise exBa2 = new Exercise("cable seated row", null, false, LoadTypeEnum.CABLE, null);
        final Exercise exBa3 = new Exercise("incline dumbbell row", null, false, LoadTypeEnum.DUMBBELL, null);
        final Exercise exBa4 = new Exercise("machine row", null, false, LoadTypeEnum.MACHINE, null);
        final Exercise exBa5 = new Exercise("dumbbell single arm bent over row", null, true, LoadTypeEnum.DUMBBELL, null);
        final Exercise exBa6 = new Exercise("neutral grip lat pulldown", null, false, LoadTypeEnum.CABLE, null);
        final Exercise exBa7 = new Exercise("unilateral cable row", null, true, LoadTypeEnum.CABLE, null);
        // Shoulders
        final Exercise exSh1 = new Exercise("dumbbell seated press", null, false, LoadTypeEnum.DUMBBELL, null);
        final Exercise exSh2 = new Exercise("dumbbell smith machine seated press", null, false, LoadTypeEnum.MULTIPOWER, null);
        final Exercise exSh3 = new Exercise("machine lateral raise", null, false, LoadTypeEnum.MACHINE, null);
        final Exercise exSh4 = new Exercise("dumbbell lateral raise", null, false, LoadTypeEnum.DUMBBELL, null);
        final Exercise exSh5 = new Exercise("cable lateral raise", null, false, LoadTypeEnum.CABLE, null);
        final Exercise exSh6 = new Exercise("incline lateral raise", null, true, LoadTypeEnum.DUMBBELL, null);
        final Exercise exSh7 = new Exercise("shoulder reverse fly", null, false, LoadTypeEnum.MACHINE, null);
        final Exercise exSh8 = new Exercise("dumbbell bent over shoulder reverse fly", null, false, LoadTypeEnum.DUMBBELL, null);
        final Exercise exSh9 = new Exercise("cable reverse fly", null, false, LoadTypeEnum.CABLE, null);

        // Triceps
        final Exercise exTr1 = new Exercise("cable overhead tricep extension", null, false, LoadTypeEnum.CABLE, new HashSet<>(List.of(tricepsLong)));
        final Exercise exTr2 = new Exercise("jm press in multipower", null, false, LoadTypeEnum.MULTIPOWER, new HashSet<>(List.of(tricepsMedial)));
        final Exercise exTr3 = new Exercise("cable tricep kickback", null, false, LoadTypeEnum.CABLE, new HashSet<>(List.of(tricepsLateral)));
        final Exercise exTr4 = new Exercise("cable tricep extension", null, false, LoadTypeEnum.CABLE, new HashSet<>(List.of(tricepsMedial)));
        final Exercise exTr5 = new Exercise("dumbbell single arm tricep extension", null, true, LoadTypeEnum.CABLE, new HashSet<>(List.of(tricepsLong)));
        // Biceps
        final Exercise exBi1 = new Exercise("machine preacher curl", null, false, LoadTypeEnum.MACHINE, null);
        final Exercise exBi2 = new Exercise("dumbbell curl", null, false, LoadTypeEnum.DUMBBELL, null);
        final Exercise exBi3 = new Exercise("dumbbell hammer curl", null, false, LoadTypeEnum.DUMBBELL, null);
        final Exercise exBi4 = new Exercise("cable curl", null, false, LoadTypeEnum.CABLE, null);
        final Exercise exBi5 = new Exercise("barbell curl", null, false, LoadTypeEnum.BAR, null);
        final Exercise exBi6 = new Exercise("barbell preacher curl", null, false, LoadTypeEnum.BAR, null);

        exerciseDao.saveAll(
                Arrays.asList(exCh1, exCh2, exCh3, exCh4, exCh5, exCh6, exCh7,
                        exBa1, exBa2, exBa3, exBa4, exBa5, exBa6, exBa7,
                        exSh1, exSh2, exSh3, exSh4, exSh5, exSh6, exSh7, exSh8, exSh9,
                        exTr1, exTr2, exTr3, exTr4, exTr5,
                        exBi1, exBi2, exBi3, exBi4, exBi5, exBi6));

        final List<MuscleGroupExercise> muscleGroupExercises = new ArrayList<>();

        for (final Exercise exercise : Arrays.asList(exCh1, exCh2, exCh3, exCh4, exCh5, exCh6, exCh7)) {
            muscleGroupExercises.add(new MuscleGroupExercise(chest, exercise, 1.0));
        }
        for (final Exercise exercise : Arrays.asList(exBa1, exBa2, exBa3, exBa4, exBa5, exBa6, exBa7)) {
            muscleGroupExercises.add(new MuscleGroupExercise(lats, exercise, 1.0));
        }
        for (final Exercise exercise : Arrays.asList(exSh1, exSh2)) {
            muscleGroupExercises.add(new MuscleGroupExercise(shoulderAnterior, exercise, 1.0));
        }
        for (final Exercise exercise : Arrays.asList(exSh3, exSh4, exSh5, exSh6)) {
            muscleGroupExercises.add(new MuscleGroupExercise(shoulderLateral, exercise, 1.0));
        }
        for (final Exercise exercise : Arrays.asList(exSh3, exSh4, exSh5, exSh6)) {
            muscleGroupExercises.add(new MuscleGroupExercise(shoulderPosterior, exercise, 1.0));
        }
        for (final Exercise exercise : Arrays.asList(exTr1, exTr2, exTr3, exTr4, exTr5)) {
            muscleGroupExercises.add(new MuscleGroupExercise(triceps, exercise, 1.0));
        }
        for (final Exercise exercise : Arrays.asList(exBi1, exBi2, exBi3, exBi4, exBi5, exBi6)) {
            muscleGroupExercises.add(new MuscleGroupExercise(biceps, exercise, 1.0));
        }

        muscleGroupExercises.add(new MuscleGroupExercise(shoulderPosterior, exBa3, 0.33));

        muscleGroupExerciseDao.saveAll(muscleGroupExercises);

    }

    private void createPrograms(final UserApp userApp) {
        final String desc1 = "Desc1";
        final String desc2 = "Desc2";

        final Program pushPullLegs = new Program("Push-Pull-Legs", "Push pull legs frec2", null, ProgramLevelEnum.MEDIUM, userApp, null, null);
        final Program fullBody = new Program("Full body", "Full body frec1", null, ProgramLevelEnum.EASY, userApp, null, null);
        final Program weider = new Program("Weider", "Weider frec1", null, ProgramLevelEnum.HARD, userApp, null, null);
        final Program relleno1 = new Program("Relleno1", desc1, null, ProgramLevelEnum.HARD, userApp, null, null);
        final Program relleno2 = new Program("Relleno2", desc2, null, ProgramLevelEnum.HARD, userApp, null, null);
        final Program relleno3 = new Program("Relleno2", desc2, null, ProgramLevelEnum.HARD, userApp, null, null);
        final Program relleno4 = new Program("relleno4", desc1, null, ProgramLevelEnum.HARD, userApp, null, null);
        final Program relleno5 = new Program("relleno5", desc2, null, ProgramLevelEnum.HARD, userApp, null, null);
        final Program relleno6 = new Program("relleno6", desc2, null, ProgramLevelEnum.HARD, userApp, null, null);
        final Program relleno7 = new Program("relleno7", desc1, null, ProgramLevelEnum.HARD, userApp, null, null);
        final Program relleno8 = new Program("relleno8", desc2, null, ProgramLevelEnum.HARD, userApp, null, null);
        final Program relleno9 = new Program("Relleno9", desc2, null, ProgramLevelEnum.HARD, userApp, null, null);


        programDao.saveAll(Arrays.asList(pushPullLegs, fullBody, weider, relleno1, relleno2, relleno3, relleno4, relleno5, relleno6, relleno7, relleno8, relleno9));

        final Session push = new Session("Push", null, DayOfWeek.MONDAY, pushPullLegs, null);
        final Session pull = new Session("Pull", null, DayOfWeek.TUESDAY, pushPullLegs, null);
        final Session legs = new Session("Legs", null, DayOfWeek.WEDNESDAY, pushPullLegs, null);
        final Session reversePush = new Session("Reverse push", null, DayOfWeek.THURSDAY, pushPullLegs, null);
        final Session reversePull = new Session("Reverse pull", null, DayOfWeek.SATURDAY, pushPullLegs, null);
        push.setListOrder(0);
        pull.setListOrder(1);
        legs.setListOrder(2);
        reversePush.setListOrder(3);
        reversePull.setListOrder(4);
        final List<Session> sessions = new ArrayList<>(Arrays.asList(push, pull, legs, reversePush, reversePull));

        final Session upperFirst = new Session("Upper first", null, DayOfWeek.MONDAY, fullBody, null);
        final Session lowerFirst = new Session("Lower first", null, DayOfWeek.TUESDAY, fullBody, null);
        final Session upperSecond = new Session("Upper second", null, DayOfWeek.THURSDAY, fullBody, null);
        final Session lowerSecond = new Session("Lower second", null, DayOfWeek.FRIDAY, fullBody, null);
        upperFirst.setListOrder(0);
        lowerFirst.setListOrder(1);
        upperSecond.setListOrder(2);
        lowerSecond.setListOrder(3);
        sessions.addAll(Arrays.asList(upperFirst, lowerFirst, upperSecond, lowerSecond));

        final Session chest = new Session("Chest", null, DayOfWeek.MONDAY, weider, null);
        final Session back = new Session("Back", null, DayOfWeek.TUESDAY, weider, null);
        final Session shoulder = new Session("Shoulder", null, DayOfWeek.WEDNESDAY, weider, null);
        final Session legs2 = new Session("Legs", null, DayOfWeek.THURSDAY, weider, null);
        final Session arms = new Session("Arms", null, DayOfWeek.FRIDAY, weider, null);
        chest.setListOrder(0);
        back.setListOrder(1);
        shoulder.setListOrder(2);
        legs2.setListOrder(3);
        arms.setListOrder(4);
        sessions.addAll(Arrays.asList(chest, back, shoulder, legs2, arms));

        sessionDao.saveAll(sessions);
    }

    private void createVTapper2(final UserApp userApp) {
        final Program vTapper2 = new Program("V-TAPER 2.0",
                "Este programa durará 6 semanas, esta parte es para tres semanas.",
                "https://nutrihealthgundin.com/", ProgramLevelEnum.MEDIUM, userApp, null, null);
        programDao.save(vTapper2);
        final Session upperH1 = new Session("Parte superior del cuerpo (horizontal)", null, DayOfWeek.MONDAY, vTapper2, new HashSet<>());
        final Session upperV1 = new Session("Parte superior del cuerpo (vertical)", null, DayOfWeek.TUESDAY, vTapper2, new HashSet<>());
        final Session legs = new Session("Parte inferior del cuerpo", null, DayOfWeek.WEDNESDAY, vTapper2, new HashSet<>());
        final Session upperH2 = new Session("Parte superior del cuerpo (horizontal)", null, DayOfWeek.FRIDAY, vTapper2, new HashSet<>());
        final Session upperV2 = new Session("Parte superior del cuerpo (vertical)", null, DayOfWeek.SATURDAY, vTapper2, new HashSet<>());
        upperH1.setListOrder(0);
        upperV1.setListOrder(1);
        legs.setListOrder(2);
        upperH2.setListOrder(3);
        upperV2.setListOrder(4);
        final var sessions = Arrays.asList(upperH1, upperV1, legs, upperH2, upperV2);
        sessionDao.saveAll(sessions);

        final List<SetGroup> setGroups = new ArrayList<>();
        final var pressBanca = new SetGroup("", exerciseById(1L), upperH1, null, null);
        final List<Set> sets = new ArrayList<>(giveMeSets(pressBanca, 4, 5));
        pressBanca.setListOrder(0);
        setGroups.add(pressBanca);
        final var remoUnaMano = new SetGroup("", exerciseById(2L), upperH1, null, null);
        sets.addAll(giveMeSets(remoUnaMano, 4, 5));
        remoUnaMano.setListOrder(1);
        setGroups.add(remoUnaMano);

        this.setGroupDao.saveAll(setGroups);
        this.setDao.saveAll(sets);
    }

    private Exercise exerciseById(final Long id) {
        final var exercise = new Exercise();
        exercise.setId(id);
        return exercise;
    }

    private List<Set> giveMeSets(final SetGroup setGroup, final int setSize, final int reps) {
        final var sets = new ArrayList<Set>(setSize);
        for (int i = 0; i < setSize; i++) {
            final Set set = new Set("", reps, 2.0, null, setGroup);
            set.setListOrder(i);
            sets.add(set);
        }
        return sets;
    }

    private void createWorkouts(final UserApp userApp) {
        final List<Workout> workouts = new ArrayList<>();
        final int days = 35;
        final Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -days - 1);
        for (int i = 0; i < days; i++) {
            c.add(Calendar.DATE, 1);
            if (random.nextDouble() < 0.2) {
                continue;
            }
            workouts.add(new Workout(c.getTime(), null, userApp, null));
        }
        this.workoutDao.saveAll(workouts);
    }

    private void createSets() {
        final List<Exercise> exercises = this.exerciseDao.findAll();
        final List<Session> sessions = this.sessionDao.findAll();
        final List<Workout> workouts = this.workoutDao.findAll();
        final List<SetGroup> setGroups = new ArrayList<>();
        for (final Session session : sessions) {
            for (int i = 0; 5 > i; i++) {
                int rnd = random.nextInt(exercises.size() - 1);
                final SetGroup setGroup = new SetGroup("SessionSetGroup" + rnd, exercises.get(rnd), session, null, null);
                setGroup.setListOrder(i);
                setGroups.add(setGroup);
            }
        }
        for (final Workout workout : workouts) {
            for (int i = 0; 5 > i; i++) {
                int rnd = random.nextInt(exercises.size() - 1);
                final SetGroup setGroup = new SetGroup(random.nextDouble() < 0.2 ? "WorkoutSetGroup" + rnd : null, exercises.get(rnd), null, workout, null);
                setGroup.setListOrder(i);
                setGroups.add(setGroup);
            }
        }
        this.setGroupDao.saveAll(setGroups);

        final List<Set> sets = new ArrayList<>();
        for (final SetGroup setGroup : setGroups) {
            final int totalSeries = random.nextInt(2, 6);
            for (int i = 0; i < totalSeries; i++) {
                final int reps = random.nextInt(3, 15);
                final double rir = random.nextInt(0, 4);
                final double weight = Math.round((random.nextInt(5, 100)) * 100.0) / 100.0;
                final Set set = new Set(random.nextDouble() < 0.2 ? "SetDescription" : null, reps, rir, weight, setGroup);
                set.setListOrder(i);
                sets.add(set);
            }
        }

        setDao.saveAll(sets);
        LOGGER.info("Creados las sets");
    }
}