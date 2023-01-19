package pe.parnertdigital.test3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pe.parnertdigital.test3.exceptions.DineroInsuficienteException;
import pe.parnertdigital.test3.models.Banco;
import pe.parnertdigital.test3.models.Cuenta;
import pe.parnertdigital.test3.repositories.BancoRepository;
import pe.parnertdigital.test3.repositories.CuentaRespository;
import pe.parnertdigital.test3.services.CuentaService;
import pe.parnertdigital.test3.services.CuentaServiceImpl;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import static  org.mockito.Mockito.*;
import static pe.parnertdigital.test3.Datos.*;

@SpringBootTest
class Test3ApplicationTests {
	@MockBean
	CuentaRespository cuentaRespository;
	@MockBean
	BancoRepository bancoRepository;
	@Autowired
	CuentaServiceImpl service;
	//...hacer de esta clase un componente de spring
	// ...registrar como un bean osea un componente del framework
	//...etiquetamos con @Service en su clase

	@BeforeEach
	void setUp() {

	}

	@Test
	void contextLoads() {
		when(cuentaRespository.buscarPorId(1L)).thenReturn(crearCuenta001());
		when(cuentaRespository.buscarPorId(2L)).thenReturn(crearCuenta002());
		when(bancoRepository.buscarPorId(1L)).thenReturn(crearBanco());

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

		int total = service.revisarTotalTransferencias(1L);
		assertEquals(1, total);

		//por defecto es uno, y le llega tres , para especificar que espera 3 es con times
		verify(cuentaRespository, times(3)).buscarPorId(1L);
		verify(cuentaRespository, times(3)).buscarPorId(2L);
		//especificar cuantas veces se va a ejecutar el update
		verify(cuentaRespository, times(2)).actualizar(any(Cuenta.class));

		verify(bancoRepository,times(2)).buscarPorId(1L);
		verify(bancoRepository).actualizar(any(Banco.class));

		//verificar que no se llame al buscartodos
		verify(cuentaRespository, times(6)).buscarPorId(anyLong());
		verify(cuentaRespository, never()).buscarTodos();
	}

//	que pasa que tine la cuenta es menor al mono que se va a transferir
@Test
void contextLoads2() {
	when(cuentaRespository.buscarPorId(1L)).thenReturn(crearCuenta001());
	when(cuentaRespository.buscarPorId(2L)).thenReturn(crearCuenta002());
	when(bancoRepository.buscarPorId(1L)).thenReturn(crearBanco());


	BigDecimal saldoOrigen = service.revisarSaldo(1L);
	BigDecimal saldoDestino = service.revisarSaldo(2L);

	assertEquals("1000", saldoOrigen.toPlainString());
	assertEquals("2000", saldoDestino.toPlainString());


	assertThrows(DineroInsuficienteException.class,()->{
		service.tranferir(1L,2L, new BigDecimal("1200"), 1L);

	});

	saldoOrigen = service.revisarSaldo(1L);
	saldoDestino = service.revisarSaldo(2L);

	//como se lanza la exception  no cambioa nada sigue on 1000 y 2ooo
	assertEquals("1000", saldoOrigen.toPlainString());
	assertEquals("2000", saldoDestino.toPlainString());

	int total = service.revisarTotalTransferencias(1L);
	assertEquals(0, total);

	//por defecto es uno, y le llega tres , para especificar que espera 3 es con times
	verify(cuentaRespository, times(3)).buscarPorId(1L);
	verify(cuentaRespository, times(2)).buscarPorId(2L);
	//como no se ejecuta se pone never
	verify(cuentaRespository, never()).actualizar(any(Cuenta.class));

	verify(bancoRepository,times(1)).buscarPorId(1L);
	verify(bancoRepository, never()).actualizar(any(Banco.class));

	verify(cuentaRespository, times(5)).buscarPorId(anyLong());
	verify(cuentaRespository, never()).buscarTodos();


}


	@Test
	void contextLoads3(){
		when(cuentaRespository.buscarPorId(1L)).thenReturn(crearCuenta001());

		//comprobamos dos cuentas
		Cuenta cuenta1 = service.buscarPorId(1L);
		Cuenta cuenta2 = service.buscarPorId(1L);

		//afirmamos que las dos instancias sean la misma
		assertSame(cuenta1, cuenta2);
		//otra forma
		assertTrue(cuenta1 == cuenta2);
		//
		assertEquals("David", cuenta1.getPersona());
		assertEquals("David", cuenta2.getPersona());
		//verificar que se haya invocado dos veces..
		verify(cuentaRespository, times(2)).buscarPorId(1L);



	}

}
