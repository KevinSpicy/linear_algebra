import EigenSolvers.EigenSolver;
import MatrixExceptions.SizeOfMatrixException;
import Numbers.ComplexRational;
import Numbers.Rational;
import Matrices.MatrixR;
import Matrices.MatrixRS;
import Solvers.Solver;
import Decorator.SolutionDecorator;
import Decorator.SLEDecorator;
import Decorator.PolynomDecorator;
import Polynoms.Polynom;


class LinearAlgebra {
    static public void main(String[] args) {
       try {
            MatrixR nr = new MatrixR(3, 4);

            for (int i = 0; i < nr.getSizeY(); ++i) {
                for (int j = 0; j < nr.getSizeX(); ++j) {
                    nr.set(i, j, new Rational(i + j, 1));
                }
            }

            System.out.println(nr.getRank());
            nr.watch();
            System.out.println();

            var sol = Solver.createSolver(Solver.SolverId.GAUSS);
            var rp = new MatrixR(3, 1);

            rp.set(0, 0, 0);
            rp.set(1, 0, new Rational(2, 2));
            rp.set(2, 0, new Rational(2));

            Solver.SolutionId id = sol.solve(nr, rp);

            MatrixR ans = null;
            SolutionDecorator sd = null;

            switch (id) {
                case SINGLE:
                    sd = new SLEDecorator(sol);
                    System.out.println(sd.toReadableString());
                    break;
                case INFINITE:
                    sd = new SLEDecorator(sol);
                    System.out.println(sd.toReadableString());
                    break;
                case NONE:
                    System.out.println("No solve");
                    break;
            }

            System.out.println();
        } catch (ArithmeticException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            System.out.println(e);
        }  catch (SizeOfMatrixException e) {
            System.out.println(e);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }
}