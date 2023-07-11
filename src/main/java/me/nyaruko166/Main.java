package me.nyaruko166;

import com.google.gson.*;
import me.nyaruko166.entity.Person;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        List<String> lstFilePath;
        while (true) {
            System.out.println("\nEnter the absolute path to the directory containing the json files to be merged:");
            String absolutePath = sc.nextLine();

            lstFilePath = getAllJsonFiles(absolutePath);
            if (lstFilePath != null) {
                break;
            }
        }
        List<JsonElement> listJSON = new ArrayList<>();

        //Đọc từng filePath và add data vào List

        for (String fileName : lstFilePath) {
            try {
                JsonElement jsonElement = readJsonFromFile(fileName);
                if (jsonElement != null) {
                    listJSON.add(jsonElement);
                }
            } catch (IOException e) {
                System.out.println("Unable to read file: " + fileName);
                e.printStackTrace();
            }
        }

        //Chuyển element sang array json
        JsonArray jsonArray = new JsonArray();
        for (JsonElement jsonElement : listJSON) {
            if (jsonElement.isJsonArray()) {
                jsonArray.addAll(jsonElement.getAsJsonArray());
            } else {
                jsonArray.add(jsonElement);
            }
        }

        //Tạo file merged
        if (lstFilePath != null) {
            System.out.println("Enter your json merged file name: ");
            String mergedFileName = sc.nextLine() + ".json";
            try {
                writeJsonToFile(jsonArray, mergedFileName);
                System.out.println("Merge JSON file successfully.\nFile name: " + mergedFileName);
            } catch (IOException e) {
                System.out.println("Unable to write merge file: " + mergedFileName);
                e.printStackTrace();
            }
        }
//        Gson gson = new Gson();
//        JsonElement jsonElement = readJsonFromFile("./src/main/resources/rawdata/people_0.json");
//        JsonArray jsonArray = jsonElement.getAsJsonArray();
//        List<Person> lstPerson = new ArrayList<>();
//        for (JsonElement x : jsonArray) {
//            JsonObject jsonObject = x.getAsJsonObject();
//            Person p = gson.fromJson(x, Person.class);
//            p.setFirstName(jsonObject.get("first_name").getAsString());
//            p.setLastName(jsonObject.get("last_name").getAsString());
//            p.setIpAddress(jsonObject.get("ip_address").getAsString());
//            lstPerson.add(p);
//        }
//        for (Person y : lstPerson) {
//            System.out.println(y.toString());
//        }
    }

    private static JsonElement readJsonFromFile(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        JsonElement jsonElement = JsonParser.parseReader(reader);
        reader.close();
        return jsonElement;
    }

    private static void writeJsonToFile(JsonElement jsonElement, String fileName) throws IOException {
        Gson gson = new GsonBuilder()
                .disableHtmlEscaping() // Tắt mã hóa HTML
                .setPrettyPrinting()
                .create();
        File file = new File(fileName);
        if (!file.exists()) {

            file.createNewFile();
            FileWriter writer = new FileWriter(fileName);
            writer.write(gson.toJson(jsonElement));
            writer.close();
        } else {
            FileWriter writer = new FileWriter(fileName);
            writer.write(gson.toJson(jsonElement));
            writer.close();
        }

    }

    private static List<String> getAllJsonFiles(String path) {
        List<String> lstFilePath = new ArrayList<>();
        File folderJson = new File(path);
        if (!folderJson.exists()) {
            System.out.println("The path does not exist!!!");
            return null;
        } else if (!folderJson.isDirectory()) {
            System.out.println("The path is not a folder!!!");
            return null;
        } else {
            for (File f : Objects.requireNonNull(folderJson.listFiles())) {
                if (f.isFile() && f.getName().endsWith(".json")) {
                    lstFilePath.add(f.getPath());
                }
            }
            return lstFilePath;
        }
//        for (String x : lstFilePath) {
//            System.out.println(x);
//        }
    }
}