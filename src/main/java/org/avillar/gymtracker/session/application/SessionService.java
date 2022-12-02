package org.avillar.gymtracker.session.application;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface SessionService {

    List<SessionDto> getAllProgramSessions(Long programId) throws IllegalAccessException;

    /**
     * Devuelve el DTO de la entidad con el ID especificado
     *
     * @param sessionId ID de la entidad a obtener
     * @return DTO de la entidad solicitada
     * @throws EntityNotFoundException Se genera si no existe una entidad con ese ID
     * @throws IllegalAccessException  Se genera si solicita la operación un usuario no propietario ni admin
     */
    SessionDto getSession(Long sessionId) throws EntityNotFoundException, IllegalAccessException;


    /**
     * Crea una nueva entidad a partir del DTO. Si se especifica un ID y este ya existe, se le asigna otro.
     *
     * @param sessionDto DTO de la entidad a crear
     * @return DTO de la entidad creada
     * @throws EntityNotFoundException Se genera si no existe una entidad padre con ese ID
     * @throws IllegalAccessException  Se genera si solicita la operación un usuario no propietario ni admin
     */
    SessionDto createSession(SessionDto sessionDto) throws EntityNotFoundException, IllegalAccessException;

    /**
     * Actualiza la entidad con el ID del DTO a los campos del DTO si lo solicita su usuario propietario o un admin
     *
     * @param sessionDto DTO de la entidad a actualizar, con su ID y sus datos
     * @return DTO de la entidad con los datos ya actualizados
     * @throws EntityNotFoundException Se genera si no existe una entidad con ese ID
     * @throws IllegalAccessException  Se genera si solicita la operación un usuario no propietario ni admin
     */
    SessionDto updateSession(SessionDto sessionDto) throws EntityNotFoundException, IllegalAccessException;

    /**
     * Elimina la entidad con el ID indicado
     *
     * @param sessionId ID de la entidad a eliminar
     * @throws EntityNotFoundException Se genera si no existe una entidad con ese ID
     * @throws IllegalAccessException  Se genera si solicita la operación un usuario no propietario ni admin
     */
    void deleteProgram(Long sessionId) throws EntityNotFoundException, IllegalAccessException;
}
