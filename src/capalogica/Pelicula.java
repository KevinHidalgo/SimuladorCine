package capalogica;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Kevin Hidalgo
 */
public class Pelicula {
    // Atributos
    private String nombre;
    private int numeroSala;
//    private double precio;  // Para que el precio ? si ya se manejan en la clase cine 
    private double ventas; // Para controlar las ventas que se hacen
    private Date fecha;
    private String hora;
    
    // Constructor
   public Pelicula(String nombre, int numeroSala, Date fecha) {
        this.nombre = nombre;
        this.numeroSala = numeroSala;// la persona coloca del 1 al 3 pero el arreglo inicia en 0
 //       this.precio = precio;
        this.ventas=0;
        this.fecha = fecha;
    }
   
    // Metodos SET y GET
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroSala() {
        return numeroSala;
    }

    public void setNumeroSala(int numeroSala) {
        this.numeroSala = numeroSala;
    }

    public double getVentas() {
        return ventas;
    }

    public void setVentas(double ventas) {
        this.ventas = ventas;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) throws ParseException {
            // Formato de entrada y salida
            SimpleDateFormat formatoMilitar = new SimpleDateFormat("HH:mm");
            SimpleDateFormat formatoAMPM = new SimpleDateFormat("hh:mm a");   
             try {
            // Parsear la hora militar
            Date fechaHora = formatoMilitar.parse(hora);
            // Convertir al formato AM/PM
            String horaConAMPM = formatoAMPM.format(fechaHora);
            this.hora = horaConAMPM;
              } catch (ParseException e) {
                System.out.println("Formato de hora inválido.");
              }
    }

    // Otros Metodos
    @Override
    public  String toString(){
    SimpleDateFormat  formatoFecha  =  new  SimpleDateFormat("dd 'de' MMMM  'de' yyyy");
    String hilera="";
        hilera += "Pelicula: " + nombre + "\n";
        hilera += "Numero De Sala: " + (numeroSala+1) + "\n";
        hilera += "Fecha: " + formatoFecha.format(fecha) + "\n";
        hilera +="Hora: " + hora + "\n";
    return hilera;
    }
    
}
