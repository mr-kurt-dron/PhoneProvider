package com.example.phoneprovider;

import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PhoneDriver {
    private final DB db;
    private List<Phone> phoneList;

    public PhoneDriver(DB db) {
        this.db = db;
    }

    public List<Phone> getPhoneList() {
        if (phoneList == null) {
            loadPhoneList();
        }
        return phoneList;
    }

    private void loadPhoneList() {
        List<Phone> loadedPhoneList = new ArrayList<>();

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
            this.phoneList = loadedPhoneList;

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

        var provider = getPhoneList().stream().filter(x -> x.getCode() == code
                        && (num >= x.getNumfirst() && num <= x.getNumlast()))
                .findFirst();

        return provider.map(Phone::getProvider).orElse("Incorrect number");
    }
}