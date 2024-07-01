package rj.training.rest.service;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.management.InstanceAlreadyExistsException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import rj.training.rest.exceptions.DuplicateMovieException;
import rj.training.rest.exceptions.InvalidMovieException;
import rj.training.rest.model.Movie;

@Service
public class MyRestService {

	static {
		System.out.println("loading app service bean");
	}
 public String getMessage(){
	 return "Ravi";
 }
 
 public Movie findMovie(int id) {
	 return movieSet.stream().filter(m -> m.getId().equals(id)).findAny().orElse(null);
     //return new Movie(1,"","");
 }
 
 private Set<Movie> movieSet = new HashSet<>();

 public Set<Movie> getAll() {
     return movieSet;
 }

public void add(Movie m) throws DuplicateMovieException, InvalidMovieException {
	// TODO Auto-generated method stub
	if (m.getId() <=0 || m.getId() >10000) {
		throw new InvalidMovieException("invalid movie id");
	}
	
	if (movieSet.contains(m)) {
		throw new DuplicateMovieException("the movie already exists");
	}
     movieSet.add(m);
     System.out.println("size of movies "+movieSet.size());
}

public File getFile(int path) throws IOException {
	//return new DefaultResourceLoader().getResource("classpath:"+String.valueOf(path));
	String paths = String.valueOf(path);
	File f = null;
	//InputStream is =  getClass().getClassLoader().getResourceAsStream(paths);
	try {
	 f = new ClassPathResource(String.valueOf(path)).getFile();
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	return f;
	 
	// return is;
}
}
