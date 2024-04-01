package main.java;

import com.ampl.AMPL;
import com.ampl.Environment;

public class MinimizeWeightedDelayAMPL2 {

    public static void main(String[] args) {
        try {

            Environment amplEnv = new Environment();


            AMPL ampl = new AMPL(amplEnv);
            ampl.read("src/main/ampl/weighted_delay_corrected.mod");


            double[] p = { 26,24,79,46,32,35,73,74,14,67,86,46,78,40,29,94,64,27,90,55,35,52,36,69,85,95,14,78,37,86,44,28,39,12,30,68,70, 9,49,50};
            double[] d = {1588,1620,1731,1773,1694,1487,1566,1844,1727,1636,1599,1539,1855,1645,1709,1660,1582,1836,1484,1559,1772,1510,1512,1795,1522,1509,1598,1658,1826,1628,1650,1833,1627,1528,1541,1497,1481,1446,1579,1814 };
            double[] peso = {1,10, 9,10,10, 4, 3, 2,10, 3, 7, 3, 1, 3,10, 4, 7, 7, 4, 7, 5, 3, 5, 4, 9, 5, 2, 8,10, 4, 7, 4, 9, 5, 7, 7, 5,10, 1, 3};


            ampl.getParameter("p").setValues(p);
            ampl.getParameter("d").setValues(d);
            ampl.getParameter("peso").setValues(peso);


            ampl.solve();


            double zIP = ampl.getVariable("Tmax").value();


            System.out.println("Optimal maximum delay: " + zIP);


            ampl.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
