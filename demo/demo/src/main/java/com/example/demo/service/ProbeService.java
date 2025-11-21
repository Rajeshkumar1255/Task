package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Direction;
import com.example.demo.model.Grid;
import com.example.demo.model.Position;
import com.example.demo.model.Probe;
import com.example.demo.repo.InMemoryRepo;

@Service
public class ProbeService {

	public void createGrid(Grid grid) {
		InMemoryRepo.setGrid(grid);
		InMemoryRepo.resetProbe();
	}

	public void placeProbe(Position pos, Direction dir) {
		Grid g = InMemoryRepo.getGrid();
		if (g == null)
			throw new IllegalStateException("Grid not initialized");
		if (!g.inBounds(pos))
			throw new IllegalArgumentException("Starting position out of bounds");
		if (g.isObstacle(pos))
			throw new IllegalArgumentException("Starting position is an obstacle");
		InMemoryRepo.getProbe().init(pos, dir);
	}

	public Object getProbeStatus() {
		Probe p = InMemoryRepo.getProbe();
		return p;
	}

	public Probe sendCommands(String commands) {
		Probe p = InMemoryRepo.getProbe();
		Grid g = InMemoryRepo.getGrid();
		if (!p.isInitialized())
			throw new IllegalStateException("Probe not initialized");
		if (g == null)
			throw new IllegalStateException("Grid not initialized");
		p.setBlockedAt(null);

		// process each char
		for (char c : commands.toUpperCase().toCharArray()) {
			switch (c) {
			case 'F' -> attemptMove(p, g, +1);
			case 'B' -> attemptMove(p, g, -1);
			case 'L' -> p = turnLeft(p);
			case 'R' -> p = turnRight(p);
			default -> {

			}
			}
		}
		return p;
	}

	private Probe turnLeft(Probe p) {
		p.init(p.getPosition(), p.getDirection().turnLeft());

		return turnLeftFixed(p);
	}

	private Probe turnLeftFixed(Probe p) {
		Direction d = p.getDirection().turnLeft();

		p = setDirection(p, p.getDirection().turnLeft());
		return p;
	}

	private Probe turnRight(Probe p) {
		p = setDirection(p, p.getDirection().turnRight());
		return p;
	}

	private Probe setDirection(Probe p, Direction d) {

		Position pos = p.getPosition().copy();
		List<Position> visited = new ArrayList<>(p.getVisited());
		Position blocked = p.getBlockedAt();
		Probe newP = new Probe();
		newP.init(pos, d);

		newP.getVisited().clear();
		newP.getVisited().addAll(visited);
		newP.setBlockedAt(blocked);
		return newP;
	}

	private void attemptMove(Probe p, Grid g, int step) {
		Direction dir = p.getDirection();
		int nx = p.getPosition().getX() + dir.dx() * step;
		int ny = p.getPosition().getY() + dir.dy() * step;
		Position next = new Position(nx, ny);

		if (!g.inBounds(next)) {
			p.setBlockedAt(next);

			return;
		}
		if (g.isObstacle(next)) {
			p.setBlockedAt(next);
			return;
		}

		List<Position> visited = new ArrayList<>(p.getVisited());
		Position blocked = p.getBlockedAt();

		Probe newP = new Probe();
		newP.init(next, dir);

		newP.getVisited().clear();
		newP.getVisited().addAll(visited);
		newP.addVisited(next);
		newP.setBlockedAt(null);

		InMemoryRepo.resetProbe();

		Probe repoP = InMemoryRepo.getProbe();
		repoP.init(newP.getPosition(), newP.getDirection());
		repoP.getVisited().clear();
		repoP.getVisited().addAll(newP.getVisited());
		repoP.setBlockedAt(newP.getBlockedAt());
	}
}
