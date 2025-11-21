package com.example.demo.model;

public enum Direction {
	N, E, S, W;

	public Direction turnLeft() {
		return switch (this) {
		case N -> W;
		case W -> S;
		case S -> E;
		case E -> N;
		};
	}

	public Direction turnRight() {
		return switch (this) {
		case N -> E;
		case E -> S;
		case S -> W;
		case W -> N;
		};
	}

	// delta when moving forward
	public int dx() {
		return switch (this) {
		case E -> 1;
		case W -> -1;
		default -> 0;
		};
	}

	public int dy() {
		return switch (this) {
		case N -> 1;
		case S -> -1;
		default -> 0;
		};
	}
}