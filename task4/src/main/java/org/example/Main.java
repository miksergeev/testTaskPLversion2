package org.example;
import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите путь к файлу со значениями чисел: ");
        String numbersFilePath = scanner.nextLine();
        ArrayList<Integer> pointsList = new ArrayList<>();

        try {
            File numbersFile = new File(numbersFilePath);
            Scanner fileScanner = new Scanner(numbersFile);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                Scanner lineScanner = new Scanner(line);
                try {
                    pointsList.add(lineScanner.nextInt());
                } catch (InputMismatchException ime) {
                    System.out.println("В файле указано по крайней мере одно дробное значение элемента массива, что противоречит условию задачи.");
                    return;
                }
                lineScanner.close();
            }
            fileScanner.close();
        } catch (FileNotFoundException pointsFileNotFound) {
            System.out.println("Файл не найден.");
            return;
        }
        scanner.close();

        int minimumOfSteps = Integer.MAX_VALUE;
        for (int i = 0; i < pointsList.size(); i++) {
            int numberOfSteps = 0;
            for (int j = 0; j < pointsList.size(); j++) {
                numberOfSteps += Math.abs(pointsList.get(i) - pointsList.get(j));
            }
            if (numberOfSteps < minimumOfSteps) {
                minimumOfSteps = numberOfSteps;
            }
        }
        System.out.println(minimumOfSteps);
    }
}