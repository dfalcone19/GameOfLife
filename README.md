# Conway's Game of Life - Java Swing Implementation

## Introduction

This is a Java Swing implementation of Conway's Game of Life, a cellular automaton devised by the mathematician John Conway. In this version of the game, you can interact with the grid by creating patterns manually or generating random ones. You also have the flexibility to customize the rules, colors, and more. Let's get started!

## Getting Started

To run the Conway's Game of Life program, follow these steps:

Clone or download the repository to your local machine.
Open the project in your preferred Java development environment (e.g., Eclipse, IntelliJ IDEA).
Build and run the program.

## Features

Blank Grid

Upon launching the game, you'll be presented with a blank grid to start with.

Random Pattern

Click the "Random" button to generate a random pattern on the grid. This adds an element of surprise and unpredictability to the game.

Manual Mode

In manual mode, you can click on the cells of the grid to toggle them between alive and dead. Use this feature to create custom patterns or make changes during gameplay.

Rule Configuration

You can customize the rules of the game by inputting a binary configuration in the "Model" textbox. This allows you to experiment with different rule sets and observe how they affect the evolution of the cells.

Multicolour Option

Enabling the "Multicolour" checkbox will dynamically assign colors to each cell based on the number of living neighbors. This adds a visual dimension to the game and makes it more engaging.

Custom Base Colour

Click the "Color" button to select your own base color for the cells. This allows you to personalize the appearance of the grid.

Start and Stop

Use the "Start" button to begin the simulation, and the "Stop" button to pause it. Watch how the cells evolve and interact based on the rules and configurations you've set.

## Game Rules

Conway's Game of Life follows a few simple rules in the standard configuration:

- Any live cell with fewer than two live neighbors dies (underpopulation).
- Any live cell with two or three live neighbors lives on to the next generation.
- Any live cell with more than three live neighbors dies (overpopulation).
- Any dead cell with exactly three live neighbors becomes a live cell (reproduction).

## Acknowledgments

John Conway for creating Conway's Game of Life.
Swing for providing the Java GUI components.
