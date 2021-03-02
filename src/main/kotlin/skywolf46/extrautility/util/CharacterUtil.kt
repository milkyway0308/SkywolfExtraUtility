package skywolf46.extrautility.util

private val WHITESPACE = listOf(' ')


fun String.isDigit(): Boolean =
        this.isNotEmpty() && this.all { it.isDigit() }

fun String.isKorean() =
        this.isNotEmpty() && this.all { it.isKorean() }

fun String.isEnglish() =
        this.isNotEmpty() && this.all { it.isEnglish() }

fun String.isSpecial() =
        this.isNotEmpty() && this.all { it.isSpecial() }


fun String.containsDigit(): Boolean =
        this.isNotEmpty() && this.any { it.isDigit() }

fun String.containsKorean() =
        this.isNotEmpty() && this.any { it.isKorean() }

fun String.containsEnglish() =
        this.isNotEmpty() && this.any { it.isEnglish() }

fun String.containsSpecial() =
        this.isNotEmpty() && this.any { it.isSpecial() }


fun Char.isDigit() = this in '0'..'9'

fun Char.isKorean() = this in '가'..'힣'

fun Char.isEnglish() = this in 'a'..'z'

fun Char.isSpecial() = this.isDigit().or(this.isKorean()).or(this.isEnglish()).not()


