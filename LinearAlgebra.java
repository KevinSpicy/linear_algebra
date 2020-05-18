import Numbers.*;
import Matrices.*;

class LinearAlgebra
{
	static public void main(String[] args)
	{
		/*
		Rational x = new Rational(2, 3);
		Rational y = new Rational(4,-3);

		Rational z = x.divide(y);
		z = z.pow(-6);

		System.out.println(z.getNum() + "/" + z.getDom());
		*/
		/*
		Matrix<Rational> mr = new Matrix<Rational>(2,2);

		ComplexRational x = new ComplexRational(2,-3);
		ComplexRational y = new ComplexRational(-5,0);

		ComplexRational z = x.conjugate();

		System.out.println(z.getRe().getNum() + "/" + z.getRe().getDom() + " + " + z.getIm().getNum() + "/" + z.getIm().getDom() + " i");
		System.out.println(y.equals(-5));
		*/

		MatrixR mr = new MatrixR(3,3);
		MatrixRS nr = new MatrixRS(3);

		for(int i = 0; i < mr.getSizeY(); ++i)
		{
			for(int j = 0; j < mr.getSizeX(); ++j)
			{
				mr.set(i,j, new Rational(2*j-i + 1*j*i, i+1));
			}
		}

		for(int i = 0; i < nr.getSizeY(); ++i)
		{
			for(int j = 0; j < nr.getSizeX(); ++j)
			{
				nr.set(i,j, new Rational(j+4*i + 3, i+1));
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

		nr.set(1,1,-4);
		MatrixRS mrs = new MatrixRS(nr);

		//nr.watch();
		System.out.println(mrs.getDet());
		mrs.watch();
		System.out.println();
		mrs.inverse().multiply(mrs).watch();

		()->System.out.println("asd");
	}
}