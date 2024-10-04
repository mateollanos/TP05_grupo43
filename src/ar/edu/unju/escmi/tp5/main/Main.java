package ar.edu.unju.escmi.tp5.main;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import ar.edu.unju.escmi.tp5.collections.CollectionLibro;
import ar.edu.unju.escmi.tp5.collections.CollectionPrestamo;
import ar.edu.unju.escmi.tp5.dominio.*;
import ar.edu.unju.escmi.tp5.collections.CollectionUsuario;
import ar.edu.unju.escmi.tp5.exceptions.*;
import ar.edu.unju.escmi.tp5.utils.FechaUtil;

public class Main {
    public static void main(String[] args) {
    	  Scanner scanner = new Scanner(System.in);
	        String opc;
	        do {
	        
	            System.out.println("******** MENU ******** ");
	            System.out.println("1. Registrar libro");
	            System.out.println("2. Registrar usuario");
	            System.out.println("3. Prestamo de libro");
	            System.out.println("4. Devolucion de libro");
	            System.out.println("5. Listar libros");
	            System.out.println("6. Salir");
	            System.out.print("Seleccione una opción: ");
	            opc = scanner.nextLine();  
	            switch(opc) {
	                case "1":
	                	registrarLibro(scanner);
	                    break;
	                case "2":
	                	registrarUsuario(scanner);
	                	break;	
	                case "3":
	                	registrarPrestamo(scanner);
	                	break;
	                case "4":
	                    registrarDevolucion(scanner);
	                    break;
	                case "5":
	                	List<Libro> libros = CollectionLibro.libros;
	                    if(libros.isEmpty()) System.out.println("\nLa lista de libros esta vacia");
	                    else CollectionLibro.mostrarLibros();
	                    break;
	                case "6":
	                    System.out.println("\nSALIENDO");
	                    break;
	                default:
	                    System.out.println("\nOPCION INVALIDA. Vuelva a intentarlo");
	            }
	        } while(!opc.equals("6"));
	        scanner.close();
	    }

    public static void registrarLibro(Scanner scanner) {
        System.out.print("\nIngrese el ID del libro: ");
        String id = scanner.nextLine();
        System.out.print("Ingrese el título del libro: ");
        String titulo = scanner.nextLine();
        System.out.print("Ingrese el autor del libro: ");
        String autor = scanner.nextLine();
        System.out.print("Ingrese el ISBN del libro: ");
        String isbn = scanner.nextLine();

        Libro libro = new Libro(id, titulo, autor, isbn);
        CollectionLibro.agregarLibro(libro);
    }
    
    public static void registrarUsuario(Scanner scanner) throws InputMismatchException {
    	try {
            System.out.println("\nRegistrar usuario:");
            System.out.println("1. Alumno");
            System.out.println("2. Bibliotecario");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();

            switch(opcion) {
            case "1":
            	System.out.print("\nIngrese el ID del usuario: ");
                String id1 = scanner.nextLine();
                System.out.print("Ingrese el nombre del alumno: ");
                String nombreAlumno = scanner.nextLine();
                System.out.print("Ingrese el apellido del alumno: ");
                String apellidoAlumno = scanner.nextLine();
                System.out.print("Ingrese el email del alumno: ");
                String emailAlumno = scanner.nextLine();
                System.out.print("Ingrese el curso del alumno: ");
                int curso = scanner.nextInt();
                scanner.nextLine(); 
                System.out.print("Ingrese el número de libreta del alumno: ");
                int numeroLibreta = scanner.nextInt();
                scanner.nextLine(); 
                
                Alumno alumno = new Alumno(id1, nombreAlumno, apellidoAlumno, emailAlumno, curso, numeroLibreta);
                CollectionUsuario.agregarUsuario(alumno);
                
                alumno.mostrarDatos();
                
                System.out.println("\nUsuario agregado correctamente.");           
            break;
            case "2":
            	System.out.print("\nIngrese el ID del usuario: ");
                String id2 = scanner.nextLine();
                System.out.print("Ingrese el nombre del bibliotecario: ");
                String nombreBibliotecario = scanner.nextLine();
                System.out.print("Ingrese el apellido del bibliotecario: ");
                String apellidoBibliotecario = scanner.nextLine();
                System.out.print("Ingrese el email del bibliotecario: ");
                String emailBibliotecario = scanner.nextLine();
                System.out.print("Ingrese el legajo del bibliotecario: ");
                int legajo = scanner.nextInt();
                scanner.nextLine(); 
                
                Bibliotecario bibliotecario = new Bibliotecario(id2, nombreBibliotecario, apellidoBibliotecario, emailBibliotecario, legajo);
                CollectionUsuario.agregarUsuario(bibliotecario);
                
                bibliotecario.mostrarDatos();
                
                System.out.println("\nUsuario agregado correctamente.");           
            break;
            default: System.out.println("\nError al seleccionar opcion");
            }       
        } catch (InputMismatchException e) {
            System.out.println("\nError: valor invalido \nNo se agrego el usuario.");
            scanner.nextLine();
        }
    }
    
