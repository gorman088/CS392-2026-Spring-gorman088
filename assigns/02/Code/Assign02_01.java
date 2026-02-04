public class Assign02_01 {
    /*
      HX-2025-02-13: 10 points
      The 'int' type in Java is for integers of some fixed precision.
      More precisely, each integer of the type int is represented as a sequence of bits
      of some fixed length. Please write a program that finds this length. Your program
      is expected return the correct answer instantly. Note that you can only use arithmetic
      operations here. In particular, NO BIT-WISE OPERATIONS ARE ALLOWED.
     */
    public void main(String[] argv) {
	// Please give your implementation here
        int value = 1;
        int bits = 0;

        // Keep doubling until overflow makes the value negative
        while (value > 0) {
            value = value * 2;
            bits++;
        }

        System.out.println(bits);
    }
}
