package util;

import java.util.regex.*;

public class ValidadorPatente {
    private static final String PATRON = "^[A-Z]{2,3}[0-9]{2,3}$";

    public static boolean validarPatente(String patente) {
        Pattern pattern = Pattern.compile(PATRON);
        Matcher matcher = pattern.matcher(patente.toUpperCase());
        return matcher.matches();
    }
}