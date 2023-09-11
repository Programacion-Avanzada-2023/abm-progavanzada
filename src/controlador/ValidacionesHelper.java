/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.time.Year;
import java.util.regex.Pattern;

/**
 *
 * @author mazal
 */
public class ValidacionesHelper {

    /**
     * Valida que los campos de texto tengan menos de 32 caracteres y no tengan numeros.
     * @param texto
     * @return 
     */
    public static boolean validarStringLongitudSinNumeros(String texto) {
        return validarSoloLetras(texto) && validarLongitudTexto(texto, 32);
    }

    /**
     * Valida que un string sea solo de letras mayusculas y minsculas, permitiendo solo un '-' como caracter especial.
     * @param texto
     * @return 
     */
    private static boolean validarSoloLetras(String texto) {
        return Pattern.matches("[A-Za-z -]+", texto);
    }
    
    /**
     * Validar que un string sea un anio valido.
     * @param texto
     * @return
     */
    public static boolean validarSoloNumerosFormatoAnio(String texto) {
        boolean esNumerico = Pattern.matches("[0-9]{4}", texto);
        
        int anio = Year.now().getValue();
        
        return esNumerico && anio >= Integer.parseInt(texto) && Integer.parseInt(texto) >= 1886;
    }
    
    /**
     * Valida un DNI
     * @param texto
     * @return
     */
    public static boolean validarDni(String texto) {
        return Pattern.matches("[0-9]{7,9}", texto);
    }
    
    /**
     * Valida el formato de una patente.
     *
     * @param texto
     * @return
     */
    public static boolean validarFormatoPatente(String patente) {
        return Pattern.matches("^([A-Za-z]{3}[0-9]{3}|[A-Za-z]{2}[0-9]{3}[A-Za-z]{2})$", patente);
    }
    
    /**
     * Validar que un string sea solo numerico.
     * @param texto
     * @return
     */
    public static boolean validarSoloNumeros(String texto) {
        return Pattern.matches("[0-9]+", texto);
    }

    /**
     * Valida que un campo de string tenga menos de cierta cantidad de
     * caracteres
     *
     * @param texto Texto a validar
     * @param cant Cantidad de caracteres maxima
     *
     * @returns boolean Esta bien o no.
     */
    private static boolean validarLongitudTexto(String texto, int cant) {
        return texto.length() < cant;
    }
}
