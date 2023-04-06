package travelAgency.helper;

import org.apache.commons.lang.RandomStringUtils;

public class UniqueIdGenerator {

    public static String generate(final int max_length){
        return RandomStringUtils.randomNumeric(max_length);
    }


}
