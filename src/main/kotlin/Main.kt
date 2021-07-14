import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

val list = ArrayList<Date>()

fun main(args: Array<String>) {

    val listTwo = ArrayList<Calendar>()
    val calOnew = removeTime(Calendar.getInstance())
    val calTwo = removeTime(Calendar.getInstance())
    val cal3 = removeTime(Calendar.getInstance())
    val cal4 = removeTime(Calendar.getInstance())
    val cal5 = removeTime(Calendar.getInstance())
    val cal6 = removeTime(Calendar.getInstance())
    val cal7 = removeTime(Calendar.getInstance())

// 25
    cal6[Calendar.HOUR_OF_DAY] = 16
    cal6[Calendar.DAY_OF_MONTH] = 25
    cal7[Calendar.HOUR_OF_DAY] = 17
    cal7[Calendar.DAY_OF_MONTH] = 25
// today
    cal5[Calendar.HOUR_OF_DAY] = 15
    cal3[Calendar.HOUR_OF_DAY] = 19
    calOnew[Calendar.HOUR_OF_DAY] = 20
    calTwo[Calendar.HOUR_OF_DAY] = 21
    cal4[Calendar.HOUR_OF_DAY] = 23




    listTwo.add(cal6)
    listTwo.add(calOnew)
    listTwo.add(calTwo)
    listTwo.add(cal3)
    listTwo.add(cal4)
    listTwo.add(cal5)
    listTwo.add(cal7)
    val listPair = associationCalendar(listTwo)
    for (i in listPair) {
        println(i.first[Calendar.DAY_OF_MONTH])
        println(i.second)
    }

}

fun addCalandar(list: ArrayList<CalendarCen>): ArrayList<CalendarCen> {
    val calOne = if (list.isNotEmpty()) {
        list[list.size - 1].data
    } else {
        Calendar.getInstance()
    }
    for (i in 0..59) {
        val tempCal = calOne.clone() as Calendar
        tempCal.add(Calendar.DAY_OF_MONTH, i)
        list += CalendarCen(data = tempCal)
    }
    return list
}

fun getDateToString(calendar: Calendar): String {
    val df: DateFormat = SimpleDateFormat("EEEE, dd MMMM")
    val date = df.format(calendar.time)
    return Character.toUpperCase(date[0]) + date.substring(1);

}

fun getTimeToDay(): List<Pair<String, Int>> {
    val list: ArrayList<Pair<String, Int>> = ArrayList()
    list.add(Pair("Любое время", 0))
    for (i in 9..22) {
        list.add(Pair("$i:00", i))
    }

    return list
}

fun addDate(date: Date) {
    list.add(date)
}

fun removeDate(date: Date) {
    list.remove(date)
}


fun associationCalendar(inputList: ArrayList<Calendar>): ArrayList<Pair<Calendar, String>> {
    val listAssociationDate = ArrayList<Pair<Calendar, String>>()
    inputList.sort()
    while (inputList.isNotEmpty()) {
        val tempList = ArrayList<Calendar>()
        val iteratorInputList = inputList.iterator()
        while (iteratorInputList.hasNext()) {
            val cal = iteratorInputList.next()
            if (tempList.isEmpty()) {
                tempList.add(cal)
                iteratorInputList.remove();
                continue
            }
            if (cal[Calendar.DAY_OF_MONTH] == tempList[0][Calendar.DAY_OF_MONTH]) {
                tempList.add(cal)
                iteratorInputList.remove();
            }
        }
        var time = ""
        var start = -1
        var end = -1
        var difference = 1
        val iteratorTemp = tempList.iterator()
        while (iteratorTemp.hasNext()) {
            val cal = iteratorTemp.next()
            if (start == -1) {
                start = cal[Calendar.HOUR_OF_DAY]
                time += "$start:00"
                difference = 1
                continue
            }

            if (cal[Calendar.HOUR_OF_DAY] - difference == start) {
                if (iteratorTemp.hasNext()) {
                    difference++
                    end = cal[Calendar.HOUR_OF_DAY]
                    continue
                } else {
                    end = cal[Calendar.HOUR_OF_DAY]
                    time += " - ${end/*[Calendar.HOUR_OF_DAY]*/}:00"
                    continue
                }
            }
            if (difference != 1) {
                time += " - ${end/*[Calendar.HOUR_OF_DAY]*/}:00"
                start = cal[Calendar.HOUR_OF_DAY]
            } else {
                time += ", ${cal[Calendar.HOUR_OF_DAY]}:00"
                start = cal[Calendar.HOUR_OF_DAY]
            }
            if (!iteratorTemp.hasNext()) {
                time += ", $start:00"
            }
        }
        listAssociationDate.add(Pair(removeTime(tempList[0]), time))
    }
    return listAssociationDate
}

fun removeTime(calendar: Calendar): Calendar {
    calendar[Calendar.HOUR_OF_DAY] = 0
    calendar[Calendar.MINUTE] = 0
    calendar[Calendar.SECOND] = 0
    calendar[Calendar.MILLISECOND] = 0
    return calendar
}