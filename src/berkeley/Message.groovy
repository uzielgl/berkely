/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package berkeley;
import java.io.Serializable;
/**
 *
 * @author uzielgl
 */
class Message implements Serializable{
    public static final int TYPE_GET_HOUR = 1;
    public static final int TYPE_RESPONSE_HOUR = 2;
    public static final int TYPE_SET_HOUR = 3; 
    
    public int type;
    
}

