package BlueBase

/*
 * A collection of pre-defined functions, like mappers
 *
 * @author Ben Leggiero
 * @since 2018-07-08
 */


typealias Function0<Return> = () -> Return
typealias Function1<Arg1, Return> = (Arg1) -> Return
typealias Function2<Arg1, Arg2, Return> = (Arg1, Arg2) -> Return
typealias Function3<Arg1, Arg2, Arg3, Return> = (Arg1, Arg2, Arg3) -> Return


typealias Mapper<OldElement, NewElement> = Function1<OldElement, NewElement>
typealias FlatMapper<OldElement, NewElement> = Function1<OldElement, NewElement?>
