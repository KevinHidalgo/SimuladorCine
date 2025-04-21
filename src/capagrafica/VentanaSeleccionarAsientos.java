package capagrafica;

import capalogica.Cine;
import capalogica.Cliente;
import capalogica.TipoTarjeta;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Kevin Hidalgo
 */
public class VentanaSeleccionarAsientos extends javax.swing.JFrame {
    FondoPanel fondo=new FondoPanel();
    private final Cine miCine;
    private int regular;
    private int mayor;
    private int ninos;
    private int numeroPelicula;
    private DefaultTableModel modelo;
    private boolean reservados;
    /**
     * Creates new form VentanaSeleccionarAsientos
     */
    public VentanaSeleccionarAsientos(Cine miCine,int regular,int mayor,int ninos,int numeroPelicula) {
        initComponents();
        this.miCine=miCine;
        this.regular= regular;
        this.mayor=mayor;
        this.ninos=ninos;
        this.numeroPelicula = numeroPelicula; // para conocer la sala que vamos a mostrar, obtenemos 0,1,2
        this.reservados = false; // para conocer cuando ya reservo todos los boletos solicitados
        modelo = (DefaultTableModel)  tblAsientos.getModel();
        llenarTabla();
       
     //   btnReservar.addActionListener(e ->  seleccionarAsientos());
    }

