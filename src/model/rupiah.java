/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 *
 * @author Surianto
 */
public class rupiah {
    private static DecimalFormat decimalFormat;

    static {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("Rp");
        decimalFormatSymbols.setMonetaryDecimalSeparator(',');
        decimalFormatSymbols.setGroupingSeparator('.');

        decimalFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
    }

    public static String rupiah(int number) {
        return decimalFormat.format(number);
    }
}

