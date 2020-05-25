import MatrixExceptions.SizeOfMatrixException;
import Numbers.*;
import Matrices.*;
import Solvers.*;

class LinearAlgebra {
    static public void main(String[] args) {
        try {
            MatrixR mr = new MatrixR(3, 3);
            MatrixRS nr = new MatrixRS(3);

            for (int i = 0; i < mr.getSizeY(); ++i) {
                for (int j = 0; j < mr.getSizeX(); ++j) {
                    mr.set(i, j, new Rational(i + j, 1));
                }
            }

            for (int i = 0; i < nr.getSizeY(); ++i) {
                for (int j = 0; j < nr.getSizeX(); ++j) {
                    nr.set(i, j, new Rational(i + j, 3));
                }
            }
        /*
		nr.set(0,0, 5);
		nr.set(0,1, 2);
		nr.set(0,2, 0);
		nr.set(1,0, 1);
		nr.set(1,1, 2);
		nr.set(1,2, 1);
		nr.set(2,0, 0);
		nr.set(2,1, 1);
		nr.set(2,2, 2);
        */

            var mrs = new MatrixRS(nr);
            //nr.watch();
            System.out.println(mrs.getRank());
            mrs.watch();
            System.out.println();
            //mrs.inverse().multiply(mrs).watch();

            var sol = Solver.createSolver(Solver.SolverId.GAUSS);
            var rp = new MatrixR(3, 1);

            rp.set(0, 0, 2);
            rp.set(1, 0, new Rational(2, 3));
            rp.set(2, 0, new Rational(-1));
            rp.clear();
            Solver.SolutionId id = sol.solve(nr, rp);

            MatrixR ans = null;
            if (id == Solver.SolutionId.NONE) {
                System.out.println("No solve");
            } else {
                ans = new MatrixR(sol.getSolveVector());
            }

            var Ker = sol.getKernelMatrix();

            for (var i = 0; i < Ker.getSizeY(); ++i) {
                for (var j = 0; j < Ker.getSizeX(); ++j) {
                    System.out.print(Ker.at(i, j));
                }
                System.out.println();
            }
            System.out.println();
            for (int i = 0; i < ans.getSizeY(); ++i)
                System.out.println(ans.at(i, 0));

        } catch (ArithmeticException | ArrayIndexOutOfBoundsException e) {
            System.out.println(e);
        }  catch (SizeOfMatrixException e) {
            System.out.println(e);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}