
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Function;

public class Input {
    private static Scanner userInput = new Scanner(System.in);
    private static boolean success = false;

    public static void closeScanner(){
        userInput.close();
    }

    public static Scanner getScanner() {
        return userInput;
    }
    /**
    * Input dengan validasi integer sekalian validator ekstra dengan error message ekstranya.
    *
    * @param  errorMessage pesan yang ditampilkan jika validator mengembalikan false
    * @param  validator masukan fungsi lambda untuk validasi tambahan terhadap input jika input memang integer
    * @return      input integer dari user
    */
    public static int getInt(String errorMessage, Function<Integer,Boolean> validator)
    {
        success = false;
        Integer num = -1;
        while (!success) {
            try {
                num = userInput.nextInt();
                success = validator.apply(num);
                if(!success) {
                    System.out.println(errorMessage);
                }
            } catch (InputMismatchException e) {
                success = false;
                System.out.println(mustIntegerMessage);
                userInput.next();
            } 
        }

        return num;
    }

    public static double getDouble(String errorMessage, Function<Double,Boolean> validator)
    {
        success = false;
        double num = -1;
        while (!success) {
            try {
                num = userInput.nextDouble();
                success = validator.apply(num);
                if(!success) {
                    System.out.println(errorMessage);
                }
            } catch (InputMismatchException e) {
                success = false;
                System.out.println(mustIntegerMessage);
                userInput.next();
            }
        }

        return num;
    }

    public static String getString(String errorMessage, Function<String, Boolean> validator) {
        boolean success = false;
        String ret = "";

        while (!success) {
            ret = userInput.next();
            success = validator.apply(ret);
            if (!success) {
                System.out.println(errorMessage);
                System.out.println("Invalid input. Please try again.");
                userInput.next();
            }
            

            
        }

        return ret;
    }


    /**
    * Input tanpa validasi integer error message
    *
    * @return      input integer dari user
    */
    public static int getInt()
    {
        success = false;
        int res = -1;
        while (!success) {
            try {
                res = userInput.nextInt();
                success = true;
            } catch (InputMismatchException e) {
                success = false;
                System.out.println(mustIntegerMessage);
            } 
        }

        return res;
    }

    /**
    * Input tanpa validasi double error message
    * @return      input double dari user
    */
    public static double getDouble()
    {
        success = false;
        double res = -1;
        while (!success) {
            try {
                res = userInput.nextDouble();
                success = true;
            } catch (InputMismatchException e) {
                success = false;
                System.out.println(mustIntegerMessage);
            } 
        }

        return res;
    }


    private static final String mustIntegerMessage = "Masukan harus bilangan bulat";
}