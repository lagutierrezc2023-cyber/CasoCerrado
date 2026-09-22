package com.example.casocerrado

import com.example.casocerrado.data.model.EstateCase
import com.example.casocerrado.data.model.TypeEvidence
import com.example.casocerrado.data.repository.CasesManaging
import com.example.casocerrado.domain.CasesManagement
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CasesManagementTest {
    private lateinit var casesManagement: CasesManagement

    @Before
    fun setUp() {
        val repositorioVacio = CasesManaging()
        casesManagement = CasesManagement(repositorioVacio)
    }

    @Test
    fun crearCaso_conDatosValidos_loAgregaALaLista() {
        val mensajeDeError = casesManagement.createCase(
            title = "Robo en la galeria",
            description = "Se llevaron varias piezas",
            date = "12 mar 2025"
        )
        assertEquals("", mensajeDeError)
        val listaDeCasos = casesManagement.getCases()
        assertEquals(1, listaDeCasos.size)
        assertEquals("Robo en la galeria", listaDeCasos[0].title)
    }

    @Test
    fun crearCaso_sinTitulo_devuelveError() {
        val mensajeDeError = casesManagement.createCase(
            title = "",
            description = "Descripcion cualquiera",
            date = "12 mar 2025"
        )
        assertEquals("The title is required", mensajeDeError)
        val listaDeCasos = casesManagement.getCases()
        assertEquals(0, listaDeCasos.size)
    }

    @Test
    fun editarCaso_queExiste_actualizaLosDatos() {
        casesManagement.createCase("Titulo viejo", "Desc vieja", "1 ene 2025")
        val listaDeCasos = casesManagement.getCases()
        val idDelCasoCreado = listaDeCasos[0].id
        val mensajeDeError = casesManagement.editCase(
            id = idDelCasoCreado,
            title = "Titulo nuevo",
            description = "Desc nueva",
            date = "2 ene 2025"
        )
        assertEquals("", mensajeDeError)
        val casoDespuesDeEditar = casesManagement.getCase(idDelCasoCreado)
        assertEquals("Titulo nuevo", casoDespuesDeEditar?.title)
    }

    @Test
    fun editarCaso_queNoExiste_devuelveError() {
        val mensajeDeError = casesManagement.editCase(
            id = 999,
            title = "Titulo",
            description = "Desc",
            date = "1 ene 2025"
        )

        assertEquals("Case not found", mensajeDeError)
    }

    @Test
    fun eliminarCaso_queExiste_loQuitaDeLaLista() {
        casesManagement.createCase("Caso a borrar", "Desc", "1 ene 2025")

        val listaDeCasos = casesManagement.getCases()
        val idDelCasoCreado = listaDeCasos[0].id

        val mensajeDeError = casesManagement.deleteCase(idDelCasoCreado)
        assertEquals("", mensajeDeError)
        assertEquals(0, casesManagement.getCases().size)
        assertNull(casesManagement.getCase(idDelCasoCreado))
    }

    @Test
    fun buscarCasos_porTexto_soloDevuelveLosQueCoinciden() {
        casesManagement.createCase("Robo en la galeria", "Desc", "1 ene 2025")
        casesManagement.createCase("Fraude corporativo", "Desc", "2 ene 2025")

        val resultadoDeLaBusqueda = casesManagement.searchCases("galeria")
        assertEquals(1, resultadoDeLaBusqueda.size)
        assertEquals("Robo en la galeria", resultadoDeLaBusqueda[0].title)
    }

    @Test
    fun agregarHallazgo_aCasoAbierto_seAgregaCorrectamente() {
        casesManagement.createCase("Caso 1", "Desc", "1 ene 2025")

        val listaDeCasos = casesManagement.getCases()
        val idDelCaso = listaDeCasos[0].id
        val mensajeDeError = casesManagement.addFinding(
            caseId = idDelCaso,
            title = "Se revisaron camaras",
            description = "No se encontro nada raro"
        )
        assertEquals("", mensajeDeError)
        val caso = casesManagement.getCase(idDelCaso)
        assertEquals(1, caso?.finding?.size)
    }

    @Test
    fun agregarEvidencia_aCasoCerrado_devuelveError() {
        casesManagement.createCase("Caso 1", "Desc", "1 ene 2025")

        val listaDeCasos = casesManagement.getCases()
        val idDelCaso = listaDeCasos[0].id

        casesManagement.closeCase(idDelCaso)
        val mensajeDeError = casesManagement.addEvidence(
            caseId = idDelCaso,
            type = TypeEvidence.PHOTO,
            description = "Foto de la escena"
        )
        assertEquals("A closed case cannot receive new evidence", mensajeDeError)
    }

    @Test
    fun cerrarCaso_cambiaElEstadoACerrado() {
        casesManagement.createCase("Caso 1", "Desc", "1 ene 2025")

        val listaDeCasos = casesManagement.getCases()
        val idDelCaso = listaDeCasos[0].id
        casesManagement.closeCase(idDelCaso)
        val caso = casesManagement.getCase(idDelCaso)
        assertEquals(EstateCase.CLOSED, caso?.state)
        assertTrue(caso!!.state.isClosed)
    }

    @Test
    fun cerrarCaso_queYaEstaCerrado_devuelveError() {
        casesManagement.createCase("Caso 1", "Desc", "1 ene 2025")

        val listaDeCasos = casesManagement.getCases()
        val idDelCaso = listaDeCasos[0].id

        casesManagement.closeCase(idDelCaso)
        val mensajeDeError = casesManagement.closeCase(idDelCaso)

        assertEquals("The case is already closed", mensajeDeError)
    }
}