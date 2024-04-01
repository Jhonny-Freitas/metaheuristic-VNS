package main.java;

import com.ampl.AMPL;
import com.ampl.Environment;

public class SingleProblemAMPL {

    public static void main(String[] args) {
        try {
            // Inicialize o ambiente AMPL
            Environment amplEnv = new Environment();

            // Carregue o modelo AMPL
            AMPL ampl = new AMPL(amplEnv);
            ampl.read("src/main/ampl/single.mod"); // Caminho relativo para o arquivo single.mod

            // Dados do problema
            double[] p = {64, 53, 63, 99, 189, 44, 50, 22};
            double[] d = {100, 70, 150, 601, 118, 590, 107, 180};

            // Defina os dados no AMPL
            ampl.getParameter("p").setValues(p);
            ampl.getParameter("d").setValues(d);

            // Resolva o modelo
            ampl.solve();

            // Obtenha os resultados
            double zIP = ampl.getObjective("TotalAtraso").value();

            // Imprima os resultados
            System.out.println("Optimal solution: " + zIP);

            // Feche o ambiente AMPL
            ampl.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
