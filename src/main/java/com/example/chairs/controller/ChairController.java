package com.example.chairs.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.chairs.model.Chair;
import com.example.chairs.repository.ChairRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class ChairController {

  @Autowired
  ChairRepository chairRepository;

  @GetMapping("/chairs")
  public ResponseEntity<List<Chair>> getAllChairs(@RequestParam(required = false) String room) {
    try {
      List<Chair> chairsList = new ArrayList<Chair>();

      if (room == null)
        chairRepository.findAll().forEach(chairsList::add);
      else
        chairRepository.findByRoomContaining(room).forEach(chairsList::add);

      if (chairsList.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      System.out.println(chairsList);
      return new ResponseEntity<>(chairsList, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/chairs/{id}")
  public ResponseEntity<Chair> getChairById(@PathVariable("id") long id) {
    Optional<Chair> chairData = chairRepository.findById(id);

    if (chairData.isPresent()) {
      return new ResponseEntity<>(chairData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/chairs")
  public ResponseEntity<Chair> createChair(@RequestBody Chair chair) {
    try {
      Chair _chair = chairRepository
          .save(new Chair(chair.getRoom(), chair.getPatient(), false));
      return new ResponseEntity<>(_chair, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
    }
  }

  @PutMapping("/chairs/{id}")
  public ResponseEntity<Chair> updateChair(@PathVariable("id") long id, @RequestBody Chair chair) {
    Optional<Chair> chairData = chairRepository.findById(id);

    if (chairData.isPresent()) {
      Chair _chair = chairData.get();
      _chair.setRoom(chair.getRoom());
      _chair.setPatient(chair.getPatient());
      _chair.setOccupied(chair.isOccupied());
      return new ResponseEntity<>(chairRepository.save(_chair), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/chairs/{id}")
  public ResponseEntity<HttpStatus> deleteChair(@PathVariable("id") long id) {
    try {
      chairRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }

  @DeleteMapping("/chairs")
  public ResponseEntity<HttpStatus> deleteAllChairs() {
    try {
      chairRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }

  }

  @GetMapping("/chairs/occupied")
  public ResponseEntity<List<Chair>> findByOccupied() {
    try {
      List<Chair> chairs = chairRepository.findByOccupied(true);

      if (chairs.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(chairs, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }

}