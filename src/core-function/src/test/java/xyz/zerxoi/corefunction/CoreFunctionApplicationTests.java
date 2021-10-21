package xyz.zerxoi.corefunction;

import java.util.Scanner;

class CoreFunctionApplicationTests {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        int max = 0;
        for (int i = k; k > 0; k--) {
            max = Math.max(max, reverse(n * i));
        }
        System.out.println(max);
    }

    public static int reverse(int v) {
        int r = 0;
        while (v > 0) {
            r = r * 10 + (v % 10);
            v /= 10;
        }
        return r;
    }


}
