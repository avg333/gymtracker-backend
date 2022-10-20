package org.avillar.gymtracker.services;

import org.avillar.gymtracker.model.dto.ProgramDto;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface ProgramService {

    List<ProgramDto> getUserAllLoggedUserProgramsWithVolume();

    /**
     * Devuelve el DTO de la entidad con el ID especificado
     *
     * @param programId ID de la entidad a obtener
     * @return DTO de la entidad solicitada
     * @throws EntityNotFoundException Se genera si no existe una entidad con ese ID
     * @throws IllegalAccessException  Se genera si solicita la operación un usuario no propietario ni admin
     */
    ProgramDto getProgram(Long programId) throws EntityNotFoundException, IllegalAccessException;

    /**
     * Crea una nueva entidad a partir del DTO. Si se especifica un ID y este ya existe, se le asigna otro.
     *
     * @param programDto DTO de la entidad a crear
     * @return DTO de la entidad creada
     */
    ProgramDto createProgram(ProgramDto programDto);

    /**
     * Actualiza la entidad con el ID del DTO a los campos del DTO si lo solicita su usuario propietario o un admin
     *
     * @param programDto DTO de la entidad a actualizar, con su ID y sus datos
     * @return DTO de la entidad con los datos ya actualizados
     * @throws EntityNotFoundException Se genera si no existe una entidad con ese ID
     * @throws IllegalAccessException  Se genera si solicita la operación un usuario no propietario ni admin
     */
    ProgramDto updateProgram(ProgramDto programDto) throws EntityNotFoundException, IllegalAccessException;

    /**
     * Elimina la entidad con el ID indicado
     *
     * @param programId ID de la entidad a eliminar
     * @throws EntityNotFoundException Se genera si no existe una entidad con ese ID
     * @throws IllegalAccessException  Se genera si solicita la operación un usuario no propietario ni admin
     */
    void deleteProgram(Long programId) throws EntityNotFoundException, IllegalAccessException;

    /**
     * Verifica que la entidad con el ID especificado exista y pertenezca al usuario logeado
     *
     * @param programId ID de la entidad a verificar
     * @throws EntityNotFoundException Se genera si no existe una entidad con ese ID
     * @throws IllegalAccessException  Se genera si solicita la operación un usuario no propietario ni admin
     */
    void programExistsAndIsFromLoggedUser(Long programId) throws EntityNotFoundException, IllegalAccessException;
}
