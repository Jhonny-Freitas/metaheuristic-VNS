reset;

# parameters

param n > 0; # number of tasks
param d {1..n}; # due date
param p {1..n}; # proc time
param w {1..n}; # task weight

# decision variables

var T {1..n} integer >= 0; # Tardiness
var C {1..n} integer >= 0; # Makespan
var x {1..n, 1..n} binary;

# objective

minimize Tardiness:
	sum {j in 1..n} w[j] * T[j];

# constraints

s.t. s1
	{j in 1..n}: sum {i in 1..n} x[i,j] = 1;

s.t. s2
	{i in 1..n}: sum {j in 1..n} x[i,j] = 1;

s.t. s3
	{j in 2..n}: C[j] >= C[j-1] + sum {i in 1..n} p[i] * x[i,j];

s.t. s4:
	C[1] = sum {i in 1..n} p[i] * x[i,1];

s.t. s5
	{j in 1..n}: T[j] >= C[j] - sum {i in 1..n} x[i,j] * d[i];
