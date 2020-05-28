package Decorator;

import Numbers.Rational;
import Solvers.Solver;
import Matrices.MatrixR;

public class SLEDecorator extends SolutionDecorator {
    private final Solver sol;

    public SLEDecorator(Solver sol) {
        this.sol = sol;
    }
    @Override
    public String toReadableString() {
        StringBuilder str = new StringBuilder();
        int countVars = sol.getCntVars();
        if (countVars == 0) {
            throw new NullPointerException("SLEDecorator.toReadableString(): Solver is empty");
        }

        int[] permVars = sol.getPermVars();

        MatrixR solveVector = sol.getSolveVector();
        if (solveVector == null) {
            throw new NullPointerException("SLEDecorator.toReadableString(): solveVector is null");
        }

        MatrixR kerMatrix = sol.getKernelMatrix();
        int kerSize = 0;
        if (kerMatrix != null) {
            kerSize = kerMatrix.getSizeX();
        }

        int rank = countVars - kerSize;
        boolean flag = false;
        for (var i = 0; i < countVars; ++i) {
            flag = false;
            for (var j = 0; j < countVars + 1; ++j) {
                if (permVars[j] == -1) {
                    flag = true;
                    break;
                }

                if (permVars[j] == i) {
                    break;
                }
            }

            if (flag) {
                str.append(String.format("x_%d = v_%d, v_%d belongs to |R\n", i + 1, i + 1, i + 1));
            } else {
                String buff = String.format("x_%d = %s%s", i + 1, sign(solveVector.at(i, 0), 1),
                        formatRational(Rational.abs(solveVector.at(i, 0)), 0));
                str.append(buff);
                for (var j = 0; j < kerSize; ++j) {
                    if (!kerMatrix.at(i, j).equals(0)) {
                        if (buff.equals("x_" + (i + 1) + " = ")) {
                            String wo1 = formatRational(Rational.abs(kerMatrix.at(i, j)), 0);
                            wo1 = (wo1.equals("1")) ? "" : wo1;
                            buff = String.format("%s%sv_%d", sign(kerMatrix.at(i, j), 1), wo1,
                                                 permVars[rank + j + 1] + 1);
                        } else {
                            String wo1 = formatRational(Rational.abs(kerMatrix.at(i, j)), 0);
                            wo1 = (wo1.equals("1")) ? "" : wo1;
                            buff = String.format(" %s %sv_%d", sign(kerMatrix.at(i, j), 0), wo1,
                                                 permVars[rank + j + 1] + 1);
                        }

                        str.append(buff);
                    }
                }

                if (buff.equals("x_" + (i + 1) + " = ")) {
                    str.append(String.valueOf(0));
                }

                str.append("\n");
            }
        }

        return str.toString();
    }
}
