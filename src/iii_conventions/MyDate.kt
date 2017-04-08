package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int):Comparable<MyDate>{
    override fun compareTo(other: MyDate): Int {
        return ((year-other.year)*12+month-other.month)*31+dayOfMonth-other.dayOfMonth
    }


}

operator fun MyDate.rangeTo(other: MyDate): DateRange  = DateRange(this,other)
operator fun MyDate.plus(interval:TimeInterval){
    addTimeIntervals(interval, 1)
}

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class DateRange(override val start: MyDate, override val endInclusive: MyDate):ClosedRange<MyDate>,Iterable<MyDate>{
    override fun iterator(): Iterator<MyDate> = DateIterator(this)

    override fun contains(d: MyDate): Boolean {
        return (start<d) && (endInclusive>=d)
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
