package de.fhkiel.advancedjava.service;

import de.fhkiel.advancedjava.domain.common.Type;
import de.fhkiel.advancedjava.domain.common.TypeDomainService;
import de.fhkiel.advancedjava.domain.disturbance.Disturbance;
import de.fhkiel.advancedjava.domain.disturbance.queryresults.DisturbancesSection;
import de.fhkiel.advancedjava.domain.disturbance.queryresults.DisturbancesStop;
import de.fhkiel.advancedjava.domain.stop.DomainService;
import de.fhkiel.advancedjava.domain.stop.SectionStop;
import de.fhkiel.advancedjava.domain.stop.Stop;
import de.fhkiel.advancedjava.domain.stop.queryresults.NodePath;
import de.fhkiel.advancedjava.domain.trafficline.Section;
import de.fhkiel.advancedjava.exception.section.InvalidSectionException;
import de.fhkiel.advancedjava.exception.stop.InvalidStopIdException;
import de.fhkiel.advancedjava.exception.stop.PathDoesNotExistException;
import de.fhkiel.advancedjava.persistence.SectionRepository;
import de.fhkiel.advancedjava.persistence.StopRepository;
import org.apache.commons.text.similarity.LevenshteinDetailedDistance;
import org.apache.commons.text.similarity.LevenshteinResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class StopService {


    private final StopRepository stopRepository;
    private final SectionRepository sectionRepository;

    @Autowired
    public StopService(StopRepository stopRepository, SectionRepository sectionRepository) {
        this.stopRepository = stopRepository;
        this.sectionRepository = sectionRepository;
    }

    public Stop saveStop(Stop stop) {
        return stopRepository.save(stop);
    }

    public Iterable<Stop> saveAllStops(Iterable<Stop> stops) {
        return stopRepository.saveAll(stops);
    }

    public Stop getStopById(Integer id) {
        return stopRepository.findById(id).orElseThrow(() -> new InvalidStopIdException(id));
    }


    public Optional<Stop> getStopByName(String name) {
        return stopRepository.findByName(name);
    }


    public Stop updateConnectionTime(Integer id, Integer connectionTime) {
        Stop stop = getStopById(id);
        stop.updateConnectionTime(connectionTime);
        return stopRepository.save(stop);
    }

    public Stop openStop(Integer id) {
        Stop stop = getStopById(id);
        stop.openStop();
        //activate stop id needed
        if (stop.hasFunctionalChanged()) {
            stopRepository.saveAll(connectWithSections(stop));
            return stop;
        } else {
            return stopRepository.save(stop);
        }
    }

    public Stop closeStop(Integer id) {
        Stop stop = getStopById(id);
        stop.closeStop();
        //deactivate stop if state Changed
        if (stop.hasFunctionalChanged())
            deactivate(stop.getId());
        return stopRepository.save(stop);
    }

    public Stop addDisturbance(Integer id, Disturbance disturbance) {
        Stop stop = getStopById(id);
        stop.addDisturbance(disturbance);
        //deactivate stop if state Changed
        if (stop.hasFunctionalChanged())
            deactivate(stop.getId());
        return stopRepository.save(stop);
    }

    public Stop resolveDisturbance(Integer stopId, String description) {
        Stop stop = getStopById(stopId);
        stop.resolveDisturbance(description);
        //activate stop if state Changed
        if (stop.hasFunctionalChanged()) {
            stopRepository.saveAll(connectWithSections(stop));
            return stop;
        } else
            return stopRepository.save(stop);
    }

    public Section addDisturbance(Disturbance disturbance, Integer startStopId, Integer endStopId, String stringType) {
        TypeDomainService.getType(stringType);
        Section section = getSectionWException(startStopId, endStopId, stringType);
        section.addDisturbance(disturbance);
        //deactivate section if state Changed
        if (section.hasFunctionalChanged())
            deactivate(section);
        return sectionRepository.save(section);
    }

    public Section resolveDisturbance(Integer startStopId, Integer endStopId, String stringType, String description) {
        TypeDomainService.getType(stringType);
        Section section = getSectionWException(startStopId, endStopId, stringType);
        section.resolveDisturbance(description);
        //activate section if state changed
        if (section.hasFunctionalChanged())
            activate(section);
        return sectionRepository.save(section, 3);
    }


    private Collection<Stop> connectWithSections(Stop stop) {
        Iterable<Section> sections = this.getConnectableSectionsByStopId(stop.getId());
        return connectWithSections(sections, stop);
    }

    /**
     * Connects all the Section stops related to a corresponding stop
     * according to the sections in the process of activating the stop
     *
     * @param sections section that are functional and contain functional stops
     * @param stop     main stop
     * @return Collection of all stops related throw sections to the main stop
     */
    private Collection<Stop> connectWithSections(Iterable<Section> sections, Stop stop) {
        Map<Integer, Stop> idStopMap = new HashMap<>();
        for (Section section : sections) {

            //get section attributes
            Type connectionType = section.getType();
            Integer cost = section.getDurationInMinutes();

            if (isOutgoing(stop, section)) {
                Stop endStop = section.getEndStop();
                //get the section stop that corresponds to the outside stop and make connection
                SectionStop endSectionStop = endStop.getSectionStopByType(connectionType).get();
                stop.getSectionStopByType(connectionType).get().addConnection(endSectionStop, cost);
            } else {
                //get start stop id
                Integer startStopId = section.getStartStop().getId();
                Stop startStop;
                //get stop based on being unique to save later
                if (idStopMap.containsKey(startStopId)) {
                    startStop = idStopMap.get(startStopId);
                } else {
                    startStop = section.getStartStop();
                    idStopMap.put(startStopId, startStop);
                }
                //make the connection
                startStop.getSectionStopByType(connectionType).get().addConnection(
                        stop.getSectionStopByType(connectionType).get(), cost
                );
            }
        }
        idStopMap.put(stop.getId(), stop);
        return idStopMap.values();
    }

    private boolean isOutgoing(Stop stop, Section section) {
        return section.getStartStop().equals(stop);
    }

    private void deactivate(Integer stopId) {
        stopRepository.deactivateStop(stopId);
    }


    public Iterable<Stop> getAllStops() {
        return stopRepository.findAll();
    }

    public String getMostSimilarStopName(String name) {
        String hint = "";
        Integer minimumDistance = Integer.MAX_VALUE;
        Iterable<String> allNames = stopRepository.getAllStopNames();
        for (String stopName : allNames) {
            Integer distance = distanceBetweenWords(name, stopName);
            if (distance < minimumDistance) {
                minimumDistance = distance;
                hint = stopName;
            }
        }
        return hint;
    }

    private Integer distanceBetweenWords(String s1, String s2) {
        LevenshteinResults results = new LevenshteinDetailedDistance().apply(s1, s2);
        return results.getDistance();
    }

    /**
     * Get a section based on it's attributes
     *
     * @param startStopId start stop id of the Section
     * @param endStopId   end stop id of the Section
     * @param stringType  type of the Section
     * @param duration    time that it takes to go through the section in minutes
     * @return the section according to the attributes weather it already exists or not
     */
    public Section getSection(Integer startStopId, Integer endStopId, String stringType, Integer duration) {
        //validates string type before making query
        TypeDomainService.getType(stringType);

        Optional<Section> optionalSection = this.getSection(
                startStopId,
                endStopId,
                stringType
        );

        if (optionalSection.isPresent()) {
            //add already existent section
            return optionalSection.get();
        } else {
            Stop startStop = getStopById(startStopId);
            Stop endStop = getStopById(endStopId);
            //add a non existent section
            return new Section(startStop, endStop, stringType, duration);
        }
    }


    private void activate(Section section) {

        Type type = section.getType();
        Integer cost = section.getDurationInMinutes();

        Stop startStop = section.getStartStop();
        Stop endStop = section.getEndStop();
        if (!(startStop.isFunctional() && endStop.isFunctional()))
            return;

        //get start section Stop
        SectionStop startSectionStop = startStop.getSectionStopByType(type).get();
        //get end section stop
        SectionStop endSectionStop = endStop.getSectionStopByType(type).get();
        //connect both
        startSectionStop.addConnection(endSectionStop, cost);
    }

    private void deactivate(Section section) {
        sectionRepository.deactivateSection(
                section.getStartStop().getId(),
                section.getEndStop().getId(),
                section.getType().name());
    }

    public Section getSectionWException(Integer startStopId, Integer endStopId, String type) {
        //validate type before query
        TypeDomainService.getType(type);
        Long id = sectionRepository.findIdByStopIdsAndType(startStopId, endStopId, type).orElseThrow(() -> new InvalidSectionException(
                        String.format("Section starting in stop with id %d and ending in stop with id %d of type %s, does not exist",
                                startStopId,
                                endStopId,
                                type)
                )
        );
        return sectionRepository.findById(id, 2).get();
    }


    public Optional<Section> getSection(Integer startStopId, Integer endStopId, String type) {
        Optional<Long> id = sectionRepository.findIdByStopIdsAndType(startStopId, endStopId, type);
        if (id.isPresent()) {
            return sectionRepository.findById(id.get(), 2);
        } else {
            return Optional.empty();
        }
    }

    public Iterable<Section> getConnectableSectionsByStopId(Integer stopId) {
        return sectionRepository.findConnectableSectionsByStopId(stopId);
    }

    public List<NodePath> getShortestPathBetweenStops(Stop startStop, Stop endStop) {
        DomainService.validateFunctionality(startStop);
        DomainService.validateFunctionality(endStop);

        List<NodePath> path = stopRepository.shortestPathBetweenSectionStops(startStop.getId(), endStop.getId());

        if (path.isEmpty())
            throw new PathDoesNotExistException(
                    String.format("path from %s to %s does not exist", startStop.getName(), endStop.getName()));


        return buildNewPath(path);
    }

    /**
     * Polishes and completes the path
     * Removes first and last node of the path
     * Gets stop associated with Id contained in NodePath object and adds it to the same object
     *
     * @param path Path from one node to another
     */
    private List<NodePath> buildNewPath(List<NodePath> path) {
        Map<Integer, Stop> idStopMap = new HashMap<>();
        int size = path.size();
        List<NodePath> newPath = path.subList(1, size - 1);


        for (NodePath node : newPath) {
            Integer stopId = node.stopId.intValue();
            if (idStopMap.containsKey(stopId)) {
                node.stop = idStopMap.get(stopId);
            } else {
                Stop stop = getStopById(stopId);
                idStopMap.put(stopId, stop);
                node.stop = stop;
            }
        }

        return newPath;
    }


    public Long getTotalNumberOfDisturbances() {
        return stopRepository.getTotalNumberOfDisturbances();
    }

    public Set<DisturbancesStop> getTotalNumberOfDisturbancesPerStop() {
        return stopRepository.getNumberOfDisturbancePerStop();
    }

    public Set<DisturbancesSection> getTotalNumberOfDisturbancePerSection() {
        return sectionRepository.totalNumberOfDisturbancePerSection();
    }

    public Long getTotalNumberOfStops() {
        return stopRepository.getTotalNumberOfStops();
    }

    public Long getTotalNumberOfBusStops() {
        return stopRepository.getNumberOfBusStops();
    }

    public Long getTotalNumberOfSubwayStops() {
        return stopRepository.getNumberOfSubwayStops();
    }

    public Long getTotalNumberOfSuburbanTrainStops() {
        return stopRepository.getNumberOfSuburbanTrainStops();
    }

    public Long getTotalNumberOfConnections() {
        return sectionRepository.getTotalNumberOfConnections();
    }
}
