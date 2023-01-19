package pe.parnertdigital.test3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import pe.parnertdigital.test3.repositories.BancoRepository;
import pe.parnertdigital.test3.repositories.CuentaRespository;
import pe.parnertdigital.test3.services.CuentaService;
import pe.parnertdigital.test3.services.CuentaServiceImpl;
import static org.junit.jupiter.api.Assertions.*;

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

		//antes de realizar la transferencia, la cuenta 1 va transferir a la cuenta 2
		BigDecimal saldoOrigen = service.revisarSaldo(1L);
		BigDecimal saldoDestino = service.revisarSaldo(2L);
		//vemos como estan los saldos
		assertEquals("1000", saldoOrigen.toPlainString());
		assertEquals("2000", saldoDestino.toPlainString());

		//realizamos la tranferencias
		service.tranferir(1L,2L, new BigDecimal("100"), 1L);
		//..cuenta 1 , cuenta 2 destino, monto , banco

		saldoOrigen = service.revisarSaldo(1L);
		saldoDestino = service.revisarSaldo(2L);

		//cuanto quedo , deberia ser 900, se sumo 100 lo cual seria 2100
		assertEquals("900", saldoOrigen.toPlainString());
		assertEquals("2100", saldoDestino.toPlainString());
	}

}
