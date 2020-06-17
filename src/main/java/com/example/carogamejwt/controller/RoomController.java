package com.example.carogamejwt.controller;

import com.example.carogamejwt.model.Room;
import com.example.carogamejwt.model.User;
import com.example.carogamejwt.service.RoomService;
import com.example.carogamejwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@CrossOrigin
public class RoomController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private UserService userService;

    //API trả về List Room.
    @RequestMapping(value = "/rooms", method = RequestMethod.GET)
    public ResponseEntity<List<Room>> listAllRooms() {
        List<Room> rooms = roomService.findAll();
        if (rooms.isEmpty()) {
            return new ResponseEntity<List<Room>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Room>>(rooms, HttpStatus.OK);
    }

    //API trả về Room có ID trên url.
    @RequestMapping(value = "/rooms/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Room> getRoomById(@PathVariable("id") long id) {
        System.out.println("Fetching Room with id " + id);
        Room room = roomService.findById(id);
        if (room == null) {
            System.out.println("Room with id " + id + " not found");
            return new ResponseEntity<Room>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Room>(room, HttpStatus.OK);
    }

    //API tạo một Room mới.
    @RequestMapping(value = "/rooms", method = RequestMethod.POST)
    public ResponseEntity<Void> createRoom(@RequestBody Room room, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating Room " + room.getId());
        roomService.createRoom(room);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/rooms/{id}").buildAndExpand(room.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/rooms/join/{id}", method = RequestMethod.POST)
    public ResponseEntity<Room> join(@PathVariable("id") long id, @RequestHeader(value = "Authorization") String token) {
        Room currentRoom = roomService.findById(id);
        token = token.substring(7);
        User currentUser = userService.findByToken(token);
        if (currentRoom.getFirstUser() == null) {
            currentRoom.setFirstUser(currentUser);
            roomService.updateRoom(currentRoom);
            return new ResponseEntity<Room>(currentRoom, HttpStatus.OK);
        } else if (currentRoom.getSecondUser() == null && currentRoom.getFirstUser() != currentUser) {
            currentRoom.setSecondUser(currentUser);
            roomService.updateRoom(currentRoom);
            return new ResponseEntity<Room>(currentRoom, HttpStatus.OK);
        } else {
            return new ResponseEntity<Room>(HttpStatus.IM_USED);
        }
    }

    @RequestMapping(value = "/rooms/leave/{id}", method = RequestMethod.POST)
    public ResponseEntity<Room> leave(@PathVariable("id") long id, @RequestHeader(value = "Authorization") String token) {
        Room currentRoom = roomService.findById(id);
        token = token.substring(7);
        User currentUser = userService.findByToken(token);
        if (currentUser == currentRoom.getFirstUser()) {
            currentRoom.setFirstUser(null);
            roomService.updateRoom(currentRoom);
            return new ResponseEntity<Room>(currentRoom, HttpStatus.OK);
        } else if (currentUser == currentRoom.getSecondUser()) {
            currentRoom.setSecondUser(null);
            roomService.updateRoom(currentRoom);
            return new ResponseEntity<Room>(currentRoom, HttpStatus.OK);
        } else {
            return new ResponseEntity<Room>(HttpStatus.BAD_REQUEST);
        }
    }

    //API cập nhật một Room với ID trên url.
    @RequestMapping(value = "/rooms/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Room> updateRoom(@PathVariable("id") long id, @RequestBody Room room) {
        System.out.println("Updating Room " + id);

        Room currentRoom = roomService.findById(id);

        if (currentRoom == null) {
            System.out.println("Room with id " + id + " not found");
            return new ResponseEntity<Room>(HttpStatus.NOT_FOUND);
        }

        currentRoom.setId(room.getId());
        currentRoom.setPassword(room.getPassword());
        currentRoom.setFirstUser(room.getFirstUser());
        currentRoom.setSecondUser(room.getSecondUser());

        roomService.updateRoom(currentRoom);
        return new ResponseEntity<Room>(currentRoom, HttpStatus.OK);
    }

    //API xóa một Room với ID trên url.
    @RequestMapping(value = "/rooms/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Room> deleteUser(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting Room with id " + id);

        Room room = roomService.findById(id);
        if (room == null) {
            System.out.println("Unable to delete. Room with id " + id + " not found");
            return new ResponseEntity<Room>(HttpStatus.NOT_FOUND);
        }

        roomService.deleteRoom(id);
        return new ResponseEntity<Room>(HttpStatus.NO_CONTENT);
    }

//    @RequestMapping(value = "/usersname/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<User> getAvatarByName(@PathVariable("id") String id) {
//        System.out.println("Fetching User with name " + id);
//        User account = userService.findByName(id);
//        if (account == null) {
//            System.out.println("User with name " + id + " not found");
//            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<User>(account, HttpStatus.OK);
//    }

}
