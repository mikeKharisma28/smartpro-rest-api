package com.juaracoding.smartpro_rest_api.util;

import com.github.javafaker.Faker;

import java.time.Year;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataGenerator {
    private Faker faker = new Faker(new Locale("in_ID", "ID"));
    private boolean isValid = false;
    private Matcher matcher = null;
    private int intLoop = 0;

    public String dataUsername() {
        isValid = false;
        intLoop = 0;
        String username = "";
        while (!isValid) {
            try {
                username = faker.name().username();
                matcher = Pattern.compile("^([a-z0-9\\.]{8,16})$").matcher(username);
                matcher.find();
                if (intLoop == 250) {
                    System.out.println("Reached 250 times and failed!");
                    System.exit(1);
                }
                intLoop++;
            } catch (Exception e) {
                isValid = false;
            }
        }

        return username;
    }

    public String dataFullName() {
        isValid = false;
        intLoop = 0;
        String fullName = "";
        while (!isValid) {
            try {
                fullName = faker.name().fullName();
                matcher = Pattern.compile("^[a-zA-Z\\s?]{6,45}$").matcher(fullName);
                matcher.find();
                if (intLoop == 250) {
                    System.out.println("Reached 250 times and failed!");
                    System.exit(1);
                }
                intLoop++;
            } catch (Exception e) {
                isValid = false;
            }
        }

        return fullName;
    }

    public String dataPassword() {
        isValid = false;
        intLoop = 0;
        String password = "";
        while(!isValid) {
            try {
                password = faker.internet().password(8,15,true,true,true);
                matcher = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@_#\\-$])[\\w].{8,15}$").matcher(password);
                isValid = matcher.find();
                if(intLoop == 250){
                    System.out.println("Reached 250 times and failed!");
                    System.exit(1);
                }
                intLoop++;
            } catch (Exception e) {
                isValid = false;
            }
        }
        return password;
    }

    public String dataBarang() {
        List<String> officeObjects = List.of(
                "Desk", "Chair", "Laptop", "Monitor", "Keyboard", "Mouse", "Printer",
                "Whiteboard", "Projector", "Stapler", "Notebook", "Pen", "Paper Tray"
        );

        Random random = new Random();
        return officeObjects.get(random.nextInt(officeObjects.size()));
    }

    public String dataDescription(Integer numOfChars) {
        return faker.lorem().characters(numOfChars);
    }

    public String dataProcurement() {
        isValid = false;
        intLoop = 0;
        String procurement = "";
        while (!isValid) {
            try {
                procurement = faker.name().username();
                matcher = Pattern.compile("^([a-z0-9\\.]{8,16})$").matcher(generateNo("PRC"));
                matcher.find();
                if (intLoop == 250) {
                    System.out.println("Reached 250 times and failed!");
                    System.exit(1);
                }
                intLoop++;
            } catch (Exception e) {
                isValid = false;
            }
        }

        return procurement;
    }

    private String generateNo(String prefix) {
        Random random = new Random();
        Long number = random.nextLong(1000000000, 9999999999L);
        String year = String.valueOf(Year.now().getValue()).substring(2);
        return String.format("%s-%s%s", prefix, year, number.toString());
    }
}
