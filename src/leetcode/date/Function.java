package leetcode.date;

import java.util.Arrays;
import java.util.Stack;

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

    // 1477 2020-07-31 14:52:46
    // 此方法超时
    public int maxEvents(int[][] events) {
        Arrays.sort(events, (o1, o2) -> o1[1] == o2[1] ? o1[0] - o2[0] : o1[1] - o2[1]);
        boolean[] flag = new boolean[100001];
        int res = 0;
        for (int[] event : events) {
            for (int day = event[0]; day <= event[1]; day++) {
                if (!flag[day]) {
                    flag[day] = true;
                    res++;
                    break;
                }
            }
        }
        return res;
    }

    // 415 2020-08-03 09:08:58
    public String addStrings(String num1, String num2) {
        int i = num1.length() - 1, j = num2.length() - 1, add = 0;
        StringBuffer ans = new StringBuffer();
        while (i >= 0 || j >= 0 || add != 0) {
            int x = i >= 0 ? num1.charAt(i) - '0' : 0;
            int y = j >= 0 ? num2.charAt(j) - '0' : 0;
            int result = x + y + add;
            ans.append(result % 10);
            add = result / 10;
            i--;
            j--;
        }
        ans.reverse();
        return ans.toString();
    }

    // 680 2020-08-03 09:19:57
    public boolean validPalindrome(String s) {
        int low = 0, high = s.length() - 1;
        while (low < high) {
            char c1 = s.charAt(low), c2 = s.charAt(high);
            if (c1 == c2) {
                low++;
                high--;
            } else {
                boolean flag1 = true, flag2 = true;
                for (int i = low, j = high - 1; i < j; i++, j--) {
                    char c3 = s.charAt(i), c4 = s.charAt(j);
                    if (c3 != c4) {
                        flag1 = false;
                        break;
                    }
                }
                for (int i = low + 1, j = high; i < j; i++, j--) {
                    char c3 = s.charAt(i), c4 = s.charAt(j);
                    if (c3 != c4) {
                        flag2 = false;
                        break;
                    }
                }
                return flag1 || flag2;
            }
        }
        return true;
    }

    // 482 2020-08-03 09:39:13
    public String licenseKeyFormatting(String S, int K) {
        char[] chars = S.toCharArray();
        char[] result = new char[chars.length + S.length() / K];
        int length = 0;
        int i = chars.length - 1, j = result.length - 1;
        for (; i >= 0; ) {
            if (chars[i] == '-') {
                i--;
                continue;
            }
            if (chars[i] >= 'a' && chars[i] <= 'z') {
                chars[i] = (char) (chars[i] - 32);
            }
            if (length != K) {
                result[j] = chars[i];
                length++;
                if (i == 0) {
                    j--;
                    break;
                } else {
                    i--;
                    j--;
                }
            } else {
                length = 1;
                result[j] = '-';
                result[j - 1] = chars[i];
                j -= 2;
                i--;
            }
        }
        return new String(result, j + 1, result.length - j - 1);
    }

    // 28 2020-08-03 15:49:13
    public int strStr(String haystack, String needle) {
        int L = needle.length(), n = haystack.length();
        if (L == 0) return 0;
        int pn = 0;
        while (pn < n - L + 1) {
            while (pn < n - L + 1 && haystack.charAt(pn) != needle.charAt(0)) ++pn;
            int currLen = 0, pL = 0;
            while (pL < L && pn < n && haystack.charAt(pn) == needle.charAt(pL)) {
                ++pn;
                ++pL;
                ++currLen;
            }
            if (currLen == L) return pn - L;
            pn = pn - currLen + 1;
        }
        return -1;
    }

    // 172 2020-08-03 16:02:20
    public int trailingZeroes(int n) {
        int zeroCount = 0;
        long currentMultiple = 5;
        while (n > 0) {
            n /= 5;
            zeroCount += n;
        }
        return zeroCount;
    }

    // 556 2020-08-03 16:34:11
    private void reverse(char[] a, int start) {
        int i = start, j = a.length - 1;
        while (i < j) {
            swap(a, i, j);
            i++;
            j--;
        }
    }

    private void swap(char[] a, int i, int j) {
        char temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public int nextGreaterElement(int n) {
        char[] a = ("" + n).toCharArray();
        int i = a.length - 2;
        while (i >= 0 && a[i + 1] <= a[i]) i--;
        if (i < 0) return -1;
        int j = a.length - 1;
        while (j >= 0 && a[j] <= a[i]) j--;
        swap(a, i, j);
        reverse(a, i + 1);
        try {
            return Integer.parseInt(new String(a));
        } catch (Exception e) {
            return -1;
        }
    }

    // 面试题 16.26 2020-08-03 16:58:54
    public int calculate(String s) {
        char[] cs = s.trim().toCharArray();
        Stack<Integer> st = new Stack<>();
        int ans = 0, i = 0;
        while (i < cs.length) {
            if (cs[i] == ' ') {
                i++;
                continue;
            }
            char tmp = cs[i];
            if (tmp == '*' || tmp == '/' || tmp == '+' || tmp == '-') {
                i++;
                while (i < cs.length && cs[i] == ' ') i++;
            }
            int num = 0;
            while (i < cs.length && Character.isDigit(cs[i])) {
                num = num * 10 + cs[i] - '0';
                i++;
            }
            switch (tmp) {
                case '-':
                    num = -num;
                    break;
                case '*':
                    num = st.pop() * num;
                    break;
                case '/':
                    num = st.pop() / num;
                    break;
                default:
                    break;
            }
            st.push(num);
        }
        while (!st.isEmpty()) ans += st.pop();
        return ans;
    }
}

