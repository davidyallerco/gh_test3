package pe.parnertdigital.test3.repositories;

import pe.parnertdigital.test3.models.Cuenta;

import java.util.List;

public interface CuentaRespository {
    List<Cuenta> buscarTodos();
    Cuenta buscarPorId(Long id);
    void actualizar(Cuenta cuenta);
}
