package Solvers;
import Matrices.*

public enum SolverId
{
	GAUSS
}

public enum SolutionID
{
	SINGLE, INFINITE, NONE
}

public abstract class Solver
{
	protected MatrixR solveVector;
	protected MatrixR kernelMatrix;
	protected int[]   permVars;
	protected int     cntVars;

	public MatrixR getSolveVector()
	{
		return solveVector;
	}

	public MatrixR getKernelMatrix()
	{
		return kernelMatrix;
	}

	public int getCntVars()
	{
		return cntVars;
	}

	public void getPermVars(int[] buff)
	{
		buff = new int[cntVars + 1];

		for(int i = 0; i < cntVars + 1; ++i)
		{
			buff[i] = permVars[i];
		}
	}

	public abstract void solve(MatrixR mat, MatrixR rightPart);

	static Solver createSolver(SolverId id)
	{
		switch(id)
		{
			case GAUSS:
				return null;
			default:
				return null;
		}
	}
} 