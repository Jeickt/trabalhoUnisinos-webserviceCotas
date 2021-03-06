package br.com.jeisonruckert.wscotas.resources;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.jeisonruckert.wscotas.entities.Candidato;
import br.com.jeisonruckert.wscotas.entities.Cota;
import br.com.jeisonruckert.wscotas.entities.Curso;
import br.com.jeisonruckert.wscotas.services.CandidatoService;

@RestController
@RequestMapping(value = "/candidatos")
public class CandidatoResource {
	
	private final CandidatoService service;
	
	public CandidatoResource(CandidatoService service) {
		this.service = service;
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping
	public ResponseEntity<List<Candidato>> generateList(@RequestParam(value = "cursoId") Integer cursoId,
			@RequestParam(value = "chamadaId") Integer chamadaId) {
		List<Candidato> chamada = service.prepararGeracaoDeLista(cursoId, chamadaId);
		return ResponseEntity.ok().body(chamada);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(value = "/retrieveList")
	public ResponseEntity<List<Candidato>> retrieveList(@RequestParam(value = "cursoId") Integer cursoId,
			@RequestParam(value = "chamadaId") Integer chamadaId) {
		List<Candidato> chamada = service.recuperarListaDeChamada(cursoId, chamadaId);
		return ResponseEntity.ok().body(chamada);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(value = "/enrolledList")
	public ResponseEntity<List<Candidato>> enrolledList(@RequestParam(value = "cursoId") Integer cursoId) {
		List<Candidato> matriculados = service.recuperarListaDeMatriculados(cursoId);
		return ResponseEntity.ok().body(matriculados);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping
	public ResponseEntity<Curso> insertFile(@RequestBody byte[] csvFile) {
		Curso curso = service.insercaoDeArquivoPrincipal(csvFile);
		return ResponseEntity.ok().body(curso);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping
	public ResponseEntity<List<Cota>> callResultFile(@RequestBody List<Candidato> candidatos) {
		System.out.println(candidatos);
		service.inserirArquivoDeResultado(candidatos);
		return ResponseEntity.noContent().build();
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping(value = "/{cursoId}")
	public ResponseEntity<Void> delete(@PathVariable Integer cursoId) {
		service.delete(cursoId);
		return ResponseEntity.noContent().build();
	}

}
