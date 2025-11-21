package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public class Probe {
	private Position position;
	private Direction direction;
	private final List<Position> visited = new ArrayList<>();
	private Position blockedAt; // the position that blocked the last attempted move (if any)
	private boolean initialized = false;

	public Probe() {
	}

	public Position getPosition() {
		return position;
	}

	public Direction getDirection() {
		return direction;
	}

	public List<Position> getVisited() {
		return visited;
	}

	public Position getBlockedAt() {
		return blockedAt;
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void init(Position pos, Direction dir) {
		this.position = pos.copy();
		this.direction = dir;
		visited.clear();
		visited.add(pos.copy());
		blockedAt = null;
		initialized = true;
	}

	public void setBlockedAt(Position p) {
		this.blockedAt = p;
	}

	public void addVisited(Position p) {
		visited.add(p.copy());
	}
}
