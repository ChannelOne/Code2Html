package Model;

import java.util.List;

/**
 * Created by duzhong on 17-3-23.
 */
public interface ITokenizer {

    List<String> tokenize(StringStream stream);

}
