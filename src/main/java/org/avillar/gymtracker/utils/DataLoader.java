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

    private final MuscleGroupDao muscleGroupDao;
    private final MuscleSubGroupDao muscleSubGroupDao;
    private final ExerciseDao exerciseDao;
    private final ProgramDao programDao;
    private final SessionDao sessionDao;
    private final UserDao userDao;
    private final SetGroupDao setGroupDao;
    private final SetDao setDao;
    private final MeasureDao measureDao;

    @Autowired
    public DataLoader(MeasureDao measureDao, SetDao setDao, MuscleGroupDao muscleGroupDao, MuscleSubGroupDao muscleSubGroupDao, ExerciseDao exerciseDao,
                      ProgramDao programDao, SessionDao sessionDao, UserDao userDao, SetGroupDao setGroupDao) {
        this.muscleGroupDao = muscleGroupDao;
        this.muscleSubGroupDao = muscleSubGroupDao;
        this.exerciseDao = exerciseDao;
        this.programDao = programDao;
        this.sessionDao = sessionDao;
        this.userDao = userDao;
        this.setGroupDao = setGroupDao;
        this.setDao = setDao;
        this.measureDao = measureDao;
    }

    public void run(ApplicationArguments args) {
        final Random random = new Random();
        this.createExercises();
        final UserApp user = new UserApp("chema", new BCryptPasswordEncoder().encode("chema69"),
                null, "Chema", "Garcia", "Romero", null,
                GenderEnum.MALE, ActivityLevelEnum.EXTREME, null, null, null, null);
        userDao.save(user);
        this.createPrograms(user);
        this.crearSets(random);
        this.createMeasures(user, random);
        this.createSessions(user);

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
        muscleGroupDao.saveAll(Arrays.asList(chest, lats, shoulders, lowerBack, biceps, triceps, abs, glute,
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
        muscleSubGroupDao.saveAll(Arrays.asList(chestUpper, chestLower, chestMiddle, tricepsLateral, tricepsLong,
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
        exerciseDao.saveAll(exercises);

    }

    private void createPrograms(UserApp user) {
        final Program pushPullLegs = new Program("Push-Pull-Legs", "Push pull legs frec2", null, ProgramLevelEnum.MEDIUM, user, null, null);
        final Program fullBody = new Program("Full body", "Full body frec1", null, ProgramLevelEnum.EASY, user, null, null);
        final Program weider = new Program("Weider", "Weider frec1", null, ProgramLevelEnum.HARD, user, null, null);
        programDao.saveAll(Arrays.asList(pushPullLegs, fullBody, weider));

        final Session push = new Session("Push", null, 0, new Date(), user, pushPullLegs, null);
        final Session pull = new Session("Pull", null, 1, new Date(), user, pushPullLegs, null);
        final Session legs = new Session("Legs", null, 2, new Date(), user, pushPullLegs, null);
        final Session reversePush = new Session("Reverse push", null, 3, new Date(), user, pushPullLegs, null);
        final Session reversePull = new Session("Reverse pull", null, 4, new Date(), user, pushPullLegs, null);
        final List<Session> sessions = new ArrayList<>(Arrays.asList(push, pull, legs, reversePush, reversePull));

        final Session upperFirst = new Session("Upper first", null, 0, new Date(), user, fullBody, null);
        final Session lowerFirst = new Session("Lower first", null, 1, new Date(), user, fullBody, null);
        final Session upperSecond = new Session("Upper second", null, 2, new Date(), user, fullBody, null);
        final Session lowerSecond = new Session("Lower second", null, 3, new Date(), user, fullBody, null);
        sessions.addAll(Arrays.asList(upperFirst, lowerFirst, upperSecond, lowerSecond));

        final Session chest = new Session("Chest", null, 0, new Date(), user, weider, null);
        final Session back = new Session("Back", null, 1, new Date(), user, weider, null);
        final Session shoulder = new Session("Shoulder", null, 2, new Date(), user, weider, null);
        final Session legs2 = new Session("Legs", null, 3, new Date(), user, weider, null);
        final Session arms = new Session("Arms", null, 4, new Date(), user, weider, null);
        sessions.addAll(Arrays.asList(chest, back, shoulder, legs2, arms));

        sessionDao.saveAll(sessions);
    }

    private void crearSets(final Random random) {
        final List<Exercise> exercises = this.exerciseDao.findAll();
        final List<Session> sessions = this.sessionDao.findAll();
        final List<SetGroup> setGroups = new ArrayList<>();

        for (final Session session : sessions) {
            for (int i = 0; i < 5; i++) {
                int rnd = random.nextInt(exercises.size() - 1);
                setGroups.add(new SetGroup("Descripcion" + rnd, i, exercises.get(rnd), session, null));
            }
        }
        this.setGroupDao.saveAll(setGroups);

        final List<Set> sets = new ArrayList<>();
        for (final SetGroup setGroup : setGroups) {
            final int totalSeries = random.nextInt(1, 5);
            for (int i = 0; i < totalSeries; i++) {
                final int reps = random.nextInt(3, 15);
                final int rir = random.nextInt(5, 10);
                final double weight = Math.round((random.nextDouble(5, 100)) * 100.0) / 100.0;
                sets.add(new Set("Descripcion", reps, rir, i, weight, setGroup));
            }
        }

        setDao.saveAll(sets);
    }

    private void createMeasures(UserApp userApp, final Random random) {
        final List<Measure> measures = new ArrayList<>();
        String dt = "2022-10-10";  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            throw new RuntimeException("Error al parsear");
        }// number of days to add
        for (int i = 0; i < 100; i++) {
            c.add(Calendar.DATE, 1);
            final Measure measure = new Measure(c.getTime(), 185.0, random.nextDouble(80, 90), random.nextDouble(10, 15), userApp);
            measures.add(measure);
        }
        this.measureDao.saveAll(measures);

    }

    private void createSessions(UserApp userApp) {
        final List<Session> sessions = new ArrayList<>();
        String dt = "2022-10-20";  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            throw new RuntimeException("Error al parsear");
        }// number of days to add
        for (int i = 0; i < 5; i++) {
            c.add(Calendar.DATE, 1);
            final Session session = new Session("Upper first", null, 0, c.getTime(), userApp, null, null);

            sessions.add(session);
        }

        this.sessionDao.saveAll(sessions);
    }
}