package main.java;

public class SequencingData {
    // Parâmetros
    public static int n = 6; // Número de tarefas

    public static double[][] p = {
            {1, 3},
            {2, 2},
            {3, 2},
            {4, 3},
            {5, 4},
            {6, 3}
    }; // Tempo de processamento

    public static double[][] d = {
            {1, 6},
            {2, 13},
            {3, 4},
            {4, 9},
            {5, 7},
            {6, 17}
    }; // Data de vencimento

    public static double[][] w = {
            {1, 2},
            {2, 3},
            {3, 1},
            {4, 5},
            {5, 1},
            {6, 2}
    }; // Peso
}
