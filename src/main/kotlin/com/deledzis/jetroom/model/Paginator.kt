package com.deledzis.jetroom.model

import com.deledzis.jetroom.controller.ListPrintPreparerImpl
import com.deledzis.jetroom.util.Consts
import com.deledzis.jetroom.util.localized

object Paginator {
    const val PAGE_SIZE: Int = 20

    private val listPrintPreparer by lazy { ListPrintPreparerImpl() }

    var inPaginationMode: Boolean = false

    var currentPage: Int = 0

    fun enterPaginationMode() {
        inPaginationMode = true
        currentPage = 0
    }

    fun exitPaginationMode() {
        inPaginationMode = false
        currentPage = 0
    }

    fun nextPage(): List<String> {
        return when {
            Repository.getForListing().size > currentPage * PAGE_SIZE + PAGE_SIZE -> {
                currentPage++
                listPrintPreparer.preparePageToPrint()
            }
            currentPage > 0 -> {
                listOf(
                    "${localized(Consts.L_LAST_PAGE)} ${localized(
                        Consts.L_PREV_PAGE
                    )} ${localized(Consts.L_EXIT_MORE_MODE)}"
                )
            }
            else -> {
                listOf(
                    "${localized(Consts.L_ONE_PAGE)} ${localized(
                        Consts.L_EXIT_MORE_MODE
                    )}"
                )
            }
        }
    }

    fun previousPage(): List<String> {
        return when {
            currentPage > 0 -> {
                currentPage--
                listPrintPreparer.preparePageToPrint()
            }
            Repository.getForListing().size > currentPage * PAGE_SIZE + PAGE_SIZE -> {
                listOf(
                    "${localized(Consts.L_FIRST_PAGE)} ${localized(
                        Consts.L_NEXT_PAGE
                    )} ${localized(Consts.L_EXIT_MORE_MODE)}"
                )
            }
            else -> {
                listOf(
                    "${localized(Consts.L_ONE_PAGE)} ${localized(
                        Consts.L_EXIT_MORE_MODE
                    )}"
                )
            }
        }
    }
}