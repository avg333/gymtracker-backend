package org.avillar.gymtracker.services;

public interface StatisticsService {

    int getNumberOfProgramSessions(Long programId);

    int getNumberOfSessionSets(Long sessionId);

    int getNumberofSessionExercises(Long sessionId);
}
