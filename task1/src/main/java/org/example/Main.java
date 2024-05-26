package org.example;
import javax.swing.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InputMismatchException {
        // ввод чисел
        int numN = 0;
        int numM = 0;
        Scanner scanner = new Scanner(System.in);
        try {
            numN = scanner.nextInt();
            numM = scanner.nextInt();
            scanner.close();
        } catch (InputMismatchException ime) {
            System.out.println("Указаны дробные значения. Создание/обход массива с такими параметрами невозможен.");
            return;
        }
        // при m = 0 обход невозможен
        if (numM <= 0) {
            System.out.println("Указана нулевая или отрицательная длина обхода. Обход массива невозможен.");
            return;
        }
        // при m = 1 стартовый элемент является и конечным
        if (numM == 1) {
            System.out.print("Круговой массив: ");
            for (int i = 1; i <= numN; i++) {
                System.out.print(i);
            }
            System.out.println(".");
            System.out.println("При длине обхода " + numM + " получаем интервал: 1. Полученный путь: 1.");
            return;
        }
        if (numM > 1) {
            // если m > 1, получается массив из целых положительных чисел, как в примере
            if (numN > 1) {
                // интервалы обхода
                ArrayList<String> intervals = new ArrayList<>();
                // путь обхода
                String path = "";
                // начальная позиция
                int startNumber = 1;
                do {
                    String newPath = "";
                    for (int j = 0; j < numM; j++) {
                        if (startNumber > numN) {
                            startNumber = 1;
                        }
                        if (j == 0) {
                            path += String.valueOf(startNumber);
                        }
                        newPath += String.valueOf(startNumber);
                        ++startNumber;
                    }
                    intervals.add(newPath);
                    --startNumber;
                } while (startNumber != 1);
                // вывод
                System.out.print("Круговой массив: ");
                for (int i = 1; i <= numN; i++) {
                    System.out.print(i);
                }
                System.out.println(".");
                System.out.print("При длине обхода " + numM + " получаем интервалы: ");
                for (int i = 0; i < intervals.size(); i++)
                {
                    System.out.print(intervals.get(i));
                    if (i < intervals.size() - 1) {
                        System.out.print(", ");
                    } else {
                        System.out.print(". ");
                    }
                }
                System.out.println("Полученный путь: " + path + ".");
                return;
            } else { // при m < 1 последующие элементы массива будут меньше первого
                // интервалы обхода
                ArrayList<String> intervals = new ArrayList<>();
                // путь обхода
                String path = "";
                // начальная позиция

                int startNumber = 1;
                do {
                    String newPath = "";
                    for (int j = 0; j < numM; j++) {
                        if (startNumber < numN) {
                            startNumber = 1;
                        }
                        if (j == 0) {
                            path += String.valueOf(startNumber);
                        }
                        newPath += String.valueOf(startNumber);
                        --startNumber;
                    }
                    intervals.add(newPath);
                    ++startNumber;
                } while (startNumber != 1);
                System.out.print("При длине обхода " + numM + " получаем интервалы: ");
                for (int i = 0; i < intervals.size(); i++)
                {
                    System.out.print(intervals.get(i));
                    if (i < intervals.size() - 1) {
                        System.out.print(", ");
                    } else {
                        System.out.print(". ");
                    }
                }
                System.out.println("Полученный путь: " + path + ".");
            }
        }
    }
}