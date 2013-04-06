/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package berkeley;

/**
 *
 * @author uzielgl
 */
public class Berkeley {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Clock c = new Clock( 30 );
        Clock c2 = new Clock();
        c.start();
        c2.start();
        
        c.updateClock( 30 );
    }
}
