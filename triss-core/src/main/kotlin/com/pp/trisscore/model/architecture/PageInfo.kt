package com.pp.trisscore.model.architecture

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

/**
 *
 * author: Marceli Jerzyński
 * e-mail: marceli.jerzynski@student.put.poznan.pl
 * Date: 11.10.2020
 **/
data class PageInfo<T> (
     val filter: T,
     val desc:Boolean,
     val orderBy: String,
     val pageSize: Int,
     val pageNumber: Long
){
    fun getSort(): Sort {
        if(desc)
            return Sort.by(this.orderBy).descending()
        return Sort.by(this.orderBy)
    }
}