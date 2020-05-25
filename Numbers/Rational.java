package Numbers;
//import java.util.*

public class Rational implements MyNumber<Rational> {
    int sign;
    long num;
    long dom;

    public Rational() {
        this.num = 0;
        this.dom = 1;
        this.sign = 1;
    }

    public Rational(long _num, long _dom) {
        if (_dom == 0) {
           throw new ArithmeticException("Rational.Rational(long, long): Division by zero");
        }

        this.sign = 1;
        if ((_num < 0 && _dom > 0) || (_num > 0 && _dom < 0)) {
            this.sign = -1;
        }

        this.num = (_num < 0) ? -_num : _num;
        this.dom = (_dom < 0) ? -_dom : _dom;

        long tmp = GCD.gcd(this.num, this.dom);

        this.num /= tmp;
        this.dom /= tmp;
    }

    public Rational(long o) {
        assign(o);
    }

    public Rational(Rational o) {
        assign(o);
    }

    @Override
    public void assign(Rational o) {
        this.num = o.num;
        this.dom = o.dom;
        this.sign = o.sign;
    }

    @Override
    public void assign(long o) {
        this.num = (o < 0) ? -o : o;
        this.dom = 1;
        this.sign = (o < 0) ? -1 : 1;
    }

    public long getNum() {
        return num * sign;
    }

    public long getDom() {
        return dom;
    }

    @Override
    public Rational plus(Rational o) {
        return new Rational(this.sign * this.num * o.dom + o.sign * o.num * this.dom, this.dom * o.dom);
    }

    @Override
    public Rational plus(long o) {
        return new Rational(this.sign * this.num + o * this.dom, this.dom);
    }

    @Override
    public Rational minus(Rational o) {
        return new Rational(this.sign * this.num * o.dom - o.sign * o.num * this.dom, this.dom * o.dom);
    }

    @Override
    public Rational minus(long o) {
        return new Rational(this.sign * this.num - o * this.dom, this.dom);
    }

    @Override
    public Rational multiply(Rational o) {
        return new Rational(this.sign * o.sign * this.num * o.num, this.dom * o.dom);
    }

    @Override
    public Rational multiply(long o) {
        return new Rational(this.sign * this.num * o, this.dom);
    }

    @Override
    public Rational divide(Rational o) {
        if(o.equals(0)) {
            throw new ArithmeticException("Rational.divide(Rational): Division by zero");
        }

        return new Rational(this.sign * o.sign * this.num * o.dom, this.dom * o.num);
    }

    @Override
    public Rational divide(long o) {
        if(o == 0) {
            throw new ArithmeticException("Rational.divide(long): Division by zero");
        }

        return new Rational(this.sign * this.num, this.dom * o);
    }

    @Override
    public Rational pow(int _ext) {
        if (this.equals(0) && _ext < 0) {
            throw new ArithmeticException("Rational.pow(int): 0^x (x < 0) error");
        }

        Rational tmp = new Rational(1);

        if (_ext > 0) {
            for (int i = 0; i < _ext; ++i) {
                tmp = tmp.multiply(this);
            }

            return tmp;
        }

        if (_ext < 0) {
            for (int i = 0; i < -_ext; ++i) {
                tmp = tmp.divide(this);
            }

            return tmp;
        }

        return new Rational(1);
    }

    @Override
    public Rational inverse() {
        if(this.num == 0) {
            throw new ArithmeticException("Rational.inverse(): 0^(-1) error");
        }

        Rational tmp = new Rational(1);

        return tmp.divide(this);
    }

    public String toString() {
        return this.sign * this.num + "/" + this.dom;
    }

    public boolean equals(Rational o) {
        return (this.num == o.num && this.dom == o.dom && this.sign == o.sign);
    }

    public boolean equals(long o) {
        return (this.sign * this.num == o && this.dom == 1);
    }

    public boolean lt(Rational o) {
        Rational tmp = this.minus(o);

        return (tmp.sign == -1);
    }

    public boolean le(Rational o) {
        return this.lt(o) || this.equals(o);
    }

    public boolean gt(Rational o) {
        return !this.le(o);
    }

    public boolean ge(Rational o) {
        return !this.lt(o);
    }

    ////
    public static Rational Abs(Rational o) {
        return (o.sign == -1) ? new Rational(-o.num, o.dom) : new Rational(o.num, o.dom);
    }

    public static void swap(Rational o1, Rational o2) {
        Rational tmp = new Rational(o1);
        o1.assign(o2);
        o2.assign(tmp);
    }

    public static Rational max(Rational o1, Rational o2) {
        return o1.lt(o2) ? o2 : o1;
    }

    public static Rational min(Rational o1, Rational o2) {
        return o1.lt(o2) ? o1 : o2;
    }
}

class GCD {
    static long gcd(long a, long b) {
        long k = a % b;

        while ((a % b) != 0) {
            long tmp = a;
            a = b;
            b = tmp % b;
        }

        return b;
    }
}