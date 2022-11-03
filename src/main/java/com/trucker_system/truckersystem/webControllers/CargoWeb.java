package com.trucker_system.truckersystem.webControllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trucker_system.truckersystem.hibernate.CargoHib;
import com.trucker_system.truckersystem.model.Cargo;
import com.trucker_system.truckersystem.utils.CargoDeserializer;
import com.trucker_system.truckersystem.utils.CargoSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

@Controller
public class CargoWeb {
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("truckerdb");
    private final CargoHib cargoHib = new CargoHib(entityManagerFactory);

    @RequestMapping(value = "cargos/getAll", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getAllCargos() {
        List<Cargo> cargoList = cargoHib.getAllCargos();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Cargo.class, new CargoSerializer());
        Gson gson = gsonBuilder.create();
        return gson.toJson(cargoList);
    }

    @RequestMapping(value = "cargos/getCargoById/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getCargoById(@PathVariable(name = "id") int id) {
        Cargo cargo = cargoHib.getCargoById(id);

        if (cargo == null) return "Cargo ID: " + id + " does not exist";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Cargo.class, new CargoSerializer());
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        return gson.toJson(cargo);
    }

    @RequestMapping(value = "cargos/createCargo", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String createCargo(@RequestBody String request) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Cargo.class, new CargoDeserializer());
        Cargo cargo = gsonBuilder.create().fromJson(request, Cargo.class);

        try {
            cargoHib.createCargo(cargo);
        } catch (Exception e) {
            return "Failed to create Cargo";
        }

        return "Cargo created successfully";
    }

    @RequestMapping(value = "cargos/updateCargo/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String updateCargo(@RequestBody String request, @PathVariable(name = "id") int id) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Cargo.class, new CargoDeserializer());
        Cargo cargo = cargoHib.getCargoById(id);

        if (cargo == null) return "Cargo with ID: " + id + " not found";

        Cargo updatedCargo = gsonBuilder.create().fromJson(request, Cargo.class);

        cargo.setClient(updatedCargo.getClient());
        cargo.setStartDestination(updatedCargo.getStartDestination());
        cargo.setFinalDestination(updatedCargo.getFinalDestination());
        cargo.setAssignedAt(updatedCargo.getAssignedAt());
        cargo.setDeliverUntil(updatedCargo.getDeliverUntil());
        cargo.setCargo(updatedCargo.getCargo());
        cargo.setFinished(updatedCargo.isFinished());
        cargo.setTrucker(updatedCargo.getTrucker());

        try {
            cargoHib.updateCargo(cargo);
        } catch (Exception e) {
            return "Failed to update Cargo";
        }

        return "Cargo ID: " + id + " updated successfully";
    }

    @RequestMapping(value = "cargos/deleteCargo/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String updateCargo(@PathVariable(name = "id") int id) {
        Cargo cargo = cargoHib.getCargoById(id);

        if (cargo == null) return "Cargo with ID: " + id + " not found";

        try {
            cargoHib.deleteCargo(cargo);
        } catch (Exception e) {
            return "Failed to delete Cargo";
        }

        return "Cargo ID: " + id + " deleted successfully";
    }
}
