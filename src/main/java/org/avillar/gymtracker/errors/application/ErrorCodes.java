package org.avillar.gymtracker.errors.application;

public enum ErrorCodes {
    //COMMON
    ERR300("La longitud de la descripción es superior a la máxima"),

    // ERRORES SET
    ERR301("El número de repeticiones no puede ser negativo"),
    ERR302("El número de repeticiones excede el máximo"),
    ERR303("El RIR es inferior al mínimo"),
    ERR304("El RIR es superior al máximo"),
    ERR305("El peso no puede ser negativo"),
    ERR306("El peso no puede ser superior al máximo"),
    ERR307("El set especificado no existe"),
    ERR308("Acceso al set especificado no permitido"),


    // ERRORES SETGROUP
    ERR309("El setGroup especificado no existe"),
    ERR310("Acceso al setGroup especificado no permitido"),
    ERR400("El setGroup no puede tener session y workout simultaneamente"),
    ERR401("El setGroup debe tener una session o un workout"),


    // ERRORES EXERCISE
    ERR509("El exercise especificado no existe"),
    ERR510("Acceso al exercise especificado no permitido"),


    // ERRORES SESSION
    ERR609("La session especificada no existe"),
    ERR610("Acceso a la session especificada no permitido"),


    // ERRORES WORKOUT
    ERR700("Ya existe un workout en esa fecha"),
    ERR701("La fecha del workout no es valida"),
    ERR702("La fecha es anterior al rango máximo permitido"),
    ERR703("La fecha es posterior al rango maximo permitido"),
    ERR709("El workout especificado no existe"),
    ERR710("Acceso al workout especificado no permitido"),


    // ERRORES USUARIO
    ERR809("El usuario especificado no existe"),
    ;

    public final String defaultMessage;

    ErrorCodes(final String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }
}
