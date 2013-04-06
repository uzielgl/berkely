/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package berkeley;

/**
 *
 * @author uzielgl
 */
public interface ComunicatorListener {
    
    void onReceiveMessage( Message m );
    
    //Cuando recibe un mensaje de tipo GET_HOUR debe de regresar un mensaje con la hora
    Message onGetHour();
}
