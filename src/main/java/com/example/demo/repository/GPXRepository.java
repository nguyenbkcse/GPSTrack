package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.domain.GPX;

public interface GPXRepository extends JpaRepository<GPX, String> {
	
	@Query("select g.id, g.gpxName, g.description, g.author, g.linkHref, g.linkText, g.time from GPX g")
	List<Object[]> findAllWithBasicInfo(Pageable pageable);

}
