package com.panchenko.LogisticsApp.repository;

import com.panchenko.LogisticsApp.model.Trailer;
import com.panchenko.LogisticsApp.model.enumeration.PetroleumType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrailerRepository extends JpaRepository<Trailer, Long> {
    Optional<List<Trailer>> findAllByPetroleumType(PetroleumType petroleumType);
}
