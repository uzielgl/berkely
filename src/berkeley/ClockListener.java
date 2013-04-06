/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package berkeley;

/**
 *
 * @author uzielgl
 */
public interface ClockListener {
    
    /** Cuando cambia el segundo, se manda el clock*/
    void onChangeSecond( Clock clock );
    
    
}
