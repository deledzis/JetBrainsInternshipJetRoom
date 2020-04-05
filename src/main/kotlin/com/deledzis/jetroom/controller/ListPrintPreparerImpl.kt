package com.deledzis.jetroom.controller

import com.deledzis.jetroom.model.Paginator
import com.deledzis.jetroom.model.Repository
import com.deledzis.jetroom.util.Consts
import com.deledzis.jetroom.util.localized
import kotlin.math.ceil

class ListPrintPreparerImpl : IListPrintPreparer {
    override fun prepareListToPrint(): List<String> {
        return if (Repository.getForListing().isEmpty()) {
            listOf(localized(Consts.L_EMPTY_LIST))
        } else {
            Repository.getForListing().mapIndexed { index, todoItem -> "${index + 1}. $todoItem" }
        }
    }

    override fun preparePageToPrint(): List<String> {
        val output = mutableListOf<String>()

        if (Repository.getForListing().isEmpty()) {
            output.add(localized(Consts.L_EMPTY_LIST))
            Paginator.exitPaginationMode()
            return output
        }

        val page = Paginator.currentPage
        val items = Repository.getForListing()

        if (page * Paginator.PAGE_SIZE >= items.size) {
            output.add("${localized(Consts.L_LAST_PAGE)} ${localized(
                Consts.L_PREV_PAGE
            )} ${localized(Consts.L_EXIT_MORE_MODE)}")
            return output
        }

        output.add(
            "${localized(
                Consts.L_PAGE,
                page + 1,
                maxOf(1, ceil((items.size.toDouble() / Paginator.PAGE_SIZE)).toInt())
            )}:"
        )

        output.addAll(
            items.subList(
                page * Paginator.PAGE_SIZE,
                minOf(page * Paginator.PAGE_SIZE + Paginator.PAGE_SIZE, items.size)
            ).mapIndexed { index, todoItem -> "${page * Paginator.PAGE_SIZE + (index + 1)}. $todoItem" }
        )

        if (items.size > page * Paginator.PAGE_SIZE + Paginator.PAGE_SIZE) {
            output.add(
                "${localized(
                    Consts.L_PAGE,
                    page + 1,
                    maxOf(1, ceil((items.size.toDouble() / Paginator.PAGE_SIZE)).toInt())
                )}. " + localized(
                    Consts.L_TASKS_LEFT,
                    items.size - (page * Paginator.PAGE_SIZE + Paginator.PAGE_SIZE)
                )
            )
        }

        output.add(
            "${localized(Consts.L_NEXT_PAGE)} ${localized(
                Consts.L_PREV_PAGE
            )} ${localized(Consts.L_EXIT_MORE_MODE)} ${localized(
                Consts.L_SHOW_HELP
            )}"
        )

        return output
    }
}