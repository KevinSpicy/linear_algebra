package Matrices;

import Numbers.Rational;

public class MatrixRS extends MatrixR
{
	protected Rational det = new Rational();

	public MatrixRS(int _size)
	{
		super(_size, _size);
		det.assign(0);
	}

	public MatrixRS(MatrixRS o)
	{
		super(o);
		det.assign(o.det);
	}

	public MatrixRS(MatrixR o)
	{
		super(o);
		
		if(o.sizeX != o.sizeY)
		{
			//Exception
		}

		det.assign(0);
	}

	public MatrixRS()
	{
		super();
		det.assign(0);
	}

	public int getSize()
	{
		return sizeX;
	}

	public Rational getDet()
	{
		this.evalRank();

		return det;
	}

	@Override
	protected void evalRank()
	{
		MatrixRS _mat = new MatrixRS(this);
		det.assign(1);

		for(int i = 0; i < _mat.getSize(); ++i)
		{
			int rowInd = 0;
			boolean flag = false;
			int col = _mat.findNonzeroCol(i, i);

			if(col == -1)
			{
				break;
			}
			else if(col != i)
			{
				det = det.multiply(-1);

				for(int j = 0; j < _mat.getSize(); ++j)
				{
					Rational.swap(_mat.at(j, col), _mat.at(j, i));
				}
			}

			for(int j = i; j < _mat.getSize(); ++j)
			{
				if(!_mat.at(j, i).equals(0))
				{
					rowInd = j;
					break;
				}
			}

			if(rowInd != i)
			{
				det = det.multiply(-1);

				for(int j = i; j < _mat.getSize(); ++j)
				{
					Rational.swap(_mat.at(i, j), _mat.at(rowInd, j));
				}
			}

			Rational _r = new Rational(_mat.at(i, i));
			det = det.multiply(_r);

			for(int j = i; j < _mat.getSize(); ++j)
			{
				_mat.set(i, j, _mat.at(i, j).divide(_r));
			}

			for(int j = i+1; j < _mat.getSize(); ++j)
			{
				_r.assign(_mat.at(j, i));

				for(int k = i; k < _mat.getSize(); ++k)
				{
					_mat.set(j, k, _mat.at(j, k).minus( _r.multiply(_mat.at(i, k))));
				}
			}
		}

		rank = 0;

		for(int i = 0; i < _mat.getSize(); ++i)
		{
			det = det.multiply(_mat.at(i, i));
			if(!_mat.at(i, i).equals(0))
			{
				rank++;
			}
		}
	}

	public MatrixRS inverse()
	{
		MatrixRS mat = new MatrixRS(this);
		mat.evalRank();

		if(mat.det.equals(0))
		{
			//Exception
		}

		MatrixRS inv = MatrixRS.getIdentity( mat.getSize() );

		for(int i = 0; i < inv.getSize(); ++i)
		{
			int rowInd = 0;
			boolean flag = false;
			int col = mat.findNonzeroCol(i, i);

			if(col == -1)
			{
				break;
			}
			else if(col != i)
			{
				for(int j = 0; j < mat.getSize(); ++j)
				{
					Rational.swap(mat.at(j, col), mat.at(j, i));
					Rational.swap(inv.at(j, col), inv.at(j, i));
				}
			}

			for(int j = i; j < mat.getSize(); ++j)
			{
				if(!mat.at(j, i).equals(0))
				{
					rowInd = j;
					break;
				}
			}

			if(rowInd != i)
			{
				for(int j = 0; j < mat.getSize(); ++j)
				{
					Rational.swap(mat.at(i, j), mat.at(rowInd, j));
					Rational.swap(inv.at(i, j), inv.at(rowInd, j));
				}
			}

			Rational rat = new Rational( mat.at(i, i) );

			for(int j = 0; j < mat.getSize(); ++j)
			{
				mat.set(i, j, mat.at(i, j).divide(rat) );
				inv.set(i, j, inv.at(i, j).divide(rat) );
			}

			for(int j = i+1; j < mat.getSize(); ++j)
			{
				rat.assign(mat.at(j, i));

				for(int k = 0; k < mat.getSize(); ++k)
				{
					mat.set(j, k, mat.at(j, k).minus( rat.multiply(mat.at(i, k))));
					inv.set(j, k, inv.at(j, k).minus( rat.multiply(inv.at(i, k))));
				}
			}
		}

		for(int j = mat.getSize() - 1; j > 0; --j)
		{
			for(int i = j; i > 0; --i)
			{
				for(int k = 0; k < mat.getSize(); ++k)
				{
					inv.set(i-1, k, inv.at(i-1,k).minus( mat.at(i-1,j).multiply( inv.at(j, k) ) ) );
				}

				mat.set(i,j, 0);
			}
		}

		return inv;
	}

	public static MatrixRS getIdentity(int _size)
	{
		if(_size <= 0)
		{
			// Exception
		}

		MatrixRS identity = new MatrixRS(_size);

		for(int i = 0; i < _size; ++i)
		{
			identity.set(i, i, 1);
		}

		return identity;
	}
}