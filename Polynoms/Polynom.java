package Polynoms;

import MatrixExceptions.SizeOfMatrixException;
import Numbers.Rational;

import java.util.Arrays;

public class Polynom {
    private Rational[] arr;
    private int sizePol;
    private int degPol;

    public Polynom() {
        degPol = -1;
        sizePol = 1;
        arr = new Rational[1];
        this.clear();
    }

    public Polynom(int oSizePol) throws SizeOfMatrixException {
        if (oSizePol > 0) {
            degPol = -1;
            sizePol = oSizePol;
            arr = new Rational[oSizePol];
            this.clear();
        } else {
            throw new SizeOfMatrixException("Polynom.Polynom(int): size of polynom <= 0");
        }
    }

    public Polynom(Polynom o) {
        degPol = o.degPol;
        sizePol = o.sizePol;
        arr = Arrays.copyOf(o.arr, o.arr.length);
    }

    public void assign(Polynom o) {
        degPol = o.degPol;
        sizePol = o.sizePol;
        arr = Arrays.copyOf(o.arr, o.arr.length);
    }

    public void resize(int oSizePol) throws SizeOfMatrixException {
        if (oSizePol > 0) {
            Polynom tmp = new Polynom(oSizePol);
            int min = Math.min(sizePol, oSizePol);
            tmp.arr = Arrays.copyOf(arr, min);
            tmp.evalDeg();
            this.assign(tmp);
        } else {
            throw new SizeOfMatrixException("Polynom.resize(int): new size <= 0");
        }
    }

    public int getSize() {
        return sizePol;
    }

    public int getDeg() {
        return degPol;
    }

    public void clear() {
        for(var i = 0; i < sizePol; ++i) {
            arr[i] = new Rational(0);
        }
    }

    public Rational getVal(int i) {
        return arr[i];
    }

    public void setVal(int i, Rational r) {
        arr[i].assign(r);
        this.evalDeg();
    }

    public void setVal(int i, long r) {
        arr[i].assign(r);
        this.evalDeg();
    }

    public Polynom multiply(Rational r) throws SizeOfMatrixException {
        Polynom tmp = new Polynom(sizePol);
        for (var i = 0; i < sizePol; ++i) {
            tmp.setVal(i, arr[i].multiply(r));
        }

        tmp.evalDeg();

        return tmp;
    }

    public Polynom multiply(long r) throws SizeOfMatrixException {
        return this.multiply(new Rational(r));
    }

    public Polynom plus(Polynom o) throws SizeOfMatrixException {
        int maxSize = Math.max(sizePol, o.sizePol);
        int minSize = Math.min(sizePol, o.sizePol);
        Polynom tmp = new Polynom(maxSize);

        for (var i = 0; i < minSize; ++i) {
            tmp.arr[i] = this.arr[i].plus(o.arr[i]);
        }

        tmp.evalDeg();

        return tmp;
    }

    public Polynom minus(Polynom o) throws SizeOfMatrixException {
        return this.plus(o.multiply(-1));
    }

    public Polynom multiply(Polynom o) throws SizeOfMatrixException {
        if (degPol == -1 || o.degPol == -1) {
            return new Polynom();
        }

        Polynom tmp = new Polynom(degPol + o.degPol + 1);

        for (var k = 0; k < tmp.getSize(); ++k) {
            tmp.arr[k].assign(0);
            int min = Math.min(sizePol - 1, k);
            for (var i = 0; i <= min; ++i) {
                if (k - i >= o.getSize()) {
                    continue;
                }

                tmp.arr[k] = tmp.arr[k].plus(arr[i].multiply(o.arr[k - i]));
            }
        }

        tmp.evalDeg();

        return tmp;
    }

    public void evalDeg() {
        degPol = -1;
        for (var i = sizePol - 1; i >= 0; --i) {
            if (!arr[i].equals(0)) {
                degPol = i;
                return;
            }
        }
    }

    public boolean equals(Polynom o) {
        int minSize = Math.min(sizePol, o.sizePol);
        for (var i = 0; i < minSize; ++i) {
            if (!arr[i].equals(o.arr[i])) {
                return false;
            }
        }

        if(o.sizePol > sizePol) {
            for(var i = sizePol; i < o.sizePol; ++i) {
                if(!o.arr[i].equals(0)) {
                    return false;
                }
            }
        } else {
            for(var i = o.sizePol; i < sizePol; ++i) {
                if(!arr[i].equals(0)) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean equals(long r) {
        if(!arr[0].equals(r)) {
            return false;
        }

        for(var i = 1; i < sizePol; ++i) {
            if(!arr[i].equals(0)) {
                return false;
            }
        }

        return true;
    }
}
