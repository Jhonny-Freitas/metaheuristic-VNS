option solver cplex;

set Trabalhos := 1..40;
param p {Trabalhos};
param d {Trabalhos};
param peso {Trabalhos};

var T {Trabalhos} >= 0;
var C {Trabalhos} >= 0;
var x {Trabalhos, Trabalhos} binary;
var Tmax;

minimize AtrasoMaximo:
    Tmax;

subject to AlocacaoTrabalho {j in Trabalhos}:
    sum{i in Trabalhos} x[i,j] = 1;

subject to AtribuicaoPosicao {i in Trabalhos}:
    sum{j in Trabalhos} x[i,j] = 1;

subject to TempoTerminoPrimeiro:
    C[1] = sum{i in Trabalhos} p[i] * x[i,1];

subject to TempoTerminoSubsequente {j in 2..card(Trabalhos)}:
    C[j] >= C[j-1] + sum{i in Trabalhos} p[i] * x[i,j];

subject to Atraso {j in Trabalhos}:
    T[j] >= C[j] - sum{i in Trabalhos} x[i,j] * d[i];

subject to AtrasoMaximoRestricao:
    Tmax >= sum {j in Trabalhos} T[j] * peso[j];

