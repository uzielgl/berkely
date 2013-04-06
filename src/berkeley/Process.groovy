/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package berkeley

/**
 *
 * @author uzielgl
 */
class Process implements ComunicatorListener{
    public int id;
    public String ip;
    public int port;
    
    public boolean isCoordinator = false;
    
    public Comunicator comunicator;
    
    public Clock clock;
    
    public int gapClock = new Random().nextInt(600) - 300;
    
    public long timestamp; //Sirve de auxiliar para el calculo de los promedios, NO para el reloj
    
    public BerkeleyGUI window;
    
    //Sólo si es coordinador
    public ArrayList<Process> slaves; //Sólo si el proceso es coordinador
    public int gapAdmissible = 180; // Segundos. Mientras el desface de los sclavos
                                    // sea menor a esto, se considerarán para el promedio
    
    
    public Process(int id, String ip, int port){
        this.id = id;
        this.ip = ip;
        this.port = port;
        comunicator = new Comunicator(ip, port);
        comunicator.udpServer.listeners.add(this);
        clock = new Clock( gapClock );
        clock.start();
    }
    
    public Process(int id, String ip, int port, boolean is_coordinator){
        isCoordinator = is_coordinator;
        this.id = id;
        this.ip = ip;
        this.port = port;
        comunicator = new Comunicator(ip, port);
        comunicator.udpServer.listeners.add(this);
        clock = new Clock( gapClock );
        clock.start();
    }
        
    public startServer(){
        comunicator.startServer();
    }
    
    //Sólo para los coordinadores
    
    /** Setter de los slaves*/
    public void setSlaves( ArrayList<Process> processes ){
        this.slaves = processes;
    }
    
    /* Hara todo el algoritmo de sincronización*/
    public void synchronizeClocks(){
        ArrayList<Long> times = new ArrayList<Long>();
        for( Process p: slaves ){
            Message m = comunicator.sendMessageReply( p, new Message( Message.TYPE_GET_HOUR ) );
            p.timestamp = m.timestamp;
            if( m.timestamp != 0 && m.timestamp <= clock.timestamp + gapAdmissible && m.timestamp >= clock.timestamp - gapAdmissible ){
                times.add( m.timestamp );
            }
        }
        window.addHistory( "Tiempos a promediar :" + times );
        long avg_time = ( times.sum() / times.size() ); /// asd
        
        //Calculamos el desface para cada proceso y se lo enviamos
        for( Process p: slaves ){
            Message m = new Message( Message.TYPE_SET_HOUR );
            m.gap =  p.timestamp - avg_time;
            window.addHistory( "desface para $p.id : $m.gap" );
            comunicator.sendMessage( p, m);
        }
        
    }
    
    //@overray
    public void onReceiveMessage( Message m ){
        if( m.type == Message.TYPE_SET_HOUR){
            clock.updateClock( m.gap );
            window.addHistory( "Desface de " + m.gap );
        }
    }
    
    //Cuando recibe un mensaje de tipo GET_HOUR debe de regresar un mensaje con la hora
    public Message onGetHour(){
        Message m = new Message( Message.TYPE_RESPONSE_HOUR );
        m.timestamp = clock.timestamp;
        return m;
    }
}

