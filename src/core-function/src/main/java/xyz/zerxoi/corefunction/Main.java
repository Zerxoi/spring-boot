package xyz.zerxoi.corefunction;

import java.util.Scanner;

/**
 * @author zouxin <zouxin@kuaishou.com>
 * Created on 2021-09-14
 */
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int q = in.nextInt();
        boolean[] queue = new boolean[n];
        for (int i = 0; i < n; i++) {
            queue[i] = true;
        }
        for (int i = 0; i < q; i++) {
            int sum = 0;
            int m = in.nextInt();
            int k = 0;
            while (!queue[k]) {
                k++;
            }
            queue[k] = false;
            sum += k;
            int count = 0;
            while (m > 0) {
                int l = count;
                while (!queue[l]) {
                    l++;
                }
                queue[l] = false;
                queue[count] = true;
                sum += l - count;
                count++;
                m--;
            }
            System.out.print(sum + " ");
        }
    }

}