    public static void registrarPrestamo(Scanner scanner) {
    	
    	List<Libro> libros = CollectionLibro.libros;
    	for (Libro libro : libros) {
    		if(libro.isEstado()) {
    			libro.mostrarDatos();
    		}
    	}
    	try {    		
        	System.out.print("\nIngrese el ID del libro que se va a prestar: ");
        	String idLibro = scanner.nextLine();
        	Libro libroPrestado = CollectionLibro.buscarLibroPorCodigo(idLibro);
        	if (libroPrestado == null) {
                throw new LibroNoEncontradoException("El libro con ID " + idLibro + " no se encuentra.");
            }
        
        	if (libroPrestado.isEstado()==false){
                throw new LibroNoDisponibleException("\nEl libro con ID " + idLibro + " no está disponible para prestar.");
            }
             
           	
	    	List<Usuario> usuarios = CollectionUsuario.usuarios;
	    	for(Usuario usuario : usuarios) {
	    		usuario.mostrarDatos();
	    	}
	    	System.out.print("\nIngrese el ID del usuario a quien se va a prestar: ");
	    	String idUsuario = scanner.nextLine();    	
	    	Usuario usuarioPrestado = CollectionUsuario.buscarUsuarioPorID(idUsuario);
	    	
	    	if (usuarioPrestado == null) {
	            throw new UsuarioNoRegistradoException("\nEl usuario con ID " + idUsuario + " no está registrado.");
	        }
	    	
			System.out.print("\nIngrese el ID del préstamo: ");
			String idPrestamo = scanner.nextLine();
	    	
			
			System.out.print("Ingrese la fecha del préstamo (YYYY-MM-DD): ");
			String fechaPrestamo = scanner.nextLine();
			LocalDate fecPrestamo;
			
	        try {
	        	
	        	fecPrestamo = FechaUtil.convertirStringLocalDate(fechaPrestamo);
	        	
	        } catch (DateTimeParseException e) {
	        	
	            System.out.println("\nError: Formato inválido. Debe ser YYYY-MM-DD");
	            return; 
	            
	        }
	    	
			Prestamo prestamo = new Prestamo(idPrestamo, fecPrestamo, null, libroPrestado, usuarioPrestado);
			CollectionPrestamo.agregarPrestamo(prestamo);
			prestamo.mostrarDatos();
			System.out.println("\nPrestamo realizado correctamente.");
			libroPrestado.setEstado(false);
	
	        } catch (LibroNoEncontradoException | LibroNoDisponibleException | UsuarioNoRegistradoException e) {
	            System.out.println("\nError: " + e.getMessage());
	        } catch (Exception e) {
	            System.out.println("\nOcurrió un error inesperado: " + e.getMessage());
	        }
    }
    
    public static void registrarDevolucion(Scanner scanner) {
        List<Prestamo> prestamos = CollectionPrestamo.prestamos;
        
        prestamos.stream().filter(prestamo -> prestamo.getFechaDevolucion()==null).forEach(prestamo -> prestamo.mostrarDatos());
        
        try {
            System.out.print("\nIngrese el ID del préstamo ha devolver: ");
            String idPrestamo = scanner.nextLine();
            
            Prestamo prestamo = prestamos.stream().filter(p -> p.getId().equals(idPrestamo)).findFirst().orElse(null);
            
            if (prestamo == null) {
                System.out.println("\nError: Préstamo no encontrado.");
                return;
            }
            
            System.out.print("Ingrese la fecha de devolución (YYYY-MM-DD): ");
            String fechaDevolucion = scanner.nextLine();
            
            LocalDate fecha = FechaUtil.convertirStringLocalDate(fechaDevolucion);
            
            prestamo.registrarDevolucion(fecha);
            System.out.println("\nDevolución registrada correctamente.");
            
        } catch (DateTimeParseException e) {
            System.out.println("\nError: Formato de fecha inválido.");
        } catch (Exception e) {
            System.out.println("\nError inesperado: " + e.getMessage());
        }
    }
}