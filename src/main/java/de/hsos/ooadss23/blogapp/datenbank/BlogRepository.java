package de.hsos.ooadss23.blogapp.datenbank;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BlogRepository extends CrudRepository<Blog, Integer> {
    List<Blog> findAllByOrderByZeitstempelDesc();
}
