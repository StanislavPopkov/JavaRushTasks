package com.javarush.task.task40.task4004;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/* 
Принадлежность точки многоугольнику
*/

class Point {
    public int x;
    public int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class Solution {
    public static void main(String[] args) {
        List<Point> polygon = new ArrayList<>();
        polygon.add(new Point(0, 0));
        polygon.add(new Point(0, 10));
        polygon.add(new Point(10, 10));
        polygon.add(new Point(10, 0));

        System.out.println(isPointInPolygon(new Point(5, 5), polygon));
        System.out.println(isPointInPolygon(new Point(100, 100), polygon));
    }

    public static boolean isPointInPolygon(Point point, List<Point> polygon) {
/*        Polygon polygon1 = new Polygon();
        for (Point point1 : polygon) {
            polygon1.addPoint(point1.x, point1.y);
        }
        polygon1.contains(point.x, point.y);*/

        int i1, i2, n, N, S, S1, S2, S3;
        int flag = 0;
        N = polygon.size();
        for (n=0; n<N; n++) {
            flag = 0;
            i1 = n < N-1 ? n + 1 : 0;
            while (flag == 0)
            {
                i2 = i1 + 1;
                if (i2 >= N)
                    i2 = 0;
                if (i2 == (n < N-1 ? n + 1 : 0))
                    break;
                S = Math.abs (polygon.get(i1).x * (polygon.get(i2).y - polygon.get(n).y) + polygon.get(i2).x *
                        (polygon.get(n).y - polygon.get(i1).y) + polygon.get(n).x * (polygon.get(i1).y - polygon.get(i2).y));
                S1 = Math.abs (polygon.get(i1).x * (polygon.get(i2).y - point.y) + polygon.get(i2).x * (point.y - polygon.get(i1).y)
                        + point.x * (polygon.get(i1).y - polygon.get(i2).y));
                S2 = Math.abs (polygon.get(n).x * (polygon.get(i2).y - point.y) + polygon.get(i2).x * (point.y - polygon.get(n).y)
                        + point.x * (polygon.get(n).y - polygon.get(i2).y));
                S3 = Math.abs (polygon.get(i1).x * (polygon.get(n).y - point.y) + polygon.get(n).x *
                        (point.y - polygon.get(i1).y) + point.x * (polygon.get(i1).y - polygon.get(n).y));
                if (S == S1 + S2 + S3) {
                    flag = 1;
                    break;
                }
                i1 = i1 + 1;
                if (i1 >= N)
                    i1 = 0;
                break;
            }
            if (flag == 0)
                break;
        }
        return flag == 1;
    }

}

