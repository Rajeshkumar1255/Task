package com.example.demo.repo;

import com.example.demo.model.Grid;
import com.example.demo.model.Probe;

public class InMemoryRepo {
	private static Grid grid = null;
	private static Probe probe = new Probe();

	public static Grid getGrid() {
		return grid;
	}

	public static void setGrid(Grid g) {
		grid = g;
	}

	public static Probe getProbe() {
		return probe;
	}

	public static void resetProbe() {
		probe = new Probe();
	}
}