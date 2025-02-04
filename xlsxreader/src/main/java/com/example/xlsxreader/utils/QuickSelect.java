package com.example.xlsxreader.utils;

import java.util.List;
import java.util.stream.Collectors;

public class QuickSelect {
    public static int findNthLargest(List<Integer> numbers, int n){
        System.out.println("Исходный массив: " + numbers);

        List<Integer> uniqueNumbers = numbers.stream().distinct().collect(Collectors.toList());

        int result = quickSelect(uniqueNumbers, 0, uniqueNumbers.size() - 1, n - 1);

        System.out.println("Найденное " + n + "-е по величине число: " + result);
        return result;
    }

    private static int quickSelect(List<Integer> arr, int left, int right, int n) {
        if (left == right) return arr.get(left);

        int pivotIndex = partition(arr, left, right);

        if (pivotIndex == n) return arr.get(pivotIndex);
        else if (pivotIndex > n) return quickSelect(arr, left, pivotIndex - 1, n);
        else return quickSelect(arr, pivotIndex + 1, right, n);
    }

    private static int partition(List<Integer> arr, int left, int right) {
        int pivotIndex = left + (right - left) / 2;
        swap(arr, pivotIndex, right);

        int pivot = arr.get(right);
        int i = left;
        for (int j = left; j < right; j++){
            if (arr.get(j) > pivot){
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, i, right);
        return i;
    }

    private static void swap(List<Integer> arr, int i, int j) {
        int temp = arr.get(i);
        arr.set(i, arr.get(j));
        arr.set(j, temp);
    }
}
