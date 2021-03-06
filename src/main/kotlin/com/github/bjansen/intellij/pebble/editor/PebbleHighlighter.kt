package com.github.bjansen.intellij.pebble.editor

import com.github.bjansen.intellij.pebble.psi.PebbleParserDefinition.Companion.tokens
import com.github.bjansen.intellij.pebble.psi.createLexer
import com.github.bjansen.pebble.parser.PebbleLexer
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.openapi.project.Project
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import java.util.*

class PebbleHighlighter(val project: Project? = null) : SyntaxHighlighterBase() {

    override fun getHighlightingLexer(): Lexer {
        return createLexer(null, project)
    }

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        val attributes: TextAttributesKey?

        if (delimiters.contains(tokenType)) {
            attributes = DELIMITER
        } else if (keywords.contains(tokenType)) {
            attributes = KEYWORDS
        } else if (braces.contains(tokenType)) {
            attributes = BRACES
        } else if (brackets.contains(tokenType)) {
            attributes = BRACKETS
        } else if (operators.contains(tokenType)) {
            attributes = OPERATORS
        } else if (parens.contains(tokenType)) {
            attributes = PARENTHESES
        } else if (strings.contains(tokenType)) {
            attributes = STRINGS
        } else if (tokenType == tokens[PebbleLexer.COMMENT]) {
            attributes = COMMENT
        } else if (tokenType == tokens[PebbleLexer.ID_NAME]) {
            attributes = IDENTIFIER
        } else if (tokenType == tokens[PebbleLexer.NUMERIC]) {
            attributes = NUMBER
        } else if (tokenType == TokenType.BAD_CHARACTER) {
            attributes = BAD_CHARACTER
        } else {
            attributes = null
        }
        return pack(BACKGROUND, attributes)
    }

    companion object Highlights {
        val BACKGROUND = createTextAttributesKey("PEBBLE_BACKGROUND",
                DefaultLanguageHighlighterColors.TEMPLATE_LANGUAGE_COLOR)

        val BAD_CHARACTER = createTextAttributesKey("PEBBLE_BAD_CHARACTER",
                HighlighterColors.BAD_CHARACTER)

        val BRACES = createTextAttributesKey("PEBBLE_BRACES",
                DefaultLanguageHighlighterColors.BRACES)

        val BRACKETS = createTextAttributesKey("PEBBLE_BRACKETS",
                DefaultLanguageHighlighterColors.BRACKETS)

        val COMMENT = createTextAttributesKey("PEBBLE_COMMENT",
                DefaultLanguageHighlighterColors.DOC_COMMENT)

        val DELIMITER = createTextAttributesKey("PEBBLE_DELIMITER",
                DefaultLanguageHighlighterColors.INSTANCE_FIELD)

        val IDENTIFIER = createTextAttributesKey("PEBBLE_IDENTIFIER",
                DefaultLanguageHighlighterColors.IDENTIFIER)

        val KEYWORDS = createTextAttributesKey("PEBBLE_KEYWORD",
                DefaultLanguageHighlighterColors.KEYWORD)

        val NUMBER = createTextAttributesKey("PEBBLE_NUMBER",
                DefaultLanguageHighlighterColors.NUMBER)

        val OPERATORS = createTextAttributesKey("PEBBLE_OPERATOR",
                DefaultLanguageHighlighterColors.OPERATION_SIGN)

        val PARENTHESES = createTextAttributesKey("PEBBLE_PARENTHESIS",
                DefaultLanguageHighlighterColors.PARENTHESES)

        val STRINGS = createTextAttributesKey("PEBBLE_STRING",
                DefaultLanguageHighlighterColors.STRING)
    }

    // Built-in reserved words that can't be used as legal identifiers, and thus should
    // always be highlighted as keywords.
    private val keywords = Arrays.asList(
            // Unary operators listed in com.mitchellbosecke.pebble.extension.core.CoreExtension
            tokens[PebbleLexer.NOT],

            // Binary operators listed in com.mitchellbosecke.pebble.extension.core.CoreExtension
            tokens[PebbleLexer.OR], tokens[PebbleLexer.AND], tokens[PebbleLexer.IS],
            tokens[PebbleLexer.CONTAINS], tokens[PebbleLexer.EQUALS],

            // Reserved words listed in com.mitchellbosecke.pebble.parser.ExpressionParser
            // "none" can be a legal Java identifier so we do not list it here
            tokens[PebbleLexer.TRUE], tokens[PebbleLexer.FALSE], tokens[PebbleLexer.NULL]
    )

    private val delimiters = Arrays.asList(
            tokens[PebbleLexer.TAG_OPEN], tokens[PebbleLexer.TAG_CLOSE],
            tokens[PebbleLexer.PRINT_OPEN], tokens[PebbleLexer.PRINT_CLOSE],
            tokens[PebbleLexer.INTERPOLATED_STRING_START], tokens[PebbleLexer.INTERPOLATED_STRING_STOP]
    )

    private val braces = Arrays.asList(
            tokens[PebbleLexer.LBRACE], tokens[PebbleLexer.RBRACE]
    )

    private val brackets = Arrays.asList(
            tokens[PebbleLexer.LBRACKET], tokens[PebbleLexer.RBRACKET]
    )

    private val parens = Arrays.asList(
            tokens[PebbleLexer.LPAREN], tokens[PebbleLexer.RPAREN]
    )

    private val operators = Arrays.asList(
            tokens[PebbleLexer.OP_PLUS], tokens[PebbleLexer.OP_MINUS],
            tokens[PebbleLexer.OP_MULT], tokens[PebbleLexer.OP_DIV],
            tokens[PebbleLexer.OP_MOD], tokens[PebbleLexer.OP_PIPE]
    )

    private val strings = Arrays.asList(
            tokens[PebbleLexer.STRING_START], tokens[PebbleLexer.STRING_END],
            tokens[PebbleLexer.TEXT], tokens[PebbleLexer.SINGLE_QUOTED_STRING]
    )
}
