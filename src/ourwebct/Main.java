/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ourwebct;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.script.ScriptException;

/**
 *
 * @author Usuario
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ScriptException {
        // TODO code application logic here

	String nombre, apellidos;
	int cedula;

	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	System.out.println("Cedula: ");
	cedula = Integer.parseInt(br.readLine());

	System.out.println("Nombre: ");
	nombre = br.readLine();

	System.out.println("Apellidos: ");
	apellidos = br.readLine();

	EstudianteJpaController a = new EstudianteJpaController();

	Estudiante e = new Estudiante(cedula, nombre, apellidos);

	try{
	    a.create(e);
	}catch(Exception ex){
	    System.out.println("Error. "+ ex.getMessage());
	}


    }

}
