package Numbers;
//import java.util.*

public interface MyNumber<T>
{	
	void assign(T o);
	void assign(long o);
	
	T 	 plus(T o);
	T	 plus(long o);
	//void add(T o);
	
	T  	 minus(T o);
	T	 minus(long o);
	//void subtract(T o);
	
	T  	 multiply(T o);
	T	 multiply(long o);
	
	T 	 divide(T o);
	T	 divide(long o);

	T	 pow(int _ext);

	T    inverse();
}