package Matrices;

import Numbers.Rational;
import MatrixExceptions.SizeOfMatrixException;

public class MatrixR {
    protected Rational[][] array;
    protected int sizeX;
    protected int sizeY;
    protected int rank;

    public MatrixR(int _sizeY, int _sizeX) throws SizeOfMatrixException {
        if (_sizeX > 0 && _sizeY > 0) {
            sizeX = _sizeX;
            sizeY = _sizeY;
            rank = 0;
            array = new Rational[_sizeY][_sizeX];

            this.clear();
        } else {
            throw new SizeOfMatrixException("MatrixR.MatrixR(int, int): Incorrect sizes of matrix");
        }
    }

    public MatrixR()  {
        sizeX = 1;
        sizeY = 1;
        rank = 0;
        array = new Rational[1][1];

        this.clear();
    }

    public MatrixR(MatrixR o) {
        assign(o);
    }

    public void assign(MatrixR o) {
        sizeX = o.sizeX;
        sizeY = o.sizeY;
        rank = o.rank;
        Rational[][] _array = new Rational[sizeY][sizeX];

        for (int i = 0; i < sizeY; ++i) {
            for (int j = 0; j < sizeX; ++j) {
                _array[i][j] = new Rational(o.array[i][j]);
            }
        }

        this.array = _array;
    }

    public Rational at(int i, int j) {
        if ((i < 0 && i >= sizeY) || (j < 0 && j >= sizeX)) {
            throw new ArrayIndexOutOfBoundsException("MatrixR.at(int, int): Indices out of bounds");
        }

        return array[i][j];
    }

    public void set(int i, int j, Rational o) {
        if ((i < 0 && i >= sizeY) || (j < 0 && j >= sizeX)) {
            throw new ArrayIndexOutOfBoundsException("MatrixR.set(int, int, Rational): Indices out of bounds");
        }

        array[i][j].assign(o);
    }

    public void set(int i, int j, long o) {
        this.set(i, j, new Rational(o));
    }

    public MatrixR multiply(Rational o) throws SizeOfMatrixException {
        MatrixR _mat = new MatrixR(sizeY, sizeX);

        for (int i = 0; i < sizeY; ++i) {
            for (int j = 0; j < sizeX; ++j) {
                _mat.array[i][j] = array[i][j].multiply(o);
            }
        }

        return _mat;
    }

    public MatrixR multiply(long o) throws SizeOfMatrixException {
        return this.multiply(new Rational(o));
    }

    public MatrixR multiply(MatrixR o) throws SizeOfMatrixException {
        if (sizeX != o.sizeY) {
           throw new SizeOfMatrixException("MatrixR.multiply(MatrixR): Sizes of matrices mismatches");
        }

        MatrixR _mat = new MatrixR(sizeY, o.sizeX);
        Rational _r = new Rational(0);

        for (int i = 0; i < _mat.sizeY; ++i) {
            for (int j = 0; j < _mat.sizeX; ++j) {
                _r.assign(0);
                for (int k = 0; k < sizeX; ++k) {
                    _r = _r.plus(array[i][k].multiply(o.array[k][j]));
                }

                _mat.array[i][j].assign(_r);
            }
        }

        return _mat;
    }

    public boolean equals(MatrixR o) {
        if (sizeX != o.sizeX || sizeY != o.sizeY) {
            return false;
        }

        for (int i = 0; i < sizeY; ++i) {
            for (int j = 0; j < sizeX; ++j) {
                if (!array[i][j].equals(o.array[i][j])) {
                    return false;
                }
            }
        }

        return true;
    }

    public MatrixR plus(MatrixR o) throws SizeOfMatrixException {
        if (sizeX != o.sizeX || sizeY != o.sizeY) {
            throw new SizeOfMatrixException("MatrixR.plus(MatrixR): Sizes of matrices mismatches");
        }

        MatrixR _mat = new MatrixR(sizeY, sizeX);

        for (int i = 0; i < sizeY; ++i) {
            for (int j = 0; j < sizeX; ++j) {
                _mat.array[i][j] = array[i][j].plus(o.array[i][j]);
            }
        }

        return _mat;
    }

    public MatrixR minus(MatrixR o) throws SizeOfMatrixException {
        return this.plus(o.multiply(-1));
    }

    public int findNonzeroCol(int startCol, int startRow) {
        for (int i = startRow; i < sizeX; ++i) {
            for (int j = startCol; j < sizeY; ++j) {
                if (!array[j][i].equals(0)) {
                    return i;
                }
            }
        }

        return -1;
    }

    protected void evalRank() throws SizeOfMatrixException{
        MatrixR _mat = new MatrixR(this);

        for (int i = 0; i < sizeY; ++i) {
            int rowInd = 0;
            boolean flag = false;
            int col = _mat.findNonzeroCol(i, i);

            if (col == -1) {
                break;
            } else if (col != i) {
                for (int j = 0; j < sizeY; ++j) {
                    Rational.swap(_mat.at(j, col), _mat.at(j, i));
                }
            }

            for (int j = i; j < sizeY; ++j) {
                if (!_mat.at(j, i).equals(0)) {
                    rowInd = j;
                    break;
                }
            }

            if (rowInd != i) {
                for (int j = i; j < sizeX; ++j) {
                    Rational.swap(_mat.at(i, j), _mat.at(rowInd, j));
                }
            }

            Rational _r = new Rational(_mat.at(i, i));

            for (int j = i; j < sizeX; ++j) {
                _mat.set(i, j, _mat.at(i, j).divide(_r));
            }

            for (int j = i + 1; j < sizeY; ++j) {
                _r.assign(_mat.at(j, i));

                for (int k = i; k < sizeX; ++k) {
                    _mat.set(j, k, _mat.at(j, k).minus(_r.multiply(_mat.at(i, k))));
                }
            }
        }

        rank = 0;
        int maxRank = Math.min(sizeX, sizeY);

        for (int i = 0; i < maxRank; ++i) {
            if (!_mat.at(i, i).equals(0)) {
                rank++;
            }
        }
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getRank() throws SizeOfMatrixException{
        this.evalRank();

        return rank;
    }

    public void clear() {
        for (int i = 0; i < sizeY; ++i) {
            for (int j = 0; j < sizeX; ++j) {
                array[i][j] = new Rational(0);
            }
        }
    }

    public void watch() {
        for (int i = 0; i < sizeY; ++i) {
            for (int j = 0; j < sizeX; ++j) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }
}