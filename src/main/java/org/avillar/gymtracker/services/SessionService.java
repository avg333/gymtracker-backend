package org.avillar.gymtracker.services;

import org.avillar.gymtracker.model.dto.SessionDto;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;

public interface SessionService {

    List<SessionDto> getAllProgramSessionsWithData(Long programId) throws IllegalAccessException;

    List<SessionDto> getAllNotProgramsLoggedUserSessionsWithData();

    /**
     * Devuelve el DTO de la entidad con el ID especificado
     *
     * @param sessionId ID de la entidad a obtener
     * @return DTO de la entidad solicitada
     * @throws EntityNotFoundException Se genera si no existe una entidad con ese ID
     * @throws IllegalAccessException  Se genera si solicita la operación un usuario no propietario ni admin
     */
    SessionDto getSession(Long sessionId) throws EntityNotFoundException, IllegalAccessException;

    List<SessionDto> getSessionByDate(Date date) throws EntityNotFoundException;


    /**
     * Crea una nueva entidad a partir del DTO. Si se especifica un ID y este ya existe, se le asigna otro.
     *
     * @param sessionDto DTO de la entidad a crear
     * @return DTO de la entidad creada
     * @throws EntityNotFoundException Se genera si no existe una entidad padre con ese ID
     * @throws IllegalAccessException  Se genera si solicita la operación un usuario no propietario ni admin
     */
    SessionDto createSessionInProgram(SessionDto sessionDto) throws EntityNotFoundException, IllegalAccessException;

    SessionDto createSession(SessionDto sessionDto) throws EntityNotFoundException;

    /**
     * Actualiza la entidad con el ID del DTO a los campos del DTO si lo solicita su usuario propietario o un admin
     *
     * @param sessionDto DTO de la entidad a actualizar, con su ID y sus datos
     * @return DTO de la entidad con los datos ya actualizados
     * @throws EntityNotFoundException Se genera si no existe una entidad con ese ID
     * @throws IllegalAccessException  Se genera si solicita la operación un usuario no propietario ni admin
     */
    SessionDto updateProgramSession(SessionDto sessionDto) throws EntityNotFoundException, IllegalAccessException;

    SessionDto updateSession(SessionDto sessionDto) throws EntityNotFoundException, IllegalAccessException;

    /**
     * Elimina la entidad con el ID indicado
     *
     * @param sessionId ID de la entidad a eliminar
     * @throws EntityNotFoundException Se genera si no existe una entidad con ese ID
     * @throws IllegalAccessException  Se genera si solicita la operación un usuario no propietario ni admin
     */
    void deleteProgramSession(Long sessionId) throws EntityNotFoundException, IllegalAccessException;

    void deleteSession(Long sessionId) throws EntityNotFoundException, IllegalAccessException;
}
