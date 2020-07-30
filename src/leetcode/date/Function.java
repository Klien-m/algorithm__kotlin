package leetcode.date;

import java.util.Arrays;

/**
 * @since 2020/7/25 7:59
 */
public class Function {

    // 1201 2020-07-29 11:33:42
    class TODO1 {
        private long lcmAB;
        private long lcmAC;
        private long lcmBC;
        private long lcmABC;

        public int nthUglyNumber(int n, int a, int b, int c) {
            if (n < 1 || a < 1 || b < 1 || c < 1) return -1;
            long lcmAB = lcm(a, b);
            long lcmAC = lcm(a, c);
            long lcmBC = lcm(b, c);
            long lcmABC = lcm(lcmAB, c);
            long low = Math.min(Math.min(a, b), c);
            long high = low * n;
            long res = binarySearch(low, high, a, b, c, n);
            long leftA = res % a;
            long leftB = res % b;
            long leftC = res % c;
            return (int) (res - Math.min(Math.min(leftA, leftB), leftC));
        }

        private long binarySearch(long low, long high, int a, int b, int c, int n) {
            if (low >= high) return low;
            long mid = (low + high) >> 1;
            long count = mid / a + mid / b + mid / c - mid / lcmAB - mid / lcmBC - mid / lcmAC + mid / lcmABC;
            if (count == n) return mid;
            if (count < n) return binarySearch(mid + 1, high, a, b, c, n);
            return binarySearch(low, mid - 1, a, b, c, n);
        }

        // 最小公倍数
        private long lcm(long a, long b) {
            long multi = a * b;
            while (b > 0) {
                long tmp = a % b;
                a = b;
                b = tmp;
            }
            return multi / a;
        }
    }

    // 14 编写一个函数来查找字符串数组中的最长公共前缀。
    // (1)排序，比较头尾字符串相同字符 2020-07-30 21:22:19
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        Arrays.sort(strs);
        String sta = strs[0], end = strs[strs.length - 1];
        int i, num = Math.min(sta.length(), end.length());
        for (i = 0; i < num && sta.charAt(i) == end.charAt(i); i++) ;
        return sta.substring(0, i);
    }

    // (2)横向扫描 2020-07-30 21:37:26
    // 时间复杂度 O(mn)，空间复杂度 O(1)
    private String longestCommonPrefix(String str1, String str2) {
        int length = Math.min(str1.length(), str2.length());
        int index = 0;
        while (index < length && str1.charAt(index) == str2.charAt(index))
            index++;
        return str1.substring(0, index);
    }

    public String longestCommonPrefix2(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        String prefix = strs[0];
        int count = strs.length;
        for (int i = 1; i < count; i++) {
            prefix = longestCommonPrefix(prefix, strs[i]);
            if (prefix.length() == 0)
                break;
        }
        return prefix;
    }

    // (3)纵向扫描 2020-07-30 21:49:23
    // 时间复杂度 O(mn)，空间复杂度 O(1)
    public String longestCommonPrefix3(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        int length = strs[0].length();
        int count = strs.length;
        for (int i = 0; i < length; i++) {
            char c = strs[0].charAt(i);
            for (int j = 1; j < count; j++) {
                if (i == strs[j].length() || strs[j].charAt(i) != c)
                    return strs[0].substring(0, i);
            }
        }
        return strs[0];
    }

    // (4)分治 2020-07-30 21:55:21
    // 时间复杂度 O(mn)，空间复杂度 O(m log n)
    private String longestCommonPrefix(String[] strs, int start, int end) {
        if (start == end) {
            return strs[start];
        } else {
            int mid = start + (end - start) / 2;
            String lcpLeft = longestCommonPrefix(strs, start, mid);
            String lcpRight = longestCommonPrefix(strs, mid + 1, end);
            return commonPrefix(lcpLeft, lcpRight);
        }
    }

    private String commonPrefix(String lcpLeft, String lcpRight) {
        int minLength = Math.min(lcpLeft.length(), lcpRight.length());
        for (int i = 0; i < minLength; i++) {
            if (lcpLeft.charAt(i) != lcpRight.charAt(i)) {
                return lcpLeft.substring(0, i);
            }
        }
        return lcpLeft.substring(0, minLength);
    }

    public String longestCommonPrefix4(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        return longestCommonPrefix(strs, 0, strs.length - 1);
    }

    // (5)二分查找 2020-07-30 22:07:46
    // 时间复杂度 O(mn log m)，空间复杂度 O(1)
    private boolean isCommonPrefix(String[] strs, int length) {
        String str0 = strs[0].substring(0, length);
        int count = strs.length;
        for (int i = 1; i < count; i++) {
            String str = strs[i];
            for (int j = 0; j < length; j++) {
                if (str0.charAt(j) != str.charAt(j))
                    return false;
            }
        }
        return true;
    }

    public String longestCommonPrefix5(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        int minLength = Integer.MAX_VALUE;
        for (String str : strs) {
            minLength = Math.min(minLength, str.length());
        }
        int low = 0, hight = minLength;
        while (low < hight) {
            int mid = low + (hight - low + 1) / 2;
            if (isCommonPrefix(strs, mid)) {
                low = mid;
            } else {
                hight = mid - 1;
            }
        }
        return strs[0].substring(0, low);
    }
}

