package com.example.chairs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.chairs.model.Chair;

public interface ChairRepository extends JpaRepository<Chair, Long> {
  List<Chair> findByOccupied(boolean occupied);
  List<Chair> findByRoomContaining(String room);
}