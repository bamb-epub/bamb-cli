package bamb.cli.utils;

import java.io.Serializable;
import java.util.Random;

public final class ISBN implements Serializable {
    private final static long serialVersionUID = 1L;
    public String isbn;

    private ISBN(String isbn) {
        this.isbn = isbn;
    }

    public static ISBN randomISBN13(){
        StringBuilder isnbBuilder = new StringBuilder("978");

        Random random = new Random();
        for(int i = 0; i < 9; i++){
            isnbBuilder.append(random.nextInt(10));
        }

        isnbBuilder.append(random.nextInt(10));

        int checkDigit = calculateCheckDigit(isnbBuilder.toString());
        isnbBuilder.append(checkDigit);

        return new ISBN(isnbBuilder.toString());
    }

    private static int calculateCheckDigit(String partialISBN) {
        int sum = 0;
        for (int i = 0; i < partialISBN.length(); i++) {
            int digit = Character.getNumericValue(partialISBN.charAt(i));
            sum += (i % 2 == 0) ? digit : digit * 3;
        }

        int checkDigit = (10 - (sum % 10)) % 10;
        return checkDigit;
    }

    public static boolean isValidISBN13(String isbn) {
        isbn = isbn.replaceAll("[\\s-]", "");

        if (isbn.length() != 13) {
            return false;
        }

        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = Character.getNumericValue(isbn.charAt(i));
            sum += (i % 2 == 0) ? digit : digit * 3;
        }

        int checkDigit = (10 - (sum % 10)) % 10;

        return checkDigit == Character.getNumericValue(isbn.charAt(12));
    }

    public static boolean fromString(String isbn){
        if(isValidISBN13(isbn)){

        }

        
    }
}
