package com.ambrizals.wallme;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class currencyControl {
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

    // Belum Jalan !
    public Integer unRupiah(String param){
        Integer cur;
        Pattern pattern = Pattern.compile("([0-9]{1,3})");
        Matcher matcher = pattern.matcher(param);

        if (matcher.find()) {
            cur = Integer.valueOf(matcher.group(0));
        } else {
            cur = 0;
        }
        return cur;
    }

}
