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
package com.github.lombrozo.jsmith.antlr.view;

import com.github.lombrozo.jsmith.antlr.rules.Rule;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Text output labels.
 * You can label text to format it in a specific way.
 * We don't need to pass labels to child text nodes or parent nodes.
 * They just attached to a particular text node.
 * @since 0.1
 */
public final class Labels {

    /**
     * Rule label.
     */
    public static final String RULE = "$jsmith-rule-label";

    /**
     * Author label.
     */
    public static final String AUTHOR = "$jsmith-author-label";

    /**
     * Additional custom attributes.
     */
    private final Map<String, String> properties;


    /**
     * Default constructor.
     */
    public Labels(final Rule author) {
        this(author.name());
    }

    public Labels(final String author) {
        this(Collections.singletonMap(Labels.AUTHOR, author));
    }

    /**
     * Constructor.
     * @param additional Additional attributes.
     */
    private Labels(final Map<String, String> additional) {
        this.properties = new HashMap<>(additional);
    }

    public String author() {
        return this.properties.get(Labels.AUTHOR);
    }

    public Optional<String> rule() {
        return Optional.ofNullable(this.properties.get(Labels.RULE));
    }

    public Labels rule(final String rule) {
        this.properties.put(Labels.RULE, rule);
        return new Labels(this.properties);
    }

    /**
     * Check if contains a label.
     * @param key Label key.
     * @return True if contains.
     */
    public boolean contains(final String key) {
        return this.properties.containsKey(key);
    }
}
