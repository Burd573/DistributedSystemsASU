import java.io.*;

/**
 * Purpose: demonstrate simple Java Fraction class with command line,
 * jdb debugging, and Ant build file.
 * <p>
 * Ser321 Foundations of Distributed Applications
 * see http://pooh.poly.asu.edu/Ser321
 *
 * @author Tim Lindquist Tim.Lindquist@asu.edu
 * Software Engineering, CIDSE, IAFSE, ASU Poly
 * @version January 2020
 */
public class Fraction {

    private int numerator, denominator;
    private Fraction frac;

    public Fraction() {
        numerator = denominator = 0;
    }

    public void print() {
        System.out.print(numerator + "/" + denominator);
    }

    public void setNumerator(int n) {
        numerator = n;
    }

    public void setDenominator(int d) {
        denominator = d;
    }

    public int getDenominator() {
        return denominator;
    }

    public int getNumerator() {
        return numerator;
    }

    public static void main(String args[]) {
        if (args.length == 2) {
            int num = 0;
            int den = 1;
            try {
                num = Integer.parseInt(args[0]);
                den = Integer.parseInt(args[1]);
                if (den == 0)
                    throw new ArithmeticException();
                Fraction frac = new Fraction();

                frac.setNumerator(num);
                frac.setDenominator(den);

                System.out.print("The fraction is: ");
                frac.print();
                System.out.println("");
            } catch (ArithmeticException e) {
                System.out.println("Denominator cannot bee zero");
                System.exit(1);
            } catch (Exception e) {
                System.out.println("Arguments: " + args[0] + ", " + args[1] + " must be integers.");
                System.exit(1);
            }
        }
    }
}

