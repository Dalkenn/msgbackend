package fr.dalkenn.msgbackend.controller

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class SearchMessageTypeEnumConverter : Converter<String?, SearchMessageType?> {
    override fun convert(value: String): SearchMessageType {
        return SearchMessageType.valueOf(value.uppercase())
    }
}
