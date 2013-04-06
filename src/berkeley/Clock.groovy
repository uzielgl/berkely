/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package berkeley
import java.text.SimpleDateFormat;

/**
 *
 * @author uzielgl
 */
class Clock extends Thread{
    public long timestamp; //El timestamp, este será el que se ira incrementando en uno
    public Calendar calendar = Calendar.getInstance(); //Con ese puedo acceder a las horas, minutos y segundos
    public int interval = 1000; // Cada cuantos milesegundos se incrementa en uno el intervalo
    public int gap = 0; //Desfase
    public int interval_relantelizado = 2000;
    public int id = new Random().nextInt(100);
    
    public List<ClockListener> listeners = new ArrayList<ClockListener>();
    
    public Clock(){
        timestamp = System.currentTimeMillis() / 1000;
        updateCalendar(  timestamp ) ;
    }
    
    public Clock( long timestamp ){ 
        this.timestamp = timestamp;
        updateCalendar( timestamp );
    }
    
    public Clock( int segundos ){ 
        timestamp = ( System.currentTimeMillis() / 1000 ) + segundos;
        updateCalendar(  timestamp ) ;
    }
    
    //@override
    public void run(){
        while( true ){
            if( gap > 0 ){
                Thread.sleep( interval_relantelizado );
                gap--;
            }else{
                Thread.sleep( interval );
            }       
            timestamp++;
            updateCalendar( timestamp );
            println this;
        }
    }
    
    
    public void updateCalendar(long timestamp){
        calendar.setTimeInMillis( timestamp * 1000 );
    }
    
    /** Actualiza el reloj según un desfaze que le da el coordinador
     *si el desface es positivo, significa que este reloj está adelantado
     *y hay que relantizarlo.
     *Si el desface es negativo, significa que este relojo está atrasado, y
     *sólo hay que actualizarlo
     **/
    public void updateClock( int gap){
        if( gap > 0){ //relantizamos
            this.gap = gap;
        }else{ //Agregamos los segundos
            updateCalendar( timestamp + abs(gap) );
        }
    }
    
    public String toString(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setCalendar( calendar );
        return "ID: $id - " + df.format( calendar.getTime() );
    }
}

