package Task.API;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

@SpringBootApplication
public class Fibonacci {
    private static final String PATH_TO_GET_DATA = "http://localhost:8080/getData";
    private static final String FILE_NAME = "file.txt";

    /**
     * Sends a request for test data in ArrayList<ArrayList<Integer>> format.
     * Each array is checked on the fibonacci sequence.
     * If array has fibonacci sequence its reversed.
     * Result is written to a file
     */
    public static void main(String[] args) {
        SpringApplication.run(Fibonacci.class, args);
        final RestTemplate restTemplate = new RestTemplate();
        final ArrayList<ArrayList<Integer>> data = restTemplate.getForObject(PATH_TO_GET_DATA, ArrayList.class);

        if (data != null) {
            ArrayList<ArrayList<Integer>> arrayForSave = checkFibonacci(data);
            ArrayList<ArrayList<Integer>> reverseArray = reverseArray(arrayForSave);
            writeDataToFile(reverseArray, data);
//            System.out.println("All data " + data);
//            System.out.println("chosen rows " + arrayForSave);
//            System.out.println("reverse rows " + reverseArray);
        }
    }

    /**
     * Write to a file all received array and result reversed array
     *
     * @param reverseArray with filtered data and reversed sequence
     * @param data         gets from api call
     */
    private static void writeDataToFile(ArrayList<ArrayList<Integer>> reverseArray, ArrayList<ArrayList<Integer>> data) {
        FileWriter nFile;
        try {
            nFile = new FileWriter(FILE_NAME);
            nFile.write("All rows were got for checking: \n\n");
            for (ArrayList<Integer> allData : data) {
                nFile.write(allData.toString().replaceAll("[\\]\\[]", "") + "\n");
            }
            nFile.write("\nResult: \n\n");
            for (ArrayList<Integer> finalArr : reverseArray) {
                nFile.write(finalArr.toString().replaceAll("[\\]\\[]", "") + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            nFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * received arrays and revers its order
     *
     * @param arrayForSave array with arrays of fibonacci sequences
     * @return same as arrayForSave but reversed
     */
    private static ArrayList<ArrayList<Integer>> reverseArray(ArrayList<ArrayList<Integer>> arrayForSave) {
        ArrayList<ArrayList<Integer>> reverseArray = new ArrayList<>();
        for (ArrayList<Integer> arrInteger : arrayForSave) {
            ArrayList<Integer> saveTempResult = new ArrayList<>();
            for (int i = arrInteger.size(); i > 0; i--) {
                saveTempResult.add(arrInteger.get(i - 1));
            }
            reverseArray.add(saveTempResult);
        }
        return reverseArray;
    }

    /**
     * checks if the data in the received arrays are fibonacci sequences
     *
     * @param arrayWithData all data received from api call
     * @return array with fibonacci sequences
     */
    private static ArrayList<ArrayList<Integer>> checkFibonacci(ArrayList<ArrayList<Integer>> arrayWithData) {
        ArrayList<ArrayList<Integer>> arrayForSave = new ArrayList<>();

        for (ArrayList<Integer> arrInteger : arrayWithData) {
            boolean isFibonacci = false;
            int firstNum = 0, secondNum = 1;

            isFibonacci = checkOneArray(arrInteger, isFibonacci, firstNum, secondNum);
            if (isFibonacci) {
                arrayForSave.add(arrInteger);
            }
        }
        return arrayForSave;
    }

    /**
     * Check one array if it has fibonacci sequences
     *
     * @param arrInteger  one array with some sequences
     * @param isFibonacci boolean value. True if array has fibonacci sequences
     * @param firstNum    first arg in fibonacci sequences
     * @param secondNum   second arg in fibonacci sequences
     * @return boolean value. True if array has fibonacci sequences
     */
    private static boolean checkOneArray(ArrayList<Integer> arrInteger, boolean isFibonacci, int firstNum, int secondNum) {
        for (int i = 2; i < arrInteger.size(); i++) {

            int sum = firstNum + secondNum;
            int currentNum = arrInteger.get(i);
            if (sum == currentNum) {
                if (i + 1 == arrInteger.size()) {
                    isFibonacci = true;
                }
                firstNum = secondNum;
                secondNum = currentNum;
            } else {
                break;
            }
        }
        return isFibonacci;
    }
}
