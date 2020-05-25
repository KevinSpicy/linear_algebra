package Solvers;

import MatrixExceptions.SizeOfMatrixException;
import Numbers.Rational;
import Matrices.MatrixR;

public class GaussSolver extends Solver {
    public GaussSolver() {
        solveVector = null;
        kernelMatrix = null;
        permVars = null;
        cntVars = 0;
    }

    @Override
    public SolutionId solve(MatrixR mat, MatrixR rightPart) throws SizeOfMatrixException {
        solveVector = null;
        kernelMatrix = null;
        permVars = null;
        cntVars = 0;

        if (mat.getSizeY() != rightPart.getSizeY()) {
            //Exception
        }

        MatrixR m = new MatrixR(mat);
        MatrixR rp = new MatrixR(rightPart);

        cntVars = m.getSizeX();
        permVars = new int[cntVars + 1];

        for (int i = 0; i < cntVars; ++i) {
            permVars[i] = i;
        }

        for (int i = 0; i < m.getSizeY(); ++i) {
            int rowInd = 0;
            boolean flag = false;
            int col = m.findNonzeroCol(i, i);

            if (col == -1) {
                break;
            } else if (col != i) {
                int tmp = permVars[i];
                permVars[i] = permVars[col];
                permVars[col] = tmp;
                for (int j = 0; j < m.getSizeY(); ++j) {
                    Rational.swap(m.at(j, col), m.at(j, i));
                }
            }

            for (int j = i; j < m.getSizeY(); ++j) {
                if (!m.at(i, j).equals(0)) {
                    rowInd = j;
                    break;
                }
            }

            if (rowInd != i) {
                Rational.swap(rp.at(i, 0), rp.at(rowInd, 0));
                for (int j = i; j < m.getSizeX(); ++j) {
                    Rational.swap(m.at(i, j), m.at(rowInd, j));
                }
            }

            Rational rat = new Rational(m.at(i, i));
            rp.set(i, 0, rp.at(i, 0).divide(rat));
            for (int j = i; j < m.getSizeX(); ++j) {
                m.set(i, j, m.at(i, j).divide(rat));
            }

            for (int j = i + 1; j < m.getSizeY(); ++j) {
                rat.assign(m.at(j, i));
                rp.set(j, 0, rp.at(j, 0).minus(rat.multiply(rp.at(i, 0))));
                for (int k = i; k < m.getSizeX(); ++k) {
                    m.set(j, k, m.at(j, k).minus(rat.multiply(m.at(i, k))));
                }
            }
        }

        int _rank = 0;
        int maxRank = Math.min(m.getSizeX(), m.getSizeY());
        for (int i = 0; i < maxRank; ++i) {
            if (m.at(i, i).equals(0)) {
                if (!rp.at(i, 0).equals(0)) {
                    permVars = null;
                    cntVars = 0;
                    return SolutionId.NONE;
                }
            } else {
                _rank++;
            }
        }

        for (int i = m.getSizeX() - 1; i > _rank; --i) {
            permVars[i] = permVars[i - 1];
        }
        permVars[_rank] = -1;

        if (_rank > 0) {
            for (int j = _rank - 1; j > 0; --j) {
                for (int i = j; i > 0; --i) {
                    rp.set(i - 1, 0, rp.at(i - 1, 0).minus(m.at(i - 1, j).multiply(rp.at(j, 0))));
                    for (int k = _rank; k < m.getSizeX(); ++k) {
                        m.set(i - 1, k, m.at(i - 1, k).minus(m.at(i - 1, j).multiply(m.at(j, k))));
                    }
                    m.set(i - 1, j, 0);
                }
            }
        }

        solveVector = new MatrixR(m.getSizeX(), 1);
        for (int i = 0; i < _rank; ++i) {
            solveVector.set(i, 0, rp.at(i, 0));
        }

        int sizeKer = cntVars - _rank;
        if(sizeKer > 0) {
            kernelMatrix = new MatrixR(_rank, sizeKer);
            for (int i = 0; i < kernelMatrix.getSizeY(); ++i) {
                for(int j = 0; j < kernelMatrix.getSizeX(); ++j) {
                    kernelMatrix.set(i, j, m.at(i, j + _rank).multiply(-1));
                }
            }
        }

        return (sizeKer > 0) ? SolutionId.INFINITE : SolutionId.SINGLE;
    }
}