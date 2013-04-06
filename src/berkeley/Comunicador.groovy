/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package berkeley;

/**
 *
 * @author uzielgl
 */
class Comunicador {
    public UDPServer udpServer;
    public UDPClient udpClient;
    
    Comunicador(String ip, int port){
        udpServer = new UDPServer(ip, port); //Siempre levanta el servidor
        udpClient = new UDPClient();
    }
    
    /**
     * Sólo se enviarán objectos "Mensaje".
     **/
    public sendMessage(Process p, Message m){
        udpClient.sendMenssage( p.ip, p.port, m );
    }
    
    public Message sendMessageReply(Process p, Message m){
        return udpClient.sendMenssageReply( p.ip, p.port, m );
    }
    
    public startServer(){
        this.udpServer.start();
    }
	
}

