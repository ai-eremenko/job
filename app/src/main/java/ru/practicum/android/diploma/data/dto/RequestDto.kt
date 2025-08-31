package ru.practicum.android.diploma.data.dto

sealed interface RequestDto {
    data object AreasRequest : RequestDto
    data object IndustriesRequest : RequestDto
    //добавить еще запросы
}
