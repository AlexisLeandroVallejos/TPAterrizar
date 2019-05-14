package test;

import org.junit.Assert;
import org.junit.Test;

import excepciones.ExcepcionAsientoReservado;
import modelo.*;

public class UsuarioTest {
	
	@Test
	public void realizarBusqueda_usuarioEstandarRealizaUnaBusqueda() {
		String codDeVuelo1 = "EC0344";
		Aerolinea aero = new Aerolinea();
		Vuelo vuelo1 = new Vuelo(codDeVuelo1, "BUE", "LA", "20121010", "2010111", "20:10", "14:20");
		UsuarioEstandar usuario = new UsuarioEstandar("Roman","Perez", 24888654, aero);
		aero.agregarVuelo(vuelo1);
		Aterrizar aterrizar = new Aterrizar();
		aterrizar.setAerolinea(aero);
		String lugarOrigen = "BUE";
		String lugarDestino = "LA";
		String fechaSalida = "20121010";
		String fechaLlegada = null;
		String horaSalida = null;
		String horaLlegada = null;
		Assert.assertEquals("El usuario no realizo la busqueda.", 
				usuario.realizarBusqueda(lugarOrigen, fechaSalida, horaSalida, lugarDestino, fechaLlegada, horaLlegada),
				aterrizar.asientosDisponibles(lugarOrigen, fechaSalida, horaSalida, lugarDestino, fechaLlegada, horaLlegada));
		
	}
	
	@Test
	public void comprar_UsuarioCompraUnAsiento(){
		Aerolinea aero = new Aerolinea();
		UsuarioEstandar usuario = new UsuarioEstandar("Roman","Perez", 24888654, aero);
		String codDeVuelo1 = "EC0344";
		Vuelo vuelo1 = new Vuelo(codDeVuelo1, "BUE", "LA", "2010116", "2010117", "20:10", "14:20");
		//Asientos vuelo1
		Asiento asiento1 = new Asiento(vuelo1, usuario, "P", "P", "D");
		vuelo1.agregarAsiento(asiento1);
		Asiento asiento2 = new Asiento(vuelo1, usuario, "E", "P", "R");
		vuelo1.agregarAsiento(asiento2);
		Asiento asiento3 = new Asiento(vuelo1, usuario, "T", "V", "D");
		vuelo1.agregarAsiento(asiento3);
		//Agregar vuelos a aerolinea:
		aero.agregarVuelo(vuelo1);
		usuario.comprar("EC0344-1");
		
	}
	

	@Test
	public void comprar_UsuarioCompraUnAsientoYNoQuedaDisponible(){
		Aerolinea aero = new Aerolinea();
		UsuarioEstandar usuario = new UsuarioEstandar("Roman","Perez", 24888654, aero);
		String codDeVuelo1 = "EC0344";
		Vuelo vuelo1 = new Vuelo(codDeVuelo1, "BUE", "LA", "2010116", "2010117", "20:10", "14:20");
		//Asientos vuelo1
		Asiento asiento1 = new Asiento(vuelo1, usuario, "P", "P", "D");
		vuelo1.agregarAsiento(asiento1);
		Asiento asiento2 = new Asiento(vuelo1, usuario, "E", "P", "R");
		vuelo1.agregarAsiento(asiento2);
		Asiento asiento3 = new Asiento(vuelo1, usuario, "T", "V", "D");
		vuelo1.agregarAsiento(asiento3);
		//Agregar vuelos a aerolinea:
		aero.agregarVuelo(vuelo1);
		usuario.comprar("EC0344-1");
		Assert.assertEquals("El asiento no esta reservado", asiento1.getEstadoAsiento(), "R");
		
	}
	
	@Test
	public void comprar_DosUsuariosCompranDifrentesAsientosDisponiblesDelMismoVuelo(){
		Aerolinea aero = new Aerolinea();
		UsuarioEstandar usuario = new UsuarioEstandar("Roman","Perez", 24888654, aero);
		UsuarioEstandar otroUsuario = new UsuarioEstandar("Mariano","Martinez", 31256484, aero);
		String codDeVuelo1 = "EC0344";
		Vuelo vuelo1 = new Vuelo(codDeVuelo1, "BUE", "LA", "2010116", "2010117", "20:10", "14:20");
		//Asientos vuelo1
		Asiento asiento1 = new Asiento(vuelo1, usuario, "P", "P", "D");
		vuelo1.agregarAsiento(asiento1);
		Asiento asiento2 = new Asiento(vuelo1, usuario, "E", "P", "R");
		vuelo1.agregarAsiento(asiento2);
		Asiento asiento3 = new Asiento(vuelo1, usuario, "T", "V", "D");
		vuelo1.agregarAsiento(asiento3);
		//Agregar vuelos a aerolinea:
		aero.agregarVuelo(vuelo1);
		usuario.comprar("EC0344-1");
		otroUsuario.comprar("EC0344-3");	
	}	
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void comprar_UsuarioCompraUnAsientoYOtroUsuarioIntentaComprarloPeroNoLoEncuentraDisponible(){
		Aerolinea aero = new Aerolinea();
		UsuarioEstandar usuario = new UsuarioEstandar("Roman","Perez", 24888654, aero);
		UsuarioEstandar otroUsuario = new UsuarioEstandar("Mariano","Martinez", 31256484, aero);
		String codDeVuelo1 = "EC0344";
		Vuelo vuelo1 = new Vuelo(codDeVuelo1, "BUE", "LA", "2010116", "2010117", "20:10", "14:20");
		//Asientos vuelo1
		Asiento asiento1 = new Asiento(vuelo1, usuario, "P", "P", "D");
		vuelo1.agregarAsiento(asiento1);
		Asiento asiento2 = new Asiento(vuelo1, usuario, "E", "P", "R");
		vuelo1.agregarAsiento(asiento2);
		Asiento asiento3 = new Asiento(vuelo1, usuario, "T", "V", "D");
		vuelo1.agregarAsiento(asiento3);
		//Agregar vuelos a aerolinea:
		aero.agregarVuelo(vuelo1);
		usuario.comprar("EC0344-1");
		otroUsuario.comprar("EC0344-1");
	}
	
	@Test
	public void comprar_unUsuarioEstandarIntentaComprarUnAsientoConsideradoSuperOferta(){
		Aerolinea aero = new Aerolinea();
		UsuarioEstandar usuario = new UsuarioEstandar("Roman","Perez", 24888654, aero);
		String codDeVuelo1 = "EC0344";
		Vuelo vuelo1 = new Vuelo(codDeVuelo1, "BUE", "LA", "2010116", "2010117", "20:10", "14:20");
		//Asientos vuelo1
		Asiento asiento1 = new Asiento(vuelo1, usuario, "P", "P", "D");
		vuelo1.agregarAsiento(asiento1);
		Asiento asiento2 = new Asiento(vuelo1, usuario, "E", "P", "R");
		vuelo1.agregarAsiento(asiento2);
		Asiento asiento3 = new Asiento(vuelo1, usuario, "T", "V", "D");
		vuelo1.agregarAsiento(asiento3);
		//Agregar vuelos a aerolinea:
		aero.agregarVuelo(vuelo1);
		usuario.comprar("EC0344-1");
	}
}
