package main.java;

import ilog.concert.*;
import ilog.cplex.*;

public class SequencingProblem {

    public static void main(String[] args) {
        try {
            // Criação do objeto Cplex
            try (IloCplex cplex = new IloCplex()) {

                // Parâmetros
                int n = SequencingData.n;
                double[][] p = SequencingData.p;
                double[][] d = SequencingData.d;
                double[][] w = SequencingData.w;

                // Variáveis de decisão
                IloIntVar[] T = cplex.intVarArray(n, 0, Integer.MAX_VALUE); // Tardiness
                IloIntVar[] C = cplex.intVarArray(n, 0, Integer.MAX_VALUE); // Makespan
                IloIntVar[][] x = new IloIntVar[n][];
                for (int i = 0; i < n; i++) {
                    x[i] = cplex.boolVarArray(n);
                }

                // Função objetivo
                IloLinearNumExpr objective = cplex.linearNumExpr();
                for (int j = 0; j < n; j++) {
                    objective.addTerm(w[j][0], T[j]); // Adiciona o termo ao objetivo
                }
                cplex.addMinimize(objective); // Define a função objetivo como minimização

                // Restrições

                // Restrições de sequenciamento
                for (int j = 0; j < n; j++) {
                    IloLinearNumExpr s1Expr = cplex.linearNumExpr();
                    for (int i = 0; i < n; i++) {
                        s1Expr.addTerm(1.0, x[i][j]); // Soma as variáveis de sequenciamento para a tarefa j
                    }
                    cplex.addEq(s1Expr, 1); // Cada tarefa deve ser executada exatamente uma vez após outra tarefa
                }

                for (int i = 0; i < n; i++) {
                    IloLinearNumExpr s2Expr = cplex.linearNumExpr();
                    for (int j = 0; j < n; j++) {
                        s2Expr.addTerm(1.0, x[i][j]); // Soma as variáveis de sequenciamento para a tarefa i
                    }
                    cplex.addEq(s2Expr, 1); // Cada tarefa deve ser executada exatamente uma vez antes de outra tarefa
                }

                // Restrição do makespan
                for (int j = 1; j < n; j++) {
                    IloLinearNumExpr s3Expr = cplex.linearNumExpr();
                    s3Expr.addTerm(1.0, C[j - 1]); // Tempo de conclusão da tarefa anterior
                    for (int i = 0; i < n; i++) {
                        s3Expr.addTerm(p[i][0], x[i][j]); // Soma os tempos de processamento das tarefas
                    }
                    cplex.addGe(C[j], s3Expr); // Define o makespan
                }

                // Restrição do tempo de conclusão da primeira tarefa
                IloLinearNumExpr s4Expr = cplex.linearNumExpr();
                for (int i = 0; i < n; i++) {
                    s4Expr.addTerm(p[i][0], x[i][0]); // Soma os tempos de processamento das tarefas
                }
                cplex.addEq(C[0], s4Expr); // Define o tempo de conclusão da primeira tarefa

                // Restrições de tardiness
                for (int j = 0; j < n; j++) {
                    IloLinearNumExpr s5Expr = cplex.linearNumExpr();
                    s5Expr.addTerm(1.0, C[j]); // Tempo de conclusão da tarefa
                    for (int i = 0; i < n; i++) {
                        s5Expr.addTerm(-d[i][0], x[i][j]); // Soma as datas de vencimento das tarefas
                    }
                    cplex.addGe(T[j], cplex.diff(s5Expr, d[j][0])); // Define o atraso da tarefa j
                }

                // Resolvendo o modelo
                if (cplex.solve()) {
                    System.out.println("Solução ótima encontrada.");
                    System.out.println("Valor da função objetivo: " + cplex.getObjValue());

                    // obter os valores das variáveis de decisão aqui se necessário
                } else {
                    System.out.println("O problema não pôde ser resolvido.");
                }

                // Fechando o objeto Cplex
                cplex.end();
            }

        } catch (IloException e) {
            System.err.println("Concert exception caught: " + e);
        }
    }
}
