package Model;


import java.io.CharArrayReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by duzhong on 17-3-4.
 */
public final class GenHandler implements Runnable{

    private Generator _generator;
    private Configuration _config;
    private String _srcCode;
    private ITokenizer _tokenizer;
    private List<IResultGetter> _getters;

    private final String HTMLBegin = "<html>\n" +
            "   <head>\n" +
            "       <title>Java Code</title>\n" +
            "   </head>\n" +
            "   <body>\n";

    private final String HTMLEnd = "" +
            "   </body>\n" +
            "</html>\n";

    GenHandler(Generator generator,
               Configuration config,
               String srcCode,
               ITokenizer tokenizer,
               List<IResultGetter> getters) {
        _generator = generator;
        _config = config;
        _srcCode = srcCode;
        _tokenizer = tokenizer;
        _getters = getters;
    }

    @Override
    public void run() {
        StringBuilder sb = new StringBuilder();
        sb.append(HTMLBegin);
        sb.append("<div>\n");

        sb.append(generateCodeHTML());

        sb.append("</div>\n");
        sb.append(HTMLEnd);

        dispatchGetter(sb.toString());
    }

    private String generateCodeHTML() {
        List<Token> tokens = tokenize();

        StringBuilder sb = new StringBuilder();
        for (Token tok : tokens) {
            String[] _syntax = tok.getSyntax();
            String content = escapeString(tok.getContent());
            if (_syntax.length > 0) {
                sb.append("<span class=\"");
                for (String tokStr : tok.getSyntax()) {
                    sb.append("jc-");
                    sb.append(tokStr);
                    sb.append(" ");
                }
                sb.deleteCharAt(sb.length() - 1); // remove the last space
                sb.append("\">");
                sb.append(content);
                sb.append("</span>");
            } else {
                sb.append(content);
            }
        }

        return sb.toString();
    }

    private String escapeString(String code) {
        String result = code.replaceAll("\n", "<br>\n");
        result = result.replaceAll(" ", "&nbsp;");
        return result;
    }

    private List<Token> tokenize() {
        ArrayList<Token> result = new ArrayList<>();
        StringStream ss = new StringStream(_srcCode);

        while(!ss.reachEnd()) {
            Token tok = new Token();
            tok.setSyntax(_tokenizer.tokenize(ss));
            tok.setContent(ss.popString());
            result.add(tok);
        }

        return result;
    }

    class Token {

        private String[] syntax;
        private String content;

        public String[] getSyntax() {
            return syntax;
        }

        public void setSyntax(String[] syntax) {
            this.syntax = syntax;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

    }

    private void dispatchGetter(String destCode) {
        for (IResultGetter getter : _getters) {
            getter.getResult(destCode);
        }
        finish();
    }

    private void finish() {
        _generator.removeHandler(this);
    }

    public Generator get_generator() {
        return _generator;
    }

    public Configuration get_config() {
        return _config;
    }

}
