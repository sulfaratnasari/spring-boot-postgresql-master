package com.demo.postgres.controller;

import com.demo.postgres.entity.Carpark;
import com.demo.postgres.entity.Constant;
import com.demo.postgres.entity.CarparkAvailability;
import com.demo.postgres.entity.CarparkDataResponse;
import com.demo.postgres.entity.CarparkInfoCSV;
import com.demo.postgres.entity.Location;
import com.demo.postgres.entity.LocationDTO;
import com.demo.postgres.repository.CarparkRepository;
import com.demo.postgres.repository.LocationRepository;
import com.demo.postgres.service.CarparkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;

@RestController
@RequestMapping("/carparks")
public class CarparkController {

    @Autowired
    private CarparkService carparkService;

    @Autowired
    private CarparkRepository carparkRepository;

    @Autowired
    private LocationRepository locationRepository;

    @PostMapping("/input-availability")
    public CarparkDataResponse getCarparkAvailabilityInsert() {
        // find all car park availability
        List<Location> locationListDB = locationRepository.findAll();

        if (locationListDB.size() == 0) {
            return null;
        }

        // hashMap[carparkNumber] = Location.Id
        Map<String, Long> locationIdMap = locationListDB.stream()
    .collect(Collectors.toMap(Location::getCarparkNumber, Location::getId));

        CarparkDataResponse carparkResponse = carparkService.GetCarparkAvailability();
        List<Carpark> carparkDataDBList = new ArrayList<Carpark>();
        List<CarparkAvailability> carparkData = carparkResponse.getItems().get(0).getCarpark_data();

        for (CarparkAvailability carparkdata : carparkData) {
            String carparkNumber = carparkdata.getCarpark_number();
            String lotsAvailable = carparkdata.getCarpark_info().get(0).getLots_available();
            String totalLots = carparkdata.getCarpark_info().get(0).getTotal_lots();

            Long locationId = locationIdMap.get(carparkNumber);

            // no nedd to insert availability id location detail not found
            if(locationId == null) {
                continue;
            }

            // check if current_location_id based on carpark_number is exist
            Carpark currentCarparkDB = getCarparkDBExist(locationId);

            if (currentCarparkDB != null) {
                // update
                currentCarparkDB.setLotsAvailable(lotsAvailable);
                currentCarparkDB.setTotalLots(totalLots);
                carparkDataDBList.add(currentCarparkDB);
            } else {
                // insert
                Carpark carparkDB = new Carpark();
                carparkDB.setCarparkLocationID(locationId);

                carparkDB.setLotsAvailable(lotsAvailable);
                carparkDB.setTotalLots(totalLots);
                carparkDataDBList.add(carparkDB);
            }
        }

        carparkRepository.saveAll(carparkDataDBList);
        carparkRepository.flush();
        carparkDataDBList.clear();

        return carparkResponse;
    }

    private Carpark getCarparkDBExist(Long locationId) {
        Carpark carpark = new Carpark();

        List<Carpark> carparkList = carparkRepository.findByCarparkLocationID(locationId);

        if (carparkList.size() > 0) {
            carpark = carparkList.get(0);
            return carpark;
        }

        return null;
    }

    @GetMapping("/nearest")
    public List<LocationDTO> getNearestCarpark(@RequestParam(name = "latitude", required = true) double latitude,
            @RequestParam(name = "longitude", required = true) double longitude,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "per_page", required = false) Integer limit) {

        Integer offset = (page - 1) * limit;
        return convertToLocationList(locationRepository.findNearestLocations(latitude, longitude, limit, offset));
    }

    private List<LocationDTO> convertToLocationList(List<Object[]> list) {
        List<LocationDTO> locations = new ArrayList<>();
        for (Object[] obj : list) {
            LocationDTO location = new LocationDTO();
            location.setAddress(obj[0].toString());
            location.setLatitude(Double.parseDouble(obj[1].toString()));
            location.setLongitude(Double.parseDouble(obj[2].toString()));
            location.setTotal_lots(obj[3].toString());
            location.setLots_available(obj[4].toString());
            locations.add(location);
        }
        return locations;
    }

    @PostMapping("/input-location")
    public List<CarparkInfoCSV> getCarparkInfoCSV() {
        // convert csv to class
        String filePath = new File("").getAbsolutePath();
        String csvFile = filePath + Constant.LocationCSVPath;
        String line;
        String csvSplitBy = ";";

        List<CarparkInfoCSV> carparkInfoList = new ArrayList<>();
        List<Location> locations = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // this will read the first line

            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                String carparkNumber = data[0];
                String address = data[1];
                Double coord_x = Double.parseDouble(data[2]);
                Double coord_y = Double.parseDouble(data[3]);

                CarparkInfoCSV carparkInfo = new CarparkInfoCSV(carparkNumber, address, coord_x, coord_y);
                carparkInfoList.add(carparkInfo);
                Location location = new Location();
                location.setAddress(address);
                location.setCarparkNumber(carparkNumber);
                location.setLatitude(coord_x);
                location.setLongitude(coord_y);
                locations.add(location);
            }
            locationRepository.saveAll(locations);
            locationRepository.flush();
            locations.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return carparkInfoList;
    }

}