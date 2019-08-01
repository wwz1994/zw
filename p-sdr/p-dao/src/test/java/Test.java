/**
 * Created by wwz on 2018-11-23.
 */
public class Test {

    public static void main(String [] args){
        StringBuilder builder = new StringBuilder();
        builder.append("1\n");
        builder.append("12/asd.23123");
        System.out.print(builder.toString().substring(builder.length()-1,builder.length()));
    }
}