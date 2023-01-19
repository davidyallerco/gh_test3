package pe.parnertdigital.test3;

import pe.parnertdigital.test3.models.Banco;
import pe.parnertdigital.test3.models.Cuenta;

import java.math.BigDecimal;

public class Datos {
    public static final Cuenta CUENTA_001 = new Cuenta(1L, "David", new BigDecimal("1000"));
    public static final Cuenta CUENTA_002 = new Cuenta(2L, "Ezer", new BigDecimal("2000"));
    public static final Banco BANCO = new Banco(1L, "El banco financiero", 0);


    public  static Cuenta crearCuenta001(){
        return new Cuenta(1L, "David", new BigDecimal("1000"));
    }
    public  static Cuenta crearCuenta002(){
        return new Cuenta(1L, "Ezer", new BigDecimal("2000"));
    }
    public static Banco crearBanco(){
        return new Banco(1L,"El banco financiero", 0);
    }
}
