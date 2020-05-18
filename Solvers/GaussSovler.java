package Solvers;

import Numbers.Rational;

public class GaussSolver extends Solver
{
	public GaussSolver()
	{
		solveVector  = null;
		kernelMatrix = null;
		permVars	 = null;
		cnt_vars	 = 0; 
	}

	@Override
	public void solve(MatrixR mat, MatrixR rightPart)
	{
		MatrixR m  = new MatrixR(mat);
		MatrixR rp = new MatrixR(rightPart);

		cntVars  = m.getSizeX();
		permVars = new int[cntVars + 1];

		for(int i = 0; i < cntVars; ++i)
		{
			permVars[i] = i;
		}  

		for(int i = 0; i < m.getSizeY(); ++i)
		{
			int 	rowInd = 0;
			boolean flag   = false;
			int 	col    = m.finNonzeroCol(i, i);

			if(col == -1)
			{
				break;
			}
			else if(col != i)
			{
				int tmp = permVars[i];
				permVars[i]   = permVars[col];
				permVars[col] = tmp;

				for(int j = 0; j < _mat.getSizeY(); ++j)
				{
					Rational.swap(m.at(j, col), m.at(j, i));
				}
			}

			
		}
	}
}