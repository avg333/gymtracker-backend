package org.avillar.gymtracker.utils;

import org.avillar.gymtracker.model.dao.*;
import org.avillar.gymtracker.model.entities.Set;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class DataLoader implements ApplicationRunner {

    private final Random random = new Random();

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
                      SetGroupDao setGroupDao,
                      ExerciseDao exerciseDao, UserDao userDao, ProgramDao programDao, SessionDao sessionDao,
                      MuscleSubGroupDao muscleSubGroupDao, WorkoutDao workoutDao) {
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
        final UserApp user = this.userDao.save(new UserApp(
                "chema", new BCryptPasswordEncoder().encode("chema69"), null, "Chema",
                "Garcia", "Romero", null, GenderEnum.MALE, ActivityLevelEnum.EXTREME,
                null, null, null, null));
        this.createMeasures(user);
        this.createExercisesWithMuscleGroups();

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
        final MuscleGroup chest = new MuscleGroup("chest", null, chestSupGroup, null, null);
        // Back
        final MuscleGroup lats = new MuscleGroup("lats", null, backSupGroup, null, null);
        final MuscleGroup trapezius = new MuscleGroup("trapezius", null, backSupGroup, null, null);
        // Shoulder
        final MuscleGroup shoulderAnterior = new MuscleGroup("shoulder anterior", null, shouldersSupGroup, null, null);
        final MuscleGroup shoulderLateral = new MuscleGroup("shoulders lateral", null, shouldersSupGroup, null, null);
        final MuscleGroup shoulderPosterior = new MuscleGroup("shoulders posterior", null, shouldersSupGroup, null, null);
        // Arms
        final MuscleGroup forearms = new MuscleGroup("forearms", null, armsSupGroup, null, null);
        final MuscleGroup biceps = new MuscleGroup("biceps", null, armsSupGroup, null, null);
        final MuscleGroup triceps = new MuscleGroup("triceps", null, armsSupGroup, null, null);
        // Core
        final MuscleGroup abs = new MuscleGroup("abs", null, coreSupGroup, null, null);
        final MuscleGroup lowerBack = new MuscleGroup("lower back", null, coreSupGroup, null, null);
        // Legs
        final MuscleGroup quadriceps = new MuscleGroup("quadriceps", null, legsSupGroup, null, null);
        final MuscleGroup hamstrings = new MuscleGroup("hamstrings", null, legsSupGroup, null, null);
        final MuscleGroup glute = new MuscleGroup("glute", null, legsSupGroup, null, null);
        final MuscleGroup calves = new MuscleGroup("calves", null, legsSupGroup, null, null);
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

        final List<Exercise> exercises = new ArrayList<>();
        // Chest
        exercises.add(new Exercise("hammer strength press", null, false, LoadTypeEnum.MACHINE, new HashSet<>(List.of(chest)), new HashSet<>(List.of(chestUpper))));
        exercises.add(new Exercise("cable crossover", null, false, LoadTypeEnum.CABLE, new HashSet<>(List.of(chest)), new HashSet<>(List.of(chestMiddle))));
        exercises.add(new Exercise("incline smith machine press", null, false, LoadTypeEnum.MULTIPOWER, new HashSet<>(List.of(chest)), new HashSet<>(List.of(chestUpper))));
        exercises.add(new Exercise("incline dumbbell press", null, false, LoadTypeEnum.DUMBBELL, new HashSet<>(List.of(chest)), new HashSet<>(List.of(chestUpper))));
        exercises.add(new Exercise("pec deck", null, false, LoadTypeEnum.MACHINE, new HashSet<>(List.of(chest)), new HashSet<>(List.of(chestMiddle))));
        exercises.add(new Exercise("bench press", null, false, LoadTypeEnum.BAR, new HashSet<>(List.of(chest)), null));
        exercises.add(new Exercise("push up", null, false, LoadTypeEnum.BODYWEIGHT, new HashSet<>(List.of(chest)), new HashSet<>(List.of(chestLower))));
        // Back
        exercises.add(new Exercise("pulldown machine", null, false, LoadTypeEnum.MACHINE, new HashSet<>(List.of(lats)), null));
        exercises.add(new Exercise("cable seated row", null, false, LoadTypeEnum.CABLE, new HashSet<>(List.of(lats)), null));
        exercises.add(new Exercise("incline dumbbell row", null, false, LoadTypeEnum.DUMBBELL, new HashSet<>(List.of(lats)), null));
        exercises.add(new Exercise("machine row", null, false, LoadTypeEnum.MACHINE, new HashSet<>(List.of(lats)), null));
        exercises.add(new Exercise("dumbbell single arm bent over row", null, true, LoadTypeEnum.DUMBBELL, new HashSet<>(List.of(lats)), null));
        exercises.add(new Exercise("neutral grip lat pulldown", null, false, LoadTypeEnum.CABLE, new HashSet<>(List.of(lats)), null));
        exercises.add(new Exercise("unilateral cable row", null, true, LoadTypeEnum.CABLE, new HashSet<>(List.of(lats)), null));
        // Shoulders
        exercises.add(new Exercise("dumbbell seated press", null, false, LoadTypeEnum.DUMBBELL, new HashSet<>(List.of(shoulderAnterior)), null));
        exercises.add(new Exercise("dumbbell smith machine seated press", null, false, LoadTypeEnum.MULTIPOWER, new HashSet<>(List.of(shoulderAnterior)), null));
        exercises.add(new Exercise("machine lateral raise", null, false, LoadTypeEnum.MACHINE, new HashSet<>(List.of(shoulderLateral)), null));
        exercises.add(new Exercise("dumbbell lateral raise", null, false, LoadTypeEnum.DUMBBELL, new HashSet<>(List.of(shoulderLateral)), null));
        exercises.add(new Exercise("cable lateral raise", null, false, LoadTypeEnum.CABLE, new HashSet<>(List.of(shoulderLateral)), null));
        exercises.add(new Exercise("incline lateral raise", null, true, LoadTypeEnum.DUMBBELL, new HashSet<>(List.of(shoulderLateral)), null));
        exercises.add(new Exercise("shoulder reverse fly", null, false, LoadTypeEnum.MACHINE, new HashSet<>(List.of(shoulderPosterior)), null));
        exercises.add(new Exercise("dumbbell bent over shoulder reverse fly", null, false, LoadTypeEnum.DUMBBELL, new HashSet<>(List.of(shoulderPosterior)), null));
        exercises.add(new Exercise("cable reverse fly", null, false, LoadTypeEnum.CABLE, new HashSet<>(List.of(shoulderPosterior)), null));

        // Triceps
        exercises.add(new Exercise("cable overhead tricep extension", null, false, LoadTypeEnum.CABLE, new HashSet<>(List.of(triceps)), new HashSet<>(List.of(tricepsLong))));
        exercises.add(new Exercise("jm press in multipower", null, false, LoadTypeEnum.MULTIPOWER, new HashSet<>(List.of(triceps)), new HashSet<>(List.of(tricepsMedial))));
        exercises.add(new Exercise("cable tricep kickback", null, false, LoadTypeEnum.CABLE, new HashSet<>(List.of(triceps)), new HashSet<>(List.of(tricepsLateral))));
        exercises.add(new Exercise("cable tricep extension", null, false, LoadTypeEnum.CABLE, new HashSet<>(List.of(triceps)), new HashSet<>(List.of(tricepsMedial))));
        exercises.add(new Exercise("dumbbell single arm tricep extension", null, true, LoadTypeEnum.CABLE, new HashSet<>(List.of(triceps)), new HashSet<>(List.of(tricepsLong))));
        // Biceps
        exercises.add(new Exercise("machine preacher curl", null, false, LoadTypeEnum.MACHINE, new HashSet<>(List.of(biceps)), null));
        exercises.add(new Exercise("dumbbell curl", null, false, LoadTypeEnum.DUMBBELL, new HashSet<>(List.of(biceps)), null));
        exercises.add(new Exercise("dumbbell hammer curl", null, false, LoadTypeEnum.DUMBBELL, new HashSet<>(List.of(biceps)), null));
        exercises.add(new Exercise("cable curl", null, false, LoadTypeEnum.CABLE, new HashSet<>(List.of(biceps)), null));
        exercises.add(new Exercise("barbell curl", null, false, LoadTypeEnum.BAR, new HashSet<>(List.of(biceps)), null));
        exercises.add(new Exercise("barbell preacher curl", null, false, LoadTypeEnum.BAR, new HashSet<>(List.of(biceps)), null));

        exerciseDao.saveAll(exercises);
    }

    private void createPrograms(final UserApp userApp) {
        final Program pushPullLegs = new Program("Push-Pull-Legs", "Push pull legs frec2", null, ProgramLevelEnum.MEDIUM, userApp, null, null);
        final Program fullBody = new Program("Full body", "Full body frec1", null, ProgramLevelEnum.EASY, userApp, null, null);
        final Program weider = new Program("Weider", "Weider frec1", null, ProgramLevelEnum.HARD, userApp, null, null);
        final Program relleno1 = new Program("Relleno1", "Desc1", null, ProgramLevelEnum.HARD, userApp, null, null);
        final Program relleno2 = new Program("Relleno2", "Desc2", null, ProgramLevelEnum.HARD, userApp, null, null);
        final Program relleno3 = new Program("Relleno2", "Desc2", null, ProgramLevelEnum.HARD, userApp, null, null);
        final Program relleno4 = new Program("relleno4", "Desc1", null, ProgramLevelEnum.HARD, userApp, null, null);
        final Program relleno5 = new Program("relleno5", "Desc2", null, ProgramLevelEnum.HARD, userApp, null, null);
        final Program relleno6 = new Program("relleno6", "Desc2", null, ProgramLevelEnum.HARD, userApp, null, null);
        final Program relleno7 = new Program("relleno7", "Desc1", null, ProgramLevelEnum.HARD, userApp, null, null);
        final Program relleno8 = new Program("relleno8", "Desc2", null, ProgramLevelEnum.HARD, userApp, null, null);
        final Program relleno9 = new Program("Relleno9", "Desc2", null, ProgramLevelEnum.HARD, userApp, null, null);


        programDao.saveAll(Arrays.asList(pushPullLegs, fullBody, weider, relleno1, relleno2, relleno3, relleno4, relleno5, relleno6, relleno7, relleno8, relleno9));

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

        sessionDao.saveAll(sessions);
    }

    private void createWorkouts(final UserApp userApp) {
        final List<Workout> workouts = new ArrayList<>();
        final String dt = "2022-10-20";  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }// number of days to add
        for (int i = 0; 5 > i; i++) {
            c.add(Calendar.DATE, 1);
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
                setGroups.add(new SetGroup("SessionSetGroup" + rnd, i, exercises.get(rnd), session, null, null));
            }
        }
        for (final Workout workout : workouts) {
            for (int i = 0; 5 > i; i++) {
                int rnd = random.nextInt(exercises.size() - 1);
                setGroups.add(new SetGroup("WorkoutSetGroup" + rnd, i, exercises.get(rnd), null, workout, null));
            }
        }
        this.setGroupDao.saveAll(setGroups);

        final List<Set> sets = new ArrayList<>();
        for (final SetGroup setGroup : setGroups) {
            final int totalSeries = random.nextInt(2, 6);
            for (int i = 0; i < totalSeries; i++) {
                final int reps = random.nextInt(3, 15);
                final double rir = random.nextDouble(0, 4);
                final double weight = Math.round((random.nextDouble(5, 100)) * 100.0) / 100.0;
                sets.add(new Set("SetDescription", i, reps, rir, weight, setGroup));
            }
        }

        setDao.saveAll(sets);
    }
}