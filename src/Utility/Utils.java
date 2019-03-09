/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import java.util.ArrayList;
import java.util.ListIterator;

public class Utils {

    static double PI = 3.1415926;
    static long MBIG = 1000000000;
    static long MSEED = 161803398;
    static int MZ = 0;
    static float FAC = (float) 1.0E-9;
    static boolean first_time = true;
    static int idum;
    static int inext, inextp;
    static long[] ma = new long[56];
    static int iff = 0;
    static int rn=-1;


    public static int searchInArrayList(ArrayList<Wavelength> a, double d) {
        int cont = 0;
        ListIterator it = a.listIterator();
        while (it.hasNext()) {
            Wavelength temp = (Wavelength) it.next();
            if (temp.getValue() == d) {
                return cont++;
            }
            cont++;
        }
        return -1;
    }

    private static float ran3(int idum) {

//        int inext = 0, inextp = 0;
//        long[] ma = new long[56];
//        int iff = 0;
        long mj, mk;
        int i, ii, k;

        if (idum < 0 || iff == 0) {
            iff = 1;
            mj = MSEED - (idum < 0 ? -idum : idum);
            mj %= MBIG;
            ma[55] = mj;
            mk = 1;
            for (i = 1; i <= 54; i++) {
                ii = (21 * i) % 55;
                ma[ii] = mk;
                mk = mj - mk;
                if (mk < MZ) {
                    mk += MBIG;
                }
                mj = ma[ii];
            }
            for (k = 1; k <= 4; k++) {
                for (i = 1; i <= 55; i++) {
                    ma[i] -= ma[1 + (i + 30) % 55];
                    if (ma[i] < MZ) {
                        ma[i] += MBIG;
                    }
                }
            }
            inext = 0;
            inextp = 31;
            idum = 1;
        }
        if (++inext == 56) {
            inext = 1;
        }
        if (++inextp == 56) {
            inextp = 1;
        }
        mj = ma[inext] - ma[inextp];
        if (mj < MZ) {
            mj += MBIG;
        }
        ma[inext] = mj;
        float mmm=mj * FAC;
        return (mj * FAC);
    }

    public static double RandomNum() {

        //int idum = -1;
        if (first_time) {
            long time = System.currentTimeMillis();
            idum = -(int) time % (1 << 15);
            //idum = - 1; //Fixed seed idum=-1 (Comment line for random seed)
            ran3(idum);
            first_time = false;
            idum = 1;
        }
        return ((double) ran3(idum));
    }
}
