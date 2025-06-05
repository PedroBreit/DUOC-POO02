package bank_europe.validadores;

import java.util.Scanner;

public class ValidarEntradas {
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
            if (input.equals("true")|| input.equals("sí") || input.equals("si") || input.equals("s") || input.equals("1")) return true;
            if (input.equals("false") || input.equals("no") || input.equals("n") || input.equals("0")) return false;
            System.out.println("Entrada inválida. Ingrese 'si' o 'no'.");
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
