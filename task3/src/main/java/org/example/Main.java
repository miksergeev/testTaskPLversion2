package org.example;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;


class Parser {
    // парсинг объектов из values
    public static HashMap<String, String> parseValuesJSON(String filePathValuesJSON) {
        // здесь будет словарь ключ-значение id:value
        HashMap<String, String> dictionaryValues = new HashMap<String, String>();
        JSONParser parser = new JSONParser();
            try (Reader reader = new FileReader(filePathValuesJSON)) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray valuesArrayJSON = (JSONArray) jsonObject.get("values");
            Iterator<JSONObject> iterator = valuesArrayJSON.iterator();
            while (iterator.hasNext()) {
                JSONObject currentTestValueJSON = iterator.next();
                dictionaryValues.put(currentTestValueJSON.get("id").toString(), currentTestValueJSON.get("value").toString());
            }
        }
            catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
                System.out.println("Файл values.json по указанному пути отсутствует.");
            }
            catch (IOException e) {
                e.printStackTrace();
        }
            catch (ParseException e) {
                e.printStackTrace();
        }
            return dictionaryValues;
    }

    public static JSONArray fillValuesTestsJSON(JSONArray testsJSONArray, HashMap<String, String> dictionaryValues) {
        Iterator<JSONObject> iterator = testsJSONArray.iterator();
        while (iterator.hasNext()) {
            JSONObject currentTestJSON = iterator.next();
            // в исходном файле tests.json для некоторых тестов отсутствовали поля value, поэтому их необходимо добавить для таких тестов
            // например, это тесты 110, 230 или 261
            // в случае потенциального отсутствия других полей в других входящих json-файлах это тоже необходимо будет предусмотреть схожим образом
            if (currentTestJSON.get("value") == null) {
                currentTestJSON.put("value", "");
            }

            // заменяем значения в поле value
            if (dictionaryValues.get(currentTestJSON.get("id").toString()) == null) {
                currentTestJSON.replace("value", "");
            } else {
                currentTestJSON.replace("value", dictionaryValues.get(currentTestJSON.get("id").toString()));
            }

            JSONArray arrayOfValues = (JSONArray) currentTestJSON.get("values");
            if (arrayOfValues != null) {
                for (int i = 0; i < arrayOfValues.size(); i++) {
                    fillValuesTestsJSON(arrayOfValues, dictionaryValues);
                }
            }
        }
        return testsJSONArray;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите путь к файлу values.json");
        String filePathValuesJSON= scanner.nextLine();
        System.out.println("Введите путь к файлу tests.json");
        String filePathTestsJSON = scanner.nextLine();
        System.out.println("Введите путь к файлу report.json");
        String filePathReportJSON = scanner.nextLine();
        scanner.close();

        // словарь со значениями values
        HashMap<String, String> dictionaryValues = Parser.parseValuesJSON(filePathValuesJSON);

        // парсинг объектов из tests
        try (Reader reader = new FileReader(filePathTestsJSON)) {
            JSONParser parser = new JSONParser();
            JSONObject fileTestsJSON = (JSONObject) parser.parse(reader);
            JSONArray testsJSONArray = (JSONArray) fileTestsJSON.get("tests");

            // mapper используется для форматирования сохраняемого файла report.json
            ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            String outputReportJSONFile = "{\n  \"tests\": " + mapper.writeValueAsString(Parser.fillValuesTestsJSON(testsJSONArray, dictionaryValues)) + "\n}";
            try (PrintWriter out = new PrintWriter(filePathReportJSON)) {
                out.println(outputReportJSONFile);
                if (out.checkError() == true) {
                    System.out.println("Запись файла report.json по указанному адресу невозможна.");
                } else {
                    System.out.println("Файл report.json сохранен по адресу " + filePathReportJSON);
                }
            }
        }
        catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            System.out.println("Файл tests.json по указанному адресу отсутствует.");
            return;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }
}