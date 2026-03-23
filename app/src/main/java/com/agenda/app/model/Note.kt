package com.agenda.app.model

import java.util.UUID

data class Note(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val content: String = "",
    val color: NoteColor = NoteColor.DEFAULT,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

enum class NoteColor(val hexLight: Long, val hexDark: Long) {
    DEFAULT(0xFFFFFFFF, 0xFF2D2D2D),
    RED(0xFFF28B82, 0xFF5C2B29),
    ORANGE(0xFFFBBC04, 0xFF614A19),
    YELLOW(0xFFFFF475, 0xFF635D19),
    GREEN(0xFFCCFF90, 0xFF345920),
    TEAL(0xFFA7FFEB, 0xFF16504B),
    BLUE(0xFFCBF0F8, 0xFF2D555E),
    PURPLE(0xFFD7AEFB, 0xFF42275E),
    PINK(0xFFFDCFE8, 0xFF5B2245)
}
