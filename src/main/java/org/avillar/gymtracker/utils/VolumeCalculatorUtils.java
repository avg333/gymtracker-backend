package org.avillar.gymtracker.utils;

import org.avillar.gymtracker.model.dto.ProgramDto;
import org.avillar.gymtracker.model.dto.SessionDto;
import org.avillar.gymtracker.model.entities.Exercise;
import org.avillar.gymtracker.model.entities.Program;
import org.avillar.gymtracker.model.entities.Session;
import org.avillar.gymtracker.model.entities.SetGroup;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class VolumeCalculatorUtils {
    private final ModelMapper modelMapper;

    VolumeCalculatorUtils(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProgramDto calculateProgramVolume(final Program program) {
        final ProgramDto programDto = this.modelMapper.map(program, ProgramDto.class);
        final Set<Session> sessions = program.getSessions();
        programDto.setSessionNumber(sessions.size());
        programDto.setOwnerName(program.getUserApp().getName());

        final List<Integer> sessionsVolume = new ArrayList<>(sessions.size());
        final List<Integer> exercisesPerSessions = new ArrayList<>(sessions.size());
        for (final Session session : sessions) {
            int sessionVolume = 0;
            final Set<Exercise> exercisesInSession = new HashSet<>();
            for (final SetGroup setGroup : session.getSetGroups()) {
                for (final org.avillar.gymtracker.model.entities.Set set : setGroup.getSets()) {
                    if (3 > set.getRir()) {
                        sessionVolume++;
                        exercisesInSession.add(setGroup.getExercise());
                    }
                }
            }
            sessionsVolume.add(sessionVolume);
            exercisesPerSessions.add(exercisesInSession.size());
        }

        int volTotal = 0;
        int exerTotal = 0;
        for (int i = 0; i < sessions.size(); i++) {
            volTotal += sessionsVolume.get(i);
            exerTotal += exercisesPerSessions.get(i);
        }
        programDto.setAverageVolumePerSession(sessions.size() != 0 ? volTotal / sessions.size() : 0);
        programDto.setAverageExercisesNumberPerSession(sessions.size() != 0 ? exerTotal / sessions.size() : 0);

        return programDto;
    }

    public SessionDto calculateSessionVolume(final Session session) {
        final SessionDto sessionDto = this.modelMapper.map(session, SessionDto.class);
        final Set<Long> exerciseIds = new HashSet<>();
        int sets = 0;
        for (final SetGroup setGroup : session.getSetGroups()) {
            exerciseIds.add(setGroup.getExercise().getId());
            for (final org.avillar.gymtracker.model.entities.Set set : setGroup.getSets()) {
                if (3 >= set.getRir()) {
                    sets++;
                }
            }
        }
        sessionDto.setExercisesNumber(exerciseIds.size());
        sessionDto.setSetsNumber(sets);
        return sessionDto;
    }
}
