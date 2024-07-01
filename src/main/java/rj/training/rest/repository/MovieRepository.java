package rj.training.rest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import rj.training.rest.model.Movie;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Integer>{


}
