package bank_europe.validadores;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidarFormato {
    public static boolean validarFormatoRut(String rut) {
        String regex = "^\\d{1,2}\\.\\d{3}\\.\\d{3}-[\\dkK]$";
	
	//Compila el patrón regex en un objeto
	Pattern patron = Pattern.compile(regex);
        
	//Crea un objeto que compara el rut con el patron
	Matcher validar = patron.matcher(rut);

	// devuelve true si el rut calza exactamente
        return validar.matches();
    }
}
