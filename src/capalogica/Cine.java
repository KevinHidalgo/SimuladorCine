package capalogica;

/**
 *
 * @author Kevin Hidalgo
 */
public class Cine {
     // Atributos
    private final double REGULAR=2800; // PRECIOS
    private final double MAYOR=2300;
    private final double NINIO=2000;
    private Pelicula[] peliculas;
    private Cliente[][] Sala1;
    private Cliente[][] Sala2;
    private Cliente[][] Sala3;
    private boolean existePelicula;
    
    // Constructor
    public Cine() {
        this.peliculas = new Pelicula[3];
        this.Sala1 = new Cliente[10][8];
        this.Sala2 = new Cliente[10][8];
        this.Sala3 = new Cliente[10][8];
        this.existePelicula=false;
    }
    
    // Metodos SET y GET
    public Pelicula[] getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(Pelicula[] peliculas) {
        this.peliculas = peliculas;
    }

    public Cliente[][] getSala1() {
        return Sala1;
    }

    public void setSala1(Cliente[][] Sala1) {
        this.Sala1 = Sala1;
    }

    public Cliente[][] getSala2() {
        return Sala2;
    }

    public void setSala2(Cliente[][] Sala2) {
        this.Sala2 = Sala2;
    }

    public Cliente[][] getSala3() {
        return Sala3;
    }

    public void setSala3(Cliente[][] Sala3) {
        this.Sala3 = Sala3;
    }

      public Cliente[][] getSala(int numero) {
        if(numero==0){
        return Sala1;
        }else if(numero==1){
        return Sala2;
        }else{
        return Sala3;
        }
          
    }
    
    public boolean isExistePelicula() {
        return existePelicula;
    }

    public void setExistePelicula(boolean existePelicula) {
        this.existePelicula = existePelicula;
    }

    // Otros Metodos
    public boolean ingresarPelicula(Pelicula miPelicula){
    boolean ingresoPelicula= false;
        for(int i=0;i<peliculas.length;i++){
          if(peliculas[i] == null){ // si hay campo para agregar pelicula
                    switch(i){
                        case 0: // primer pelicula, se ingresa directamente
                            peliculas[i]=miPelicula;
                            ingresoPelicula= true;
                            i=peliculas.length;// para que salga del for
                            break;
                        case 1:
                            if(peliculas[i-1].getNombre().equalsIgnoreCase(miPelicula.getNombre())){
                           ingresoPelicula= false;
                                i=peliculas.length; // para que salga del for
                            }else{
                                peliculas[i]=miPelicula;
                                ingresoPelicula= true;
                                i=peliculas.length;// para que salga del for
                            }
                            break;
                        case 2:
                            if(peliculas[i-2].getNombre().equalsIgnoreCase(miPelicula.getNombre()) || 
                                    peliculas[i-1].getNombre().equalsIgnoreCase(miPelicula.getNombre())){
                                 ingresoPelicula= false;
                                i=peliculas.length; // para que salga del for
                            }else{
                              peliculas[i]=miPelicula;
                                ingresoPelicula= true;                            
                            }
                            break;
                    }
            }
        }
    return ingresoPelicula;
    }
    
    public String mostrarPrecios(){
       String hilera ="Regular: " + "₡"+this.REGULAR + "   Mayor: " + "₡"+this.MAYOR + "   Niños: " + "₡"+this.NINIO ;
        return hilera;
    }
    
    public String mostrarCartelera(){
        String hilera="Lista de Peliculas\n";
           for(int i=0;i<this.peliculas.length;i++) {
                if(this.peliculas[i] != null){
                hilera += this.peliculas[i].toString()+"\n";
                } 
           }
        return hilera;
    }
    
    public String mostrarPelicula(int numero){
            return  this.peliculas[numero].toString();
    }
    
    public String mostrarSala(int numeroSala){ // 1,2 o3
      int sala=numeroSala-1;
      Cliente[][] salaCopia;
      if(sala==0){
      salaCopia = this.Sala1;
      }else if(sala==1){
         salaCopia = this.Sala2;
      }else{
         salaCopia = this.Sala3;
      }
      String hilera="Sala: N-" + numeroSala + "\n\n";
                hilera+="     0   1    2    3   4   5   6   7\n";
                for(int i=0; i<salaCopia.length;i++){
                    switch(i){
                            case 0:
                            hilera+="A";
                            break;
                            case 1:
                            hilera+="B";
                            break;
                            case 2:
                            hilera+="C";
                            break;
                            case 3:
                            hilera+="D";
                            break;
                            case 4:
                            hilera+="E";
                            break;
                            case 5:
                            hilera+="F";
                            break;
                            case 6:
                            hilera+="G";
                            break;
                            case 7:
                            hilera+="H";
                            break;
                            case 8:
                            hilera+="I";
                            break;
                            case 9:
                            hilera+="J";
                            break;
                    }
                      for(int j=0; j<salaCopia[i].length;j++){
                          if(salaCopia[i][j]==null){
                            hilera += "   O"; // Campo Vacio puede comprarlo 
                          }else if(salaCopia[i][j].getNombre().equalsIgnoreCase("RESERVADO")){ // el cliente default tendra la palabra RESERVADO antes de ser comprado
                              hilera += "   R";// Campo Reservado pero aun no ha sido comprado
                          }else{
                            hilera += "   X";// Campo ha sido comprado
                          }
                    }
                      hilera+="\n";
                }
                      hilera+="\nO=Campo Vacio\n";
                      hilera+="X=Campo Comprado\n";
                      hilera+="R=Campo Reservado\n";
        return hilera;
    }
    public int totalCantidadAsientos(int numeroSala){ // 1,2 o3
     int cantidadAsientos=0;
      int sala=numeroSala-1;
      Cliente[][] salaCopia;
      if(sala==0){
      salaCopia = this.Sala1;
      }else if(sala==1){
         salaCopia = this.Sala2;
      }else{
         salaCopia = this.Sala3;
      }
                for(int i=0; i<salaCopia.length;i++){   
                for(int j=0; j<salaCopia[i].length;j++){ // tomar en cuenta que en una reservacion pueden comprar 4 campos
                    if(salaCopia[i][j] != null && !salaCopia[i][j].getNombre().equalsIgnoreCase("RESARVADO")){ // el campo esta comprado pero no reservado
                    cantidadAsientos++;
                    }
                }
           }
    return cantidadAsientos;
    }
    
    public void sumarVentas(int numeroPelicula,double montoVenta){ // suma la ventas de las salas 1,2 o 3
        this.peliculas[numeroPelicula].setVentas( this.peliculas[numeroPelicula].getVentas() + montoVenta); // ir acumulando cada venta
    }  
    
    public String precioTotalBoletos(int regular,int mayor,int ninos){ // Recibe cantidad boletos
        String resultado = " ";
        double total = regular * this.REGULAR + mayor * this.MAYOR+ ninos * this.NINIO;
        resultado += "Total Boletos: ₡" + total;
        return resultado;
    }
        public double precioTotalBoletos2(int regular,int mayor,int ninos){ // Recibe cantidad boletos 
        double total = regular * this.REGULAR + mayor * this.MAYOR+ ninos * this.NINIO;
        return total;
    }
}
