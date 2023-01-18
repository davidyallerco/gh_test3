package pe.parnertdigital.test3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import pe.parnertdigital.test3.repositories.BancoRepository;
import pe.parnertdigital.test3.repositories.CuentaRespository;
import pe.parnertdigital.test3.services.CuentaService;
import pe.parnertdigital.test3.services.CuentaServiceImpl;

import java.math.BigDecimal;

import static  org.mockito.Mockito.*;

@SpringBootTest
class Test3ApplicationTests {

	CuentaRespository cuentaRespository;
	BancoRepository bancoRepository;

	CuentaService service;

	@BeforeEach
	void setUp() {
		cuentaRespository = mock(CuentaRespository.class);
		bancoRepository = mock(BancoRepository.class);
		service = new CuentaServiceImpl(cuentaRespository, bancoRepository);
	}

	@Test
	void contextLoads() {
		when(cuentaRespository.buscarPorId(1L)).thenReturn(Datos.CUENTA_001);
		when(cuentaRespository.buscarPorId(2L)).thenReturn(Datos.CUENTA_002);
		when(bancoRepository.buscarPorId(1L)).thenReturn(Datos.BANCO);

		//antes de realizar la transferencia
		BigDecimal saldoOrigen = service.revisarSaldo(1L);
		BigDecimal saldoDestino = service.revisarSaldo(2L);
		
	}

}
