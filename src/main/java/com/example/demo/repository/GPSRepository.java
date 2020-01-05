package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.domain.GPS;
import com.example.demo.response.GPSResponse;

public interface GPSRepository extends JpaRepository<GPS, String> {
	
	@Query("select new com.example.demo.response.GPSResponse(g.id, g.gpsName, g.description, g.author, g.time) from GPS g")
	List<GPSResponse> findAllWithBasicInfo(Pageable pageable);

}
