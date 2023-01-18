package pe.parnertdigital.test3.repositories;

import pe.parnertdigital.test3.models.Banco;

import java.util.List;

public interface BancoRepository {
    List<Banco> buscarTodos();
    Banco buscarPorId(Long id);
    void actualizar(Banco banco);
}
