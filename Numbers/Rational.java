package Numbers;
//import java.util.*

public class Rational implements MyNumber<Rational>
{
	private int sign;
	private long num;
	private long dom;

	public Rational()
	{
		num = 0;
		dom = 1;
		sign = 1;
	}

	public Rational(long _num, long _dom)
	{
		if(_dom == 0)
		{
			;
		}

		sign = 1;
		if((_num < 0 && _dom > 0) || (_num > 0 && _dom < 0))
		{
			sign = -1;
		}

		num = (_num < 0) ? -_num : _num;
		dom = (_dom < 0) ? -_dom : _dom;

		long tmp = GCD.gcd(num, dom);

		num /= tmp;
		dom /= tmp;
	}

	public Rational(long o)
	{
		assign(o);
	}

	public Rational(Rational o)
	{
		assign(o);
	}

	@Override
	public void assign(Rational o)
	{
		num  = o.num;
		dom  = o.dom;
		sign = o.sign; 
	}

	@Override
	public void assign(long o)
	{
		num  = (o < 0) ? -o : o;
		dom  = 1;
		sign = (o < 0) ? -1 : 1; 
	}

	public long getNum()
	{
		return num*sign;
	}

	public long getDom()
	{
		return dom;
	}

	@Override
	public Rational plus(Rational o)
	{
		return new Rational(sign*num*o.dom + o.sign*o.num*dom, dom*o.dom);
	}

	@Override
	public Rational plus(long o)
	{
		return new Rational(sign*num + o*dom, dom);
	}

	@Override
	public Rational minus(Rational o)
	{
		return new Rational(sign*num*o.dom - o.sign*o.num*dom, dom*o.dom);
	}

	@Override
	public Rational minus(long o)
	{
		return new Rational(sign*num - o*dom, dom);
	}	

	@Override
	public Rational multiply(Rational o)
	{
		return new Rational(sign*o.sign*num*o.num, dom*o.dom);
	}

	@Override
	public Rational multiply(long o)
	{
		return new Rational(sign*num*o, dom);
	}

	@Override
	public Rational divide(Rational o)
	{
		return new Rational(sign*o.sign*num*o.dom, dom*o.num);
	}

	@Override
	public Rational divide(long o)
	{
		return new Rational(sign*num, dom*o);
	}

	@Override
	public Rational pow(int _ext)
	{
		Rational tmp = new Rational(1);

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

		return new Rational(1);
	}

	@Override
	public Rational inverse()
	{
		Rational tmp = new Rational(1);

		return tmp.divide(this);
	}

	public String toString()
	{
		return sign*num + "/" + dom;
	}

	public boolean equals(Rational o)
	{
		return (num == o.num && dom == o.dom && sign == o.sign);
	}

	public boolean equals(long o)
	{
		return (sign*num == o && dom == 1);
	}

	public boolean lt(Rational o)
	{
		Rational tmp = this.minus(o);

		return (tmp.sign == -1);
	}

	public boolean le(Rational o)
	{
		return this.lt(o) || this.equals(o);
	}

	public boolean gt(Rational o)
	{
		return !this.le(o);
	}

	public boolean ge(Rational o)
	{
		return !this.lt(o);
	}

	////
	public static Rational Abs(Rational o)
	{
		return (o.sign == -1) ? new Rational(-o.num, o.dom) : new Rational(o.num, o.dom);
	}

	public static void swap(Rational o1, Rational o2)
	{
		Rational tmp = new Rational(o1);
		o1.assign(o2);
		o2.assign(tmp);
	}

	public static Rational max(Rational o1, Rational o2)
	{
		return o1.lt(o2) ? o2 : o1;
	}

	public static Rational min(Rational o1, Rational o2)
	{
		return o1.lt(o2) ? o1 : o2;
	}
}

class GCD
{
	static long gcd(long a, long b)
	{
		if(a%b == 0)
		{
			return b;
		}

		return gcd(b, a%b);
	}
}