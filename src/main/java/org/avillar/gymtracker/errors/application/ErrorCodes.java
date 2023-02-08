package org.avillar.gymtracker.errors.application;

public enum ErrorCodes {

    // COMMON ERROR CODES
    ERR_BF_CMM_20("La longitud de la descripción es superior a la máxima"),

    // SET ERROR CODES
    ERR_BF_SET_03("Acceso al set especificado no permitido"),
    ERR_BF_SET_04("El set especificado no existe"),
    ERR_BF_SET_20("El número de repeticiones no puede ser negativo"),
    ERR_BF_SET_21("El número de repeticiones excede el máximo"),
    ERR_BF_SET_22("El RIR es inferior al mínimo"),
    ERR_BF_SET_23("El RIR es superior al máximo"),
    ERR_BF_SET_24("La parte decimal del RIR debe ser 0,5 si existe"),
    ERR_BF_SET_25("El peso no puede ser negativo"),
    ERR_BF_SET_26("El peso no puede ser superior al máximo"),

    // SETGROUP ERROR CODES
    ERR_BF_SETGROUP_03("Acceso al setGroup especificado no permitido"),
    ERR_BF_SETGROUP_04("El setGroup especificado no existe"),
    ERR_BF_SETGROUP_10("El setGroup no puede tener session y workout simultaneamente"),
    ERR_BF_SETGROUP_11("El setGroup debe tener una session o un workout"),

    // WORKOUT ERROR CODES
    ERR_BF_WORKOUT_03("Acceso al workout especificado no permitido"),
    ERR_BF_WORKOUT_04("El workout especificado no existe"),
    ERR_BF_WORKOUT_10("Ya existe un workout en esa fecha"),
    ERR_BF_WORKOUT_20("La fecha del workout no es valida"),
    ERR_BF_WORKOUT_21("La fecha es anterior al rango máximo permitido"),
    ERR_BF_WORKOUT_22("La fecha es posterior al rango maximo permitido"),

    // SESSION ERROR CODES
    ERR_BF_SESSION_03("Acceso a la session especificada no permitido"),
    ERR_BF_SESSION_04("La session especificada no existe"),

    // EXERCISE ERROR CODES
    ERR_BF_EXERCISE_03("Acceso al exercise especificado no permitido"),
    ERR_BF_EXERCISE_04("El exercise especificado no existe"),

    // ERRORES USUARIO
    ERR_BF_USER_04("El usuario especificado no existe"),

    // ERRORES INTERNOS
    ERR_400("Objeto mal formado"),
    ERR_403("No hay permisos para acceder al objeto"),
    ERR_404("Objeto no encotrado"),
    ERR_500("Error en el servidor");

    public final String defaultMessage;

    ErrorCodes(final String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }
}