    private void llenarTabla() {
    // Letras de las filas
    String[] letrasFilas = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
      
    // Validar que la tabla tiene el tamaño esperado (10 filas, 8 columnas + 1 para letras)
    if (modelo.getRowCount() != 10 || modelo.getColumnCount() != 9) {
        JOptionPane.showMessageDialog(this, "La tabla no tiene el tamaño correcto.");
        return;
    }

    // Recorrer las filas y columnas de la matriz
    for (int fila = 0; fila < 10; fila++) {
        // Establecer la letra en la primera columna
        modelo.setValueAt(letrasFilas[fila], fila, 0);

        for (int columna = 0; columna < 8; columna++) {
            // Obtener el estado del asiento de la matriz
             Cliente cliente = miCine.getSala(numeroPelicula)[fila][columna]; // Obtiene matriz según sala 
                 
            String estado;
            if (cliente == null) {
                estado = "O"; // Asiento libre
            } else if ("RESERVADO".equals(cliente.getNombre())) {
                estado = "R"; // Asiento reservado
            } else {
                estado = "X"; // Asiento comprado
            }

            // Establecer el estado en la tabla (ajustar la columna por la letra)
            modelo.setValueAt(estado, fila, columna + 1);
        }
    }
}
private void actualizarTabla() {
    Cliente[][] matriz = miCine.getSala(numeroPelicula); // indice sala
    DefaultTableModel modelo = (DefaultTableModel) tblAsientos.getModel();

    for (int fila = 0; fila < 10; fila++) {
        for (int columna = 0; columna < 8; columna++) {
            Cliente cliente = matriz[fila][columna];
            String estado;

            if (cliente == null) {
                estado = "O"; // Libre
            } else if ("RESERVADO".equals(cliente.getNombre())) {
                estado = "R"; // Reservado
            } else {
                estado = "X"; // Comprado
            }

            modelo.setValueAt(estado, fila, columna + 1);
        }
    }
}

   
    private void seleccionarAsientos() {
    int seleccionados = 0;
    int cantidad = regular + mayor + ninos; // cantidad de boletos a reservar
    while (seleccionados < cantidad) {
        // Solicitar asiento al usuario
        String asiento = JOptionPane.showInputDialog(this, 
                "Seleccione un asiento (Ej: A1):", 
                "Seleccionar Asiento", 
                JOptionPane.QUESTION_MESSAGE);
        
        if (asiento == null || asiento.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Entrada inválida");
            continue;
        }

        // Validar formato (Ej: "A1")
        if (!asiento.matches("[A-J][1-8]")) {
            JOptionPane.showMessageDialog(this, "Formato inválido. Use una letra A-J y un número 1-8.");
            continue;
        }

        // Obtener fila y columna
        char filaChar = asiento.charAt(0);
        int columna = Character.getNumericValue(asiento.charAt(1)) - 1; // Convertir a índice
        int fila = filaChar - 'A'; // Convertir letra a índice (A=0, B=1, ...)

        // Verificar disponibilidad
        Cliente[][] matriz = miCine.getSala(numeroPelicula); //  índice de sala 
        Cliente cliente = matriz[fila][columna];
        if (cliente != null) {
            JOptionPane.showMessageDialog(this, "El asiento ya está ocupado o reservado.");
            continue;
        }

        // Reservar asiento
        matriz[fila][columna] = new Cliente("RESERVADO", TipoTarjeta.VISA, "***", 1, 2024, 000, 0); // Cliente con valores default
        tblAsientos.setValueAt("R", fila, columna + 1); // Mostrar "R" en la tabla

        seleccionados++;
        JOptionPane.showMessageDialog(this, "Asiento " + asiento + " reservado temporalmente.");
        actualizarTabla();
        if(seleccionados==cantidad){
          btnProcesarPago.setEnabled(true);
          btnReservar.setEnabled(false);
          JOptionPane.showMessageDialog(this, "Todos los asientos seleccionados.");
        }
    }
  
}

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Panel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAsientos = new javax.swing.JTable();
        btnReservar = new javax.swing.JButton();
        btnProcesarPago = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel3.setBackground(new java.awt.Color(204, 204, 204));
        jLabel3.setFont(new java.awt.Font("Verdana", 1, 36)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Seleccionar Asientos");

        tblAsientos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "", "1", "2", "3", "4", "5", "6", "7", "8"
            }
        ));
        jScrollPane1.setViewportView(tblAsientos);

        btnReservar.setBackground(new java.awt.Color(153, 153, 0));
        btnReservar.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        btnReservar.setForeground(new java.awt.Color(255, 255, 255));
        btnReservar.setText("Reservar");
        btnReservar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        btnReservar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReservar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReservarActionPerformed(evt);
            }
        });

        btnProcesarPago.setBackground(new java.awt.Color(153, 153, 0));
        btnProcesarPago.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        btnProcesarPago.setForeground(new java.awt.Color(255, 255, 255));
        btnProcesarPago.setText("Procesar Pago");
        btnProcesarPago.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        btnProcesarPago.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnProcesarPago.setEnabled(false);
        btnProcesarPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcesarPagoActionPerformed(evt);
            }
        });

        btnVolver.setBackground(new java.awt.Color(153, 153, 0));
        btnVolver.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        btnVolver.setForeground(new java.awt.Color(255, 255, 255));
        btnVolver.setText("Volver");
        btnVolver.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        btnVolver.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelLayout = new javax.swing.GroupLayout(Panel);
        Panel.setLayout(PanelLayout);
        PanelLayout.setHorizontalGroup(
            PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelLayout.createSequentialGroup()
                .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelLayout.createSequentialGroup()
                        .addGap(232, 232, 232)
                        .addComponent(btnReservar, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelLayout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(PanelLayout.createSequentialGroup()
                                .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnProcesarPago, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(PanelLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 543, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        PanelLayout.setVerticalGroup(
            PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReservar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProcesarPago, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnReservarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReservarActionPerformed
        seleccionarAsientos();
    }//GEN-LAST:event_btnReservarActionPerformed

    private void btnProcesarPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcesarPagoActionPerformed
        VentanaProcesarPago miVentana = new VentanaProcesarPago(miCine,regular, mayor, ninos,numeroPelicula);
        miVentana.setVisible(true);
        miVentana.setResizable(false);
        miVentana.setTitle("GOLDEN Cine Alajuela (Procesar Pago)");
        miVentana.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_btnProcesarPagoActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        VentanaDatosReservacion miVentana = new VentanaDatosReservacion(miCine);
        miVentana.setVisible(true);
        miVentana.setResizable(false);
        miVentana.setTitle("GOLDEN Cine Alajuela (Reservar Asientos)");
        miVentana.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Panel;
    private javax.swing.JButton btnProcesarPago;
    private javax.swing.JButton btnReservar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblAsientos;
    // End of variables declaration//GEN-END:variables
 class FondoPanel extends JPanel
    {
        private Image imagen;
        public void paint(Graphics g)
        {
        imagen = new ImageIcon(getClass().getResource("/imagenes/ReservacionAsientos.jpg")).getImage();
        g.drawImage(imagen, 0, 0, getWidth(),getHeight(),this);
        setOpaque(false);
        super.paint(g);
        }
    }
}
