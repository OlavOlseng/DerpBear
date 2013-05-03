package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.lwjgl.util.Point;

import rendering.Tile;
import rendering.TileType;

public class DungeonGenerator {

	public static void main(String[] args) {
		new DungeonGenerator().generateRoom(10, 5, 2,System.currentTimeMillis());
	}

	public Tile[][] generateRoom(int base, int sides, int res, long seed) {

		Tile[][] result = new Tile[base + sides * 2][base + sides * 2];

		for (int i = 0; i < result.length; i++)
			for (int j = 0; j < result[i].length; j++)
				result[i][j] = new Tile(TileType.NONE);

		Random rand = new Random();
		rand.setSeed(seed);
		ArrayList<Point> top = new ArrayList<Point>();
		ArrayList<Point> bottom = new ArrayList<Point>();
		ArrayList<Point> left = new ArrayList<Point>();
		ArrayList<Point> right = new ArrayList<Point>();

		for (int i = 0; i < res; i++)
			top.add(new Point(sides + rand.nextInt(sides), sides + base
					+ rand.nextInt(sides)));
		for (int i = 0; i < res; i++)
			bottom.add(new Point(sides + rand.nextInt(sides), rand
					.nextInt(sides)));
		for (int i = 0; i < res; i++)
			left.add(new Point(rand.nextInt(sides), sides + rand.nextInt(sides)));
		for (int i = 0; i < res; i++)
			right.add(new Point(sides + base + rand.nextInt(sides), sides
					+ rand.nextInt(sides)));

		ArrayList<Point> points = new ArrayList<Point>();
		Collections.sort(top, new Comparator<Point>() {

			@Override
			public int compare(Point o1, Point o2) {

				return o1.getX() - o2.getX();
			}

		});
		Collections.sort(bottom, new Comparator<Point>() {

			@Override
			public int compare(Point o1, Point o2) {

				return o2.getX() - o1.getX();
			}
		});

		Collections.sort(left, new Comparator<Point>() {

			@Override
			public int compare(Point o1, Point o2) {

				return o2.getY() - o1.getY();
			}

		});

		Collections.sort(right, new Comparator<Point>() {

			@Override
			public int compare(Point o1, Point o2) {

				return o1.getY() - o2.getY();
			}

		});

		points.addAll(top);
		points.addAll(right);
		points.addAll(bottom);
		points.addAll(left);

		drawLine(result, points);
		fillGrid(result);
		
		return result;
	}

	private void drawLine(Tile[][] grid, List<Point> points) {

		for (int i = 0; i < points.size() - 1; i++) {
			drawSegment(grid, points.get(i), points.get(i + 1));
		}
		drawSegment(grid, points.get(0), points.get(points.size() - 1));
	}

	private void drawSegment(Tile[][] grid, Point p1, Point p2) {
		int x1, x2, y1, y2;
		x1 = p1.getX();
		x2 = p2.getX();
		y1 = p1.getY();
		y2 = p2.getY();

		int delta_x = (x2 - x1);

		int ix;
		if (delta_x > 0)
			ix = 1;
		else
			ix = -1;

		delta_x = Math.abs(delta_x) << 1;

		int delta_y = y2 - y1;

		int iy;
		if (delta_y > 0)
			iy = 1;
		else
			iy = -1;

		delta_y = Math.abs(delta_y) << 1;
		grid[y1][x1] = new Tile(TileType.GRASS);

		if (delta_x >= delta_y) {
			// error may go below zero
			int error = (delta_y - (delta_x >> 1));

			while (x1 != x2) {
				if ((error >= 0) && (error > 0 || (ix > 0))) {
					error -= delta_x;
					y1 += iy;
				}
				// else do nothing

				error += delta_y;
				x1 += ix;

				grid[y1][x1] = new Tile(TileType.GRASS);
			}
		} else {
			// error may go below zero
			int error = (delta_x - (delta_y >> 1));

			while (y1 != y2) {
				if ((error >= 0) && (error > 0 || (iy > 0))) {
					error -= delta_y;
					x1 += ix;
				}
				// else do nothing

				error += delta_x;
				y1 += iy;

				grid[y1][x1] = new Tile(TileType.GRASS);
			}
		}
	}
	
	private void fillGrid(Tile[][] grid){
	
		for(Tile[] row : grid){
			int start = 0, end = 0;
			while(start < row.length){
				while(start < row.length && row[start].getType().ordinal() == 0){
						start++;
				}
				if(start == row.length)
					continue;
				
				end = start+1;
				
				while(end < row.length && row[end].getType().ordinal() == 0){
					end++;
				}
				
				
				if(end == row.length && row[row.length-1].getType().ordinal() == 0){
					start = end;
					continue;
					
				}
					
				if(end < row.length){
					for(int i = start; i < end; i++){
						row[i] = new Tile(TileType.GRASS);
					}
				}
				start = end;
				
				
			}
		}
	}
}
