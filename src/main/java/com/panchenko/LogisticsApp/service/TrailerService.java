package com.panchenko.LogisticsApp.service;

import com.panchenko.LogisticsApp.dto.TrailerDTO;
import com.panchenko.LogisticsApp.model.Trailer;

import java.util.List;

public interface TrailerService {
    Trailer create(Trailer trailer);

    Trailer readById(long id);

    Trailer update(Trailer trailer, TrailerDTO trailerDTO);

    void delete(long id);

    List<Trailer> getAll();

    Trailer convertToTrailer(TrailerDTO trailerDTO);

    TrailerDTO convertToTrailerDTO(Trailer trailer);
}
