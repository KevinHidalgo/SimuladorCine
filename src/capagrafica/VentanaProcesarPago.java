package capagrafica;

import capalogica.Cine;
import capalogica.Cliente;
import capalogica.TipoTarjeta;
import java.awt.Graphics;
import java.awt.Image;
import java.time.LocalDate;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Kevin Hidalgo
 */
public class VentanaProcesarPago extends javax.swing.JFrame {
    FondoPanel fondo=new FondoPanel();
    private final Cine miCine;
    private int regular; 
    private int mayor;
    private int ninos;
    private int numeroPelicula;
    private String asientosSeleccionados;
    /**
     * Creates new form VentanaProcesarPago
     */
    public VentanaProcesarPago(Cine miCine,int regular, int mayor, int ninos,int numeroPelicula) {
        initComponents();
        this.miCine = miCine;
        this.regular=regular;
        this.mayor=mayor;
        this.ninos=ninos;
        this.numeroPelicula=numeroPelicula;
        cargarTipoTarjetas();
        this.asientosSeleccionados=mostrarAsientos();
    }

    
    private void cargarTipoTarjetas() {
    cbTipo.removeAllItems(); // Limpia los elementos actuales, si hay alguno
    cbTipo.addItem(TipoTarjeta.VISA.toString()); // Añade tipo tarjeta al ComboBox  
    cbTipo.addItem(TipoTarjeta.MASTERCARD.toString()); // Añade tipo tarjeta al ComboBox  
    }
    

private boolean validarDatosTarjeta(String tipoTarjeta, String numeroTarjeta, String codigoSeguridad, String mes,String annio) {
    // Validar código de seguridad
    if (!codigoSeguridad.matches("\\d{3}")) {
        JOptionPane.showMessageDialog(this, "El código de seguridad debe ser un número de 3 dígitos.");
        return false;
    }

    try {
        // Validar fecha de vencimiento
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaVencimientoLocal = LocalDate.of(Integer.parseInt(annio), Integer.parseInt(mes), 1);

        if (fechaVencimientoLocal.isBefore(fechaActual)) {
            JOptionPane.showMessageDialog(this, "La fecha de vencimiento debe ser posterior a la fecha actual.");
            return false;
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "El mes o el año son letras o estan vacios");
        return false;
    }
  
    // Validar número de tarjeta
    if (!numeroTarjeta.matches("\\d{16}")) {
        JOptionPane.showMessageDialog(this, "El número de tarjeta debe contener exactamente 16 dígitos.");
        return false;
    }

    // Validar según tipo de tarjeta
    if ("Visa".equalsIgnoreCase(tipoTarjeta)) {
        if (!numeroTarjeta.startsWith("4")) {
            JOptionPane.showMessageDialog(this, "El número de tarjeta Visa debe comenzar con '4'.");
            return false;
        }
    } else if ("Master Card".equalsIgnoreCase(tipoTarjeta)) {
        int primerosDosDigitos = Integer.parseInt(numeroTarjeta.substring(0, 2));
        if (primerosDosDigitos < 51 || primerosDosDigitos > 55) {
            JOptionPane.showMessageDialog(this, "El número de tarjeta MasterCard debe comenzar con un número entre 51 y 55.");
            return false;
        }
    } else {
        JOptionPane.showMessageDialog(this, "Tipo de tarjeta inválido. Solo se aceptan Visa o MasterCard.");
        return false;
    }

    return true;
}

private boolean validarConLuhn(String numeroTarjeta) {
    if (numeroTarjeta == null || !numeroTarjeta.matches("\\d{16}")) {
        JOptionPane.showMessageDialog(this, "El número de tarjeta debe contener exactamente 16 dígitos.");
        return false;
    }

    int suma = 0;
    int longitud = numeroTarjeta.length();
    int digitoVerificacion = Character.getNumericValue(numeroTarjeta.charAt(longitud - 1)); // ultimo digito

   
    for (int i = 0; i < longitud - 1; i++) { // Procesar todos los dígitos excepto el último
        int digito = Character.getNumericValue(numeroTarjeta.charAt(i));

        // Multiplicar por 2 cada segundo dígito, comenzando desde el penúltimo
        if (i % 2 == (longitud - 2) % 2) { // Alternar posición
            digito *= 2;
            // Si el resultado tiene dos dígitos, sumar los dígitos individuales
            if (digito > 9) {
                digito -= 9; // Equivalente a sumar los dígitos ej: 10-9=1, 11-9=2, etc..
            }
        }

        suma += digito;
    }

    int resultado = suma * 9;
    int digitoCalculado = resultado % 10;    // El dígito de las unidades del resultado 

    if (digitoCalculado == digitoVerificacion) {
        return true; // Número de tarjeta válido
    } else {
        JOptionPane.showMessageDialog(this, "El número de tarjeta no es válido según el algoritmo de Luhn.");
        return false;
    }
}

public String mostrarAsientos(){
    String hilera="";
            Cliente matriz[][] = miCine.getSala(numeroPelicula);
              for (int fila = 0; fila < 10; fila++) {
                  for (int columna = 0; columna < 8; columna++) {
                      Cliente cliente = matriz[fila][columna];
                      if(cliente != null){
                            if ("RESERVADO".equals(cliente.getNombre())) {
                                int numero=columna+1;
                            hilera+=String.valueOf((char) ('A' + fila))+numero+" ";
                           }
                      }
                     
                  }
              }
    return hilera;
}

public String comprobantePago(int numeroReservacion, String nombre,String tipoTarjeta,String numeroTarjeta,double total){
    String hilera="GOLDEN Cine Alajuela\n\n";
            hilera+="Número de reservación: " + numeroReservacion+"\n";
            hilera+="Nombre del Cliente: " + nombre+"\n";
            hilera+="Tarjeta " + tipoTarjeta + " No. "+"xxxx-xxxx-xxxx-"+(numeroTarjeta.substring(numeroTarjeta.length() - 4))+"\n\n";
            hilera+= miCine.mostrarPelicula(numeroPelicula) +"\n";
            hilera+="Tiquetes comprados: " + regular + " regular,"+ mayor + " mayor,"+ ninos + " niño."+"\n";
            hilera+="Asientos seleccionados: " +this.asientosSeleccionados+"\n\n";
            hilera += "Total Pagado: " + total+"\n\n";
             hilera += "Que disfruten su pelicula !";
return hilera;
}

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnVolver = new javax.swing.JButton();
        btnConfirmar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        cbTipo = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtMes = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtAnnio = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtNumeroTarjeta = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 36)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Procesar Pago");

        btnVolver.setBackground(new java.awt.Color(153, 153, 0));
        btnVolver.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        btnVolver.setForeground(new java.awt.Color(255, 255, 255));
        btnVolver.setText("Regresar Menu Reservaciones");
        btnVolver.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        btnVolver.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        btnConfirmar.setBackground(new java.awt.Color(153, 153, 0));
        btnConfirmar.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        btnConfirmar.setForeground(new java.awt.Color(255, 255, 255));
        btnConfirmar.setText("Confirmar Compra");
        btnConfirmar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        btnConfirmar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel3.setText("Nombre del cliente:");

        cbTipo.setBackground(new java.awt.Color(153, 153, 0));
        cbTipo.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        cbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecciona una opción" }));
        cbTipo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel4.setText("Tipo Tarjeta:");

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel5.setText("Número de tarjeta:");

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel6.setText("Mes: ");

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel7.setText("Año:");

        jLabel8.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel8.setText("Fecha de vencimiento: ");

        jLabel9.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel9.setText("Código Tarjeta:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(199, 199, 199)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMes, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtAnnio, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(58, 58, 58)
                                .addComponent(cbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNumeroTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtAnnio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumeroTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        VentanaReservaciones miVentana = new VentanaReservaciones(miCine);
        miVentana.setVisible(true);
        miVentana.setResizable(false);
        miVentana.setTitle("GOLDEN Cine Alajuela (Reservaciones y Compras)");
        miVentana.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
    String tipoTarjeta = (String) cbTipo.getSelectedItem();
    String numeroTarjeta = txtNumeroTarjeta.getText();
    String codigoSeguridad = txtCodigo.getText();
    String mes = txtMes.getText();
    String annio = txtAnnio.getText();
    int numeroReservacion=0;
    boolean datosValidos = validarDatosTarjeta(tipoTarjeta, numeroTarjeta, codigoSeguridad, mes,annio);

    if (datosValidos && validarConLuhn(numeroTarjeta)) {
              int respuesta = JOptionPane.showConfirmDialog(
              null, 
              "¿Deseas confirmar la compra?", 
              "Confirmación", 
              JOptionPane.YES_NO_CANCEL_OPTION
          );

          if (respuesta == JOptionPane.YES_OPTION) {
              Cliente matriz[][] = miCine.getSala(numeroPelicula);
              for (int fila = 0; fila < 10; fila++) {
                  for (int columna = 0; columna < 8; columna++) {
                      Cliente cliente = matriz[fila][columna];
                    if(cliente != null){
                      if ("RESERVADO".equals(cliente.getNombre())) {
                          cliente.setNombre(txtNombre.getText());
                          if ("Visa".equalsIgnoreCase(tipoTarjeta.toString())) {
                              cliente.setTarjeta(TipoTarjeta.VISA);
                          } else {
                              cliente.setTarjeta(TipoTarjeta.MASTERCARD);
                          }
                          cliente.setNumeroTarjeta(numeroTarjeta);
                          cliente.setCodigoTarjeta(Integer.parseInt(codigoSeguridad));
                          cliente.setMes(Integer.parseInt(mes));
                          cliente.setAnnio(Integer.parseInt(annio));
                          cliente.setTotalPagado(miCine.precioTotalBoletos2(regular,mayor,ninos));
                          numeroReservacion=cliente.getNumeroReservacion();
                          miCine.getSala(numeroPelicula)[fila][columna]=cliente; // le caigo encima al cliente default
                      }
                     }
                  }
              }
              // Comprobante de pago
              JOptionPane.showMessageDialog(null, comprobantePago(numeroReservacion, txtNombre.getText(),tipoTarjeta,numeroTarjeta,miCine.precioTotalBoletos2(regular,mayor,ninos)));
              btnConfirmar.setEnabled(false); // Ya realizo la compra 
              miCine.sumarVentas(numeroPelicula,miCine.precioTotalBoletos2(regular,mayor,ninos));
          } else if (respuesta == JOptionPane.NO_OPTION) {
                for (int fila = 0; fila < 10; fila++) {
                  for (int columna = 0; columna < 8; columna++) {
                      Cliente cliente =  miCine.getSala(numeroPelicula)[fila][columna];

                      if ("RESERVADO".equals(cliente.getNombre())) {
                            miCine.getSala(numeroPelicula)[fila][columna]=null; // Elimino el cliente default
                      }
                  }
                }
                System.out.println("Compra rechazada.");
                VentanaReservaciones miVentana = new VentanaReservaciones(miCine);
                miVentana.setVisible(true);
                miVentana.setResizable(false);
                miVentana.setTitle("GOLDEN Cine Alajuela (Reservaciones y Compras)");
                miVentana.setLocationRelativeTo(null);
                this.dispose();
          } else if (respuesta == JOptionPane.CANCEL_OPTION) {
              JOptionPane.showMessageDialog(null,"Compra cancelada. Aun puedes confirmar la compra");
          } else {
              System.out.println("El cuadro de diálogo se cerró sin respuesta. Aun puedes confirmar la compra");
          }

    }
    }//GEN-LAST:event_btnConfirmarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JComboBox<String> cbTipo;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtAnnio;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtMes;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNumeroTarjeta;
    // End of variables declaration//GEN-END:variables
 class FondoPanel extends JPanel
    {
        private Image imagen;
        public void paint(Graphics g)
        {
        imagen = new ImageIcon(getClass().getResource("/imagenes/FondoMenuReservaciones.jpg")).getImage();
        g.drawImage(imagen, 0, 0, getWidth(),getHeight(),this);
        setOpaque(false);
        super.paint(g);
        }
    }
}
