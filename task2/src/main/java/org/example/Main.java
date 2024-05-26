package org.example;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите путь к файлу с координатами и радиусом окружности");
        String circleFilePath = scanner.nextLine();
        Point centre = new Point(0, 0); // центр окружности
        double circleRadius = 0; // радиус окружности

        System.out.println("Введите путь к файлу с координатами точек");
        String pointsFilePath = scanner.nextLine();
        ArrayList<Point> pointsList = new ArrayList<>(); // список точек

        // чтение из файла с параметрами окружности
        try {
            File circleFile = new File(circleFilePath);
            Scanner fileScanner = new Scanner(circleFile);
            centre = new Point(fileScanner.nextDouble(), fileScanner.nextDouble());
            circleRadius = fileScanner.nextDouble();
            fileScanner.close();
        } catch (FileNotFoundException circleFileNotFound) {
            System.out.println("Файл не найден.");
            return;
        }
        // чтение из файла с координатами точек
        try {
            File pointsFile = new File(pointsFilePath);
            Scanner fileScanner = new Scanner(pointsFile);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                Scanner lineScanner = new Scanner(line);
                Point point = new Point(lineScanner.nextDouble(), lineScanner.nextDouble());
                pointsList.add(point);
                lineScanner.close();
            }
            fileScanner.close();
        } catch (FileNotFoundException pointsFileNotFound) {
            System.out.println("Файл не найден.");
            return;
        }
        scanner.close();

        // обход списка точек
        for (Point currentPoint : pointsList) {
            double distance = Math.sqrt((currentPoint.x() - centre.x()) * (currentPoint.x() - centre.x()) + (currentPoint.y() - centre.y()) * (currentPoint.y() - centre.y()));
            if (distance < circleRadius) {
                System.out.println(1);
            } else if (distance > circleRadius) {
                System.out.println(2);
            } else {
                System.out.println(0);
            }
        }
    }
}

record Point(double x, double y) {}