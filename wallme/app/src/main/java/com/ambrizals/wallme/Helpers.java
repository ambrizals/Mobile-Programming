package com.ambrizals.wallme;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Helpers {
    public String rupiah(Double param) {
        String cur;
        DecimalFormat kursIDR = (DecimalFormat)DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIDR.setDecimalFormatSymbols(formatRp);
        cur = kursIDR.format(param);
        return cur;
    }

    public String todayDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String tanggalSekarang = dateFormat.format(date);
        return tanggalSekarang;
    }

    public String dateFormat(String param){
        String newDateString;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Date startDate;
        try {
            startDate = df.parse(param);
            newDateString = df.format(startDate);
        } catch (ParseException e) {
            newDateString = param;
            e.printStackTrace();
        }
        return newDateString;
    }

//    public Integer unRupiah(String param){
//        Integer cur;
//        Pattern pattern = Pattern.compile("([0-9]{1,3})");
//        Matcher matcher = pattern.matcher(param);
//
//        if (matcher.find()) {
//            cur = Integer.valueOf(matcher.group(0));
//        } else {
//            cur = 0;
//        }
//        return cur;
//    }

}
