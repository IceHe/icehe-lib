package xyz.icehe.sort;

import xyz.icehe.utils.SortUtils;

public class MergeSortIterative {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println("Before iterative merge sorting");
            int[] intAry = SortUtils.genAndPrint10Ints();
            mergeSortIterative(intAry);
            System.out.println("After iterative merge sorting");
            SortUtils.printInts(intAry);
            SortUtils.checkSortedInts(intAry);
            System.out.println();
        }
    }

    public static void mergeSortIterative(int[] intAry) {
        if (null == intAry || intAry.length < 2) {
            return;
        }

        int len = intAry.length;
        int[] tmpIntAry = new int[len];

        int increment = 1;
        do {
            increment *= 2;
            System.arraycopy(intAry, 0, tmpIntAry, 0, len);

            int startIdx = 0;
            do {
                int pivot = startIdx + increment / 2;
                int i = startIdx;
                int j = startIdx;
                int k = pivot;

                do {
                    if (tmpIntAry[j] <= tmpIntAry[k]) {
                        intAry[i++] = tmpIntAry[j++];
                    } else {
                        intAry[i++] = tmpIntAry[k++];
                    }
                } while(j < pivot && k < (startIdx + increment) && k < len);

                while (j < pivot) {
                    intAry[i++] = tmpIntAry[j++];
                }

                while (k < (startIdx + increment) && k < len) {
                    intAry[i++] = tmpIntAry[k++];
                }

                startIdx += increment;
            } while (startIdx + increment <= len);
        } while (increment <= len);
    }
}
