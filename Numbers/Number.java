package Numbers;
//import java.util.*

public interface MyNumber<T>
{
	void assign(T _number);
	T 	 plus(T _number);
	T 	 minus(T _number);
	T 	 multiply(T _number);
	T 	 divide(T _number);
	//boolean equals()
}