package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;

public class Grid {
	private int width;
	private int height;
	private Set<Position> obstacles = new HashSet<>();

	public Grid() {
	}

	public Grid(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Set<Position> getObstacles() {
		return obstacles;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setObstacles(Set<Position> obstacles) {
		this.obstacles = obstacles;
	}

	public boolean inBounds(Position p) {
		return p.getX() >= 0 && p.getX() < width && p.getY() >= 0 && p.getY() < height;
	}

	public boolean isObstacle(Position p) {
		return obstacles.contains(p);
	}
}
