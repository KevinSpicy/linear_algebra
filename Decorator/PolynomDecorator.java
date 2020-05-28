package Decorator;

import Numbers.Rational;
import Polynoms.Polynom;

public class PolynomDecorator extends SolutionDecorator {
    private final Polynom pol;
    private String stringVar;

    public PolynomDecorator(Polynom pol, String stringVar) {
        this.pol = pol;
        this.stringVar = stringVar;
    }

    public void setVar(String stringVar) {
        this.stringVar = stringVar;
    }

    @Override
    public String toReadableString() {
        StringBuilder str = new StringBuilder();

        str.append( formatRational(pol.getVal(0), 0) );
        for (var i = 1; i < pol.getSize(); ++i) {
            String tmp = formatRational(Rational.abs(pol.getVal(i)), 1);
            if (!tmp.isEmpty()) {
               if(pol.getVal(i).equals(1) || pol.getVal(i).equals(-1)) {
                   tmp = "";
               }

               if(str.toString().isEmpty()) {
                   str.append(sign(pol.getVal(i), 1)).append(tmp).append(stringVar).append("^").append(i);
                   continue;
               }

               str.append(" ").append(sign(pol.getVal(i), 0)).append(" ").append(tmp).append(stringVar).append("^").append(i);
            }
        }

        if(str.toString().isEmpty()) {
            str.append("0");
        }

        return str.toString();
    }
}
