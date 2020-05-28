package EigenSolvers;

import Matrices.MatrixRS;
import MatrixExceptions.SizeOfMatrixException;
import Polynoms.Polynom;

public abstract class EigenSolver {
    protected Polynom pol = null;
    public enum EIGEN_SOLVER_ID {
        DANILEVSKY
    }

    public Polynom getPol() {
        return pol;
    }

    public abstract void solve(MatrixRS mat) throws SizeOfMatrixException;

    public static EigenSolver createSolver(EIGEN_SOLVER_ID id) {
        switch (id) {
            case DANILEVSKY:
                return new DanilevskySolver();
            default:
                return new DanilevskySolver();
        }
    }
}
