/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package berkeley;

/**
 *
 * @author uzielgl
 */
class Comunicator {
    public UDPServer udpServer;
    public UDPClient udpClient;
    
    Comunicator(String ip, int port){
        udpServer = new UDPServer(ip, port); //Siempre levanta el servidor
        udpClient = new UDPClient();
    }
    
    /**
     * Sólo se enviarán objectos "Mensaje".
     **/
    public sendMessage(Process p, Message m){
        udpClient.sendMessage( p.ip, p.port, m );
    }
    
    public Message sendMessageReply(Process p, Message m){
        return udpClient.sendMessageReply( p.ip, p.port, m );
    }
    
    public startServer(){
        this.udpServer.start();
    }
	
}

