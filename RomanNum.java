import java.util.*;

public class RomanNum {

    protected int value;

    protected Map<String, Integer> romanNumbers = getRomanMap();

    private static Map<String, Integer> getRomanMap() {
        Map<String, Integer> result = new HashMap<>();
        result.put("I", 1);
        result.put("V", 5);
        result.put("X", 10);
        result.put("L", 50);
        result.put("C", 100);
        result.put("D", 500);
        result.put("M", 1000);
        return result;
    }

    protected boolean isRoman(String romanNum) {
        romanNum = romanNum.toUpperCase();

        if (!romanNum.matches("^[IVXLCDM]+$")) return false;
        if (romanNum.matches("(\\w)\\1{4,}")) return false;

        return true;
    }

    protected int romanToInt(String romanNum) {
        romanNum = romanNum.toUpperCase();

        int value = 0;

        int prev = 0;
        int curr = romanNumbers.get(Character.toString(romanNum.charAt(0)));

        for (int pos = 1; pos < romanNum.length(); pos++) {
            prev = romanNumbers.get(Character.toString(romanNum.charAt(pos - 1)));
            curr = romanNumbers.get(Character.toString(romanNum.charAt(pos)));
            if (prev >= curr) value += prev; else value -= prev;

            if (value + curr > 3999) throw new RuntimeException("Error. Invalid roman number.");
        }
        return value + curr;
    }

    protected String intToRoman(int num) {
        String roman = "";
        while (num > 0) {
            int dig = Character.getNumericValue(Integer.toString(num).charAt(0));
            int pow = Integer.toString(num).length() - 1;
            String[] letters = {"M"};

            switch (pow) {
                case 2:
                    letters = new String[] {"C", "D", "M"};
                    break;
                case 1:
                    letters = new String[] {"X", "L", "C"};
                    break;
                case 0:
                    letters = new String[] {"I", "V", "X"};
                    break;
            }

            switch (dig) {
                case 1, 2, 3:
                    for (int i = 0; i < dig; i++) roman += letters[0];
                    break;
                case 4:
                    roman += letters[0] + letters[1];
                    break;
                case 5:
                    roman += letters[1];
                    break;
                case 6, 7, 8:
                    roman += letters[1];
                    for (int i = 0; i < dig - 5; i++) roman += letters[0];
                    break;
                case 9:
                    roman += letters[0] + letters[2];
            }

            num -= Math.pow(10, pow) * dig;
        }
        return roman;
    }

    public RomanNum(int initValue) {
        if (initValue > 3999) throw new RuntimeException("Error. Roman numbers can be in the range from 1 to 3999.");
        this.value = initValue;
    }

    public RomanNum(String initValue) {
        try {
            this.value = Integer.parseInt(initValue);
        } catch (NumberFormatException e) {
            if (!this.isRoman(initValue)) throw new RuntimeException("Error. Invalid roman number.");
            this.value = this.romanToInt(initValue);
        }
        if (this.value > 3999) throw new RuntimeException("Error. Roman numbers can be in the range from 1 to 3999.");
    }

    public int getIntValue() {
        return this.value;
    }

    public String getValue() {
        return intToRoman(this.value);
    }

    public RomanNum add(RomanNum romanNum) {
        return new RomanNum(this.value + romanNum.getIntValue());
    }

    public RomanNum sub(RomanNum romanNum) {
        return new RomanNum(this.value - romanNum.getIntValue());
    }

    public RomanNum mul(RomanNum romanNum) {
        return new RomanNum(this.value * romanNum.getIntValue());
    }

    public RomanNum div(RomanNum romanNum) {
        return new RomanNum(this.value / romanNum.getIntValue());
    }
}