package com.example.phoneprovider;

import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class PhoneDriver {
    private final DB db;
    private Map<Integer, List<Phone>> phoneListMap;

    public PhoneDriver(DB db) {
        this.db = db;
    }

    public Map<Integer, List<Phone>> getPhoneListMap() {
        if (phoneListMap == null) {
            loadPhoneList();
        }
        return phoneListMap;
    }

    private void loadPhoneList() {
        var loadedPhoneListMap = new HashMap<Integer, List<Phone>>();
        var loadedPhoneList = new ArrayList<Phone>();

        try (var connection = db.connect();
             var stmt = connection.createStatement();
             var rs = stmt.executeQuery("select def, numfirst, numlast, provider from phonerules")) {

            while (rs.next()) {
                loadedPhoneList.add(
                        new Phone(rs.getInt("def"),
                                rs.getInt("numfirst"),
                                rs.getInt("numlast"),
                                rs.getString("provider")));
            }


            loadedPhoneList.stream()
                    .map(Phone::getCode)
                    .distinct()
                    .forEach(code -> loadedPhoneListMap.put(code, new ArrayList<>(loadedPhoneList.stream()
                            .filter(y -> y.getCode() == code).collect(Collectors.toList()))));

            this.phoneListMap = loadedPhoneListMap;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getProviderFromPhone(String phoneNumber) {
        if (phoneNumber.length() < 6 && (!phoneNumber.startsWith("7") || !phoneNumber.startsWith("8"))) {
            return "Incorrect phone format";
        }

        var code = Integer.parseInt(phoneNumber.substring(1,4));

        var num = Integer.parseInt(phoneNumber.substring(5));



        var provider = getPhoneListMap().getOrDefault(code, new ArrayList<>()).stream().filter(
                x -> num >= x.getNumfirst() && num <= x.getNumlast()
                ).findFirst();

        return provider.map(Phone::getProvider).orElse("Incorrect number");
    }
}