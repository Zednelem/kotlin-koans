package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int):Comparable<MyDate>{
    override fun compareTo(other: MyDate): Int {
        return ((year-other.year)*12+month-other.month)*31+dayOfMonth-other.dayOfMonth
    }
}
// Task 29
class RepeatedTimeInterval(val ti: TimeInterval, val n: Int)
operator fun MyDate.rangeTo(other: MyDate): DateRange  = DateRange(this,other)
operator fun MyDate.plus(interval:TimeInterval) = addTimeIntervals(interval, 1)
operator fun MyDate.plus(repeatedTimeInterval: RepeatedTimeInterval) = addTimeIntervals(repeatedTimeInterval.ti,repeatedTimeInterval.n)
operator fun TimeInterval.times( n:Int) = RepeatedTimeInterval(this,n)


enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class DateRange(override val start: MyDate, override val endInclusive: MyDate):ClosedRange<MyDate>,Iterable<MyDate>{
    override fun iterator(): Iterator<MyDate> = DateIterator(this)

    override fun contains(value: MyDate): Boolean {
        return (start< value) && (endInclusive>= value)
    }
}

class DateIterator(val dateRange: DateRange) : Iterator<MyDate> {
    var current: MyDate = dateRange.start
    override fun next(): MyDate {
        val result = current
        current = current.addTimeIntervals(TimeInterval.DAY, 1)
        return result
    }
    override fun hasNext(): Boolean = current <= dateRange.endInclusive
}
