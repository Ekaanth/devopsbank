package com.userfront.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by root on 29/06/17.
 */
public class Utils {

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Method to format date to dd/MM/yyyy format
    public static String formatDate(LocalDate dateInfo){
        String date = "";

        try {
            date = formatter.format(dateInfo);

        } catch (NumberFormatException ex){
            ex.printStackTrace();

        } catch (Exception e){
            e.printStackTrace();
        }


        return date;
    }

}
