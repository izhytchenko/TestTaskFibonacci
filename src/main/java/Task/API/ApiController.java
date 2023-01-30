package Task.API;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping("getData")
public class ApiController {
    @GetMapping
    public ArrayList<ArrayList<Integer>> List() {
        return createTestData();
    }

    /**
     * Create test data. Two arrays with fibonacci sequences and two without them
     *
     * @return arrays with test data
     */
    private ArrayList<ArrayList<Integer>> createTestData() {
        ArrayList<Integer> notFibonacci = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        ArrayList<Integer> notFibonacci2 = new ArrayList<>(Arrays.asList(5, 7, 10, 5, 5, 10));
        ArrayList<Integer> fibonacci = new ArrayList<>(Arrays.asList(0, 1, 1, 2, 3, 5, 8));
        ArrayList<Integer> fibonacci2 = new ArrayList<>(Arrays.asList(0, 1, 1, 2, 3));
        ArrayList<ArrayList<Integer>> testData = new ArrayList<>();
        testData.add(notFibonacci);
        testData.add(notFibonacci2);
        testData.add(fibonacci);
        testData.add(fibonacci2);
        return testData;
    }
}
