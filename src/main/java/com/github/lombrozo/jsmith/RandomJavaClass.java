/*
 * MIT License
 *
 * Copyright (c) 2023-2024 Volodya Lombrozo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.lombrozo.jsmith;

import com.diffplug.spotless.maven.generic.Format;
import com.diffplug.spotless.maven.java.GoogleJavaFormat;
import com.diffplug.spotless.maven.java.Java;
import com.github.lombrozo.jsmith.antlr.view.Text;
import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import org.cactoos.io.ResourceOf;

/**
 * Random Java class.
 * @since 0.1
 */
public final class RandomJavaClass {

    /**
     * ANTLR parser grammar for Java.
     */
    private final String parser;

    /**
     * ANTLR lexer grammar for Java.
     */
    private final String lexer;

    /**
     * Start rule.
     */
    private final String rule;

    /**
     * Default constructor.
     */
    public RandomJavaClass() {
        this(
            "grammars/Java8ReducedParser.g4",
            "grammars/Java8ReducedLexer.g4",
            "compilationUnit"
        );
    }

    /**
     * Constructor.
     * @param parser Parser.
     * @param lexer Lexer.
     * @param rule Rule.
     */
    public RandomJavaClass(
        final String parser,
        final String lexer,
        final String rule
    ) {
        this.parser = parser;
        this.lexer = lexer;
        this.rule = rule;
    }

    /**
     * Source code of the class.
     * @return Source code of the class.
     */
    public String src() {
        final Text text = new RandomScript(
            new ResourceOf(this.parser),
            new ResourceOf(this.lexer)
        ).generate(this.rule);
        final String output = text.output();
        try {
            return new Formatter().formatSource(output);
        } catch (final FormatterException exception) {
            throw new IllegalStateException(
                String.format("Failed to format source code %n%s%n", output), exception);
        }
    }
}
