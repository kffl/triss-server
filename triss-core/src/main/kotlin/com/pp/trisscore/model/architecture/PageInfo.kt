package com.pp.trisscore.model.architecture

/**
 *
 * author: Marceli Jerzyński
 * e-mail: marceli.jerzynski@student.put.poznan.pl
 * Date: 11.10.2020
 **/
data class PageInfo<T> (
     val filter: T,
     val orderBy: String,
     val pageSize: Int,
     val pageNumber: Int
)