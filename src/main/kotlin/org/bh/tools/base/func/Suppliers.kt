package org.bh.tools.base.func


/*
 * Those which supply
 *
 * @author Ben Leggiero
 * @since 2017-04-02
 */


/**
 * Supplies a generic value on command
 */
typealias Supplier<Output> = () -> Output


/**
 * Supplies a [String] on command
 */
typealias StringSupplier = Supplier<String>
