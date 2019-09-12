import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

/**
 * @author wwz
 * @date 2019-09-12
 * @descrption:
 */
public class T {
    public static void main(String[] args){
        System.out.println(Errors.class.isAssignableFrom(BindingResult.class));
    }
}