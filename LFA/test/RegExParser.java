/* 
 A parser to be constructed each time
 a regular expression needs to be parsed.
 */
class RegExParser {
    
    private String input;
    
    public RegExParser(String input) {
        this.input = input;
    }

    public RegEx parse() {
        RegEx r = regex();
        return r;
    }

    /* Recursive descent parsing internals. */
    private char peek() {
        return input.charAt(0);
    }

    private void eat(char c) {
        if (peek() == c)this.input = this.input.substring(1) ;
        else throw new RuntimeException("Expected: " + c + "; got: " + peek()) ;
    }

    private char next() {
        char c = peek() ;
        eat(c) ;
        return c ;
    }

    private boolean more() {
        return input.length() > 0 ;
    }
    
    
    /* Regular expression term types. */
    private RegEx regex() {
        RegEx term = term();
        if(more() && peek() == '|'){
            eat('|');
            RegEx regex = regex();
            return new Choice(term,regex);
        }else{
            return term;
        }
    }

    private RegEx term() {
        RegEx factor = new Blank();
        
        while(more() && peek() != ')' && peek() != '|'){
            RegEx nextFactor = factor();
            factor = new Sequence(factor, nextFactor);
        }
        return factor;
    }

    private RegEx factor() {
        RegEx base = base();
        
        while(more() && peek() == '*'){
            eat('*');
            base = new Repetition(base);
        }
        return base;
    }

    private RegEx base() {
        switch(peek()){
            case '(':
                eat('(');
                RegEx r = regex();
                eat(')');
                return r;
            default:
                return new Primitive(next());
        }
    }
    
    public static void main(String[] args){
        RegExParser r = new RegExParser("(a|b)(a|b)");
        r.parse();
    }
}
