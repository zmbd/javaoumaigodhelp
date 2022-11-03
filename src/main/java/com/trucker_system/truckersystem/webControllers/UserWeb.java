package com.trucker_system.truckersystem.webControllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trucker_system.truckersystem.hibernate.UserHib;
import com.trucker_system.truckersystem.model.Manager;
import com.trucker_system.truckersystem.model.Truck;
import com.trucker_system.truckersystem.model.Trucker;
import com.trucker_system.truckersystem.model.User;
import com.trucker_system.truckersystem.utils.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Properties;

@Controller
public class UserWeb {
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("truckerdb");
    private final UserHib userHib = new UserHib(entityManagerFactory);

    @RequestMapping(value = "users/getAll", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getAllUsers() {
        Gson gson = new Gson();
        return gson.toJson(userHib.getAllUsers());
    }

    @RequestMapping(value = "users/validateUser", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String validateUser(@RequestBody String userInfo) {
        Gson parser = new Gson();
        Properties properties = parser.fromJson(userInfo, Properties.class);
        String login = properties.getProperty("login"); //
        String password = properties.getProperty("password");

        User user = userHib.authenticateLogin(login, password);

        return user != null ? String.valueOf(user.getId()) : "User not found";
    }

    @RequestMapping(value = "users/createUser", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String createUser(@RequestBody String userInfo) {
        Gson parser = new Gson();
        Properties properties = parser.fromJson(userInfo, Properties.class);
        String dtype = properties.getProperty("dtype");
        String login = properties.getProperty("login");
        String password = properties.getProperty("password");
        String name = properties.getProperty("name");
        String surname = properties.getProperty("surname");
        String email = properties.getProperty("email");
        String phoneNumber = properties.getProperty("phoneNumber");
        boolean admin = Boolean.parseBoolean(properties.getProperty("admin"));

        if (!userHib.findByLogin(login)) {
            if (dtype.equals("Trucker")) {
                userHib.createUser(new Trucker(login, password, email, name, surname, phoneNumber, 0));
            }
            else if (dtype.equals("Manager")) {
                userHib.createUser(new Manager(login, password, email, name, surname, phoneNumber, admin));
            }
            else return "Invalid parameters";
        }
        else return "User already exists";


        return "User: " + login + " was created successfully";
    }

    @RequestMapping(value = "/users/getTruckerById/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getTruckerById(@PathVariable(name = "id") int id) {
        Trucker trucker = userHib.getUserByIdType(id, "Trucker");
        System.out.println(trucker.getTruck());

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Trucker.class, new TruckerGsonSerializer());
        Gson gson = gsonBuilder.create();
        return gson.toJson(trucker);
    }

    @RequestMapping(value = "/users/getManagerById/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getManagerById(@PathVariable(name = "id") int id) {
        Manager manager = userHib.getUserByIdType(id, "Manager");

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Manager.class, new ManagerGsonSerializer());
        Gson gson = gsonBuilder.create();
        return gson.toJson(manager.toString());
    }

    @RequestMapping(value = "/users/updateTrucker/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String updateTrucker(@RequestBody String request, @PathVariable(name = "id") int id) {
        Trucker trucker = userHib.getUserByIdType(id, "Trucker");

        if (trucker == null) return "No such trucker";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Trucker.class, new TruckerGsonDeserializer());
        Trucker temporary = gsonBuilder.create().fromJson(request, Trucker.class);

        trucker.setLogin(temporary.getLogin());
        trucker.setPassword(temporary.getPassword());
        trucker.setEmail(temporary.getEmail());
        trucker.setName(temporary.getName());
        trucker.setSurname(temporary.getSurname());
        trucker.setPhoneNumber(temporary.getPhoneNumber());
        trucker.setFinishedTrips(temporary.getFinishedTrips());
        trucker.setTruck(temporary.getTruck());

        userHib.updateUser(trucker);

        return "Trucker: " + trucker.getId() + " updated.";
    }

    @RequestMapping(value = "/users/updateManager/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String updateManager(@RequestBody String request, @PathVariable(name = "id") int id) {
        Manager manager = userHib.getUserByIdType(id, "Manager");

        if (manager == null) return "No such manager";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Manager.class, new ManagerGsonDeserializer());
        Manager temporary = gsonBuilder.create().fromJson(request, Manager.class);

        manager.setLogin(temporary.getLogin());
        manager.setPassword(temporary.getPassword());
        manager.setEmail(temporary.getEmail());
        manager.setName(temporary.getName());
        manager.setSurname(temporary.getSurname());
        manager.setPhoneNumber(temporary.getPhoneNumber());
        manager.setAdmin(temporary.isAdmin());

        userHib.updateUser(manager);

        return "Manager: " + manager.getId() + " updated.";
    }

    @RequestMapping(value = "/users/deleteUser/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String deleteUser(@PathVariable(name = "id") int id) {
        User user = userHib.getUserById(id);

        if (user == null) return "User not found";

        userHib.deleteUser(id);

        return "User: " + id + " deleted successfully";
    }

}
