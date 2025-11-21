package com.example.demo.controller;


import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Grid;
import com.example.demo.model.Position;
import com.example.demo.repo.InMemoryRepo;
import com.example.demo.service.ProbeService;

@RestController
@RequestMapping("/grid")
public class GridController {

	private final ProbeService service;

	public GridController(ProbeService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<?> createGrid(@RequestBody Grid grid) {
		if (grid.getWidth() <= 0 || grid.getHeight() <= 0) {
			return ResponseEntity.badRequest().body("width and height must be > 0");
		}
		// normalize obstacles (ensure valid positions)
		Set<Position> obs = grid.getObstacles();
		if (obs != null) {
			obs.removeIf(
					p -> p.getX() < 0 || p.getY() < 0 || p.getX() >= grid.getWidth() || p.getY() >= grid.getHeight());
		}
		service.createGrid(grid);
		return ResponseEntity.ok(grid);
	}

	@GetMapping
	public ResponseEntity<?> getGrid() {
		Grid g = InMemoryRepo.getGrid();
		if (g == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(g);
	}
}
