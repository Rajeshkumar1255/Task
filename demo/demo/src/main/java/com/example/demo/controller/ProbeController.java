package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.CommandRequest;
import com.example.demo.model.Direction;
import com.example.demo.model.Position;
import com.example.demo.model.Probe;
import com.example.demo.repo.InMemoryRepo;
import com.example.demo.service.ProbeService;

@RestController
@RequestMapping("/demo")
public class ProbeController {

	private final ProbeService service;

	public ProbeController(ProbeService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<?> placeProbe(@RequestBody ProbeInitRequest req) {
		try {
			Position pos = new Position(req.getX(), req.getY());
			Direction dir = Direction.valueOf(req.getDirection().toUpperCase());
			service.placeProbe(pos, dir);
			return ResponseEntity.ok(InMemoryRepo.getProbe());
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (IllegalStateException e) {
			return ResponseEntity.status(409).body(e.getMessage());
		}
	}

	@PostMapping("/commands")
	public ResponseEntity<?> sendCommands(@RequestBody CommandRequest cr) {
		try {
			Probe p = service.sendCommands(cr.getCommands());
			return ResponseEntity.ok(p);
		} catch (IllegalStateException | IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<?> status() {
		Probe p = InMemoryRepo.getProbe();
		if (!p.isInitialized())
			return ((BodyBuilder) ResponseEntity.notFound()).body("Probe not initialized");
		return ResponseEntity.ok(p);
	}

	@DeleteMapping
	public ResponseEntity<?> reset() {
		InMemoryRepo.resetProbe();
		return ResponseEntity.ok("Probe reset");
	}

	// small DTO for placing the probe
	public static class ProbeInitRequest {
		private int x;
		private int y;
		private String direction;

		public ProbeInitRequest() {
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public String getDirection() {
			return direction;
		}

		public void setDirection(String direction) {
			this.direction = direction;
		}
	}
}
