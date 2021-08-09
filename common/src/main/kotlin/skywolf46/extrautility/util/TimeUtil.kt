package skywolf46.extrautility.util


fun Int.toTimeString(): String {
    var second = this
    var minutes = second / 60
    second -= minutes * 60
    var hours = minutes / 60
    minutes -= hours * 60
    var days = hours / 24
    hours -= days * 24
    val builder = StringBuilder()
    if (days != 0)
        builder.append("${days}시간 ")
    if (hours != 0)
        builder.append("${hours}시간 ")
    if (minutes != 0)
        builder.append("${minutes}분 ")
    builder.append("${second}초")
    return builder.toString()
}