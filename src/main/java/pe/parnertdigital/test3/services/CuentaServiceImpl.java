package pe.parnertdigital.test3.services;

import org.springframework.beans.factory.annotation.Autowired;
import pe.parnertdigital.test3.models.Banco;
import pe.parnertdigital.test3.models.Cuenta;
import pe.parnertdigital.test3.repositories.BancoRepository;
import pe.parnertdigital.test3.repositories.CuentaRespository;

import java.math.BigDecimal;

public class CuentaServiceImpl implements CuentaService{

    private CuentaRespository cuentaRespository;
    private BancoRepository bancoRepository;

    @Autowired
    public CuentaServiceImpl(CuentaRespository cuentaRespository, BancoRepository bancoRepository) {
        this.cuentaRespository = cuentaRespository;
        this.bancoRepository = bancoRepository;
    }

    @Override
    public Cuenta buscarPorId(Long id) {
        return cuentaRespository.buscarPorId(id);
    }

    @Override
    public int revisarTotalTransferencias(Long bancoId) {
        Banco banco = bancoRepository.buscarPorId(bancoId);
        return banco.getTotalTransferencias();
    }

    @Override
    public BigDecimal revisarSaldo(Long cuentaId) {
        Cuenta cuenta = cuentaRespository.buscarPorId(cuentaId);
        return cuenta.getSaldo();
    }

    @Override
    public void tranferir(Long numCuentaOrigen, Long numCuentaDestino, BigDecimal monto, Long bancoId) {

        Cuenta cuentaOrigen = cuentaRespository.buscarPorId(numCuentaOrigen);
        cuentaOrigen.debito(monto);
        cuentaRespository.actualizar(cuentaOrigen);

        Cuenta cuentaDestino = cuentaRespository.buscarPorId(numCuentaDestino);
        cuentaDestino.credito(monto);
        cuentaRespository.actualizar(cuentaDestino);


        //primero actualizar el total de transferencia
        Banco banco = bancoRepository.buscarPorId(bancoId);
        int totalTransferencias = banco.getTotalTransferencias();
        banco.setTotalTransferencias(++totalTransferencias);
        bancoRepository.actualizar(banco);

    }
}
