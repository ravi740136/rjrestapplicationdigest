package rj.training.rest.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import rj.training.rest.exceptions.DuplicateMovieException;
import rj.training.rest.exceptions.InvalidMovieException;
import rj.training.rest.model.Movie;
import rj.training.rest.model.UploadResponse;
import rj.training.rest.repository.MovieRepository;

@RestController
@RequestMapping("/myrestapp")
public class MyRestAppController {
	@Autowired
	MovieRepository repository;

	@GetMapping(value = "/movie/{id}", produces = "application/json")
	public ResponseEntity<?> getMovie(@PathVariable int id) throws InvalidMovieException {
		Optional<Movie> m = repository.findById(id);
		if (!m.isPresent()) {
			throw new InvalidMovieException("movie with specified id " + id + " does not exist ");
		}
		return ResponseEntity.ok(m);
	}

	@PatchMapping(value = "/movie/{id}", produces = "application/json", consumes = "application/json")
	public ResponseEntity<Movie> updateMovieByPatch(@PathVariable int id, @RequestBody Movie data)
			throws InvalidMovieException {
		Optional<Movie> m = repository.findById(id);
		if (!m.isPresent()) {
			throw new InvalidMovieException("movie with specified id " + id + " does not exist ");
		}
		Movie mv = m.get();
		mv.setName(data.getName());
		repository.save(mv);
		return ResponseEntity.ok(mv);
	}

	@PutMapping(value = "/movie/{id}", produces = "application/json", consumes = "application/json")
	public ResponseEntity<Movie> updateMovie(@PathVariable int id, @RequestBody Movie data)
			throws InvalidMovieException {
		Optional<Movie> m = repository.findById(id);
		if (!m.isPresent()) {
			throw new InvalidMovieException("movie with specified id " + id + " does not exist ");
		}
		data.setId(id);
		Movie mv = repository.save(data);
		return ResponseEntity.ok(mv);
	}

	@PostMapping(value = "/movie/", consumes = "application/json")
	// @ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<Movie> addMovies(@RequestBody Movie m) throws InvalidMovieException, DuplicateMovieException {
		System.out.println("movie to be created " + m);
		Movie ms = null;
		try {
			ms = repository.save(m);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataIntegrityViolationException("movie could not be saved as incorrect data entered");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(ms);
	}

	@GetMapping("/movies")
	public ResponseEntity<?> getMovies() {
		Iterable<Movie> m = repository.findAll();
		return ResponseEntity.ok(m);
	}

	@GetMapping(value = "/movies/count", produces = "application/json")
	public ResponseEntity<Long> getMoviesCount() {
		Long m = Long.valueOf(repository.count());
		return ResponseEntity.ok(m);
	}

	@DeleteMapping(value = "/movies/{id}", produces = "application/json")
	public ResponseEntity<Movie> deleteMovie(@PathVariable Integer id) {
		Optional<Movie> m = repository.findById(id);
		repository.deleteById(id);
		return ResponseEntity.ok(m.get());
	}

	// @PostMapping(value="/movie/fileupload",consumes = "multipart/form-data",
	// produces = "application/json")
	@PostMapping(value = "/movie/fileupload", produces = "application/json")
	public ResponseEntity<UploadResponse> uploadMovieDescription(@RequestParam("file") MultipartFile file,
			@RequestParam("multifilename") String multiFileName) {
		UploadResponse us = new UploadResponse();
		us.setOriginalFileName(file.getOriginalFilename());
		us.setMultiFileName(multiFileName);
		us.setFileType(file.getContentType());
		us.setBytes(file.getSize());
		return ResponseEntity.ok(us);
	}

	@PostMapping(value = "/movie/uploaddata", produces = "application/json")
	public ResponseEntity<UploadResponse> uploadMovieData
	(@RequestParam("multifilename") String multiFileName,
			@RequestParam String originalFileName, 
			@RequestParam String fileType, 
			@RequestParam long bytes) {
		UploadResponse us = new UploadResponse();
		us.setOriginalFileName(originalFileName);
		us.setMultiFileName(multiFileName);
		us.setFileType(fileType);
		us.setBytes(bytes);
		return ResponseEntity.ok(us);
	}
}
