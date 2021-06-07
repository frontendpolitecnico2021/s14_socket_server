package s14_socket_server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class SocketServer extends Thread {
	   private ServerSocket serverSocket;
	   
	   public SocketServer(int port) throws IOException {
	      serverSocket = new ServerSocket(port);
	      //serverSocket.setSoTimeout(10000);
	   }

	   public void run() {
	      while(true) {
	         try {
	            System.out.println("Waiting for client on port " + 
	               serverSocket.getLocalPort() + "...");
	            Socket server = serverSocket.accept();
	            
	            System.out.println("Just connected to " + server.getRemoteSocketAddress());
	            DataInputStream in = new DataInputStream(server.getInputStream());
	            
	            System.out.println("Lectura Client: " + in.readUTF());
	            DataOutputStream out = new DataOutputStream(server.getOutputStream());
	            
	          //  BufferedReader readRequest = new BufferedReader(new InputStreamReader(server.getInputStream()));
	            out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress()
	               + "\nGoodbye!");
	            server.close();
	            
	         } catch (SocketTimeoutException s) {
	            System.out.println("Socket timed out!");
	            break;
	         } catch (IOException e) {
	            e.printStackTrace();
	            break;
	         }
	      }
	   }
	   
	   public static void main(String [] args) {
	    //  int port = Integer.parseInt(args[0]);
		   
		   int port = 3000;
	      try {
	         Thread t = new SocketServer(port);
	         t.start();
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	   }
	}