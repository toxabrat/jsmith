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
package com.github.lombrozo.jsmith.antlr;

import com.github.lombrozo.jsmith.antlr.representation.ProductionsChain;
import com.github.lombrozo.jsmith.antlr.rules.ParserRuleSpec;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Unparser that contains all parser rules.
 * It generates a string representation of the parser rule.
 * @since 0.1
 * @todo #1:90min Unify Recursion Detection.
 *  We use a stack to detect recursion in the {@link Unparser} class.
 *  And we use a chain of productions to detect recursion in the
 *  {@link com.github.lombrozo.jsmith.antlr.ANTLRListener}.
 *  Maybe we should unify these two approaches.
 */
public final class Unparser {

    /**
     * All the parser rules.
     */
    private final Map<String, ParserRuleSpec> rules;

    private final AtomicInteger stack;

    /**
     * Default constructor.
     */
    public Unparser() {
        this(new HashMap<>(0));
    }

    /**
     * Constructor.
     * @param all All the parser rules.
     */
    private Unparser(final Map<String, ParserRuleSpec> all) {
        this.rules = all;
        this.stack = new AtomicInteger(0);
    }

    /**
     * Add a parser rule.
     * @param rule Parser rule.
     * @return This unparser.
     */
    public Unparser with(final ParserRuleSpec rule) {
        this.rules.put(rule.name(), rule);
        return this;
    }

    /**
     * Generate a string representation of the parser rule.
     * @param rule Rule.
     * @return String representation of the parser rule.
     */
    public String generate(final String rule, final Context context) {
        if (this.stack.incrementAndGet() > 500) {
            throw new RecursionException(
                String.format(
                    "Recursion detected in rule: %n%s%n",
                    new ProductionsChain(this.rules.get(rule)).tree()
                )
            );
        }
        final String generate = this.apply(rule, context);
        this.stack.decrementAndGet();
        return generate;
    }

    /**
     * Apply the rule and produce a random string corresponding to the rule.
     * @param rule Rule.
     * @return Code string corresponding to the rule.
     */
    private String apply(final String rule, final Context context) {
        if (!this.rules.containsKey(rule)) {
            throw new IllegalStateException(
                String.format("Rule not found: %s. All available rules: [%s]", rule, this.rules)
            );
        }
        return this.rules.get(rule).generate(context);
    }

}
