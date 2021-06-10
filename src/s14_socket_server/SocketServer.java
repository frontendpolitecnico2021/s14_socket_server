package s14_socket_server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * SocketServer --- Programa que actua como servidor de peticiones del Banco Politécnico.
 * @author Martinez Alzate John Alejandro
 * @author Norena Arenas Yeferson Eduardo
 * @author Ortiz Gutierrez Raul Enrique
 * @author Salazar Gallego Kevin De Jesus
 * @author Fajardo Barragan Leidy Marcela
 * 
 */

public class SocketServer extends Thread {
	
	   /** Instacia de la clase Server Socket del paquete de utilidades de red de Java */
	   private ServerSocket serverSocket;   
	  
	   /** Puerto Definido para escuchar peticiones o solicitudes  */
	   private static int PORT = 3000;
	   
	   /** Tiempo de sessión del servidor de sockets en milisegundos */
	   private static int TIMESESSION = 0;
	   
	   
	   /**
	    * Constructor de la clase SocketServer
	    * @param port. Puerto de ejecución del servidor de sockets
	    * @throws IOException - Lanza error en caso de que la lectura de información por terminal falle
	    * */
	   public SocketServer(int port) throws IOException {
	      serverSocket = new ServerSocket(port);	    
	   }// Cierre del constructor

	   /**
	    * Este método indica al intérprete de Java que cree un 
	    * contexto del hilo del sistema y comience a ejecutarlo
	    * 
	    * */
	   public void run() {
		   // Bucle que se ejecuta como daemon mientras no se genere un error 
	      while(true) {
	         try {
	          // Imprime en consola  la conexión al puerto definido 
	        	 System.out.println("Conectando al servidor por el puerto: " + 
	               serverSocket.getLocalPort() + "...");
	        	 // Acepta peticiones del socket
	            Socket server = serverSocket.accept();
	            
	            // habilita o deshabilita la opción timeout con el valor 
	            // de tiempo de espera dado, en milisegundos
	            server.setSoTimeout(TIMESESSION);
	            
	            // Imprime en consola el cliente conectado 
	            System.out.println("Cliente conectado  desde: " + server.getRemoteSocketAddress());
	            DataInputStream in = new DataInputStream(server.getInputStream());
	            
	            // Imprime en consola la lectura del cliente
	            System.out.println("Lectura Cliente: " + in.readUTF());
	            DataOutputStream out = new DataOutputStream(server.getOutputStream());
	            // Escritura del servidor cuando termina la ejecución del servidor
	            out.writeUTF("Gracias por conectarse al servidor " + server.getLocalSocketAddress()
	               + "\nHasta Luego!");
	            
	            // Cierra la ejecución del servidor de sockets
	            server.close();
	            
	         } catch (SocketTimeoutException s) {
	            System.out.println("Timeout del servidor!");
	            break;
	         } catch (IOException e) {	        
	            e.printStackTrace();
	            break;
	         }
	         catch (Exception e) {
	        	 e.printStackTrace();
		            break;
			}
	      }
	   }// Cierra método run
	   /**
	    * Método para iniciar la aplicación
	    * @param args - Parametros que puede recibir para iniciar la aplicación
	    * */
	   public static void main(String [] args) {		   
	
	   //Captura exception cuando se genere un error
	      try {
	    	  
	    	  // Ejecución de la clase por medio de hilos donde llama al constructor y 
	    	  // recibe el puerto de ejecución
	         Thread t = new SocketServer(PORT);
	         
	         // Inicia la ejecución del hilo
	         t.start();
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	      catch (Exception e) {
	    	  e.printStackTrace();
		}
	   } // Cierre del método main
	}// Cierre de la clase SocketServer
