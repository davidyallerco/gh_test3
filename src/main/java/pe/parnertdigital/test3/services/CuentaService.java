package pe.parnertdigital.test3.services;

import pe.parnertdigital.test3.models.Cuenta;

import java.math.BigDecimal;

public interface CuentaService {
    Cuenta buscarPorId(Long id);
    int revisarTotalTransferencias(Long bancoId);
    BigDecimal revisarSaldo(Long cuentaId);
    void tranferir(Long numCuentaOrigen, Long numCuentaDestino, BigDecimal monto, Long bancoId);
}
