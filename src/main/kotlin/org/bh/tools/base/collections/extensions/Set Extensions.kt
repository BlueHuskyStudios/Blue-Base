package BlueBase

/**
 * @author Ben Leggiero
 * @since 2017-10-23
 */


@Suppress("NOTHING_TO_INLINE")
inline fun <Element> MutableSet<Element>.insert(newElement: Element) = this.add(newElement)

// TODO: Test
@Suppress("NOTHING_TO_INLINE")
inline fun <reified Element> Set<Element>.inserting(newElement: Element): Set<Element> =
        setOf(*this.toTypedArray(), newElement)
