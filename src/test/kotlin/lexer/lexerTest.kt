package lexer

import Jay_lexer
import org.antlr.v4.runtime.CharStreams
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

// assertEquals(Jay_lexer.,tokens[].type)
class LexerTest {
    private fun tokenize(input: String) = Jay_lexer(CharStreams.fromString(input)).allTokens
    private fun getTokenName(type: Int): String = Jay_lexer.VOCABULARY.getDisplayName(type)

    @Test
    fun `test keywords`(){ // Passed
        val tokens = tokenize("""
            if else while for -> >> bundle return
        """.trimIndent())

        try{
            assertEquals(8, tokens.size)
            assertEquals(Jay_lexer.IF,tokens[0].type)
            assertEquals(Jay_lexer.ELSE,tokens[1].type)
            assertEquals(Jay_lexer.WHILE,tokens[2].type)
            assertEquals(Jay_lexer.FOR,tokens[3].type)
            assertEquals(Jay_lexer.CONVERT,tokens[4].type)
            assertEquals(Jay_lexer.PROCEDURE,tokens[5].type)
            assertEquals(Jay_lexer.BUNDLE,tokens[6].type)
            assertEquals(Jay_lexer.RETURN, tokens[7].type)
        } catch(e: AssertionError){
            println("Error in test keywords:")
            tokens.forEachIndexed { index, token ->
                println("$index: ${getTokenName(token.type)} '${token.text}'")
            }
            throw e}
    }

