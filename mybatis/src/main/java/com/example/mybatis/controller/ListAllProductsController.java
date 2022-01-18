package com.example.mybatis.controller;


import com.example.mybatis.mapper.ProductMapper;
import com.example.mybatis.model.Products;
import com.example.mybatis.service.ListOfAllProductsService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Api
@RestController
@RequestMapping("/list-all-products")
@Validated
public class ListAllProductsController {

    @Autowired
    ProductMapper productMapper;
    @Autowired
    private ListOfAllProductsService listOfAllProductsService;

    @GetMapping("/generics")
    public static <T> T[] list2Array(Class<T[]> a, List<T> elements) {
        T[] array = a.cast(Array.newInstance(a.getComponentType(), elements.size()));
        return elements.toArray(array);
    }

//    @SneakyThrows
//    @GetMapping("prod")
//    public List<Products> getProducts(ProductsRequestElastic productsRequestElastic) {
//        return listOfAllProductsService.getProductsById(productsRequestElastic);
//    }

    @GetMapping("")
    public ResponseEntity<List<Products>> getAllProducts(@RequestParam("page-number") @Min(0) int pageNumber,
                                                         @RequestParam("page-size") @Min(1) int pageSize) {
        List<Products> pageableProducts = listOfAllProductsService.findByPage(pageNumber, pageSize);
        Iterator<Products> iterator = pageableProducts.stream().iterator();
        List<Products> products = new ArrayList<>();
        iterator.forEachRemaining(products::add);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/see")
    public void transferMoney(Long sender, Long receiver, Long amount) {
        StringBuilder builder = new StringBuilder();
        builder.append("call transfer(").append(sender).append(",")
                .append(receiver).append(",")
                .append(amount)
                .append(")");
        productMapper.transfer(builder.toString());
    }

    @GetMapping("/anagram")
    public String isAnagram(String s1, String s2) {
        if (s1.length() == s2.length()) {

            char[] s3 = s1.toCharArray();
            char[] s4 = s2.toCharArray();

            Arrays.sort(s3);
            Arrays.sort(s4);

            boolean isAnagram = Arrays.equals(s3, s4);
            System.out.println("isAnagram" + isAnagram);
        }
        return "not are a anagram";
    }

    @GetMapping("/factorial")
    public void getFactorial(Long number) {

        long factorial = 1;
        for (int i = 1; i < number; i++) {
            factorial *= i;
        }
        System.out.println(factorial);
    }

    @GetMapping("/generics2")
    public <T> List<T> arrayToList(T[] a) {
        return Arrays.stream(a).collect(Collectors.toList());
    }

    @GetMapping("/palindrome")
    public boolean palindrome(String word) {
        String reverse = "";
        for (int i = word.length() - 1; i >= 0; i--) {
            reverse = String.valueOf(reverse.charAt(i));
        }
        return reverse.equals(word);
    }

    @GetMapping("/sum")
    public void sumWithoutOtherVariable(Integer s, Integer z) {
        s = s + z;
        z = s - z;
        s = s - z;
        System.out.println(s);
        System.out.println(z);
    }

    @GetMapping("/solveIt")
    public void tryIt() {
        int[] a = {1, 2, 3, 4, 5, 6, 8, 9};
        int sum = 0;
        int sumArray = 0;
        for (int i = 0; i < 10; i++) {
            sum = sum + i;
        }
        for (int i = 0; i < a.length; i++) {
            sumArray = sumArray + a[i];
        }
        int result = sum - sumArray;
        System.out.println(result);
    }
    @GetMapping("/bubbleSort")
    private void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 1; j < arr.length - i; i++) {
                if (arr[j - 1] > arr[j]) {
                    int temp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }
    @GetMapping("/nameOfMethod")
    public int[][] nameOfMethod(int s, int k) {
        int[][] array = new int[s][k];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; i++) {
                array[s][k] = i * j;
            }
            System.out.println(" ");
        }
        return array;
    }
    @GetMapping("/sort2Array")
    public void sort2dArray(int[][] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; i < a[i].length; i++) {
                if (a[i][j - 1] > a[i][j]) {
                    int temp = a[i][j - 1];
                    a[i][j - 1] = a[i][j];
                    a[i][j] = temp;
                }
            }
        }
    }
}


