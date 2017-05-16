package ch.bernmobil.vibe.shared.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalTime;

@Converter(autoApply = true)
public class LocalTimeConverter implements AttributeConverter<LocalTime, String>{
    @Override
    public String convertToDatabaseColumn(LocalTime attribute) {
        if(attribute == null) {
            return null;
        }
        return attribute.toString();
    }

    @Override
    public LocalTime convertToEntityAttribute(String dbData) {
        if(dbData == null) {
            return null;
        }

        String[] digits = dbData.split(":");
        String[] correctTime = {"00", "00", "00"};

        switch(digits.length) {
            case 3: correctTime[2] = normalizeSecondsAndMinutes(digits[2]);
            case 2: correctTime[1] = normalizeSecondsAndMinutes(digits[1]);
            case 1: correctTime[0] = normalizeHour(digits[0]);
        }

        return LocalTime.parse(String.join(":", correctTime));
    }


    private String normalizeString(int digit) {
        if(digit < 10){
            return "0" + digit;
        }
         else {
            return Integer.toString(digit);
        }
    }

    private int parseInt(String digitString) {
        int digit;
        try {
            digit = Integer.parseInt(digitString);
        } catch (NumberFormatException ex) {
            return 0;
        }
        return digit;
    }

    private String normalizeSecondsAndMinutes(String digitString) {
        int digit = parseInt(digitString);
        if(digit > 60){
            return "00";
        }
        return normalizeString(digit);
    }

    private String normalizeHour(String hourString) {
        int hour = parseInt(hourString);
        if(hour > 23) {
            hour -= 24;
        }
        return normalizeString(hour);
    }




}
