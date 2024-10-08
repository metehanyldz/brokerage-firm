package com.ing.brokerage.brokerage_firm.util;

import com.ing.brokerage.brokerage_firm.exceptions.BaseException;
import org.springframework.http.HttpStatus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AssetUtil {

    private static final Pattern ibanRegexPattern =
            Pattern.compile("^([A-Z]{2}[ \\-]?[0-9]{2})(?=(?:[ \\-]?[A-Z0-9]){9,30}$)((?:[ \\-]?[A-Z0-9]{3,5})" +
                    "{2,7})([ \\-]?[A-Z0-9]{1,3})?$");
    public static final String MONEY_ASSET = "TRY";

    public static void validateIBAN(String iban) {
        Matcher matcher = ibanRegexPattern.matcher(iban);
        if (!matcher.matches()) {
            throw new BaseException(String.format("Invalid IBAN: %s", iban), HttpStatus.BAD_REQUEST);
        }
    }
}
