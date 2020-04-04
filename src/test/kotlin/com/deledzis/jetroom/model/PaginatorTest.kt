package com.deledzis.jetroom.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PaginatorTest {
    @BeforeEach
    fun init() {
        Paginator.currentPage = 0
        Paginator.inPaginationMode = false
    }

    @Test
    fun getInPaginationMode() {
        assertEquals(false, Paginator.inPaginationMode)
    }

    @Test
    fun setInPaginationMode() {
        assertEquals(false, Paginator.inPaginationMode)
        Paginator.inPaginationMode = true
        assertEquals(true, Paginator.inPaginationMode)
    }

    @Test
    fun getCurrentPage() {
        assertEquals(0, Paginator.currentPage)
    }

    @Test
    fun setCurrentPage() {
        assertEquals(0, Paginator.currentPage)
        Paginator.currentPage += 4
        assertEquals(4, Paginator.currentPage)
    }
}