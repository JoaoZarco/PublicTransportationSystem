package de.fhkiel.advancedjava.service;

import de.fhkiel.advancedjava.domain.trafficline.TrafficLine;
import de.fhkiel.advancedjava.persistence.TrafficLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TrafficLineService {

    private final TrafficLineRepository repository;

    @Autowired
    public TrafficLineService(TrafficLineRepository repository) {
        this.repository = repository;
    }

    public Iterable<TrafficLine> getAllTrafficLinesOrdered() {
        return repository.findAllWithOrderedSecions();
    }

    public TrafficLine saveTrafficLine(TrafficLine trafficLine) {
        return repository.save(trafficLine,5);
    }

    public Iterable<TrafficLine> saveAllTrafficLines(Iterable<TrafficLine> trafficLines) {
        return repository.save(trafficLines, 5);
    }


}