    @Test
    fun `test boolean operator + normal operator`() { // Passed
        val tokens = tokenize("""
            == = > < >= <= != ! and & or | xor || - + * / 
        """.trimIndent())

        try{
            assertEquals(18, tokens.size)
            assertEquals(Jay_lexer.EQUAL,tokens[0].type)
            assertEquals(Jay_lexer.ASSIGN,tokens[1].type)
            assertEquals(Jay_lexer.GREATER,tokens[2].type)
            assertEquals(Jay_lexer.LESSER,tokens[3].type)
            assertEquals(Jay_lexer.GREATEREQ,tokens[4].type)
            assertEquals(Jay_lexer.LESSEREQ,tokens[5].type)
            assertEquals(Jay_lexer.NOTEQ,tokens[6].type)
            assertEquals(Jay_lexer.NOT,tokens[7].type)
            assertEquals(Jay_lexer.AND,tokens[8].type)
            assertEquals(Jay_lexer.AND,tokens[9].type)
            assertEquals(Jay_lexer.OR,tokens[10].type)
            assertEquals(Jay_lexer.OR,tokens[11].type)
            assertEquals(Jay_lexer.XOR,tokens[12].type)
            assertEquals(Jay_lexer.XOR,tokens[13].type)
            assertEquals(Jay_lexer.OPERATOR,tokens[14].type)
            assertEquals(Jay_lexer.OPERATOR,tokens[15].type)
            assertEquals(Jay_lexer.OPERATOR,tokens[16].type)
            assertEquals(Jay_lexer.OPERATOR,tokens[17].type)
        } catch(e: AssertionError){
            println("Error in test boolean operator + normal operator:")
            tokens.forEachIndexed { index, token ->
                println("$index: ${getTokenName(token.type)} '${token.text}'")
            }
            throw e}
    }
    //Write one for Numbers
    @Test
    fun `test number`(){ // Passed
        val tokens = tokenize("""
            12312 -10923 0.1 -1.2 .2 0xa3e -10E20 -2.3E-10 10.3E-2
        """.trimIndent())

        try{
            assertEquals(9, tokens.size)
            assertEquals(Jay_lexer.NUMBER,tokens[0].type)
            assertEquals(Jay_lexer.NUMBER,tokens[1].type)
            assertEquals(Jay_lexer.NUMBER,tokens[2].type)
            assertEquals(Jay_lexer.NUMBER,tokens[3].type)
            assertEquals(Jay_lexer.NUMBER,tokens[4].type)
            assertEquals(Jay_lexer.NUMBER,tokens[5].type)
            assertEquals(Jay_lexer.NUMBER,tokens[6].type)
            assertEquals(Jay_lexer.NUMBER,tokens[7].type)
            assertEquals(Jay_lexer.NUMBER, tokens[8].type)
        } catch(e: AssertionError){
            println("Error in test number:")
            tokens.forEachIndexed { index, token ->
                println("$index: ${getTokenName(token.type)} '${token.text}'")
            }
            throw e}
    }
    @Test
    fun `test string`(){ //Passed
        val tokens = tokenize("""
            "Hello how are \"you\" my friend?" "blah"
        """.trimIndent())


        try{
            assertEquals(2,tokens.size)
            assertEquals(Jay_lexer.STRING,tokens[0].type)
            assertEquals(Jay_lexer.STRING,tokens[1].type)
        } catch(e: AssertionError){
            println("Error in test string:")
            tokens.forEachIndexed { index, token ->
                println("$index: ${getTokenName(token.type)} '${token.text}'")
            }
            throw e}
    }
    @Test
    fun `test delimiters`(){ //Passed
        val tokens = tokenize("""
            ) ( () } {{} ][ , ; :
        """.trimIndent())

        try{
            assertEquals(13,tokens.size)
            assertEquals(Jay_lexer.RPAREN,tokens[0].type)
            assertEquals(Jay_lexer.LPAREN,tokens[1].type)
            assertEquals(Jay_lexer.LPAREN,tokens[2].type)
            assertEquals(Jay_lexer.RPAREN,tokens[3].type)
            assertEquals(Jay_lexer.RBRACE,tokens[4].type)
            assertEquals(Jay_lexer.LBRACE,tokens[5].type)
            assertEquals(Jay_lexer.LBRACE,tokens[6].type)
            assertEquals(Jay_lexer.RBRACE,tokens[7].type)
            assertEquals(Jay_lexer.RSQAURE,tokens[8].type)
            assertEquals(Jay_lexer.LSQUARE,tokens[9].type)
            assertEquals(Jay_lexer.COMMA,tokens[10].type)
            assertEquals(Jay_lexer.SEMICOLON,tokens[11].type)
            assertEquals(Jay_lexer.COLON,tokens[12].type)
        } catch(e: AssertionError){
            println("Error in test delimiters:")
            tokens.forEachIndexed { index, token ->
                println("$index: ${getTokenName(token.type)} '${token.text}'")
            }
            throw e}

    }
    @Test
    fun `test comment`() { //Passed
        val tokens = tokenize("""
            // Hello there
            /*
            Hello * 
            @tag: Value
            */
            *
        """.trimIndent())

        try{
            assertEquals(6,tokens.size)
            assertEquals(Jay_lexer.LINE_COMMENT,tokens[0].type)
            assertEquals(Jay_lexer.COMMENT_START,tokens[1].type)
            assertEquals(Jay_lexer.TAG_NAME,tokens[2].type)
            assertEquals(Jay_lexer.TAG_VALUE,tokens[3].type)
            assertEquals(Jay_lexer.COMMENT_END,tokens[4].type)
            assertEquals(Jay_lexer.OPERATOR,tokens[5].type)
        }
        catch(e: AssertionError){
            println("Error in test comment:")
            tokens.forEachIndexed { index, token ->
                println("$index: ${getTokenName(token.type)} '${token.text}'")
            }
            throw e
        }
    }
    @Test
    fun `test id`(){ //Passed
        val tokens = tokenize("""
            x = 10
            y = 12
            if (x>y){
                x+y
            } else {
                x-y
            }
        """.trimIndent())
        try{ //23
            assertEquals(23,tokens.size)
            assertEquals(Jay_lexer.ID,tokens[0].type)
            assertEquals(Jay_lexer.ASSIGN,tokens[1].type)
            assertEquals(Jay_lexer.NUMBER,tokens[2].type)
            assertEquals(Jay_lexer.ID,tokens[3].type)
            assertEquals(Jay_lexer.ASSIGN,tokens[4].type)
            assertEquals(Jay_lexer.NUMBER,tokens[5].type)
            assertEquals(Jay_lexer.IF,tokens[6].type)
            assertEquals(Jay_lexer.LPAREN,tokens[7].type)
            assertEquals(Jay_lexer.ID,tokens[8].type)
            assertEquals(Jay_lexer.GREATER,tokens[9].type)
            assertEquals(Jay_lexer.ID,tokens[10].type)
            assertEquals(Jay_lexer.RPAREN,tokens[11].type)
            assertEquals(Jay_lexer.LBRACE,tokens[12].type)
            assertEquals(Jay_lexer.ID,tokens[13].type)
            assertEquals(Jay_lexer.OPERATOR,tokens[14].type)
            assertEquals(Jay_lexer.ID,tokens[15].type)
            assertEquals(Jay_lexer.RBRACE,tokens[16].type)
            assertEquals(Jay_lexer.ELSE,tokens[17].type)
            assertEquals(Jay_lexer.LBRACE,tokens[18].type)
            assertEquals(Jay_lexer.ID,tokens[19].type)
            assertEquals(Jay_lexer.OPERATOR,tokens[20].type)
            assertEquals(Jay_lexer.ID,tokens[21].type)
            assertEquals(Jay_lexer.RBRACE,tokens[22].type)
        }
        catch (e:AssertionError){
            println("Error in test id:")
            tokens.forEachIndexed { index, token ->
                println("$index: ${getTokenName(token.type)} '${token.text}'")
            }
            throw e
        }
    }
    @Test
    fun `test types`()
    {
        val tokens = tokenize("""
            Int
            String
            Bool
            Float
            bundle
        """.trimIndent())

        try{
            assertEquals(5,tokens.size)
            assertEquals(Jay_lexer.TYPE_INT, tokens[0].type)
            assertEquals(Jay_lexer.TYPE_STRING, tokens[1].type)
            assertEquals(Jay_lexer.TYPE_BOOL, tokens[2].type)
            assertEquals(Jay_lexer.TYPE_FLOAT, tokens[3].type)
            assertEquals(Jay_lexer.BUNDLE, tokens[4].type)
        }
        catch (e: AssertionError){
            println("Error in test types:")
            tokens.forEachIndexed { index, token ->
                println("$index: ${getTokenName(token.type)} '${token.text}'")
            }
            throw e
        }
    }
}