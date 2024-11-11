package com.java.testw2m.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Clase con funciones de utilidad
 */
public class Utils {

    /**
     * Funcion para comprobar si una lista esta vacia o es nula
     *
     * @param list Una lista de objetos
     * @return True en caso de estar vacia, false en caso de no estarlo
     */
    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

}
