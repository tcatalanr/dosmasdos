
package com.example.chairs.model;

import javax.persistence.*;

@Entity
@Table(name = "chairs")
public class Chair{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "room")
	private String room;

	@Column(name = "patient")
	private String patient;

	@Column(name = "occupied")
	private boolean occupied;

    public Chair() {
    }

	public Chair(String room, String patient, boolean occupied) {
		this.room = room;
		this.patient = patient;
		this.occupied = occupied;
	}
	public long getId() {
		return id;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getPatient() {
		return patient;
	}

	public void setPatient(String patient) {
		this.patient = patient;
	}

	public boolean isOccupied() {
		return occupied;
	}

	public void setOccupied(boolean isOccupied) {
		this.occupied = isOccupied;
	}

	@Override
	public String toString() {
		return "Silla [id=" + id + ", Sala=" + room + ", Paciente=" + patient + ", En uso=" + occupied + "]";
	}
}