package exp1_s2_grupo1;
import java.util.regex.*;

public class Validadores {
    
    
    public static boolean validarFormatoRut(String rut) {
        // Regex que permite el formato 12.345.678-5 o 12.345.678-K
        
	/**
 	* Explicación del regex: "^\\d{1,2}\\.\\d{3}\\.\\d{3}-[\\dkK]$"
 	*
	* ^       -> Inicio de la cadena
	 * \\d     -> Dígito numérico (0-9)
 	* {1,2}   -> Uno o dos dígitos (para los millones del RUT)
 	* \\.     -> Punto literal (separador de miles, se escapa como \\.)
 	* \\d{3}  -> Tres dígitos (miles y centenas)
 	* -       -> Guion literal antes del dígito verificador
 	* [\\dkK] -> Conjunto que permite un dígito (0-9) o las letras 'k' o 'K' como dígito verificador
 	* $       -> Fin de la cadena
 	*/


	String regex = "^\\d{1,2}\\.\\d{3}\\.\\d{3}-[\\dkK]$";
	
	//Compila el patrón regex en un objeto
	Pattern patron = Pattern.compile(regex);
        
	//Crea un objeto que compara el rut con el patron
	Matcher validar = patron.matcher(rut);

	// devuelve true si el rut calza exactamente
        return validar.matches();
    }
}
