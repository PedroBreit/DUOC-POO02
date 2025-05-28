package exp1_s2_grupo1;
import java.util.regex.*;
import java.util.Scanner;

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

    public static int leerEnteroValido(Scanner sc, String mensaje) {
            while (true) {
                System.out.print(mensaje);
                String input = sc.nextLine().trim();
                try {
                    return Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Debe ser un número entero.");
                }
            }
        }

    public static double leerDoubleValido(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String input = sc.nextLine().trim().replace(",",".");
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Debe ser un número decimal.");
            }
        }
    }

    public static boolean leerBooleanoValido(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String input = sc.nextLine().trim().toLowerCase();
            if (input.equals("true")) return true;
            if (input.equals("false")) return false;
            System.out.println("Entrada inválida. Ingrese 'true' o 'false'.");
        }
    }

    public static String leerTextoNoVacio(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.out.println("Entrada inválida. No puede estar vacía.");
        }
    }
    
    public static long leerLongValido(Scanner sc, String mensaje) {
    while (true) {
        System.out.print(mensaje);
        String input = sc.nextLine().trim();
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Debe ser un número entero largo (sin letras ni símbolos).");
        }
    }
}
}