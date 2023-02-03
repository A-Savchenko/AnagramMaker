import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//imports

/**
 *	SortMethods - Sorts an array of Integers in ascending order.
 *
 *	@author Artem Savchenko
 *	@since 1/17/23
 */
public class SortMethods {

    /**
     * Method that sorts an array of String using the merge sort technique
     * @param names array of Strings that is inputted to be sorted
     */
    public static void mergeSort(String[] names) {
        if (names.length >= 2) {
            String[] left = new String[names.length / 2];
            String[] right = new String[names.length - names.length / 2];

            for (int i = 0; i < left.length; i++) {
                left[i] = names[i];
            }

            for (int i = 0; i < right.length; i++) {
                right[i] = names[i + names.length / 2];
            }

            mergeSort(left);
            mergeSort(right);
            merge(names, left, right);
        }
    }

    /**
     * Merges the two sub-arrays
     * @param names original array of Strings
     * @param left 'left' sub-array of Strings that was taken from the side that is less than the middle index of the
     *             array
     * @param right 'right' sub-array of Strings that was taken from the side that is bigger than the middle index
     *              of the array
     */
    public static void merge(String[] names, String[] left, String[] right) {
        int a = 0;
        int b = 0;
        for (int i = 0; i < names.length; i++) {
            if (b >= right.length || (a < left.length && left[a].compareToIgnoreCase(right[b]) < 0)) {
                names[i] = left[a];
                a++;
            } else {
                names[i] = right[b];
                b++;
            }
        }
    }

}
