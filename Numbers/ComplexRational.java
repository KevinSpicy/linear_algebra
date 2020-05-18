package Numbers;

public class ComplexRational implements MyNumber<ComplexRational>
{
	private Rational re;
	private Rational im;

	public ComplexRational()
	{
		re = new Rational(0);
		im = new Rational(0);
	}

	public ComplexRational(Rational _r1, Rational _r2)
	{
		re = new Rational(_r1);
		im = new Rational(_r2);
	}	

	public ComplexRational(ComplexRational o)
	{
		re = new Rational(o.re);
		im = new Rational(o.im);
	}

	public ComplexRational(Rational o)
	{
		re = new Rational(o);
		im = new Rational(0);
	}

	public ComplexRational(long o)
	{
		re = new Rational(o);
		im = new Rational(0);
	}

	public ComplexRational(long o1, long o2)
	{
		re = new Rational(o1);
		im = new Rational(o2);
	}

	@Override
	public void assign(ComplexRational o)
	{
		re.assign(o.re);
		im.assign(o.im);
	}

	@Override
	public void assign(long o)
	{
		re.assign(o);
		im.assign(0);
	}

	public Rational getRe()
	{
		return re;
	}

	public Rational getIm()
	{
		return im;
	}

	@Override
	public ComplexRational plus(ComplexRational o)
	{
		return new ComplexRational(re.plus(o.re), im.plus(o.im));
	}

	@Override
	public ComplexRational plus(long o)
	{
		return new ComplexRational(re.plus(o), im);
	}

	@Override
	public ComplexRational minus(ComplexRational o)
	{
		return new ComplexRational(re.minus(o.re), im.minus(o.im));
	}

	@Override
	public ComplexRational minus(long o)
	{
		return new ComplexRational(re.minus(o), im);
	}

	@Override
	public ComplexRational multiply(ComplexRational o)
	{
		Rational tmp1 = re.multiply(o.re);
		Rational tmp2 = im.multiply(o.im);
		Rational tmp3 = re.multiply(o.im);
		Rational tmp4 = im.multiply(o.re);

		return new ComplexRational(tmp1.minus(tmp2), tmp3.plus(tmp4));
	}

	@Override
	public ComplexRational multiply(long o)
	{
		return new ComplexRational(re.multiply(o), im.multiply(o));
	}

	@Override
	public ComplexRational divide(ComplexRational o)
	{
		ComplexRational tmp = o.inverse();

		return this.multiply(tmp);
	}

	@Override
	public ComplexRational divide(long o)
	{
		if(o == 0)
		{
			; // Error
		}

		return new ComplexRational(re.divide(o), im.divide(o));
	}


	@Override
	public ComplexRational pow(int _ext)
	{
		ComplexRational tmp = new ComplexRational(1);

		if(_ext > 0)
		{
			for(int i = 0; i < _ext; ++i)
			{
				tmp = tmp.multiply(this);
			}

			return tmp;
		}

		if(_ext < 0)
		{
			for(int i = 0; i < -_ext; ++i)
			{
				tmp = tmp.divide(this);
			}

			return tmp;
		}

		if(this.equals(0))
		{
			;// Error;
		}

		return new ComplexRational(1);
	}


	public String toString()
	{
		return re.sign*re.num + "/" + re.dom + " " + im.sign*im.num + "/" + im.dom + " i";
	}

	public boolean equals(ComplexRational o)
	{
		return re.equals(o.re) && im.equals(o.im);
	}

	public boolean equals(long o)
	{
		return im.equals(0) && re.equals(o);
	}

	@Override
	public ComplexRational inverse()
	{
		ComplexRational conj = this.conjugate();
		Rational re_2 = conj.re.multiply(conj.re);
		Rational im_2 = conj.im.multiply(conj.im);
		Rational _abs = re_2.plus(im_2); 

		return new ComplexRational(conj.re.divide(_abs), conj.im.divide(_abs));
	}

	public ComplexRational conjugate()
	{
		return new ComplexRational(re, im.multiply(-1));
	}
} 