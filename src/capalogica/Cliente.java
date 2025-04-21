package capalogica;

/**
 *
 * @author Kevin Hidalgo
 */
public class Cliente {
    // Atributos
    private static int consecutivoReservacion=999; // para iniciar numero reservacion en 1000
    private String nombre;
    private int numeroReservacion;
    private TipoTarjeta tarjeta;  // enum
    private String numeroTarjeta;
    private int mes;         // fecha vencimiento tarjeta
    private int annio;
    private int codigoTarjeta;
    private double totalPagado;
    
    // Constructor
   public Cliente(String nombre, TipoTarjeta tarjeta, String numeroTarjeta, int mes, int annio, int codigoTarjeta, double totalPagado) {
        this.nombre = nombre;
        this.numeroReservacion = numeroReservacion(); // Metodo que maneja los consecutivos apartir de 1000
        this.tarjeta = tarjeta;
        this.numeroTarjeta = numeroTarjeta;
        this.mes = mes;
        this.annio = annio;
        this.codigoTarjeta = codigoTarjeta;
        this.totalPagado = totalPagado;
    }
    // Metodos SET y GET
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroReservacion() {
        return numeroReservacion;
    }

    public void setNumeroReservacion(int numeroReservacion) {
        this.numeroReservacion = numeroReservacion;
    }

    public TipoTarjeta getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(TipoTarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnnio() {
        return annio;
    }

    public void setAnnio(int annio) {
        this.annio = annio;
    }

    public int getCodigoTarjeta() {
        return codigoTarjeta;
    }

    public void setCodigoTarjeta(int codigoTarjeta) {
        this.codigoTarjeta = codigoTarjeta;
    }

    public double getTotalPagado() {
        return totalPagado;
    }

    public void setTotalPagado(double totalPagado) {
        this.totalPagado = totalPagado;
    }
    
 // Otros metodos
      public int numeroReservacion() {
      return consecutivoReservacion+1; // la primera vez la variable consecutivoReservacion es 999 
                                                         //e inmediatamente aumenta a 1000 para la proxima reservacion sera 1001, asi sucesivamente. 
     }
    
    
}
