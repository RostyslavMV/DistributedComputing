import dc.algo.CannonMatrix;
import dc.algo.FoxMatrix;
import dc.algo.SimpleMatrix;
import dc.algo.StringMatrix;

public class Application {
    public static void main(String[] args) {
        int[] sizes = {100, 1000, 5000};

        for (int matrixSize : sizes) {
            SimpleMatrix.calculate(args, matrixSize);
            StringMatrix.calculate(args, matrixSize);
            FoxMatrix.calculate(args, matrixSize);
            CannonMatrix.calculate(args, matrixSize);
        }
    }
}