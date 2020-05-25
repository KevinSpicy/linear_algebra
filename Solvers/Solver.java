package Solvers;

import Matrices.*;
import MatrixExceptions.SizeOfMatrixException;

public abstract class Solver {
    public enum SolutionId {
        SINGLE, INFINITE, NONE
    }

    public enum SolverId {
        GAUSS
    }

    protected MatrixR solveVector;
    protected MatrixR kernelMatrix;
    protected int[] permVars;
    protected int cntVars;

    public MatrixR getSolveVector() {
        return solveVector;
    }

    public MatrixR getKernelMatrix() {
        return kernelMatrix;
    }

    public int getCntVars() {
        return cntVars;
    }

    public void getPermVars(int[] buff) {
        buff = new int[cntVars + 1];

        for (int i = 0; i < cntVars + 1; ++i) {
            buff[i] = permVars[i];
        }
    }

    public abstract SolutionId solve(MatrixR mat, MatrixR rightPart) throws SizeOfMatrixException;

    public static Solver createSolver(SolverId id) {
        switch (id) {
            case GAUSS:
                return new GaussSolver();
            default:
                return new GaussSolver();
        }
    }
} 