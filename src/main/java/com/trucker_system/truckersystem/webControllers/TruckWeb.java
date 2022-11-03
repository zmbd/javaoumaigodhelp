package com.trucker_system.truckersystem.webControllers;

import com.google.gson.Gson;
import com.trucker_system.truckersystem.hibernate.TruckHib;
import com.trucker_system.truckersystem.model.Truck;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Properties;

@Controller
public class TruckWeb {
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("truckerdb");
    private final TruckHib truckHib = new TruckHib(entityManagerFactory);

    @RequestMapping(value = "trucks/getAll", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getAllTrucks() {
        Gson gson = new Gson();
        return gson.toJson(truckHib.getAllTrucks());
    }

    @RequestMapping(value = "trucks/getTruckById/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getTruckById(@PathVariable(name = "id") int id) {
        Gson gson = new Gson();
        return gson.toJson(truckHib.getTruckById(id));
    }

    @RequestMapping(value = "trucks/createTruck", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String createTruck(@RequestBody String request) {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(request, Properties.class);
        Truck truck = new Truck(
            properties.getProperty("brand"),
            properties.getProperty("model"),
            Integer.parseInt(properties.getProperty("hp")),
            Double.parseDouble(properties.getProperty("engine")),
            Integer.parseInt(properties.getProperty("releaseYear"))
        );

        try {
            truckHib.createTruck(truck);
        } catch (Exception e) {
            return "Failed to create truck";
        }

        return "Truck created successfully";
    }

    @RequestMapping(value = "trucks/updateTruck/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String updateTruck(@RequestBody String request, @PathVariable(name = "id") int id) {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(request, Properties.class);

        Truck truck = truckHib.getTruckById(id);

        if (truck == null) return "Truck with ID: " + id + " not found";

        truck.setBrand(properties.getProperty("brand"));
        truck.setModel(properties.getProperty("model"));
        truck.setHp(Integer.parseInt(properties.getProperty("hp")));
        truck.setEngine(Double.parseDouble(properties.getProperty("engine")));
        truck.setReleaseYear(Integer.parseInt(properties.getProperty("releaseYear")));

        try {
            truckHib.updateTruck(truck);
        } catch (Exception e) {
            return "Failed to update Truck";
        }

        return "ID: " + id + " updated successfully";
    }

    @RequestMapping(value = "trucks/deleteTruck/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String deleteTruck(@PathVariable(name = "id") int id) {
        Truck truck = truckHib.getTruckById(id);

        if (truck == null) return "Truck with ID: " + id + " not found";

        try {
            truckHib.deleteTruck(id);
        } catch (Exception e) {
            return "Failed to delete Truck";
        }

        return "ID: " + id + " deleted successfully";
    }
}
