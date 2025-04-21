package proyectocine;

import capagrafica.VentanaInicial;

/**
 *
 * @author Kevin Hidalgo
 */
public class ProyectoCine {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      VentanaInicial miVentana = new VentanaInicial();
      miVentana.setVisible(true);
      miVentana.setResizable(false);
      miVentana.setTitle("GOLDEN Cine Alajuela");
      miVentana.setLocationRelativeTo(null);
    }
    
}
