package net.lelyak.edu.additional_tasks;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author Nazar Lelyak.
 */
public class Ch02TwoSum {
    public static void main(String[] args) {
        int[] numbers = {2, 3, 7, 4, 8};
        int target = 7;
        int[] result = getTwoSum(numbers, target);
        System.out.printf("for getting target: %d we need sum for coordinates: %s\n", target, Arrays.toString(result));
    }

    private static int[] getTwoSum(int[] numbers, int target) {
        HashMap<Integer, Integer> visitedNumbers = Maps.newHashMap();

        for (int i = 0; i < numbers.length; i++) {
            int delta = target - numbers[i];

            if (visitedNumbers.containsKey(delta)) {
                return new int[]{i, visitedNumbers.get(delta)};
            }
            visitedNumbers.put(numbers[i], i);
        }

        return new int[]{-1, -1};
    }
}
