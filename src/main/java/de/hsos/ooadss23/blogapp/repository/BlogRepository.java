package de.hsos.ooadss23.blogapp.repository;

import de.hsos.ooadss23.blogapp.datenmodell.Blog;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Spring Data-Repository, das Blogs verwaltet
 * @author Roman Wasenmiller
 */
public interface BlogRepository extends CrudRepository<Blog, Integer> {
    /**
     * Findet alle Blogs und gibt diese nach Erstelldatum absteigend sortiert zurück
     * @return Alle Blogs, absteigend nach Zeitstempel sortiert
     */
    List<Blog> findAllByOrderByZeitstempelDesc();
}
