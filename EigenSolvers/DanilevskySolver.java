package EigenSolvers;

import Decorator.PolynomDecorator;
import Matrices.MatrixR;
import Matrices.MatrixRS;
import MatrixExceptions.SizeOfMatrixException;
import Numbers.Rational;
import Polynoms.Polynom;

public class DanilevskySolver extends EigenSolver {
    public DanilevskySolver() {}

    @Override
    public void solve(MatrixRS mat) throws SizeOfMatrixException {
        int sizeMat = mat.getSize();
        MatrixRS m = new MatrixRS(mat);
        MatrixRS tmp = new MatrixRS(sizeMat);
        MatrixR buff = new MatrixR(1, sizeMat);

        pol = new Polynom(1);
        pol.setVal(0, 1);

        int offset = sizeMat;
        for (var k = sizeMat - 1; k > 0; --k) {
            if (m.at(k, k - 1).equals(0)) {
                int col = findNonzeroElInRow(m, k, 0);
                if (col < k && col != -1) {
                    for (var i = 0; i < sizeMat; ++i) {
                        Rational.swap(m.at(i, k - 1), m.at(i, col));
                    }
                } else {
                    int sizePol = offset - k;
                    Polynom tmpPol = new Polynom(sizePol + 1);
                    for (var i = k; i < offset; ++i) {
                        tmpPol.setVal(i - k, m.at(k, offset - i + k - 1));
                    }

                    tmpPol.setVal(sizePol, -1);
                    offset = k;
                    /*
                    for(var i = 0; i < tmpPol.getSize(); ++i) {
                        System.out.print(tmpPol.getVal(i));
                    }
                    System.out.println();
                    PolynomDecorator pd = new PolynomDecorator(tmpPol, "x");
                    System.out.println(pd.toReadableString());
                    */
                    pol = pol.multiply(tmpPol);
                    continue;
                }
            }

            for (var i = 0; i < sizeMat; ++i) {
                for (var j = 0; j < sizeMat; ++j) {
                    if(j != k - 1) {
                        tmp.set(i, j, m.at(i, j).minus(m.at(i, k - 1).multiply(m.at(k, j).divide(m.at(k, k - 1) ) ) ) );
                    }
                }
            }

            for (var i = 0; i < sizeMat; ++i) {
                tmp.set(i, k - 1, m.at(i, k - 1).divide(m.at(k, k - 1)));
            }

            for (var i = 0; i < sizeMat; ++i) {
                Rational r = new Rational(0);
                for (var j = 0; j < sizeMat; ++j) {
                    r = r.plus(m.at(k, j).multiply(tmp.at(j, i)));
                }

                buff.set(0, i, r);
            }

            for (var i = 0; i < sizeMat; ++i) {
                tmp.set(k - 1, i, buff.at(0, i)) ;
            }

            m.assign(tmp);
        }

        Polynom tmpPol = new Polynom(offset + 1);
        for (var i = 0; i < offset; ++i) {
            tmpPol.setVal(i, m.at(0, offset - i - 1));
        }

        tmpPol.setVal(offset, -1);
        pol = pol.multiply(tmpPol);

        if ( (pol.getVal(pol.getDeg()).gt(0) && sizeMat % 2 == 1) ||  (pol.getVal(pol.getDeg()).lt(0) && sizeMat % 2 == 0) ) {
            pol = pol.multiply(-1);
        }
    }

    int findNonzeroElInRow(MatrixRS mat, int row, int startCol) {
        for( var j = 0; j < mat.getSize(); ++j) {
            if (!mat.at(row, j).equals(0)) {
                return j;
            }
        }

        return -1;
    }
}
