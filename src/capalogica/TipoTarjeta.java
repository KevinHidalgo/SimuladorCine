
package capalogica;

/**
 *
 * @author Kevin Hidalgo
 */
public enum TipoTarjeta {
           VISA{
        @Override
        public String toString(){
           return "Visa";
        }
    },
          MASTERCARD{
        @Override
        public String toString(){
           return "Master Card";
        }
    };
}
