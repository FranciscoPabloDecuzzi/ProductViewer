package com.ml.android.productviewer.util.mappers;

/**
 * Created by FranciscoPablo on 27/10/13.
 */
public class PriceStringMapper {
    public static final String ZERO_END = ".0";
    public static String formatDouble(double number)
    {
        String sDouble = Double.toString(number);
        if (sDouble.endsWith(ZERO_END))
        {
            sDouble = sDouble.replace(ZERO_END,"");
        }
        return sDouble;
    }
}
