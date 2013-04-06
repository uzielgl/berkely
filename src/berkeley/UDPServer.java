/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package berkeley;

/**
 *
 * @author uzielgl
 */
import java.net.*;
import java.io.*;
import java.util.*;

public class UDPServer extends Thread{
    private String ip;
    private String port;
    public List<ComunicatorListener> listeners = new ArrayList<ComunicatorListener>();
    
    UDPServer( String ip, String port ){
        this.ip = ip;
        this.port = port;
    }
    UDPServer( String ip, int port){
        this.ip = ip;
        this.port = Integer.toString( port );    
    }
    
    public void run(){
        System.out.println("LEvantando servidor en " + ip + ":" + port);
        try{
            DatagramSocket aSocket = new DatagramSocket( Integer.parseInt( port ) );
            while(true){
                
                //Lo recibimos
                byte[] buffer = new byte[1000];
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);
                
                //Deserializamos el objeto
                ByteArrayInputStream objIn = new ByteArrayInputStream( request.getData() );
                ObjectInputStream ois = new ObjectInputStream( objIn );
                try{
                    Message mensaje = (Message) ois.readObject();
                    for (ComunicatorListener cl : listeners) cl.onReceiveMessage( mensaje );
                    
                    //Si el mensaje es de tipo get_hour le enviamos la hora
                    if ( mensaje.type == Message.TYPE_GET_HOUR ){
                        for (ComunicatorListener cl : listeners){
                            //Hacemos la replica
                            
                            //Obtenemos el mensaje a enviar
                            Message m = cl.onGetHour();
                            //Lo serializamos
                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            ObjectOutputStream os;
                            try {
                                os = new ObjectOutputStream(bytes);
                                os.writeObject( m );  
                                os.close();
                            } catch (IOException ex) {
                                System.out.println("Error de ioexception en UDPClient:sendMessage");
                                ex.printStackTrace();
                            }
                            byte[] messageBytes = bytes.toByteArray();
                            
                            //Enviamos la replica con el mensaje con hora
                            DatagramPacket reply = new DatagramPacket( messageBytes, 
                                messageBytes.length, request.getAddress(), request.getPort());
                            aSocket.send(reply);
                        }
                    }
                    
                    System.out.print("Recibiendo mensaje con UDP: ");
                    System.out.println( mensaje );
                }catch( Exception e){
                    e.printStackTrace();
                }
                //System.out.println( new String( request.getData(), "UTF-8" ).trim() );
            } 
        }catch (SocketException e){System.out.println("Socket: " + e.getMessage());
        }catch (IOException e) {System.out.println("IO: " + e.getMessage());}
    }
} 
