package Decorator;

import Numbers.Rational;

public abstract class SolutionDecorator {
    abstract public String toReadableString();

    public String sign(Rational r, int flag) {
        if (r.equals(0)) {
            return "";
        }

        if (r.gt(0) && flag == 1) {
            return "";
        }

        return r.lt(0) ? "-" : "+";
    }

    public String formatRational(Rational r, int flag) {
        if (r.equals(0)) {
            return "";
        }

        if (r.getDom() == 1) {
            return String.valueOf(r.getNum());
        }

        if (flag == 0) {
            return String.valueOf(r.getNum()) + "/" + String.valueOf(r.getDom());
        }

        //if (flag == 1) {
            return "(" + String.valueOf(r.getNum()) + "/" + String.valueOf(r.getDom()) + ")";
        //}
    }



}
